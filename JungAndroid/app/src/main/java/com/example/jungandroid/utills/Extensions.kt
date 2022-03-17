package com.example.jungandroid.utills

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 *         익스텐션 : 반복적으로 사용되는 클래스의 기능을 확장시켜줌
 */
//에딧 텍스트 익스텐션
fun EditText.onMyTextChanged(completion: (Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(editable: Editable?) {
            completion(editable)
        }
    })
}

// 문자열이 제이슨 형태인지, 제이슨 배열 형태인지
fun String?.isJsonObject():Boolean = this?.startsWith("{") == true && this.endsWith("}")

// 문자열이 제이슨 배열인지
fun String?.isJsonArrays() : Boolean = this?.startsWith("[") == true && this.endsWith("]")