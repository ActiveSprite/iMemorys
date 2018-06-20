package com.example.guhugang.imemorys;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.guhugang.example.sqlite.DBDao;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by GuHuGang on 2017/12/19.
 */

public class MyRunnable implements Runnable {
    int start;
    DBDao dbDao;
    ArrayList<TaggedImageItem> taggedList;
    int end;
    Context mContext;

    public MyRunnable(int start, int end, ArrayList<TaggedImageItem> taggedList, Context context){
        this.start=start;
        this.end=end;
        this.taggedList=taggedList;
        this.mContext=context;
        dbDao=new DBDao(context);
    }
    public  String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    @Override
    public void run() {
        for(int i=start;i<end;i++) {
            if(isNeedGetTag(taggedList.get(i).getImageId())) {
                Myparams myparams = new Myparams();
                long time = System.currentTimeMillis() + (long) 3 * 60 * 1000;
                myparams.setTime_stamp(String.valueOf(time / 1000))
                        .setNonce_str(getRandomString(17))
                        .setSession_id("1579353187");
                myparams.setImage(myparams.Base64Image(taggedList.get(i).getImagePath()));
                myparams.setSign(myparams.getMD5Result());

                GetImgTag imgTag = new GetImgTag();
                String tag_result = imgTag.getTag(myparams);
                Log.i("tag",tag_result);
                taggedList.get(i).setTag(tag_result);
                GetPictureLocation getPictureLocation=new GetPictureLocation(mContext);
                String location=getPictureLocation.getLocationInfo(taggedList.get(i).getImagePath());
                if(location!=null)
                    taggedList.get(i).setLocation(location);
                try {
                    if(taggedList.get(i)!=null&&!taggedList.get(i).getTag().isEmpty())
                       dbDao.insertTag(taggedList.get(i));
                }catch (Exception e){

                }
            }
        }
    }
    public boolean isNeedGetTag(String id){
        return dbDao.selectTagById(id)==null;
    }


}
