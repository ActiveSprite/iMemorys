package com.example.guhugang.example.guhugang.uploadfileservice;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLException;

/**
 * Created by GuHuGang on 2017/9/29.
 */

public class ReadText extends AsyncTask{
    public static final String URL = "https://api-cn.faceplusplus.com/imagepp/v1/recognizetext";
    private final static int CONNECT_TIME_OUT = 30000;
    private final static int READ_OUT_TIME = 50000;
    private static String boundaryString = getBoundary();
    HashMap<String, String> map = new HashMap<String, String>();
    HashMap<String, byte[]> byteMap = new HashMap<>();
    public  ReadText(byte[] picture){
        byte[] buff=picture;
        map.put("api_key", Constant.api_key);
        map.put("api_secret", Constant.api_secret);
        byteMap.put("image_file",buff);

    }
    private static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }
    public JSONObject post() throws Exception {
        HttpURLConnection conne;
        URL url1 = new URL(URL);
        JSONObject rjson=null;
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(CONNECT_TIME_OUT);
        conne.setReadTimeout(READ_OUT_TIME);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
                    + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(value + "\r\n");
        }
        if(byteMap != null && byteMap.size() > 0){
            Iterator fileIter = byteMap.entrySet().iterator();
            while(fileIter.hasNext()){
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        InputStream ins = null;
        int code = conne.getResponseCode();
        try{
            if(code == 200){
                ins = conne.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(ins));
                String str=null;
                StringBuffer buffer=new StringBuffer();
                while((str=br.readLine())!=null){
                    buffer.append(str);
                }
                //ins.close();
                //br.close();
                rjson=new JSONObject(buffer.toString());
                Log.i("zxy","rjson="+rjson);
                //Log.i("message","lala");
            }else{
                ins = conne.getErrorStream();
                Log.d("zxy","error");
            }
        }catch (SSLException e){
            e.printStackTrace();
            //return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while((len = ins.read(buff)) != -1){
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        ins.close();
        Log.i("result",String.valueOf(bytes));
        return rjson;
    }
    private String encode(String value) throws Exception{
        return URLEncoder.encode(value, "UTF-8");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            this.post();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
