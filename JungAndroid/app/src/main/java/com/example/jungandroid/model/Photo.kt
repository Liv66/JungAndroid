package com.example.jungandroid.model

import java.io.Serializable

//직렬화하기 위해선 Serializable 상속해야함.
data class Photo(
    var thumbnail: String?,
    var author: String?,
    var createdAt: String?,
    var likesCount: Int?
):Serializable