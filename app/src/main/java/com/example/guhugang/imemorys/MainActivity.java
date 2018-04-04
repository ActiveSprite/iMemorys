package com.example.guhugang.imemorys;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.guhugang.example.guhugang.uploadfileservice.ShowCategoryActivity;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.AiFragment;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.ConstantState;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.FragmentFirst;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoFragment;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.SearchFragment;
import com.example.guhugang.moreused.DeletePopupWindow;
import com.example.guhugang.moreused.ShareDeleteView;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private LinearLayout mTabCamera;
    private LinearLayout mTabPhoto;
    private LinearLayout mTabSetting;
    //底部4个导航控件中的图片按钮
    private ImageButton mImgWeixin;
    private ImageButton mImgFrd;
    private ImageButton mImgAddress;
    private ImageButton mImgSetting;
    private ImageButton search;
    private Fragment tab01;
    private Fragment tab02;

    private Fragment tab03;
    private SearchFragment searchFragment;

    private Toolbar toolbar;

    private DeletePopupWindow deletePopupWindow;
    private ShareDeleteView shareDeleteView;
    SearchView searchView;

    private int states=0;

    private List<String> titles;
    private List<Fragment> fragments;
    String path="/storage/emulated/0/DCIM/P60830-140022.jpg";

    private boolean READ_EXTERNAL_STORAGE=false;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initView();
        initEvents();
        initValue();


        new Thread(new Runnable() {
            @Override
            public void run() {
               new GetPictureLocation(MainActivity.this).getLocationInfo(path);
            }
        }).start();

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
        tab03= AiFragment.newInstance();
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
}
