package com.example.jungandroid

import android.app.Application

// context를 싱글톤 패턴으로 구현해서 전역에서 사용가능하게 함
class App : Application() {
    companion object{
        lateinit var instance: App
            private set // 자기 자신을 가져옴
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}