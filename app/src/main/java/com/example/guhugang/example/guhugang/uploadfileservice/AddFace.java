package com.example.guhugang.example.guhugang.uploadfileservice;

import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by GuHuGang on 2017/4/19.
 */

public class AddFace {

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    public void AddFace(){

    }
    public  void addfacetoset(String facesettoken,String facetoken){

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("api_key",Constant.api_key);
        builder.add("api_secret",Constant.api_secret);
        builder.add("faceset_token","594c2fc047facf9824f0e65f9789bd3e");
        builder.add("face_tokens",facetoken);
        FormBody requestBody = builder.build();
        Request.Builder reqBuilder = new Request.Builder();
        Request request = reqBuilder
                .url("https://api-cn.faceplusplus.com/facepp/v3/faceset/addface")
                .post(requestBody)
                .build();
        try{
            Response response = mOkHttpClient.newCall(request).execute();
            Log.i("UploadService", "响应码" + response.code());
            String resultValue1 = response.body().string();
            Log.i("result", "响应体 " + resultValue1);
            if (response.isSuccessful()) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
