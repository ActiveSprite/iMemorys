package com.example.guhugang.imemorys;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by guhug on 2018/5/9.
 */

public class SeletActivity extends AppCompatActivity {
    public Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selet_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar_select);
        toolbar.setTitle("选择移动的文件夹");
        toolbar.setTitleTextAppearance(this,R.style.Theme_ToolBar_Base_Subtitle);
        //设置导航图标要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
