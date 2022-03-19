package com.example.jungandroid.activities


import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jungandroid.R
import com.example.jungandroid.model.Photo
import com.example.jungandroid.model.SearchData
import com.example.jungandroid.recyclerview.ISearchHistoryRecyclerView
import com.example.jungandroid.recyclerview.SearchHistoryRecyclerViewAdapter
import com.example.jungandroid.recyclerview.PhotoGridRecyclerViewAdapter
import com.example.jungandroid.retrofit.RetrofitManager
import com.example.jungandroid.utills.Constants.TAG
import com.example.jungandroid.utills.RESPONSE_STATUS
import com.example.jungandroid.utills.SharedPrefManger
import com.example.jungandroid.utills.toSimpleString
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial
import java.util.*
import kotlin.collections.ArrayList

class PhotoCollectionActivity : AppCompatActivity(),
    SearchView.OnQueryTextListener,
    CompoundButton.OnCheckedChangeListener,
    View.OnClickListener,
    ISearchHistoryRecyclerView {

    //데이터
    private var photoList = ArrayList<Photo>()

    //어뎁터
    private lateinit var photoGridRecyclerViewAdapter: PhotoGridRecyclerViewAdapter

    private lateinit var myRecyclerView: RecyclerView
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var mySearchView: SearchView
    private lateinit var mySearchViewEditText: EditText
    private lateinit var linearSeaerchLayout: LinearLayout
    private lateinit var clearHistoryBtn: Button
    private lateinit var searchHistorySwitch: SwitchMaterial
    private lateinit var searchHistoryLabel: TextView

    private var searchHistoryList = ArrayList<SearchData>()
    private lateinit var searchHistoryRecyclerView: RecyclerView
    private lateinit var mySearchHistoryRecyclerViewAdapter: SearchHistoryRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_collection)

        val bundle = intent.getBundleExtra("array_bundle")

        val searchTerm = intent.getStringExtra("search_term")

        topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        linearSeaerchLayout = findViewById(R.id.linear_search_history_view)
        searchHistoryRecyclerView = findViewById(R.id.search_history_recycler_view)
        clearHistoryBtn = findViewById(R.id.clear_history_button)
        searchHistorySwitch = findViewById(R.id.search_history_mode_switch)
        searchHistoryLabel = findViewById(R.id.search_history_recycler_view_label)


        Log.d(
            TAG,
            "PhotoCollectionActivity - onCreate() called / searchTerm : $searchTerm, photoList.count() : ${photoList.count()}"
        )


        searchHistorySwitch.setOnCheckedChangeListener(this)
        clearHistoryBtn.setOnClickListener(this)

        searchHistorySwitch.isChecked = SharedPrefManger.checkSearchHistoryMode()
        topAppBar.title = searchTerm
        setSupportActionBar(topAppBar)

        // 포토 리사이클러뷰 세팅
        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>
        this.photoCollectionRecyclerViewSetting(photoList)

        // 기존에 저장된 리스트를 불러옴
        this.searchHistoryList = SharedPrefManger.getSearchHistoryList() as ArrayList<SearchData>
        this.searchHistoryList.forEach {
            Log.d(TAG, "저장된 검색 기록 : ${it.term}, ${it.timestamp}")
        }

        handleSearchViewUi()
        searchHistoryRecyclerViewSetting(this.searchHistoryList)


        if (searchTerm!!.isNotEmpty()) {
            val term = searchTerm?.let {
                it
            } ?: ""
            insertSearchTermHistory(term)
        }
    }

    private fun photoCollectionRecyclerViewSetting(photoList: ArrayList<Photo>) {

        // reverseLayout을 true로 하면 최근 검색어가 위로 옴
        this.photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()
        this.photoGridRecyclerViewAdapter.submitList(photoList)

        myRecyclerView = findViewById<RecyclerView>(R.id.my_photo_recycler_view)
        myRecyclerView.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        myRecyclerView.adapter = photoGridRecyclerViewAdapter
    }

    private fun searchHistoryRecyclerViewSetting(searchHistoryList: ArrayList<SearchData>) {
        Log.d(TAG, "PhotoCollectionActivity - searchHistoryRecyclerViewSetting() called")

        // 리스너를 넣어준다
        this.mySearchHistoryRecyclerViewAdapter =
            SearchHistoryRecyclerViewAdapter(searchHistoryList, this)

        // reverseLayout을 true로 하면 최근 검색어가 위로 옴
        val myLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        myLinearLayoutManager.stackFromEnd = true

        searchHistoryRecyclerView.apply {
            layoutManager = myLinearLayoutManager
            this.scrollToPosition(mySearchHistoryRecyclerViewAdapter.itemCount - 1)
            adapter = mySearchHistoryRecyclerViewAdapter
        }
    }

    //앱바 작동하게 설정해줌
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "PhotoCollectionActivity - onCreateOptionsMenu() called")

        val inflater = menuInflater
        inflater.inflate(R.menu.top_app_bar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        this.mySearchView = menu?.findItem(R.id.search_menu_item)?.actionView as SearchView
        this.mySearchView.apply {
            this.queryHint = "검색어를 입력해주세요"

            this.setOnQueryTextListener(this@PhotoCollectionActivity)

            this.setOnQueryTextFocusChangeListener { _, hasExpaned ->
                when (hasExpaned) {
                    true -> {
                        Log.d(TAG, "PhotoCollectionActivity - 서치뷰 열림")
                        linearSeaerchLayout.visibility = View.VISIBLE
                        handleSearchViewUi()
                    }
                    false -> {
                        Log.d(TAG, "PhotoCollectionActivity - 서치뷰 닫힘")
                        linearSeaerchLayout.visibility = View.INVISIBLE
                    }
                }
            }

            //서치뷰에서 에딧 텍스트를 가져온다
            mySearchViewEditText = this.findViewById(androidx.appcompat.R.id.search_src_text)
        }

        this.mySearchViewEditText.apply {
            this.filters = arrayOf(InputFilter.LengthFilter(12))
            this.setTextColor(Color.WHITE)
            this.setHintTextColor(Color.WHITE)
        }

        return super.onCreateOptionsMenu(menu)
    }


    //서치뷰 검색 입력 이벤트
    //검색버튼이 클릭되었을 때
    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d(TAG, "PhotoCollectionActivity - onQueryTextSubmit() called / query : $query")

        if (!query.isNullOrEmpty()) {
            this.topAppBar.title = query

            //TODO : api 호출
            //TODO : 검색어 저장

            this.insertSearchTermHistory(query)
            this.searchPhotoApiCall(query)
        }
        this.mySearchView.setQuery("", false)
        this.mySearchView.clearFocus()
        this.topAppBar.collapseActionView()

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "PhotoCollectionActivity - onQueryTextChange() called / newText : $newText")

        val userInputText = newText ?: ""

        if (userInputText.count() == 12) {
            Toast.makeText(this, "검색어는 12자까지 가능합니다", Toast.LENGTH_SHORT).show()
        }

        return true
    }

    //스위치 버튼 클릭 감지
    override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
        when (switch) {
            searchHistorySwitch -> {
                if (isChecked) {
                    Log.d(TAG, "검색어 저장 기능 on")
                    SharedPrefManger.setSearchHistoryMode(isActivated = true)
                } else {
                    Log.d(TAG, "검색어 저장 기능 off")
                    SharedPrefManger.setSearchHistoryMode(isActivated = false)
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            clearHistoryBtn -> {
                Log.d(TAG, "검색 기록 삭제 버튼이 클릭 되었따.")
                SharedPrefManger.clearSearchHistoryList()
                this.searchHistoryList.clear()
                //ui처리
                handleSearchViewUi()
            }
        }
    }

    //검색 아이템삭제 버튼 이벤트
    override fun onSearchItemDeleteClicked(position: Int) {

        //TODO:: 해당 번째의 녀석을 삭제
        // 다시 저장
        Log.d(TAG, "PhotoCollectionActivity - 포지션 : $position")
        //해당 요소 삭제
        this.searchHistoryList.removeAt(position)
        //데이터 덮어쓰기
        SharedPrefManger.storeSearchHistoryList(this.searchHistoryList)
        //데이터 변경 됬다고 알려줌
        this.mySearchHistoryRecyclerViewAdapter.notifyDataSetChanged()
        handleSearchViewUi()
    }

    // 검색 아이템 버튼 이벤트
    override fun onSearchItemClicked(position: Int) {
        val queryString = this.searchHistoryList[position].term

        searchPhotoApiCall(queryString)

        topAppBar.title = queryString

        this.insertSearchTermHistory(searchTerm = queryString)
        this.topAppBar.collapseActionView()
    }

    // 사진 검색 api 호출
    private fun searchPhotoApiCall(query: String) {
        RetrofitManager.instance.searchPhotos(searchTerm = query, completion = { status, list ->
            when (status) {
                RESPONSE_STATUS.OKAY -> {
                    Log.d(
                        TAG,
                        "PhotoCollectionActivity - searchPhotoApiCall() / 응답 성공 list : ${list?.size}"
                    )
                    if (list != null) {
                        this.photoList.clear()
                        this.photoList = list
                        this.photoGridRecyclerViewAdapter.submitList(this.photoList)
                        this.photoGridRecyclerViewAdapter.notifyDataSetChanged()
                    }
                }
                RESPONSE_STATUS.NO_CONTENT -> {
                    Toast.makeText(this, "$query 에 대한 검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun handleSearchViewUi() {
        Log.d(
            TAG,
            "PhotoCollectionActivity - handelSearchViewUi() called / size : ${this.searchHistoryList.size}"
        )
        if (this.searchHistoryList.size > 0) {
            searchHistoryRecyclerView.visibility = View.VISIBLE
            searchHistoryLabel.visibility = View.VISIBLE
            clearHistoryBtn.visibility = View.VISIBLE
        } else {
            searchHistoryRecyclerView.visibility = View.INVISIBLE
            searchHistoryLabel.visibility = View.INVISIBLE
            clearHistoryBtn.visibility = View.INVISIBLE
        }
    }

    //검색어 저장
    private fun insertSearchTermHistory(searchTerm: String) {
        if (SharedPrefManger.checkSearchHistoryMode()) {
            //중복 아이템 삭제
            val indexListToRemove = ArrayList<Int>()

            this.searchHistoryList.forEachIndexed { index, searchDataItem ->
                if (searchDataItem.term == searchTerm) {
                    indexListToRemove.add(index)
                }
            }
            indexListToRemove.forEach {
                this.searchHistoryList.removeAt(it)
            }

            // 새 아이템 넣기
            val newSearchData = SearchData(term = searchTerm, timestamp = Date().toSimpleString())
            this.searchHistoryList.add(newSearchData)

            SharedPrefManger.storeSearchHistoryList(this.searchHistoryList)
            this.mySearchHistoryRecyclerViewAdapter.notifyDataSetChanged()
        }
    }
}