package com.example.jungandroid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MySQLOpenHelperSingleton(context: Context) : SQLiteOpenHelper(context, "MyDB", null, 1) {

    companion object {
        @Volatile // Volatile 사용시 캐시를 거치지 않고 메인메모리로 가서 항상 최신값을 가질 수 있다.
        private var instance: MySQLOpenHelperSingleton? = null

        fun getInstance(context: Context): MySQLOpenHelperSingleton {
            instance ?: synchronized(this) {
                instance ?: MySQLOpenHelperSingleton(context).also {
                    instance = it
                }
            }
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}