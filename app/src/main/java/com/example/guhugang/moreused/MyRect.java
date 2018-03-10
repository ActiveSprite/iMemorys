package com.example.guhugang.moreused;

import android.graphics.Rect;

import java.io.Serializable;

/**
 * Created by GuHuGang on 2018/1/22.
 */

public class MyRect implements Serializable {
    public int left;
    public int top;
    public int right;
    public int bottom;
    public MyRect(Rect rect){
        this.left=rect.left;
        this.top=rect.top;
        this.right=rect.right;
        this.bottom=rect.bottom;
    }
    public int width(){
        return right-left;
    }
    public int height(){
        return bottom-top;
    }
}

