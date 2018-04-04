package com.example.guhugang.moreused;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.view.View;
import android.view.ViewGroup;


import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PictureFragment;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.seles.tusdk.FilterManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by GuHuGang on 2017/5/21.
 */

public class ShowGalleryActivity extends GalleryActivity {

    public static void startWithElement(Activity context, ArrayList<PhotoUpImageItem> imglist,
                                        int firstIndex, View view) {
        Intent intent = new Intent(context, ShowGalleryActivity.class);
        intent.putExtra("imagelist", imglist);
        intent.putExtra("position", firstIndex);
        ActivityOptionsCompat compat = null;
        if (view == null) {
            compat = ActivityOptionsCompat.makeSceneTransitionAnimation(context);
        } else {
            compat = ActivityOptionsCompat.makeSceneTransitionAnimation(context, view,
                    "tansition_view");
        }
        ActivityCompat.startActivity(context, intent, compat.toBundle());
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setEnterSharedElementCallback(new SharedElementCallback() {

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                PictureFragment pictureFragment=(PictureFragment)adapter.currentFragment;
                if (pictureFragment == null) {
                    return;
                }
                View sharedView = pictureFragment.getView().findViewById(R.id.layer_bottom);
                sharedElements.clear();
                sharedElements.put("tansition_view", sharedView);
            }
        });

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
