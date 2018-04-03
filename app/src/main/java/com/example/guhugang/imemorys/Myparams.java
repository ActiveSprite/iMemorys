package com.example.guhugang.imemorys;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.example.guhugang.moreused.Debug;
import com.example.guhugang.moreused.ImageResizer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by GuHuGang on 2017/12/18.
 */

public class Myparams {
    String app_id="1106618622";
    String time_stamp;
    String nonce_str;
    String sign;
    String image;
    String session_id;

    String app_key="dpW2mFEknTHJ8GJs";
    private byte[] mImageData = null;

    String T[]=new String[]{"app_id=","image=","nonce_str=","time_stamp=","app_key="};

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public Myparams setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
        return this;
    }

    public Myparams setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
        return this;
    }

    public Myparams setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public Myparams setImage(String image) {
        this.image = image;
        return this;
    }

    public Myparams setSession_id(String session_id) {
        this.session_id = session_id;
        return this;
    }



    public String getNonce_str() {
        return nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public String getImage() {
        return image;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public String getTime_stamp() {
        return time_stamp;
    }
    public String getMD5Result(){
        String header[]=new String[]{app_id,image,nonce_str,time_stamp,app_key};
        StringBuilder result=new StringBuilder();
        String temp=null;
        for(int i=0;i<T.length;i++){
            result.append(T[i]);
            try {
                temp=URLEncoder.encode(header[i],"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            result.append(temp);

            if(i!=T.length-1){
                result.append("&");
            }

        }

        String result_temp=result.toString();

        return stringMd5(result_temp);

    }
    public  String stringMd5(String input){
        try{
            //拿到一个MD5转换器（如果想要SHA1加密参数换成"SHA1"）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //输入的字符串转换成字节数组
            byte[] inputByteArray = input.getBytes();
            //inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            //转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            //字符数组转换成字符串返回

            return byteArrayToHex(resultByteArray).toUpperCase();


        }catch(NoSuchAlgorithmException e){
            return null;
        }
    }

    public  String byteArrayToHex(byte[] byteArray){
        //首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        //new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符）
        char[] resultCharArray = new char[byteArray.length*2];
        //遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for(byte b : byteArray){
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }

        //字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    public String Base64Image(String path){
        ImageResizer resizer=new ImageResizer();
        Bitmap bitmap=resizer.getBitmap(path);
        if(bitmap!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //可根据流量及网络状况对图片进行压缩
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
            mImageData = baos.toByteArray();
            try {
                baos.flush();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return Base64.encodeToString(mImageData, Base64.DEFAULT);
        }else{
            return "error";
        }
    }

}
