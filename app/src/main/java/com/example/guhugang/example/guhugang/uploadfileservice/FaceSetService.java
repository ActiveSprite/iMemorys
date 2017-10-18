package com.example.guhugang.example.guhugang.uploadfileservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by GuHuGang on 2017/4/12.
 */

public class FaceSetService extends IntentService {

    OkHttpClient mOkHttpClient = new OkHttpClient();
    FormBody.Builder builder = new FormBody.Builder();
    public FaceSetService(){
        super("FaceSetService");
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    protected void onHandleIntent(Intent intent){
        builder.add("api_key","ZrBwXl_K9d-J9SX1xu_uGUa6XQvGkhWH");
        builder.add("api_secret","PnYTe8ljdWKiAKVC1KcuURLFYhcp0Z0H");
        builder.add("faceset_token","594c2fc047facf9824f0e65f9789bd3e");
        builder.add("face_tokens","eaeeb7f6aa76f9dbc3d8d69c56362d22");
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
                String resultValue = response.body().string();
                 Log.i("result", "响应体");
                 Log.i("result", "响应体 " + resultValue);

           }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
