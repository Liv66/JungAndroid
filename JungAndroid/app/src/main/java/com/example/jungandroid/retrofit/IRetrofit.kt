package com.example.jungandroid.retrofit

import com.example.jungandroid.utills.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//인터페이스 작명은 접두사 I를 붙인다.
interface IRetrofit {

    // https://www.unsplash.com(baseUrl)/search/photos?query="@searchTerm"
    @GET(API.SEARCH_PHOTOS)
    fun searchPhotos(@Query("query") searchTerm: String): Call<JsonElement>

    @GET(API.SEARCH_USERS)
    fun searchUsers(@Query("query") searchTerm: String): Call<JsonElement>

}