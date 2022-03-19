package com.example.jungandroid.utills

import android.content.Context
import android.util.Log
import com.example.jungandroid.App
import com.example.jungandroid.model.SearchData
import com.example.jungandroid.utills.Constants.TAG
import com.google.gson.Gson

object SharedPrefManger {
    private const val SHARED_SEARCH_HISTORY = "shared_search_history"
    private const val KEY_SEARCH_HISTORY = "key_search_history"

    private const val SHARED_SEARCH_HISTORY_MODE = "shared_search_history_mode"
    private const val KEY_SEARCH_HISTORY_MODE = "key_search_history_mode"

    // 객체 배열 -> 문자열
    // 검색 목록 저장
    fun storeSearchHistoryList(searchHistoryList: MutableList<SearchData>) {
        Log.d(TAG, "SharedPrefManger - storeSearchHistoryList() called")

        // 매개변수로 온 배열 -> 문자열로 변환
        val searchHistoryListString: String = Gson().toJson(searchHistoryList)
        Log.d(TAG, "SharedPrefManger - storeSearchHistoryListString : $searchHistoryListString")

        //쉐어드 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        //쉐어드 에디터 가져오기
        val editor = shared.edit()

        //매번 덮어쓰기 식으로 데이터가 저장된다.
        editor.putString(KEY_SEARCH_HISTORY, searchHistoryListString)

        editor.apply()
    }

    // 검색 목록 가져오기
    fun getSearchHistoryList(): MutableList<SearchData> {
        //쉐어드 가져오기
        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        val storedSearchHistoryListString =
            shared.getString(KEY_SEARCH_HISTORY, "")!! // 데이터가 없으면 ""반환

        var storedSearchHistoryList = ArrayList<SearchData>()

        // 검색 목록에 값이 있다면
        if (storedSearchHistoryListString.isNotEmpty()) {

            // 저장된 문자열을 -> 객체 배열로변경
            storedSearchHistoryList =
                Gson()
                    .fromJson(storedSearchHistoryListString, Array<SearchData>::class.java)
                    .toMutableList() as ArrayList<SearchData>
        }
        return storedSearchHistoryList
    }

    //검색어 저장 모드 설정하기
    fun setSearchHistoryMode(isActivated: Boolean) {
        Log.d(TAG, "SharedPrefManger - setSearchHistoryMode() called")

        //쉐어드 가져오기
        val shared =
            App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY_MODE, Context.MODE_PRIVATE)

        //쉐어드 에디터 가져오기
        val editor = shared.edit()

        //매번 덮어쓰기 식으로 데이터가 저장된다.
        editor.putBoolean(KEY_SEARCH_HISTORY_MODE, isActivated)

        editor.apply()
    }

    fun checkSearchHistoryMode(): Boolean {
        val shared =
            App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY_MODE, Context.MODE_PRIVATE)

        return shared.getBoolean(KEY_SEARCH_HISTORY_MODE, false)
    }

    // 검색목록 지우기
    fun clearSearchHistoryList() {
        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)
        //쉐어드 에디터 가져오기
        val editor = shared.edit()
        // 해당 데이터 지우기
        editor.clear()
        // 변경 사항 적용
        editor.apply()
    }
}