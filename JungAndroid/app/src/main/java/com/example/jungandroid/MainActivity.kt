package com.example.jungandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    val TAG: String = "로그"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "MainActivity- onCreate() called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity- onStart() called")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "MainActivity- onResume() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity- onStop() called")
    }

    override fun onDestroy() {
        Log.d(TAG, "MainActivity- onDestroy() called")
        super.onDestroy()
    }
}