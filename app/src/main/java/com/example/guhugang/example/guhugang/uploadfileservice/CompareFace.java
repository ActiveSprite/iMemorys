package com.example.guhugang.example.guhugang.uploadfileservice;

import android.util.Log;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by GuHuGang on 2017/4/23.
 */

public class CompareFace {
    public float compare(String facetoken1, String facetoken2){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        float confidence=0;
        JSONObject json=null;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("api_key",Constant.api_key);
        builder.add("api_secret",Constant.api_secret);
        builder.add("face_token1",facetoken1);
        builder.add("face_token2",facetoken2);
        FormBody requestBody = builder.build();
        Request.Builder reqBuilder = new Request.Builder();
        Request request = reqBuilder
                .url("https://api-cn.faceplusplus.com/facepp/v3/compare")
                .post(requestBody)
                .build();
        try{
            Response response = mOkHttpClient.newCall(request).execute();
            Log.i("UploadService", "响应码" + response.code());
            String resultValue1 = response.body().string();
            json=new JSONObject(resultValue1);
            confidence=(float)json.getDouble("confidence");
            Log.i("result", "响应体 " + resultValue1);
            if (response.isSuccessful()) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return confidence;
    }



}
