package com.example.jungandroid.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.jungandroid.R
import com.example.jungandroid.retrofit.RetrofitManager
import com.example.jungandroid.utills.Constants.TAG
import com.example.jungandroid.utills.RESPONSE_STATUS
import com.example.jungandroid.utills.SEARCH_TYPE
import com.example.jungandroid.utills.onMyTextChanged
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.PHOTO
    private lateinit var searchBtn: Button
    private lateinit var searchTermLayout: TextInputLayout
    private lateinit var searchTermRadioGroup: RadioGroup
    private lateinit var searchTermText: EditText
    private lateinit var frameSearchBtn: FrameLayout
    private lateinit var mainScrollView: ScrollView
    private lateinit var progressBtn: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBtn = findViewById(R.id.search_btn)
        searchTermLayout = findViewById(R.id.search_term_text_layout)
        searchTermRadioGroup = findViewById(R.id.search_term_radio_group)
        searchTermText = findViewById(R.id.search_term_edit_text)
        frameSearchBtn = findViewById(R.id.frame_search_btn)
        mainScrollView = findViewById(R.id.main_scroll_view)
        progressBtn = findViewById(R.id.progress_btn)




        Log.d(TAG, "MainActivity - onCreate() called")

//        라디오 그룹 가져오기


        searchTermRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            //swich문
            when (checkedId) {
                R.id.photo_search_radio_btn -> {
                    Log.d(TAG, "사진검색 버튼 클릭!")
                    searchTermLayout.let {
                        it.hint = "사진검색"
                        it.startIconDrawable = resources.getDrawable(
                            R.drawable.ic_photo_library_black,
                            resources.newTheme()
                        )
                        currentSearchType = SEARCH_TYPE.PHOTO
                    }
                }

                R.id.user_search_radio_btn -> {
                    Log.d(TAG, "사용자검색 버튼 클릭")
                    searchTermLayout.let {
                        it.hint = "사용자 검색"
                        it.startIconDrawable = resources.getDrawable(
                            R.drawable.ic_person_black,
                            resources.newTheme()
                        )
                        currentSearchType = SEARCH_TYPE.USER
                    }
                }
            }
            Log.d(
                TAG,
                "MainActivity - onCheckedChanged() called / searchType : ${currentSearchType}"
            )
        }

        /*
        익스텐션을 하지 않은 경우
        searchTermText.addTextChangedListener (object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }
        })
        */

        //익스텐션 사용, 텍스트 변경 되었을 떄, 자동적으로 afterTextChanged 호출됨

        searchTermText.onMyTextChanged {
            //입력된 글자가 1개라도 있으면 검색 버튼 보여줌
            if (it.toString().count() > 0) {
                frameSearchBtn.visibility = View.VISIBLE
                mainScrollView.scrollTo(0, 200)
                searchTermLayout.helperText = " "
            } else {
                frameSearchBtn.visibility = View.INVISIBLE
                searchTermLayout.helperText = "검색어를 입력해주세요."
            }
            if (it.toString().count() == 12) {
                Log.d(TAG, "MainActivity - 에러 띄우기")
                Toast.makeText(this, "검색어는 12자 까지만 입력 가능합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        //검색 버튼 클릭시
        searchBtn.setOnClickListener {
            Log.d(TAG, "MainActivity - 검색 버튼이 클릭 되었다.")
            this.handleSearchButtonUi()
            val userSearchInput = searchTermText.text.toString()

            RetrofitManager.instance.searchPhotos(
                searchTerm = userSearchInput,
                completion = { responseState, responseDataArrayList -> // 함수만 전달함
                    when (responseState) {
                        RESPONSE_STATUS.OKAY -> {
                            Log.d(TAG, "api 호출 성공 : ${responseDataArrayList?.size}")

                            val intent = Intent(this, PhotoCollectionActivity::class.java)
                            val bundle = Bundle()
                            bundle.putSerializable("photo_array_list", responseDataArrayList)
                            intent.putExtra("array_bundle", bundle)
                            intent.putExtra("search_term", userSearchInput)
                            startActivity(intent)
                        }
                        RESPONSE_STATUS.FAIL -> {
                            Log.d(TAG, "api 호출 실패 : $responseDataArrayList")
                            Toast.makeText(
                                this,
                                "api 호출 에러 : $responseDataArrayList",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        RESPONSE_STATUS.NO_CONTENT -> {
                            Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    progressBtn.visibility = View.INVISIBLE
                    searchBtn.text = "검색"
                    searchTermText.setText("")
                })
        }

    }

    private fun handleSearchButtonUi() {
        progressBtn.visibility = View.VISIBLE
        searchBtn.text = " "
//        Handler(Looper.getMainLooper()).postDelayed(
//            {
//                progressBtn.visibility = View.INVISIBLE
//                searchBtn.text = "검색"
//            }, 1500
//        )
    }
}