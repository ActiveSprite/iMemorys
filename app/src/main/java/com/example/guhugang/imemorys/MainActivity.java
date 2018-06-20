package com.example.guhugang.imemorys;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.guhugang.example.guhugang.uploadfileservice.ShowCategoryActivity;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.AiFragment;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.ConstantState;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.FragmentFirst;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoFragment;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.SniorFragment;
import com.example.guhugang.moreused.DeletePopupWindow;
import com.example.guhugang.moreused.ShareDeleteView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    private Fragment tab01;
    private Fragment tab02;

    private Fragment tab03;


    private Toolbar toolbar;

    private DeletePopupWindow deletePopupWindow;
    private ShareDeleteView shareDeleteView;


    private List<String> titles;
    private List<Fragment> fragments;
    String path="/storage/emulated/0/DCIM/P60830-140022.jpg";

    ViewPager viewPager;
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    setItem(1);
                    return true;
                case R.id.navigation_notifications:
                    setItem(2);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("相簿");
        toolbar.setTitleTextAppearance(this,R.style.Theme_ToolBar_Base_Subtitle);
        //设置导航图标要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            Log.e("permission","denied");
        }


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initView();
        initEvents();
        initValue();
        String content = "2018年5月12日";

        String pattern = "\\d{1,4}\\D\\d{1,2}\\D\\d{1,2}.";
        String result = content.replaceAll("\\D","/");
        Log.i("result",result);
        boolean isMatch = Pattern.matches(pattern, content);
        Log.i("matcher",String.valueOf(isMatch));
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//               new GetPictureLocation(MainActivity.this).getLocationInfo(path);
//            }
//        }).start();
        this.startService(new Intent(this,ImageTagService.class));


    }
    private void initEvents() {

        shareDeleteView.setShareonClickListener(this);
        shareDeleteView.setDeleteonClickListener(this);
        ConstantState state=ConstantState.getInstance();
        state.setonStateChangedListener(new ConstantState.onStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {
                if(state)
                   shareDeleteView.setVisibility(View.VISIBLE);
                else
                   shareDeleteView.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void initView() {
        shareDeleteView=(ShareDeleteView)findViewById(R.id.s_d_view);
        deletePopupWindow=new DeletePopupWindow(this,this);
        viewPager = (ViewPager) findViewById(R.id.view_page);
        tab01=PhotoFragment.newInstance();
        tab02=FragmentFirst.newInstance();
        tab03= SniorFragment.newInstance();
        fragments=new ArrayList<>();
        fragments.add(tab01);
        fragments.add(tab02);
        fragments.add(tab03);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void initValue() {
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {

               @Override
                public void transformPage(View page, float position) {
                    //rollingPage(page,position);//调用翻页效果
                     imitateQQ(page,position);
                    //raised3D(page,position);
                    //sink3D(page,position);
                }
            });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigation.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        navigation.setSelectedItemId(R.id.navigation_dashboard);
                        break;
                    case 2:
                        navigation.setSelectedItemId(R.id.navigation_notifications);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void raised3D(View view,float position){
        if(position>=-1&&position<=1){
            view.setPivotX(position<0?view.getWidth():0);//设置要旋转的Y轴的位置
            view.setRotationY(90*position);//开始设置属性动画值
        }
    }
    public void imitateQQ(View view,float position){
        if(position>=-1&&position<=1){
            view.setPivotX(position>0?0:view.getWidth()/2);
            //view.setPivotY(view.getHeight()/2);
            view.setScaleX((float)((1-Math.abs(position)<0.5)?0.5:(1-Math.abs(position))));
            view.setScaleY((float)((1-Math.abs(position)<0.5)?0.5:(1-Math.abs(position))));
        }
    }
    public void rollingPage(View view,float position){
        if(position>=-1&&position<=1){
            view.setPivotX(0);
            if(position<0){
                view.setTranslationX(-position*view.getWidth());
                view.setRotationY(90*position);
                view.setScaleX(1-Math.abs(position));
            }
            else{
                view.setTranslationX(-position*view.getWidth());
            }

        }
    }
    public void sink3D(View view,float position){
        if(position>=-1&&position<=1){
            view.setPivotX(position<0?view.getWidth():0);
            view.setRotationY(-90*position);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.delete_bt:
                deletePopupWindow.showAtLocation(MainActivity.this.findViewById(R.id.container),
                        Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.action_search:

            default:

                break;
        }
    }
    private void setItem(int i){
        viewPager.setCurrentItem(i);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.jump_search:
                Intent intent=new Intent(this,SeachActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            this.startService(new Intent(this,ImageTagService.class));
            // Handle the camera action
        } else if (id == R.id.nav_face) {
            Intent intent=new Intent(MainActivity.this, ShowCategoryActivity.class);
            MainActivity.this.startActivity(intent);

        } else if (id == R.id.nav_searchpic) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_error) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                }else {
                    Toast.makeText(this,"你拒绝了读取磁盘权限！！",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }
}
