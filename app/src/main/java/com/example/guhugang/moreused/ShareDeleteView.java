package com.example.guhugang.moreused;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.guhugang.imemorys.R;

/**
 * Created by GuHuGang on 2017/11/14.
 */

public class ShareDeleteView extends LinearLayout{
    public ImageButton share_bt;
    public ImageButton delete_bt;
    public ShareDeleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.share_delete_bottom, this);
        share_bt=(ImageButton)findViewById(R.id.share_bt);
        delete_bt=(ImageButton)findViewById(R.id.delete_bt);
    }
    public void setShareonClickListener(OnClickListener listener){
        share_bt.setOnClickListener(listener);
    }
    public void setDeleteonClickListener(OnClickListener listener){
        delete_bt.setOnClickListener(listener);
    }
}
