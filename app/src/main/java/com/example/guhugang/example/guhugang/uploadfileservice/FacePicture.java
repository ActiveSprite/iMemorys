package com.example.guhugang.example.guhugang.uploadfileservice;

import android.graphics.Rect;

import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.moreused.MyRect;

import java.io.Serializable;

/**
 * Created by GuHuGang on 2017/4/13.
 */

public class FacePicture  extends PhotoUpImageItem implements Serializable {
    private String facetoken;
    private int category;
    public String Buffer;
    public MyRect rect;

    public void setfacetoken(String facetoken){
        this.facetoken=facetoken;
    }
    public String getfacetoken(){
        return facetoken;
    }
    public void setCategory(int category){this.category=category;}
    public int getCategory(){return this.category;}
    public int getFaceTop(){
        if(rect!=null)
            return rect.top;
        return 0;
    }
    public int getFaceLeft(){
        if(rect!=null)
            return rect.left;
        return 0;
    }
    public int getFaceWidth(){
        if(rect!=null)
            return rect.width();
        return 0;
    }
    public int getFaceHeight(){
        if(rect!=null)
            return rect.height();
        return 0;
    }
    public String toString(){
       Buffer="pictureid:" +getImageId()+" "+"facetoken:"+facetoken+" "+"category:"+category;
        return Buffer;
    }

}
