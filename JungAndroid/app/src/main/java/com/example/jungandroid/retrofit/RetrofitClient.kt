package com.example.jungandroid.retrofit

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.jungandroid.App
import com.example.jungandroid.utills.API
import com.example.jungandroid.utills.Constants.TAG
import com.example.jungandroid.utills.isJsonArrays
import com.example.jungandroid.utills.isJsonObject
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

// 싱글턴
object RetrofitClient {
    // 레트로핏 클라이언트 선언
//    private lateinit var retrofitClient: Retrofit 아래와 같은 의미
    private var retrofitClient: Retrofit? = null

    //레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called")

        //okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        // 로그를 찍기 위해 로깅 인터셉터 설정, 전반적인 모든 통신내용을 볼 수 있다.
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {

            override fun log(message: String) {
                when {
                    //jsonObject면 로그 출력시 들여쓰기해서 출력한다.
                    message.isJsonObject() -> Log.d(TAG, JSONObject(message).toString(4))
                    message.isJsonArrays() -> Log.d(TAG, JSONObject(message).toString(4))
                    else -> {
                        try {
                            Log.d(TAG, JSONObject(message).toString(4))
                        } catch (e: Exception) {
                            Log.d(TAG, message)
                        }
                    }
                }
            }
        })

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // 위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가한다.
        client.addInterceptor(loggingInterceptor)

        // 기본 파라메터 인터셉터 설정
        val baseParameterInterceptor: Interceptor = (object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d(TAG, "RetrofitClient - intercept() called")
                //오리지날 리퀘스트
                val originalRequest: Request = chain.request()

                /**쿼리 파라메터 추가하기
                https://api.unsplash.com/photos/?client_id=YOUR_ACCESS_KEY에서
                ?client_id=YOUR_ACCESS_KEY 넣는 과정

                쿼리 스트링으로 추가한다.
                쿼리 스트링 : 사용자가 입력 데이터를 전달하는 방법중의 하나로,
                url 주소에 미리 협의된 데이터를 파라미터를 통해 넘기는 것을 말한다.
                ex) 엔드포인트주소/엔드포인트주소?파라미터=값&파라미터=값

                baseParameterInterceptor로
                https://api.unsplash.com/search/photos?query=cat 였던 url에 &client_id=oQF1gX-p05aw6VKIk_j27O2WN3GCPg9pdmzsmOIc4VA를 붙여서
                https://api.unsplash.com/search/photos?query=cat&client_id=oQF1gX-p05aw6VKIk_j27O2WN3GCPg9pdmzsmOIc4VA로 만든다.
                 */
                val addedUrl: HttpUrl =
                    originalRequest.url.newBuilder().addQueryParameter("client_id", API.CLIENT_ID)
                        .build()
//
                val finalRequest: Request = originalRequest.newBuilder()
                    .url(addedUrl)
                    .method(originalRequest.method, originalRequest.body)
                    .build()

                val response = chain.proceed(finalRequest)

                if (response.code != 200) {

                    Handler(Looper.getMainLooper()).post {
                        // ui 쓰레드에서 돌아가게 해줌
                        Toast.makeText(App.instance, "${response.code} 에러입니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                return response
            }
        })

        //위에서 설정한 기본파라메터 인터셉터를 okhttp 클라이언트에 추가한다.
        client.addInterceptor(baseParameterInterceptor)

        //커넥션 타임아웃
        client.connectTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)
        client.retryOnConnectionFailure(true)

        //레트로핏 빌더 패턴, .() .()로 추가하는 형식이다.
        if (retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //위에서 설정한 클라이언트로 레트로핏 클라이언트를 실행한다.
                .client(client.build())
                .build()
        }
        return retrofitClient
    }
}