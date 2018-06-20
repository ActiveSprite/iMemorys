package com.example.guhugang.imemorys.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.guhugang.example.sqlite.DBDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guhug on 2018/5/8.
 */

public class PictureUtils {
    public static void SharePictures(Context aContext, ArrayList<Uri> aUriArray){
        if(aUriArray!=null && aUriArray.size()>0)
        {
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.putExtra(Intent.EXTRA_STREAM, aUriArray);
            intent.setType("image/*"); //must set this flag
            aContext.startActivity(Intent.createChooser(intent, "分享图片"));
        }
    }
    public static String getName(String path){
        String names[]=path.split("/");
        return names[names.length-1];
    }
    public static void moveFile(Context mContext,String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                DBDao dbDao=new DBDao(mContext.getApplicationContext());
                dbDao.deleteAll(oldPath);
                mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + newPath)));
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }
    public static boolean fileIsExists(String strFlie) {
        try {
            File file = new File(strFlie);
            if (!file.exists()) {
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public static boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }
}
