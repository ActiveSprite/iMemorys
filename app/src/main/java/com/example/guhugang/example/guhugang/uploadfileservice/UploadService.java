package com.example.guhugang.example.guhugang.uploadfileservice;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.FaceDetector;
import android.provider.MediaStore.Images.Media;
import android.util.Log;


import com.example.guhugang.example.sqlite.DBDao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;

/**
 * Created by GuHuGang on 2017/4/3.
 */

public class UploadService  extends IntentService{
    String imagePath="/storage/emulated/0/Download/Browser/hh.jpeg";
//    File file = new File("/storage/emulated/0/DCIM/1473155548337.jpg");
    Bitmap srcImg = null;
    Bitmap srcFace = null;
    FaceDetector faceDetector = null;
    FaceDetector.Face[] face;
    final int N_MAX = 4;
    private byte[] mImageData = null;
    protected static String TAG = "TAG";
    ContentResolver cr;
    ExecutorService fixedThreadPool=Executors.newFixedThreadPool(4);
    OkHttpClient mOkHttpClient = new OkHttpClient();
    DBDao dbDao;

    GetFaceToken gft;
    CompareFaceplus pp=new CompareFaceplus();
    CompareFace compareFace=new CompareFace();
    AddFace addFace=new AddFace();

    MultipartBody.Builder builder = new MultipartBody.Builder();
    ArrayList<Data> plist=new ArrayList<Data>();

    SharedPreferences preference;
    SharedPreferences.Editor edit;
    public UploadService(){
        super("UploadService");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        cr =this.getContentResolver();
        dbDao=new DBDao(this);
        return super.onStartCommand(intent, flags, startId);

    }

    protected void onHandleIntent(Intent intent){
//        preference = getSharedPreferences("user", Context.MODE_PRIVATE);
//        edit = preference.edit();
//        edit.putInt("firstuse", 1);
//        edit.commit();
        SimpleDateFormat formatter    =   new    SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date curDate=   new    Date(System.currentTimeMillis());//获取当前时间
        String    str=    formatter.format(curDate);

        FormBody.Builder builder1 = new FormBody.Builder();
        String result = "error";
        try {
            float s=pp.post("3116b585ba60a74564d151bcda2952b5","3689c37e788b656f52c23de357c5f9b9");
            Log.i("s",String.valueOf(s));
        }catch (Exception e){

        }


        //Log.i("confidence",String.valueOf(s));
        buildfacepicture();
        ArrayList<FacePicture>fplist= dbDao.selectfp();
        ArrayList<FacePicture>ftklist1= dbDao.selectfacetoken();
        if(ftklist1!=null){
            for(int i=0;i<ftklist1.size();i++){

                Log.i("sqlitecat",ftklist1.get(i).toString());
            }
        }

        SharedPreferences preference = getSharedPreferences("user", Context.MODE_PRIVATE);
        int firstuse = preference.getInt("firstuse",1);
        if(firstuse==1) {
            savetosqlite();
            ArrayList<FacePicture> ftklist = dbDao.selectfacetoken();
            do_catergory(ftklist);
        }

        SimpleDateFormat formatter1=new  SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date curDate1=new Date(System.currentTimeMillis());//获取当前时间
        String  str1=formatter.format(curDate);
        Log.i("time",str1);
    }
    public float facecompare(String f1,String f2){
        float confidence=0;
        try {
            confidence = pp.post(f1, f2);
        }catch (Exception e){

        }
        return confidence;
    }
    public void do_catergory(ArrayList<FacePicture> ftklist){
        int category=0;
        if(ftklist==null){return;}
        while(ftklist.size()!=0) {
            category++;
            Log.i("category",String.valueOf(category));
            String facetoken = ftklist.get(0).getfacetoken();
            ftklist.get(0).setCategory(category);
            dbDao.updatecategory(ftklist.get(0).getImageId(),ftklist.get(0).getCategory());
            for (int i = 1; i < ftklist.size(); i++) {

                float confidence = facecompare(facetoken,ftklist.get(i).getfacetoken());
                if (confidence >= 80) {
                    ftklist.get(i).setCategory(category);
                    dbDao.updatecategory(ftklist.get(i).getImageId(),ftklist.get(i).getCategory());
                }

            }

            for (int i = 0; i < ftklist.size(); i++){
                Log.i("明细",ftklist.get(i).toString());
            }
            for (int i = 0; i < ftklist.size(); i++) {

                if (ftklist.get(i).getCategory()!=0) {
                    ftklist.remove(i);
                    --i;
                }

            }
           Log.i("ftk",String.valueOf(ftklist.size()));

        }
        preference = getSharedPreferences("user", Context.MODE_PRIVATE);
        edit = preference.edit();
        edit.putInt("firstuse", 0);
        edit.commit();
    }
    void Initface(String s){
        try{
            Initbitmap(s);
            int w = srcFace.getWidth();
            int h = srcFace.getHeight();
            faceDetector = new FaceDetector(w, h, N_MAX);
            face = new FaceDetector.Face[N_MAX];
        }catch(Exception e){

        }

    }
    void Initbitmap(String s){
        Bitmap faceimg=null;
        Bitmap srcpicture=null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        srcImg = BitmapFactory.decodeFile(s, options);
        options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max(
                (double) options.outWidth /1024f,
                (double) options.outHeight /1024f)));
        options.inJustDecodeBounds = false;
        srcImg = BitmapFactory.decodeFile(s, options);

        this.srcFace = srcImg.copy(Bitmap.Config.RGB_565, true);

    }
    void buildfacepicture(){

        String columns[] = new String[] { Media._ID, Media.BUCKET_ID,
                Media.PICASA_ID, Media.DATA, Media.DISPLAY_NAME, Media.TITLE,
                Media.SIZE, Media.BUCKET_DISPLAY_NAME,Media.DATE_ADDED};

        String selection=Media.DATA+" like ?";
        String[] selectionargs={"%/storage/emulated/0/DCIM/%"};
        final Cursor cur = cr.query(Media.EXTERNAL_CONTENT_URI, columns,null, null,
                Media.DATE_MODIFIED+" desc");

        if (cur.moveToFirst()) {

            final int photoID = cur.getColumnIndexOrThrow(Media._ID);
            final int photoPath = cur.getColumnIndexOrThrow(Media.DATA);
            int photoName=cur.getColumnIndexOrThrow(Media.DISPLAY_NAME);
            do{
                final String path = cur.getString(photoPath);
                final int id=Integer.valueOf(cur.getString(photoID));
                Data data=new Data();
                data.setId(id);
                data.setPath(path);
                plist.add(data);
//                Initface(path);
//                detectFace(id,path);
            }while(cur.moveToNext());

        }
        if(plist!=null) {
            ForkJoinPool pool = new ForkJoinPool();
            pool.submit(new DetectForkTask(this, 0, plist.size(), plist));
            try {
                pool.shutdown();
                pool.awaitTermination(Long.MAX_VALUE,TimeUnit.SECONDS);

            }catch (Exception e){

            }
            Log.i("info","任务完成");

        }
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

    public Bitmap detectFace(int id,String origpath){
        int nFace = faceDetector.findFaces(srcFace, face);

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
                //presonUtil.saveJpeg(srcFace,u);
                }
            }
        }
        return srcFace;

    }

public boolean isExist(String id){
    if(dbDao.selectby(id)==null){
        return false;
    }
    return true;
}

public void savetosqlite(){
    ArrayList<FacePicture>fplists=dbDao.selectfp();
    String fppath;
    String fpid;
    if(fplists!=null){
        for(int i=0;i<fplists.size();i++) {
            fppath = fplists.get(i).getImagePath();
            fpid = fplists.get(i).getImageId();

            Initbitmap(fppath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //可根据流量及网络状况对图片进行压缩
            srcFace.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            mImageData = baos.toByteArray();
            Log.i("need2",String.valueOf(isneedtogetfaceid(fpid)));
            if (isneedtogetfaceid(fpid)) {
                gft = new GetFaceToken(mImageData);
                Log.i("runsc","条件满足");
                try {
                    JSONObject json = gft.post();
                    JSONArray jsonarray = json.getJSONArray("faces");
                    int a = jsonarray.length();
                    if (a != 0) {
                        for (int x = 0; x < a; x++) {
                            String fid = fpid + "face" + x;
                            JSONObject facejson = jsonarray.getJSONObject(x);

                            String facetokens = facejson.getString("face_token");

                            FacePicture fp = new FacePicture();
                            //addFace.addfacetoset("hh",facetokens);
                            if (isExistfacetoken(fid) == false) {
                                fp.setImageId(fid);
                                fp.setImagePath(fppath);
                                fp.setfacetoken(facetokens);
                                addFace.addfacetoset("hh", facetokens);
                                dbDao.addfacetoken(fp);
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }
        }
    }
}

    public boolean isExistfacetoken(String ftk){
        if(dbDao.selectbyft(ftk)==null){
            return false;
        }
        return true;
    }
    public boolean isneedtogetfaceid(String pid){

        ArrayList<FacePicture> ftlist=dbDao.selectfacetoken();
        if(ftlist!=null){
            for(int i=0;i<ftlist.size();i++){
                String id[]=ftlist.get(i).getImageId().split("face");
                String id1=id[0];
                //Log.i("id",id1);
                if(pid.equals(id1))return false;
            }
        }
       return true;
    }


}
