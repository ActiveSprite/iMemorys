package com.example.guhugang.moreused;

import android.util.LruCache;
import android.view.View;

import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.SwicherAdapter;
import com.shizhefei.view.largeimage.LargeImageView;

import java.util.ArrayList;

/**
 * Created by GuHuGang on 2017/10/23.
 */

public class ViewCache implements SwicherAdapter.onCompleteBitmap {
    ArrayList<PhotoUpImageItem> list;
    ArrayList<LargeImageView> mViews;
    public ViewCache(ArrayList<PhotoUpImageItem> list){
        this.list=list;
    }


    @Override
    public void onPositionArrived(int position) {

    }
}
