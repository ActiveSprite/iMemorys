package com.example.guhugang.imemorys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoFragment;
import com.example.guhugang.moreused.GalleryActivity;
import com.example.guhugang.moreused.ShowGalleryActivity;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.seles.tusdk.FilterManager;

import java.util.ArrayList;

/**
 * Created by GuHuGang on 2018/1/7.
 */

public class PhotoFragmentActivity extends GalleryActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    private FilterManager.FilterManagerDelegate mFilterManagerDelegate = new FilterManager.FilterManagerDelegate()
    {
        @Override
        public void onFilterManagerInited(FilterManager manager)
        {
            TuSdk.messageHub().showSuccess(PhotoFragmentActivity.this, "初始化完成");
        }
    };
    public void receiveparams(){
        Intent intent = getIntent();
        imglist = (ArrayList<PhotoUpImageItem>) intent.getSerializableExtra("imagelist");
        position=getIntent().getIntExtra("position", 0);
        Log.i("photoPosition",String.valueOf(position));
        mLocationX = getIntent().getIntExtra("locationX", 0);
        mLocationY = getIntent().getIntExtra("locationY", 0);
        mWidth = getIntent().getIntExtra("width", 0);
        mHeight = getIntent().getIntExtra("height", 0);

    }
}
