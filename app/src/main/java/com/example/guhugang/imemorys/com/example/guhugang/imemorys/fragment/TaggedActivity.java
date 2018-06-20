package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import com.example.guhugang.imemorys.R;

/**
 * Created by GuHuGang on 2018/2/3.
 */

public class TaggedActivity extends AppCompatActivity {
    Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tagged_activty);
        toolbar = (Toolbar) findViewById(R.id.location_toolbar);
        toolbar.setTitle("事物");
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
