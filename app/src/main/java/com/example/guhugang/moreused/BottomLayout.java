package com.example.guhugang.moreused;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.guhugang.imemorys.R;

/**
 * Created by GuHuGang on 2017/11/20.
 */

public class BottomLayout extends LinearLayout {
    public LinearLayout edit;
    public LinearLayout reserve;
    public LinearLayout share;
    public LinearLayout delete;
    public LinearLayout more;

    public BottomLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.bottom_menu, this);
        initView();
    }
    public void setlistener(OnClickListener listener){
        edit.setOnClickListener(listener);
        reserve.setOnClickListener(listener);
        share.setOnClickListener(listener);
        delete.setOnClickListener(listener);
        more.setOnClickListener(listener);
    }
    public void initView(){
        edit=(LinearLayout)findViewById(R.id.edit);
        reserve=(LinearLayout)findViewById(R.id.reserve);
        share=(LinearLayout)findViewById(R.id.share);
        delete=(LinearLayout)findViewById(R.id.delete);
        more=(LinearLayout)findViewById(R.id.more);
    }


}
