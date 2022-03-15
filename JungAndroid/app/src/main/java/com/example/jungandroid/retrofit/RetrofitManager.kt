package com.example.jungandroid.retrofit

import android.util.Log
import com.example.jungandroid.utills.API.BASE_URL
import com.example.jungandroid.utills.Constants.TAG
import com.example.jungandroid.utills.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
import retrofit2.create

/** 리플랙션(Reflection) : 코드를 작성하는 시점에는 런타임상 컴파일된 바이트코드에서 내가 작성한 코드가
 * 어디에 위치하는지 알 수 없기때문에 바이트코드를 이용해 내가 참조하려는 값을 찾기위해 사용
 *
 *  자바에서의 리플랙션
 *  SomeClass.class //클래스 그 자체를 리플렉션
 *  someInstance.getClass() //인스턴스에서 클래스를 리플렉션
 *
 *  코틀린에서의 리플랙션
 *  SomeClass::class
 *  someInstance::class
 *
 *  코틀린에서 ::class로 넘겨줄 때 KClass(코틀린 클래스)로 넘겨준다
 *  안드로이드에서 SomeClass::class.java는 코틀린 클래스를 자바 클래스로 변환시켜 값을 불러온다.
 *
 *  코틀린에서는  SomeClass:Method와같이 클래스 내의 메서드도 참조할 수 있고
 *  ::Function형식의 함수도 참조할 수 있습니다.
 */

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    /*
        Retrofit이 아니라 IRetrofit을 사용하는 이유는 Strategy 패턴을 사용하기 때문이다.
        인터페이스 패턴을 사용하면
     */
    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(BASE_URL)?.create(IRetrofit::class.java)

    // 사진검색 API 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit) {

        val term = searchTerm.let {
            it // 매핑작업
        } ?: ""
//        val term = searchTerm ?: "" 같은 의미

        val call = iRetrofit?.searchPhotos(searchTerm = term).let {
            it
        } ?: return

//        val call: Call<JsonElement> = iRetrofit?.searchPhotos(term) ?: return 같은 의미

        call.enqueue(object : retrofit2.Callback<JsonElement> {

            // 응답이 성공 했을 때
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called")
                completion(RESPONSE_STATE.OKAY, response.raw().toString())
            }

            //응답 실패 했을 때
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called")
                completion(RESPONSE_STATE.FAIL, t.toString())
            }

        })
    }
}