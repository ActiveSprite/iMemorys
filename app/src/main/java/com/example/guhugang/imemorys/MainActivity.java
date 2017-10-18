package com.example.guhugang.imemorys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.FragmentFirst;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.FragmentSelect;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initEvents();
        setSelect(0);
    }
    private void initEvents() {
        mTabCamera.setOnClickListener(this);
        mTabPhoto.setOnClickListener(this);
        mTabSetting.setOnClickListener(this);
        search.setOnClickListener(this);

    }

    private void initView() {
        mTabCamera = (LinearLayout)findViewById(R.id.id_tab_camera);
        mTabPhoto= (LinearLayout)findViewById(R.id.id_tab_photo);
        mTabSetting = (LinearLayout)findViewById(R.id.id_tab_setting);
        mImgWeixin = (ImageButton)findViewById(R.id.id_tab_weixin_img);
        mImgFrd = (ImageButton)findViewById(R.id.id_tab_frd_img);
        mImgAddress = (ImageButton)findViewById(R.id.id_tab_address_img);
        search = (ImageButton)findViewById(R.id.searchbutton);

    }

    @Override
    public void onClick(View v) {
        resetImg();
        switch (v.getId()) {
            case R.id.id_tab_camera://当点击微信按钮时，切换图片为亮色，切换fragment为微信聊天界面
                setSelect(0);
                break;
            case R.id.id_tab_photo:
                setSelect(1);
                break;
            case R.id.id_tab_setting:
                setSelect(2);
                break;

            case R.id.searchbutton:
                //setSelect(3);
                //Intent intent=new Intent(MemoryActivity.this,InputActivity.class);

                //startActivity(intent);
                break;
//		case R.id.buttontime:
//			setSelect(4);
//			break;
            default:

                break;
        }
    }
    private void setSelect(int i) {
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
                    transaction.add(R.id.id_content, tab01);//将微信聊天界面的Fragment添加到Activity中
                }else {
                    transaction.show(tab01);
                }
                mImgWeixin.setImageResource(R.drawable.camera_pressed);
                break;
            case 1:
                if (tab02 == null) {
                    tab02 = new FragmentFirst();
				/*
				 * 将Fragment添加到活动中，public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
				*containerViewId即为Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
				 * */
                    transaction.add(R.id.id_content, tab02);//将微信聊天界面的Fragment添加到Activity中
                }else {
                    transaction.show(tab02);
                }
                mImgFrd.setImageResource(R.drawable.pitcure_pressed);
                break;
            case 2:
                if (tab03 == null) {
                    tab03 = new FragmentSelect();
				/*
				 * 将Fragment添加到活动中，public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
				*containerViewId即为Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
				 * */
                    transaction.add(R.id.id_content, tab03);//将微信聊天界面的Fragment添加到Activity中
                }else {
                    transaction.show(tab03);
                }
                mImgAddress.setImageResource(R.drawable.more_pressed);
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

    }

    private void resetImg() {
        mImgWeixin.setImageResource(R.drawable.camera);
        mImgFrd.setImageResource(R.drawable.picture);
        mImgAddress.setImageResource(R.drawable.more);
        //mImgSetting.setImageResource(R.drawable.tab_settings_normal);
    }
}
