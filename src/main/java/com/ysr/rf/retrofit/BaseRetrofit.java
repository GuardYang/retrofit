package com.ysr.rf.retrofit;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/11.
 */
public class BaseRetrofit {
    private static BaseRetrofit mRetrofitManager;
    private Retrofit mRetrofit;
    private BaseRetrofit() {
        initRetrofit();
    }

    public static synchronized BaseRetrofit getInstance() {
        if (mRetrofitManager == null) {
            mRetrofitManager = new BaseRetrofit();
        }
        return mRetrofitManager;
    }

    public class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();

            long t1 = System.nanoTime();//请求发起的时间
            System.out.println(String.format("发送请求 %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();//收到响应的时间

            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);

            System.out.println(String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                    response.request().url(),
                    responseBody.string(),
                    (t2 - t1) / 1e6d,
                    response.headers()));

            return response;
        }
    }

    private void initRetrofit() {
        HttpLoggingInterceptor LoginInterceptor = new HttpLoggingInterceptor();
        LoginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (API.DEBUG){
            builder.addInterceptor( LoginInterceptor);
//            builder.addInterceptor(new LoggingInterceptor());
        }

        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(API.ExpressReqURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
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

    public <T> T createReq(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }


}