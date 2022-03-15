package com.example.jungandroid.retrofit

import android.util.Log
import com.example.jungandroid.utills.Constants.TAG
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // 레트로핏 클라이언트 선언
    private lateinit var retrofitClient: Retrofit

    //레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit?{
        Log.d(TAG, "RetrofitClient - getClient() called")

        //레트로핏 빌더 패턴
        if(retrofitClient == null){
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient
    }
}