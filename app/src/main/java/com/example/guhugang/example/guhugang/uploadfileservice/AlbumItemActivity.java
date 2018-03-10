package com.example.guhugang.example.guhugang.uploadfileservice;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;

public abstract class AlbumItemActivity<T extends PhotoUpImageBucket> extends AppCompatActivity {
    private GridView gridView;
    public T photoUpImageBucket;
    private AlbumItemAdapter adapter;
    public Toolbar toolbar;
    public ImageView toolbar_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.album_item_images);
        toolbar = (Toolbar) findViewById(R.id.toolbar_file);
        toolbar_bg=(ImageView)findViewById(R.id.toolbar_bg);
        toolbar.setTitle("文件夹");
        setToolbarTitle();
        toolbar.setTitleTextAppearance(this,R.style.Theme_ToolBar_Base_Subtitle);
        //设置导航图标要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        init();

    }
    private void init(){
        gridView = (GridView) findViewById(R.id.album_item_gridv);
        ViewCompat.setNestedScrollingEnabled(gridView,true);
        setData();
        if(photoUpImageBucket!=null) {
            Log.i("蛤蟆皮","hhj");
            adapter = new AlbumItemAdapter(photoUpImageBucket.getImageList(), AlbumItemActivity.this);
            gridView.setAdapter(adapter);
        }
    }
    public abstract void setData();
    public abstract void setToolbarTitle();
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
