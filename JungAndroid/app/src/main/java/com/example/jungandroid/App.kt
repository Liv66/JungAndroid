package com.example.jungandroid

import android.app.Application

// 다른 곳에서 context를 사용할 수 있게 함
class App : Application() {
    companion object{
        lateinit var instance: App
            private set // 어떻게 작동?
    }

    override fun onCreate(){
        super.onCreate()
        instance = this
    }
}