package com.example.guhugang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.guhugang.imemorys.R;

/**
 * Created by GuHuGang on 2018/2/8.
 */

public class BaseItemView extends LinearLayout {
    public LinearLayout LocationItem;
    public LinearLayout CollectionItem;
    public BaseItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.base_item, this);
        LocationItem=(LinearLayout)findViewById(R.id.id_location);
        CollectionItem=(LinearLayout)findViewById(R.id.id_collection);

    }
    public void setLocationItemListener(OnClickListener listener){
        LocationItem.setOnClickListener(listener);
    }
    public void setCollectionItemListener(OnClickListener listener){
        LocationItem.setOnClickListener(listener);
    }
}
