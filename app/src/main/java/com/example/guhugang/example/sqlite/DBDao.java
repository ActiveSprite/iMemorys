package com.example.guhugang.example.sqlite;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;


import com.example.guhugang.example.guhugang.uploadfileservice.CollectImageItem;
import com.example.guhugang.example.guhugang.uploadfileservice.FacePicture;
import com.example.guhugang.imemorys.TaggedImageItem;
import com.example.guhugang.moreused.MyRect;

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
    String face_left="face_left";
    String face_top="face_top";
    String face_width="face_width";
    String face_height="face_height";

    String collection="collection";
    String src_path="src_path";
    String dest_path="dest_path";
    String pid="pid";

    String tagged_id="tagged_id";
    String tagged_path="tagged_path";
    String image_tag="image_tag";
    String imagetag_data="imagetag_data";
    String location="location";

    Context mContext;
    public DBDao(Context context) {
        mContext=context;
        helper = new DBHelper(context);

    }
    public void insertTag(TaggedImageItem taggedImageItem){

        try {
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(tagged_id, taggedImageItem.getImageId());
            values.put(tagged_path, taggedImageItem.getImagePath());
            values.put(image_tag, taggedImageItem.getTag());
            values.put(location,taggedImageItem.getLocation());
            db.insert(imagetag_data, null, values);
        } catch (SQLException e) {

        }finally {
            db.close();
        }

    }

    public ArrayList<TaggedImageItem> selectByTag(String tag){
        ArrayList<TaggedImageItem>Taglist=new ArrayList<TaggedImageItem>();
        db = helper.getWritableDatabase();
        String selection= image_tag+" like ?";
        String[] selectionargs={"%"+tag+"%"};
        Cursor cursor=db.query(imagetag_data,null,selection,selectionargs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String pid=cursor.getString(cursor.getColumnIndex(this.tagged_id));
                String tag_path=cursor.getString(cursor.getColumnIndex(this.tagged_path));
                String img_tag=cursor.getString(cursor.getColumnIndex(this.image_tag));
                TaggedImageItem tagImageItem=new TaggedImageItem();
                tagImageItem.setImageId(pid);
                tagImageItem.setImagePath(tag_path);
                tagImageItem.setTag(img_tag);
                Taglist.add(tagImageItem);
            }while(cursor.moveToNext());
            cursor.close();
            db.close();
            return Taglist;
        }else{
            return null;}

    }
    public ArrayList<TaggedImageItem> selectTagById(String id){
        ArrayList<TaggedImageItem>Taglist=new ArrayList<TaggedImageItem>();
        db = helper.getWritableDatabase();
        String selection= tagged_id+"=?";
        String[] selectionargs={id};
        Cursor cursor=db.query(imagetag_data,null,selection,selectionargs,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String pid=cursor.getString(cursor.getColumnIndex(this.tagged_id));
                String tag_path=cursor.getString(cursor.getColumnIndex(this.tagged_path));
                String img_tag=cursor.getString(cursor.getColumnIndex(this.image_tag));
                String location=cursor.getString(cursor.getColumnIndex(this.location));
                TaggedImageItem tagImageItem=new TaggedImageItem();
                tagImageItem.setImageId(pid);
                tagImageItem.setImagePath(tag_path);
                tagImageItem.setTag(img_tag);
                tagImageItem.setLocation(location);
                Taglist.add(tagImageItem);
            }while(cursor.moveToNext());
            cursor.close();
            db.close();
            return Taglist;
        }else{
            return null;}

    }
    public ArrayList<TaggedImageItem> selectTagByNull(){
        ArrayList<TaggedImageItem>Taglist=new ArrayList<TaggedImageItem>();
        db = helper.getWritableDatabase();

        Cursor cursor=db.query(imagetag_data,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String pid=cursor.getString(cursor.getColumnIndex(this.tagged_id));
                String tag_path=cursor.getString(cursor.getColumnIndex(this.tagged_path));
                String img_tag=cursor.getString(cursor.getColumnIndex(this.image_tag));
                String location=cursor.getString(cursor.getColumnIndex(this.location));
                TaggedImageItem tagImageItem=new TaggedImageItem();
                tagImageItem.setImageId(pid);
                tagImageItem.setImagePath(tag_path);
                tagImageItem.setTag(img_tag);
                tagImageItem.setLocation(location);
                Taglist.add(tagImageItem);

            }while(cursor.moveToNext());
            cursor.close();
            db.close();
            return Taglist;
        }else{
            return null;}

    }
    public void updatelocation(String tagged_id,String location){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.location,location);//key为字段名，value为值
        db.update(imagetag_data, values, this.tagged_id+"=?", new String[]{tagged_id});
        db.close();
    }

    public boolean deleteTaggedImage(String path){
        db=helper.getWritableDatabase();
        String[] s=new String[]{path};
        //db.execSQL("delete from facecategory where category=?",new Object[]{category});
        int flag=db.delete(imagetag_data, tagged_path+"=?", s);
        db.close();
        return flag>0;
    }

   public void addFacePicture(FacePicture fp) {
        try {
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(pictureid, fp.getImageId());
            values.put(path, fp.getImagePath());
            db.insert(facetable, null, values);
        } catch (SQLException e) {

        }finally {
            db.close();
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
            values.put(face_left,fp.getFaceLeft());
            values.put(face_top,fp.getFaceTop());
            values.put(face_width,fp.getFaceWidth());
            values.put(face_height,fp.getFaceHeight());

            db.insert(facetokentable, null, values);
        } catch (SQLException e) {

        } finally {
            db.close();
        }
    }

    public void addCollection(CollectImageItem collectImageItem){
        try{
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(pid, collectImageItem.getImageId());
            values.put(src_path, collectImageItem.getImagePath());
            values.put(dest_path,collectImageItem.getDest_path());
            db.insert(collection, null, values);
        }catch (SQLException e) {

        }finally {
            db.close();
        }
    }
    public ArrayList<CollectImageItem> selectCollectionItem(){
        ArrayList<CollectImageItem>collectionlist=new ArrayList<CollectImageItem>();
        db = helper.getWritableDatabase();
        Cursor cursor=db.query(collection,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String pid=cursor.getString(cursor.getColumnIndex(this.pid));
                String src_path=cursor.getString(cursor.getColumnIndex(this.src_path));
                String dest_path=cursor.getString(cursor.getColumnIndex(this.dest_path));
                CollectImageItem collectImageItem=new CollectImageItem();
                collectImageItem.setImageId(pid);
                collectImageItem.setImagePath(src_path);
                collectImageItem.setDest_path(dest_path);
                collectionlist.add(collectImageItem);
            }while(cursor.moveToNext());
            cursor.close();
            db.close();
            return collectionlist;
        }else{
            return null;}
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
            cursor.close();
            db.close();
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
                int left=cursor.getInt(cursor.getColumnIndex(face_left));
                int top=cursor.getInt(cursor.getColumnIndex(face_top));
                int width=cursor.getInt(cursor.getColumnIndex(face_width));
                int height=cursor.getInt(cursor.getColumnIndex(face_height));
                int category=cursor.getInt(cursor.getColumnIndex(this.category));
                Rect rect=new Rect(left,top,left+width,top+height);

                FacePicture fp=new FacePicture();
                fp.setImageId(pid);
                fp.setImagePath(pt);
                fp.setfacetoken(facetoken1);
                fp.rect=new MyRect(rect);
                fp.setCategory(category);

                fplist.add(fp);
            }while(cursor.moveToNext());
            cursor.close();
            db.close();
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
            cursor.close();
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
            cursor.close();
            return fplist;
        }else{
            return null;}
    }
    public void deleteFace(String picture_path){
        db=helper.getWritableDatabase();
        String[] s=new String[]{picture_path};
        //db.execSQL("delete from facecategory where category=?",new Object[]{category});
        db.delete(facetokentable, "path=?", s);
    }
    public void deleteFp(String path){
        db=helper.getWritableDatabase();
        String[] s=new String[]{path};
        //db.execSQL("delete from facecategory where category=?",new Object[]{category});
        db.delete(facetable, "path=?", s);
    }
    public void deleteCollectionItem(String path){
        db=helper.getWritableDatabase();
        String[] s=new String[]{path};
        //db.execSQL("delete from facecategory where category=?",new Object[]{category});
        db.delete(collection, "src_path=?", s);
    }

    public boolean deletePicture(String picture_id){
        deleteFace(picture_id);
        deleteFp(picture_id);
        deleteCollectionItem(picture_id);
        ContentResolver cr =mContext.getContentResolver();
        int re = cr.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "_id = ?",
                new String[]{picture_id}
        );

        if (re > 0) {
            return true;

        }
         return false;
    }
    public void deleteAll(String path){
        ContentResolver cr =mContext.getContentResolver();
        int re = cr.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "_data = ?",
                new String[]{path}
        );
        deleteCollectionItem(path);
        deleteFp(path);
        deleteFace(path);
        deleteTaggedImage(path);
    }
}