package com.example.jungandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    val TAG: String = "로그"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myFriend = MyFriend(name = "할배", age = 400, isMarried = true, nickname = "할할배배")
        myFriend.age
    }
    

}