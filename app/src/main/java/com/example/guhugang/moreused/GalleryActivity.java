package com.example.guhugang.moreused;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.example.guhugang.example.guhugang.uploadfileservice.ReadText;
import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.SwicherAdapter;

import java.util.ArrayList;

/**
 * Created by GuHuGang on 2017/5/21.
 */

public abstract class GalleryActivity extends Activity implements View.OnClickListener {
    ViewPager viewpager;
    private int tag;
    private FrameLayout fl;
    View bottom;
    View top;
    ArrayList<PhotoUpImageItem> imglist=null;
    int position;
    int mLocationX;
    int mLocationY ;
    int mWidth;
    int mHeight;
    LinearLayout share;
    ReadText readtext;
    SwicherAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_bigphoto);
        viewpager=(ViewPager) findViewById(R.id.vp);
        bottom=(View)findViewById(R.id.bottom_menu);
        top=(View)findViewById(R.id.top_menu);
        fl=(FrameLayout) findViewById(R.id.fl);
        receiveparams();
        initview();
    }
    public abstract void receiveparams();
    public void initview(){
        if(imglist!=null){
            adapter=new SwicherAdapter(imglist,this);
            adapter.setview(bottom,top);
            adapter.setpositon(position);
            adapter.setprams(mWidth,mHeight,mLocationX,mLocationY);
            viewpager.setAdapter(adapter);
            viewpager.setCurrentItem(position);
        }
        share=(LinearLayout)findViewById(R.id.share);
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public void onPause(){
        super.onPause();
        //GalleryActivity.this.overridePendingTransition(R.anim.activity_out, 0);
    }
}
