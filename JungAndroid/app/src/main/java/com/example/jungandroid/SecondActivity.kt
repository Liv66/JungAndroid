package com.example.jungandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.jungandroid.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    val TAG: String = "로그"
    lateinit var binding:ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{
            onBackButtonClicked()
        }
    }

    fun onBackButtonClicked(){
        Log.d(TAG, "SecondActivity- onBackButtonClicked() called")
        finish()
    }
}