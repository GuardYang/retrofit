package com.ysr.rf.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by Administrator on 2017/7/11.
 */
class BaseRetrofit private constructor() {
    private var mRetrofit: Retrofit? = null

    init {
        initRetrofit()
    }

    inner class LoggingInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            val request = chain.request()

            val t1 = System.nanoTime()//请求发起的时间
            println(String.format("发送请求 %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()))

            val response = chain.proceed(request)

            val t2 = System.nanoTime()//收到响应的时间

            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            val responseBody = response.peekBody((1024 * 1024).toLong())

            println(String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                    response.request().url(),
                    responseBody.string(),
                    (t2 - t1) / 7789,
                    response.headers()))

            return response
        }
    }

    private fun initRetrofit() {
        val LoginInterceptor = HttpLoggingInterceptor()
        LoginInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()

        if (API.DEBUG) {
//            builder.addInterceptor(LoggingInterceptor())
            builder.addInterceptor(LoginInterceptor)
        }

        builder.connectTimeout(15, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(true)
        val client = builder.build()

        mRetrofit = Retrofit.Builder()
                .baseUrl(API.ExpressReqURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build()
    }
    //    private void initRetrofit() {
    //        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    //        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    //        OkHttpClient client = new OkHttpClient.Builder()
    //                .addInterceptor(loggingInterceptor)
    //                .connectTimeout(15, TimeUnit.SECONDS)
    //                .readTimeout(20, TimeUnit.SECONDS)
    //                .writeTimeout(20, TimeUnit.SECONDS)
    //                .retryOnConnectionFailure(true).build();
    //
    //
    //        mRetrofit = new Retrofit.Builder()
    //                .baseUrl(API.ExpressReqURL)
    //                .addConverterFactory(GsonConverterFactory.create())
    //                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
    //                .client(client)
    //                .build();
    //    }

    fun <T> createReq(reqServer: Class<T>): T {
        return mRetrofit!!.create(reqServer)
    }

    companion object {
        private var mRetrofitManager: BaseRetrofit? = null

        val instance: BaseRetrofit
            @Synchronized get() {
                if (mRetrofitManager == null) {
                    mRetrofitManager = BaseRetrofit()
                }
                return mRetrofitManager!!
            }
    }


}
