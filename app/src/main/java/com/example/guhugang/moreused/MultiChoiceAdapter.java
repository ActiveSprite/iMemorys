package com.example.guhugang.moreused;


import android.widget.BaseAdapter;

/**
 * Created by guhug on 2018/5/5.
 */

public abstract class MultiChoiceAdapter extends BaseAdapter {
    protected boolean mCheckable;
    public void setCheckable(boolean checkable) {
        mCheckable = checkable;
    }
}
