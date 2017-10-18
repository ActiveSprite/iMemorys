package com.example.guhugang.example.guhugang.uploadfileservice;

import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GuHuGang on 2017/6/17.
 */

public class FacePictureBucket extends PhotoUpImageBucket implements Serializable {
    public List<FacePicture> imageList;
//    public void setImageList(List<FacePicture> imageList){
//        this.imageList=imageList;
//    }
    public List<FacePicture> getImageList(){
        return imageList;
    }
}
