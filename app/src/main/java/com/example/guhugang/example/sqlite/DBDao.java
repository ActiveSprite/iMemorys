package com.example.guhugang.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.example.guhugang.example.guhugang.uploadfileservice.FacePicture;

import java.util.ArrayList;

/**
 * Created by GuHuGang on 2017/4/14.
 */

public class DBDao {
    private DBHelper helper;
    private SQLiteDatabase db;
    String facetable = "facepicture";
    String pictureid="pictureid";
    String path="path";
    String facetokentable="facetoken";
    String facetoken="faceid";
    String category="category";
    public DBDao(Context context) {

        helper = new DBHelper(context);

    }

   public void addFacePicture(FacePicture fp) {
        try {
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(pictureid, fp.getImageId());
            values.put(path, fp.getImagePath());
            db.insert(facetable, null, values);
        } catch (SQLException e) {

        }
    }
    public void addfacetoken(FacePicture fp){
        try {
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(pictureid, fp.getImageId());
            values.put(path, fp.getImagePath());
            values.put(facetoken,fp.getfacetoken());
            values.put(category,fp.getCategory());
            db.insert(facetokentable, null, values);
        } catch (SQLException e) {

        }
    }


    public ArrayList<FacePicture> selectfp(){
        ArrayList<FacePicture>fplist=new ArrayList<FacePicture>();
        db = helper.getWritableDatabase();
        Cursor cursor=db.query(facetable,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String pid=cursor.getString(cursor.getColumnIndex(pictureid));
                String pt=cursor.getString(cursor.getColumnIndex(path));
                FacePicture fp=new FacePicture();
                fp.setImageId(pid);
                fp.setImagePath(pt);
                fplist.add(fp);
            }while(cursor.moveToNext());
            return fplist;
        }else{
            return null;}
    }
    public ArrayList<FacePicture> selectfacetoken(){
        ArrayList<FacePicture>fplist=new ArrayList<FacePicture>();
        db = helper.getWritableDatabase();
        Cursor cursor=db.query(facetokentable,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String pid=cursor.getString(cursor.getColumnIndex(pictureid));
                String pt=cursor.getString(cursor.getColumnIndex(path));
                String facetoken1=cursor.getString(cursor.getColumnIndex(facetoken));
                int category=cursor.getInt(cursor.getColumnIndex(this.category));
                FacePicture fp=new FacePicture();
                fp.setImageId(pid);
                fp.setImagePath(pt);
                fp.setfacetoken(facetoken1);
                fp.setCategory(category);
                fplist.add(fp);
            }while(cursor.moveToNext());
            return fplist;
        }else{
            return null;}
    }
    public void updatecategory(String pid,int category){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.category,category);//key为字段名，value为值
        db.update(facetokentable, values, pictureid+"=?", new String[]{pid});
        db.close();
    }
    public ArrayList<FacePicture> selectby(String id){

        ArrayList<FacePicture>fplist=new ArrayList<FacePicture>();
        db = helper.getWritableDatabase();
        Cursor cursor=db.query(facetable,null,pictureid+"=?",new String[]{id},null,null,null);
        if(cursor.moveToFirst()){
            do{
                String pid=cursor.getString(cursor.getColumnIndex(pictureid));
                String pt=cursor.getString(cursor.getColumnIndex(path));
                FacePicture fp=new FacePicture();
                fp.setImageId(pid);
                fp.setImagePath(pt);
                fplist.add(fp);
            }while(cursor.moveToNext());
            return fplist;
        }else{
            return null;}
    }
    public ArrayList<FacePicture> selectbyft(String id){

        ArrayList<FacePicture>fplist=new ArrayList<FacePicture>();
        db = helper.getWritableDatabase();
        Cursor cursor=db.query(facetokentable,null,pictureid+"=?",new String[]{id},null,null,null);
        if(cursor.moveToFirst()){
            do{
                String pid=cursor.getString(cursor.getColumnIndex(pictureid));
                String pt=cursor.getString(cursor.getColumnIndex(path));
                String facetk=cursor.getString(cursor.getColumnIndex(facetoken));
                int category=cursor.getInt(cursor.getColumnIndex(this.category));
                FacePicture fp=new FacePicture();
                fp.setImageId(pid);
                fp.setImagePath(pt);
                fp.setfacetoken(facetk);
                fp.setCategory(category);
                fplist.add(fp);
            }while(cursor.moveToNext());
            return fplist;
        }else{
            return null;}
    }



}