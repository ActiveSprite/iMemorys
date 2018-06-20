package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.guhugang.imemorys.PhotoUpImageItem;

import java.util.ArrayList;

/**
 * Created by GuHuGang on 2017/10/23.
 */

public class MediaAdapter extends FragmentStatePagerAdapter {
    ArrayList<PhotoUpImageItem> list;
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    public Fragment currentFragment;

    public MediaAdapter(FragmentManager fm,ArrayList<PhotoUpImageItem> list) {
        super(fm);
        this.list=list;
    }
    @Override
    public Fragment getItem(int position){
        String path=list.get(position).getImagePath();
        String id=list.get(position).getImageId();
        return new PictureFragment().newInstance(path,id,position);
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentFragment = (PictureFragment) object;
        super.setPrimaryItem(container, position, object);
    }


    public void swapDataSet(ArrayList<PhotoUpImageItem> media) {
        this.list = media;
        notifyDataSetChanged();
    }

    @Override public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }


    @Override public int getCount() {
        return list.size();
    }
}
