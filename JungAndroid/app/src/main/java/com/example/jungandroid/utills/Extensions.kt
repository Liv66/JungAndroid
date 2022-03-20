package com.example.jungandroid.utills

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.example.jungandroid.utills.Constants.TAG
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import java.text.SimpleDateFormat
import java.util.*

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
fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")

// 문자열이 제이슨 배열인지
fun String?.isJsonArrays(): Boolean = this?.startsWith("[") == true && this.endsWith("]")

// 날짜 포맷
fun Date.toSimpleString(): String {
    val format = SimpleDateFormat("HH:mm:ss") // 현재 시간의 Date객체 생성
    return format.format(this)
}

fun EditText.textChangeToFlow(): Flow<CharSequence?> {

    // flow 콜백 받기
    return callbackFlow<CharSequence> {
        val listener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG, " onTextChanged() / textChangedToFlow() 에 달려있는 텍스트 와쳐 text : $text")
                //값 내보내기
                trySend(text)
            }

            override fun afterTextChanged(s: Editable?) = Unit
        }
        //위에서 설정한 리스너 달아주기
        addTextChangedListener(listener)

        // 콜백이 사라질 때 실행됨
        awaitClose {
            Log.d(TAG, " - textChangeToFlow() / await 실행")
            removeTextChangedListener(listener)
        }
    }.onStart {
        Log.d(TAG, " - textChangeToFlow() / onStart 발동")
        // Rx의 onNext와 동일하다
        // emit으로 이벤트를 전달
        emit(text)
    }
}