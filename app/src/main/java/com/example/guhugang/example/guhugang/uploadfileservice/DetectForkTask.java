package com.example.guhugang.example.guhugang.uploadfileservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.FaceDetector;
import android.util.Log;


import com.example.guhugang.example.sqlite.DBDao;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

/**
 * Created by GuHuGang on 2017/6/20.
 */

public class DetectForkTask extends RecursiveAction {
    final int N_MAX = 4;
    int start,end;
    ArrayList<Data> plist = new ArrayList<Data>();
    Context context;
    DBDao dbDao;

    public DetectForkTask(Context context,int start,int end,ArrayList<Data> plist) {

        this.context = context;
        dbDao = new DBDao(context);
        this.start=start;
        this.end=end;
        this.plist=plist;
    }

    @Override
    protected void compute() {
        //long s=System.currentTimeMillis();
        if(end-start<=20){
            for(int i=start;i<end;i++){
                detectFace(plist.get(i).getId(),plist.get(i).getPath());
            }
        }else {
            int middle = (start+end) / 2;
            DetectForkTask left = new DetectForkTask(context,start, middle,plist);
            DetectForkTask right = new DetectForkTask(context,middle, end,plist);
            left.fork();
            right.fork();

        }
        //long e=System.currentTimeMillis();
        //Log.i("完成时间",String.valueOf(e-s));


    }
    Bitmap Initbitmap(String s){
        Bitmap faceimg=null;
        Bitmap srcpicture=null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        srcpicture = BitmapFactory.decodeFile(s, options);
        options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max(
                (double) options.outWidth /1024f,
                (double) options.outHeight /1024f)));
        options.inJustDecodeBounds = false;
        srcpicture = BitmapFactory.decodeFile(s, options);

        faceimg = srcpicture.copy(Bitmap.Config.RGB_565, true);
        return faceimg;
    }
    public boolean checkFace(Rect rect){
        int w = rect.width();
        int h = rect.height();
        int s = w*h;

        if(s < 10000){
            //Log.i(tag, "无效人脸，舍弃.");
            return false;
        }
        else{
            //Log.i(tag, "有效人脸，保存.");
            return true;
        }
    }
    public void detectFace(int id,String origpath){
        Bitmap faceimg=Initbitmap(origpath);
        int w = faceimg.getWidth();
        int h = faceimg.getHeight();
        FaceDetector faceDetector = new FaceDetector(w, h, N_MAX);
        FaceDetector.Face[] face = new FaceDetector.Face[N_MAX];
        int nFace = faceDetector.findFaces(faceimg, face);

        String u=String.valueOf(id);
        for(int i=0; i<nFace; i++){
            FaceDetector.Face f  = face[i];
            PointF midPoint = new PointF();
            float dis = f.eyesDistance();
            f.getMidPoint(midPoint);
            int dd = (int)(dis);

            Point eyeLeft = new Point((int)(midPoint.x - dis/2), (int)midPoint.y);
            Point eyeRight = new Point((int)(midPoint.x + dis/2), (int)midPoint.y);
            Rect faceRect = new Rect((int)(midPoint.x - dd-20), (int)(midPoint.y - dd), (int)(midPoint.x + dd+20), (int)(midPoint.y + dd+30));
            Log.i("tag", "左眼坐标 x = " + eyeLeft.x + "y = " + eyeLeft.y);
            if(checkFace(faceRect)) {
                Log.i("origpath",origpath);
                FacePicture fp=new FacePicture();
                if(isExist(String.valueOf(id))==false){
                fp.setImageId(String.valueOf(id));
                fp.setImagePath(origpath);
                    dbDao.addFacePicture(fp);
               // presonUtil.saveJpeg(faceimg,u);
                 }
            }
        }


    }
    public boolean isExist(String id){
        if(dbDao.selectby(id)==null){
            return false;
        }
        return true;
    }
}