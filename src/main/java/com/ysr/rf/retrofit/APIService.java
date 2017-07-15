package com.ysr.rf.retrofit;

import com.ysr.rf.bean.RequestShipperName;
import com.ysr.rf.bean.ResultDetail;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/7/11.
 */
public interface APIService {
    /**
     * 单号识别
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("/Ebusiness/EbusinessOrderHandle.aspx")
    Call<RequestShipperName> searchData(@Query("RequestData") String RequestData,
                                        @Query("EBusinessID") int EBusinessID,
                                        @Query("RequestType") int RequestType,
                                        @Query("DataType") int DataType,
                                        @Query("DataSign") String DataSign
    );

    /**
     * 即时查询
     */
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("/Ebusiness/EbusinessOrderHandle.aspx")
    Call<ResultDetail> searchDetailsData(@Query("RequestData") String RequestData,
                                         @Query("EBusinessID") int EBusinessID,
                                         @Query("RequestType") int RequestType,
                                         @Query("DataType") int DataType,
                                         @Query("DataSign") String DataSign
    );
}
