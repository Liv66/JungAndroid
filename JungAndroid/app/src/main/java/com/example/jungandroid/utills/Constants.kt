package com.example.jungandroid.utills

object Constants {
    const val TAG: String = "로그"
}

enum class SEARCH_TYPE{
    PHOTO,
    USER
}

enum class RESPONSE_STATUS{
    OKAY,
    FAIL,
    NO_CONTENT
}

object API{
    const val BASE_URL = "https://api.unsplash.com/"

    const val CLIENT_ID = "oQF1gX-p05aw6VKIk_j27O2WN3GCPg9pdmzsmOIc4VA"

    const val SEARCH_PHOTOS = "search/photos"
    const val SEARCH_USERS = "search/users"
}