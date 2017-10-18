package com.example.guhugang.example.guhugang.uploadfileservice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.GridView;

import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;

public abstract class AlbumItemActivity<T extends PhotoUpImageBucket> extends Activity {
    private GridView gridView;
    public T photoUpImageBucket;
    private AlbumItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.album_item_images);
        init();

    }
    private void init(){
        gridView = (GridView) findViewById(R.id.album_item_gridv);
        setData();
        if(photoUpImageBucket!=null) {
            Log.i("蛤蟆皮","hhj");
            adapter = new AlbumItemAdapter(photoUpImageBucket.getImageList(), AlbumItemActivity.this);
            gridView.setAdapter(adapter);
        }
    }
    public abstract void setData();


}
