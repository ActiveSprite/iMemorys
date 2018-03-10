package com.example.guhugang.imemorys;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by GuHuGang on 2017/12/19.
 */

public class ImageTagService extends Service {
    @Nullable
    private ImageScanner mScanner;
    private ArrayList<TaggedImageItem> taggedList=new ArrayList<>();
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    private int single_number=50;
    public void onCreate() {
        System.out.println("onCreate invoke");
        super.onCreate();
    }

    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }
    public int onStartCommand(Intent intent,int flags,int startId){
        GetImageTag();
        return super.onStartCommand(intent,flags,startId);
    }
    public void GetImageTag() {
        mScanner = new ImageScanner(this);
        Log.i("tagservice", "hh");

        mScanner.scanImages(new ImageScanner.ScanCompleteCallBack() {


            @Override
            public void scanComplete(Cursor cursor) {
                // 关闭进度条


                while (cursor.moveToNext()) {
                    // 获取图片的路径
                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    String id=cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media._ID));
                    TaggedImageItem imageItem=new TaggedImageItem();
                    imageItem.setImageId(id);
                    imageItem.setImagePath(path);
                    taggedList.add(imageItem);

                }
                cursor.close();
                departTask();
            }

        });



    }
    public void departTask(){
        Log.i("wwee",String.valueOf(taggedList.size()));
        if(taggedList==null)return;
        int number=taggedList.size()/single_number;
        for(int i=0;i<number;i++){
            MyRunnable syncRunnable=new MyRunnable(single_number*i,single_number*i+single_number,taggedList,this);
            executorService.execute(syncRunnable);
        }
        int last_start=single_number*number;
        int last_end=taggedList.size();
        MyRunnable syncRunnable=new MyRunnable(last_start,last_end,taggedList,this);
        executorService.execute(syncRunnable);

    }

}
