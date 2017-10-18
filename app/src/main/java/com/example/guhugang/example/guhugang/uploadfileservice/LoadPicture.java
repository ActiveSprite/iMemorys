package com.example.guhugang.example.guhugang.uploadfileservice;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.example.guhugang.example.sqlite.DBDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by GuHuGang on 2017/6/17.
 */

public class LoadPicture {
    HashMap<Integer, FacePictureBucket> bucketList = new HashMap<Integer, FacePictureBucket>();
    DBDao dbDao;
    Context context;
    FacePictureBucket fpb;
    public LoadPicture(Context context){
        this.context=context;
        dbDao=new DBDao(context);
    }
    public void scanImages(final ScanCompleteCallBack callback) {
        final Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                callback.scanComplete((List<FacePictureBucket>) msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<FacePicture> ftklist=dbDao.selectfacetoken();
                if(ftklist==null)return;
                for(int i=0;i<ftklist.size();i++) {
                    Log.i("sqlitecat",ftklist.get(i).toString());
                    fpb = bucketList.get(ftklist.get(i).getCategory());
                    if (fpb == null){
                        fpb = new FacePictureBucket();
                        bucketList.put(ftklist.get(i).getCategory(), fpb);
                        //List<FacePicture> imageList=new ArrayList<FacePicture>();
                        fpb.imageList=new ArrayList<FacePicture>();

                    }
                    fpb.imageList.add(ftklist.get(i));
                }
                List<FacePictureBucket>imglist= getImagesBucketList();
                Log.i("aaaa",imglist.get(0).getImageList().get(0).toString());
                Message msg = mHandler.obtainMessage();
                msg.obj = imglist;
                mHandler.sendMessage(msg);
            }
        }).start();
    }
    public List<FacePictureBucket> getImagesBucketList() {

        List<FacePictureBucket> tmpList = new ArrayList<FacePictureBucket>();
        Iterator<Map.Entry<Integer, FacePictureBucket>> itr = bucketList.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Integer, FacePictureBucket> entry = (Map.Entry<Integer, FacePictureBucket>) itr
                    .next();
            tmpList.add(entry.getValue());
        }
        return tmpList;
    }
    public static interface ScanCompleteCallBack{
        public void scanComplete(List<FacePictureBucket> fpblist);
    }
}
