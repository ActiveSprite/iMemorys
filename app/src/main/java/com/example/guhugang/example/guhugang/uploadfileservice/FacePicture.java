package com.example.guhugang.example.guhugang.uploadfileservice;

import com.example.guhugang.imemorys.PhotoUpImageItem;

import java.io.Serializable;

/**
 * Created by GuHuGang on 2017/4/13.
 */

public class FacePicture  extends PhotoUpImageItem implements Serializable {
    private String facetoken;
    private int category;
    public String Buffer;

    public void setfacetoken(String facetoken){
        this.facetoken=facetoken;
    }
    public String getfacetoken(){
        return facetoken;
    }
    public void setCategory(int category){this.category=category;}
    public int getCategory(){return this.category;}
    public String toString(){
       Buffer="pictureid:" +getImageId()+" "+"facetoken:"+facetoken+" "+"category:"+category;
        return Buffer;
    }
}
