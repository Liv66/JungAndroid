package com.example.jungandroid


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
import androidx.recyclerview.widget.RecyclerView
import com.example.jungandroid.model.Photo
import com.example.jungandroid.recyclerview.PhotoGridRecyclerViewAdapter
import com.example.jungandroid.utills.Constants.TAG
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial

class PhotoCollectionActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    //데이터
    private var photoList = ArrayList<Photo>()

    //어뎁터
    private lateinit var photoGridRecyclerViewAdapter: PhotoGridRecyclerViewAdapter

    private lateinit var myRecyclerView: RecyclerView
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var mySearchView: SearchView
    private lateinit var mySearchViewEditText: EditText
    private lateinit var linearSeaerchLayout: LinearLayout
    private lateinit var searchHistoryButton: Button
    private lateinit var searchHistorySwitch: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_collection)


        val bundle = intent.getBundleExtra("array_bundle")

        val searchTerm = intent.getStringExtra("search_term")

        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>
        topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        linearSeaerchLayout = findViewById(R.id.linear_search_history_view)
        searchHistoryButton = findViewById(R.id.search_history_button)
        searchHistorySwitch = findViewById(R.id.search_history_mode_switch)

        Log.d(
            TAG,
            "PhotoCollectionActivity - onCreate() called / searchTerm : $searchTerm, photoList.count() : ${photoList.count()}"
        )

        searchHistorySwitch.setOnCheckedChangeListener(this)
        searchHistoryButton.setOnClickListener(this)
        topAppBar.title = searchTerm
        setSupportActionBar(topAppBar)

        this.photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()
        this.photoGridRecyclerViewAdapter.submitList(photoList)

        myRecyclerView = findViewById<RecyclerView>(R.id.my_photo_recycler_view)
        myRecyclerView.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        myRecyclerView.adapter = photoGridRecyclerViewAdapter

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
            this.topAppBar.title = null

            //TODO : api 호출
            //TODO : 검색어 저장
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
        when(switch){
            searchHistorySwitch -> {
                if(isChecked){
                    Log.d(TAG, "검색어 저장 기능 on")
                } else {
                    Log.d(TAG, "검색어 저장 기능 off")
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when(view){
            searchHistoryButton -> {
                Log.d(TAG, "검색 기록 삭제 버튼이 클릭 되었따.")
            }
        }
    }
}