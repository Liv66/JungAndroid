package com.example.jungandroid

import android.provider.ContactsContract

// 클래스 이름
class MyFriend(
    var name: String?,
    var age: Int?,
    var isMarried: Boolean?,
    var nickname: String?,
) {
    var address: String = ""

    init {
        this.name = "할아범"
        this.age = 100
        this.isMarried = true
        this.nickname = "꼰대"
    }

    // 부생성자: 추가적으로 인자를 받는 경우가 있을 때 사용
    constructor(
        name: String?,
        age: Int?,
        isMarried: Boolean?,
        nickname: String?,
        address: String,
    ) : this(
        name, age, isMarried, nickname
    ){
        this.address = address
    }
}