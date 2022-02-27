package com.example.jungandroid

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.example.jungandroid.databinding.CustomDialogBinding

class MyCustomDialog(context: Context, myCustomDialogInterface: MyCustomDialogInterface): Dialog(context) {

    lateinit var binding: CustomDialogBinding
    private var myCustomDialogInterface:MyCustomDialogInterface? = null
    val TAG: String = "로그"

    init {
        this.myCustomDialogInterface = myCustomDialogInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "MyCustomDialog- onCreate() called")

        //배경 투명하게
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.likeBtn.setOnClickListener {
            Log.d(TAG, "MyCustomDialog- 좋아요 버튼 클릭!")
            myCustomDialogInterface?.onLikeBtnClicked()
        }

        binding.subscribeBtn.setOnClickListener {
            Log.d(TAG, "MyCustomDialog- 구독 버튼 called")
            myCustomDialogInterface?.onSubscribeBtnClicked()
        }
    }

}