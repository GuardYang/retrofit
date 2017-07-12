package com.ysr.rf;

import com.ysr.rf.bean.RequestShipperName;
import com.ysr.rf.retrofit.API;
import com.ysr.rf.retrofit.APIService;
import com.ysr.rf.retrofit.BaseRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/7/11.
 */
public class MainActivity {
    public static void main(String[] s) {
        String LogisticCode = "1000745320654";
        String requestData = "{LogisticCode:"+LogisticCode+"}";
        String RequestData="";
        String DataSign="";
        try {
            RequestData = HttpUtils.urlEncoder(requestData, "UTF-8");
            String dataSign = HttpUtils.encrypt(requestData, API.AppKey, "UTF-8");
            DataSign = HttpUtils.urlEncoder(dataSign, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        BaseRetrofit.getInstance()
                .createReq(APIService.class)
                .searchData(RequestData, API.EBusinessID, 2002, 2, DataSign)
                .enqueue(new Callback<RequestShipperName>() {
                    @Override
                    public void onResponse(Call<RequestShipperName> call, Response<RequestShipperName> response) {
                    }

                    @Override
                    public void onFailure(Call<RequestShipperName> call, Throwable t) {
                        System.out.println("失败");
                    }
                });
    }



}
