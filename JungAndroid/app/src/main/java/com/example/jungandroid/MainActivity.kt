package com.example.jungandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.jungandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG: String = "로그"
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //로그인 버튼 뷰에 클릭 리스너를 설정한다.
        binding.loginBtn.setOnClickListener {
            onLoginButtonClicked()
        }
    }

    fun onLoginButtonClicked(){
        Log.d(TAG, "MainActivity- onLoginButtonClicked() called")

        val intent = Intent(this, SecondActivity::class.java)

        startActivity(intent)
    }
}