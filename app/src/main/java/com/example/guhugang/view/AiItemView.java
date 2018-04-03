package com.example.guhugang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.guhugang.imemorys.R;

/**
 * Created by GuHuGang on 2018/2/8.
 */

public class AiItemView extends LinearLayout {
    public LinearLayout faceItem;
    public LinearLayout foodItem;
    public LinearLayout paperItem;
    public LinearLayout thingsItem;
    public AiItemView(Context context,AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.ai_item, this);
        initView();
    }
    public void initView(){
        faceItem=(LinearLayout)findViewById(R.id.id_face);
        foodItem=(LinearLayout)findViewById(R.id.id_food);
        paperItem=(LinearLayout)findViewById(R.id.id_paper);
        thingsItem=(LinearLayout)findViewById(R.id.id_things);
    }
    public void setItemOnclickListener(OnClickListener listener){
        faceItem.setOnClickListener(listener);
        foodItem.setOnClickListener(listener);
        paperItem.setOnClickListener(listener);
        thingsItem.setOnClickListener(listener);
    }
}
