package com.example.jungandroid

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyNumberViewModel : ViewModel() {

    companion object {
        const val TAG: String = "로그"
    }

    // 코틀린 컨벤션 사용
    // 뮤터블 라이브 데이터 -> 수정 가능
    private val _currentValue = MutableLiveData<Int>()

    // 일반 라이브 데이터 -> 값 변경 불가
    val currentValue : LiveData<Int>
            get() = _currentValue

    // 초기값 설정
    init {
        Log.d(TAG, "MyNumberViewModel - 생성자 호출")
        _currentValue.value = 0
    }

    fun updateValue(actionType: ActionType, input: Int){
        Log.d(TAG, "MyNumberViewModel input : $input")
        when(actionType){
            ActionType.PLUS -> _currentValue.value = _currentValue.value?.plus(input)
            ActionType.MINUS -> _currentValue.value = _currentValue.value?.minus(input)
        }
    }
}

enum class ActionType{
    PLUS, MINUS
}