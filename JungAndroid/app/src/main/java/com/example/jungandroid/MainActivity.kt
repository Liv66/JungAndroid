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
            onDialogBtnClicked()
        }

        // 람다 호출
        someLambda.invoke()
        someLambdaWithParam("하하") // 매개변수가 있는 것은 invoke로 호출 안해도 됨
//        someLambdaMultiParam("호호")  Error 매개변수를 하나 안쓴다 해도, 호출할 때는 넣어줘야함
        someLambdaMultiParam("호호", 5)

        var someList = mutableListOf<Int>(1,2,3)
        someList.add(4)
        someList.add(5)
        someList.map {

        }

        someList.forEach{

        }
    }
    
    fun someFunc(){
        Log.d(TAG, "MainActivity - someFunc() called")
    }

    fun someFunctionWithParam(title: String){
        Log.d(TAG, "MainActivity - someFunctionWithParam() $title")
    }
    val someLambda : () -> Unit = {
        Log.d(TAG, "MainActivity - () called")
    }

    val someLambdaWithParam: (String) -> Unit = {
        Log.d(TAG, "MainActivity - () called / $it")
    }

    val someLambdaMultiParam: (String, Int) -> Unit = {
        a, _ -> // 매개변수를 사용 안하는 경우는 _를 사용한다.
        Log.d(TAG, "MainActivity - () called, $a")
    }

    private fun onDialogBtnClicked() {
        Log.d(TAG, "MainActivity- onDiologBtnClicked() called")

        //첫 매개변수는 AppCompatActivity()의 this,
        //두번 째 매개변수는 MyCustomDialogInterface의 this
        val myCustomDialog = MyCustomDialog(this, this)

        myCustomDialog.likeAction = {
            Log.d(TAG, "likeAction 호출")
        }

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