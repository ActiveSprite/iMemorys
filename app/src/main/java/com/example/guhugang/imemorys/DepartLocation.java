package com.example.guhugang.imemorys;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.example.guhugang.example.guhugang.uploadfileservice.FacePicture;
import com.example.guhugang.example.guhugang.uploadfileservice.FacePictureBucket;
import com.example.guhugang.example.guhugang.uploadfileservice.LoadPicture;
import com.example.guhugang.example.sqlite.DBDao;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by GuHuGang on 2018/2/3.
 */

public class DepartLocation {
    HashMap<String, PhotoUpImageBucket<TaggedImageItem>> bucketList = new HashMap<String, PhotoUpImageBucket<TaggedImageItem>>();
    DBDao dbDao;
    Context context;
    PhotoUpImageBucket<TaggedImageItem> fpb;
    public DepartLocation(Context context){
        this.context=context;
        dbDao=new DBDao(context);
    }
    public void scanImages(final DepartLocation.ScanCompleteCallBack callback) {
        final Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                callback.scanComplete((List<PhotoUpImageBucket<TaggedImageItem>>) msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<TaggedImageItem> locatedList=dbDao.selectTagByNull();
                if(locatedList==null)return;
                for(int i=0;i<locatedList.size();i++) {
                    String location=locatedList.get(i).getLocation();
                    if(location!=null&& !TextUtils.isEmpty(location)) {
                        fpb = bucketList.get(location);
                        if (fpb == null) {
                            fpb = new PhotoUpImageBucket<TaggedImageItem>();
                            bucketList.put(locatedList.get(i).getLocation(), fpb);
                            //List<FacePicture> imageList=new ArrayList<FacePicture>();
                            fpb.imageList = new ArrayList<TaggedImageItem>();
                            fpb.bucketName = location;
                        }
                        fpb.imageList.add(locatedList.get(i));
                    }
                }
                List<PhotoUpImageBucket<TaggedImageItem>>imglist= getImagesBucketList();
//                Log.i("aaaa",imglist.get(0).getImageList().get(0).toString());
                Message msg = mHandler.obtainMessage();
                msg.obj = imglist;
                mHandler.sendMessage(msg);
            }
        }).start();
    }
    public List<PhotoUpImageBucket<TaggedImageItem>> getImagesBucketList() {

        List<PhotoUpImageBucket<TaggedImageItem>> tmpList = new ArrayList<PhotoUpImageBucket<TaggedImageItem>>();
        Iterator<Map.Entry<String, PhotoUpImageBucket<TaggedImageItem>>> itr = bucketList.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, PhotoUpImageBucket<TaggedImageItem>> entry = (Map.Entry<String, PhotoUpImageBucket<TaggedImageItem>>) itr
                    .next();
            tmpList.add(entry.getValue());

        }
        return tmpList;
    }
    public static interface ScanCompleteCallBack{
        public void scanComplete(List<PhotoUpImageBucket<TaggedImageItem>> fpblist);
    }
}
