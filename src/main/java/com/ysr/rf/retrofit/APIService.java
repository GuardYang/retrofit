package com.ysr.rf.retrofit;

import com.ysr.rf.bean.RequestShipperName;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/7/11.
 */
public interface APIService {
    /**
     * 单号识别
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("/Ebusiness/EbusinessOrderHandle.aspx")
    Call<RequestShipperName> searchData(@Query("RequestData") String RequestData,
                                        @Query("EBusinessID") int EBusinessID,
                                        @Query("RequestType") int RequestType,
                                        @Query("DataType") int DataType,
                                        @Query("DataSign") String DataSign
    );
}
