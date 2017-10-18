package com.example.guhugang.moreused;

import android.content.Intent;
import android.os.Bundle;


import com.example.guhugang.imemorys.PhotoUpImageItem;

import java.util.ArrayList;

/**
 * Created by GuHuGang on 2017/5/21.
 */

public class ShowGalleryActivity extends GalleryActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    public void receiveparams(){
        Intent intent = getIntent();
        imglist = (ArrayList<PhotoUpImageItem>) intent.getSerializableExtra("imagelist");
        position=getIntent().getIntExtra("position", 0);
        mLocationX = getIntent().getIntExtra("locationX", 0);
        mLocationY = getIntent().getIntExtra("locationY", 0);
        mWidth = getIntent().getIntExtra("width", 0);
        mHeight = getIntent().getIntExtra("height", 0);

    }
}
