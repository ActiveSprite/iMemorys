package com.example.guhugang.imemorys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoFragment;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.SearchFragment;

/**
 * Created by GuHuGang on 2018/2/9.
 */

public class SeachActivity extends AppCompatActivity {
    SearchView searchView;
    Toolbar toolbar;
    SearchFragment searchFragment;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_activty);
        toolbar = (Toolbar) findViewById(R.id.search_bar_toolbar);
        //设置导航图标要在setSupportActionBar方法之后
        toolbar.setTitle("");
        toolbar.setTitleTextAppearance(this,R.style.Theme_ToolBar_Base_Subtitle);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        init();
    }
    public void init(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();//创建一个事务
        if (searchFragment == null) {
            searchFragment = new SearchFragment();
            transaction.add(R.id.search_content, searchFragment);//将微信聊天界面的Fragment添加到Activity中
        }else {
            transaction.show(searchFragment);
        }
        transaction.commit();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search_toolbar,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        handleSearchEvent();
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    public void handleSearchEvent(){

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    if (searchFragment != null) {
                        searchFragment.OnTagChanged(newText);
                    }
                }
                    return false;

            }
        });

    }
  public void onPause(){
        super.onPause();
        overridePendingTransition(0,0);
  }
}
