package com.example.jungandroid

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.jungandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG: String = "로그"
    lateinit var binding: ActivityMainBinding
    var isLike :Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isLike = false

        binding.likeBtn.setOnClickListener {

            Log.d(TAG, "MainActivity- onCreate()/ 좋아요 버튼 클릭")

            /**
             *  ofFloat(시작지점, 종료지점)은 시작과 끝 값, setDuration(지속시간) 재생시간
             *  ofFloat(0f, 1f)일 경우 0f = 0퍼센트, 1f = 100퍼센트
             *  ofFloat(of, 0.5f)인 경우 50퍼센트까지만 실행시키고 멈춘다는 뜻
             */
            if(isLike == false) {
                val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(1000)
                animator.addUpdateListener {
                    binding.likeBtn.progress = it.animatedValue as Float
                }
                animator.start()
                isLike = true
            } else{ // 좋아요 상태일 때
                val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(1000)
                animator.addUpdateListener {
                    binding.likeBtn.progress = it.animatedValue as Float
                }
                animator.start()
                isLike = false
            }
        }
    }
}