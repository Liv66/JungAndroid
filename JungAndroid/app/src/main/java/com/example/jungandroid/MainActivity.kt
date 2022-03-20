package com.example.jungandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.w3c.dom.Text

private val MainActivity.button: Button?
    get() = findViewById<Button>(R.id.plust_btn)

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG: String = "로그"
    }

    lateinit var myNumberViewModel: MyNumberViewModel

    private lateinit var userInputTextView: EditText
    private lateinit var userTextView : TextView
    private lateinit var plustBtn: Button
    private lateinit var minusBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userInputTextView = findViewById(R.id.user_input_tv)
        userTextView = findViewById(R.id.number_tv)
        plustBtn = findViewById<Button>(R.id.plust_btn)
        minusBtn = findViewById<Button>(R.id.minus_btn)

        myNumberViewModel = ViewModelProvider(this).get(MyNumberViewModel::class.java)
        myNumberViewModel.currentValue.observe(this, Observer {
            Log.d(TAG, "MainActivity - myNumberViewModel - currnetValue 라이브데이터 값 변경 : $it")
            userTextView.text = it.toString()
        })

        plustBtn.setOnClickListener(this)
        minusBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val userInput = userInputTextView.text.toString().toInt()

        when (view) {
            plustBtn -> myNumberViewModel.updateValue(
                actionType = ActionType.PLUS,
                input = userInput
            )
            minusBtn -> myNumberViewModel.updateValue(
                actionType = ActionType.MINUS,
                input = userInput
            )
        }
    }
}