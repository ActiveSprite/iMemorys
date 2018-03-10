package com.example.guhugang.moreused;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.HomeAdapter;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;

import java.util.ArrayList;

/**
 * Created by GuHuGang on 2017/11/27.
 */

public abstract class BaseResultActivity <T extends PhotoUpImageItem>extends Activity{
    RecyclerView collection;
    public ArrayList<T> resultList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.base_result_activity);
        collection=(RecyclerView)findViewById(R.id.result_recycler);
        setResultList();

        collection.setLayoutManager(new GridLayoutManager(this,4));
        if(resultList!=null)
        collection.setAdapter(new ResultAdapter<T>(this,resultList));
    }
    public abstract void setResultList();


}
