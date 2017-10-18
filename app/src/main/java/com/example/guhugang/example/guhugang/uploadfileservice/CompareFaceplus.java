package com.example.guhugang.example.guhugang.uploadfileservice;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
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

/**
 * Created by GuHuGang on 2017/6/15.
 */

public class CompareFaceplus {
    public static final String URL = "https://api-cn.faceplusplus.com/facepp/v3/compare";
    private final static int CONNECT_TIME_OUT = 30000;
    private final static int READ_OUT_TIME = 50000;
    private static String boundaryString = getBoundary();
    HashMap<String, String> map = new HashMap<String, String>();
    public CompareFaceplus(){

    }
    private static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }
    public float post(String facetoken1,String facetoken2 ) throws Exception {
        map.put("api_key", "ZrBwXl_K9d-J9SX1xu_uGUa6XQvGkhWH");
        map.put("api_secret", "PnYTe8ljdWKiAKVC1KcuURLFYhcp0Z0H");
        map.put("face_token1",facetoken1);
        map.put("face_token2",facetoken2);
        HttpURLConnection conne;
        float confidence=0;
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

                rjson=new JSONObject(buffer.toString());
                confidence=(float)rjson.getDouble("confidence");
                Log.i("zxy","rjson="+rjson);
            }else{
                ins = conne.getErrorStream();
            }
        }catch (Exception e){
            e.printStackTrace();
            //return new byte[0];
        }

        return confidence;
    }
    private String encode(String value) throws Exception{
        return URLEncoder.encode(value, "UTF-8");
    }
}
