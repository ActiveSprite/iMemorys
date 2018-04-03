package com.example.guhugang.moreused;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

/**
 * Created by GuHuGang on 2017/10/21.
 */

public class ImageResizer {
    public ImageResizer(){

    }
    public Bitmap getBitmap(String path){

        Bitmap bitmap1=null;
        Bitmap bitmap2=null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap1 = BitmapFactory.decodeFile(path, options);
        options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max(
                (double) options.outWidth /1024f,
                (double) options.outHeight /1024f)));
        options.inJustDecodeBounds = false;

        bitmap1 = BitmapFactory.decodeFile(path, options);
        if(bitmap1!=null)
        bitmap2 = bitmap1.copy(Bitmap.Config.RGB_565, true);
        return bitmap2;
    }
    public void AsyncGetBitmap(final String path,final ResizerCompleteCallBack callback){
        final Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                callback.resizerComplete((Bitmap)msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
               Bitmap prebitmap=getBitmap(path);
                Message msg = mHandler.obtainMessage();
                msg.obj = prebitmap;
                mHandler.sendMessage(msg);
            }
        }).start();
    }
    public static interface ResizerCompleteCallBack{
        public void resizerComplete(Bitmap bitmap);
    }
}
