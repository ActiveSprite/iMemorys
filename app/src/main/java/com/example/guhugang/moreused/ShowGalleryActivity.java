package com.example.guhugang.moreused;

import android.content.Intent;
import android.os.Bundle;


import com.example.guhugang.imemorys.PhotoUpImageItem;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.seles.tusdk.FilterManager;

import java.util.ArrayList;

/**
 * Created by GuHuGang on 2017/5/21.
 */

public class ShowGalleryActivity extends GalleryActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    private FilterManager.FilterManagerDelegate mFilterManagerDelegate = new FilterManager.FilterManagerDelegate()
    {
        @Override
        public void onFilterManagerInited(FilterManager manager)
        {
            TuSdk.messageHub().showSuccess(ShowGalleryActivity.this, "初始化完成");
        }
    };
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
