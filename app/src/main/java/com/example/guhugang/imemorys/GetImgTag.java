package com.example.guhugang.imemorys;

import android.util.Log;

import com.example.guhugang.example.guhugang.uploadfileservice.Constant;
import com.example.guhugang.moreused.Debug;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by GuHuGang on 2017/12/18.
 */

public class GetImgTag {
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    public String  getTag(Myparams myparams){
        StringBuilder stringBuilder=new StringBuilder();
        String contentType = "application/x-www-form-urlencoded";

        float confidence=0;
        JSONObject json=null;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("app_id", myparams.getApp_id());
        builder.add("time_stamp",myparams.getTime_stamp());
        builder.add("nonce_str",myparams.getNonce_str());
        builder.add("sign",myparams.getSign());
        Debug.show("sign",myparams.getSign());
        builder.add("image",myparams.getImage());
       // builder.add("session_id",myparams.getSession_id());
        FormBody requestBody = builder.build();
        Request.Builder reqBuilder = new Request.Builder();
        Request request = reqBuilder
                .url("https://api.ai.qq.com/fcgi-bin/image/image_tag")
                .addHeader("Content-Type", contentType)
                .post(requestBody)
                .build();
        try{
            Response response = mOkHttpClient.newCall(request).execute();
            String resultValue1 = response.body().string();
            json=new JSONObject(resultValue1);
            JSONArray jsonarray = json.getJSONObject("data").getJSONArray("tag_list");
            //confidence=(float)json.getDouble("confidence");

            int length=jsonarray.length();
            if(length!=0){
                for(int j=0;j<length;j++){
                    JSONObject tag_json = jsonarray.getJSONObject(j);
                    stringBuilder.append("_");
                    stringBuilder.append(tag_json.getString("tag_name"));
                }
            }
            Log.i("result", "图片标签:" + stringBuilder.toString());
            if (response.isSuccessful()) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
