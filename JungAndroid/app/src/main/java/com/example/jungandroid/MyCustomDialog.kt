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

    var subScribeAction : (() -> Unit)? = null // 람다 nullable 형태 (옵셔널로 설정)
    var likeAction : (() -> Unit)? = null

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
            this.likeAction?.invoke()
//            if(this.likeAction != null){  위와 같은 형태
//                this.likeAction.invoke()
//            }
//            this.likeAction?.let { let을 사용해 위와 같은 형태
//                it()
//            }
            myCustomDialogInterface?.onLikeBtnClicked()
        }

        binding.subscribeBtn.setOnClickListener {
            Log.d(TAG, "MyCustomDialog- 구독 버튼 called")
            myCustomDialogInterface?.onSubscribeBtnClicked()
        }
    }

}