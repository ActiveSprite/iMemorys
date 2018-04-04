package com.example.guhugang.example.guhugang.uploadfileservice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;

import com.example.guhugang.imemorys.FaceListAdapter;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.HomeAdapter;

import java.util.List;

/**
 * Created by GuHuGang on 2017/6/17.
 */

public abstract class CategoryActivity<A> extends AppCompatActivity {
    RecyclerView listView;
    public FaceListAdapter adapter;
    private Toolbar toolbar;
    //private PhotoUpAlbumHelper photoUpAlbumHelper;
    public List<A> list;
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.category_activity);
          initView();

    }
    public void onStart(){
        super.onStart();
        loadData();
    }
    public void initView(){
        toolbar = (Toolbar) findViewById(R.id.face_ui_toolbar);
        toolbar.setTitle("面孔");
        toolbar.setTitleTextAppearance(this,R.style.Theme_ToolBar_Base_Subtitle);
        //设置导航图标要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        listView=(RecyclerView)findViewById(R.id.id_face_list);
        listView.setLayoutManager(new GridLayoutManager(this,3));

    }
    public abstract void loadData();
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
