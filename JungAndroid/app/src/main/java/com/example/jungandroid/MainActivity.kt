package com.example.jungandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.jungandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),MyCustomDialogInterface {

    val TAG: String = "로그"
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dialogBtn.setOnClickListener {
            onDiologBtnClicked()
        }
    }

    private fun onDiologBtnClicked() {
        Log.d(TAG, "MainActivity- onDiologBtnClicked() called")

        //첫 매개변수는 AppCompatActivity()의 this,
        //두번 째 매개변수는 MyCustomDialogInterface의 this
        val myCustomDialog = MyCustomDialog(this, this)

        myCustomDialog.show()

    }

    // 버튼 클릭시 메인액티비티에서 알 수 있다.
    //구독 버튼 클릭
    override fun onSubscribeBtnClicked() {
        Log.d(TAG, "MainActivity- onSubscribeBtnClicked() called")
        Toast.makeText(this,"구독버튼 클릭!", Toast.LENGTH_SHORT).show()
    }

    //좋아요 버튼 클릭
    override fun onLikeBtnClicked() {
        Log.d(TAG, "MainActivity- onLikeBtnClicked() called")
        Toast.makeText(this,"좋아요 버튼 클릭!", Toast.LENGTH_SHORT).show()
    }
}