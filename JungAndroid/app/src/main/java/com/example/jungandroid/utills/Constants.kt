package com.example.jungandroid.utills

object Constants {
    const val TAG: String = "로그"
}

enum class SEARCH_TYPE{
    PHOTO,
    USER
}

enum class RESPONSE_STATE{
    OKAY,
    FAIL
}

object API{
    const val BASE_URL = "https://api.unsplash.com/"

    const val CLIENT_ID = ""

    const val SEARCH_PHOTO = "search/photos"
    const val SEARCH_USER = "search/users"
}