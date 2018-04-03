package com.example.guhugang.imemorys;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_camera,
            R.drawable.ic_perm_media,
            R.drawable.ic_book
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

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        requestPermission();
        initView();
        initEvents();
        initValue();
        new Thread(new Runnable() {
            @Override
            public void run() {
               new GetPictureLocation(MainActivity.this).getLocationInfo(path);
            }
        }).start();

//        searchFragment = new SearchFragment();
				/*
				 * 将Fragment添加到活动中，public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
				*containerViewId即为Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
				 * */


//        transaction.add(R.id.id_content, searchFragment);
          this.startService(new Intent(this,ImageTagService.class));
//        transaction.commit();


//        setSelect(0);

        //startService(new Intent(MainActivity.this,UploadService.class));
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
        tabLayout = (TabLayout) findViewById(R.id.id_tab);
        tab01=PhotoFragment.newInstance();
        tab02=FragmentFirst.newInstance();
        tab03= AiFragment.newInstance();

    }
    private void initValue() {
        fragments = new ArrayList<>();
        fragments.add(tab01);
        fragments.add(tab02);
        fragments.add(tab03);

        titles = new ArrayList<>();
        titles.add("One");
        titles.add("Two");
        titles.add("Three");

        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setCustomView(getTabView(0));
        tabLayout.getTabAt(1).setCustomView(getTabView(1));
        tabLayout.getTabAt(2).setCustomView(getTabView(2));
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.delete_bt:
                //setSelect(3);
                deletePopupWindow.showAtLocation(MainActivity.this.findViewById(R.id.container),
                        Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.action_search:

//		case R.id.buttontime:
//			setSelect(4);
//			break;
            default:

                break;
        }
    }
    private void setSelect(int i) {
        resetImg();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();//创建一个事务
        hideFragment(transaction);//我们先把所有的Fragment隐藏了，然后下面再开始处理具体要显示的Fragment
        switch (i) {
            case 0:
                if (tab01 == null) {
                    tab01 = new PhotoFragment();
				/*
				 * 将Fragment添加到活动中，public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
				*containerViewId即为Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
				 * */
 //                   transaction.add(R.id.id_content, tab01);//将微信聊天界面的Fragment添加到Activity中
                }else {
                    transaction.show(tab01);
                }
                mImgWeixin.setImageResource(R.drawable.cameral_pressed);
                states=0;
                break;
            case 1:
                if (tab02 == null) {
                    tab02 = new FragmentFirst();
				/*
				 * 将Fragment添加到活动中，public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
				*containerViewId即为Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
				 * */
 //                   transaction.add(R.id.id_content, tab02);//将微信聊天界面的Fragment添加到Activity中
                }else {
                    transaction.show(tab02);
                }
                mImgFrd.setImageResource(R.drawable.picture_pressed);
                states=1;
                break;
            case 2:
                if (tab03 == null) {
//                    tab03 = new FragmentSelect();
				/*
				 * 将Fragment添加到活动中，public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
				*containerViewId即为Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
				 * */
 //                   transaction.add(R.id.id_content, tab03);//将微信聊天界面的Fragment添加到Activity中
                }else {
                    transaction.show(tab03);
                }
                mImgAddress.setImageResource(R.drawable.more_pressed);
                states=2;
                break;
            case 3:
                if (searchFragment == null) {
                    searchFragment = new SearchFragment();
				/*
				 * 将Fragment添加到活动中，public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
				*containerViewId即为Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
				 * */
//                    transaction.add(R.id.id_content, searchFragment);//将微信聊天界面的Fragment添加到Activity中
                }else {
                    transaction.show(searchFragment);
                }

                break;
            default:
                break;
        }
        transaction.commit();//提交事务
    }

    /*
     * 隐藏所有的Fragment
     * */
    private void hideFragment(FragmentTransaction transaction) {
        if (tab01 != null) {
            transaction.hide(tab01);
        }
        if (tab02 != null) {
            transaction.hide(tab02);
        }
        if (tab03 != null) {
            transaction.hide(tab03);
        }
        if(searchFragment!=null){
            transaction.hide(searchFragment);
        }

    }

    private void resetImg() {
        mImgWeixin.setImageResource(R.drawable.cameral);
        mImgFrd.setImageResource(R.drawable.picture);
        mImgAddress.setImageResource(R.drawable.more);
        //mImgSetting.setImageResource(R.drawable.tab_settings_normal);
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
                Log.i("querry",newText);
 //               setSelect(3);
                if (!TextUtils.isEmpty(newText)){
                    if(searchFragment!=null){

 //                       searchFragment.OnTagChanged(newText);
                    }

                }else{
 //                   setSelect(states);
                }
                return false;
            }
        });
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
    public void requestPermission(){
        if (PermissionsUtil.hasPermission(this, Manifest.permission.CAMERA)) {
            //有访问摄像头的权限
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                    READ_EXTERNAL_STORAGE=true;
                }


                @Override
                public void permissionDenied(@NonNull String[] permissions) {

                }
            }, new String[]{Manifest.permission.CAMERA});
        }
    }
}
