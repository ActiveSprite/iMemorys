package com.example.guhugang.moreused;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guhugang.example.guhugang.uploadfileservice.ShowCategoryActivity;
import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.HomeAdapter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by GuHuGang on 2017/11/27.
 */

public class ResultAdapter<T extends PhotoUpImageItem> extends RecyclerView.Adapter<ResultAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<T> mDatas;
    private LayoutInflater inflater;
    public ResultAdapter(Context mContext,ArrayList<T> mDatas){
        this.mContext=mContext;
        this.mDatas=mDatas;
        inflater=LayoutInflater. from(mContext);
    }
    @Override
    public ResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.album_item_adapter,parent, false);
        ResultAdapter.MyViewHolder holder= new ResultAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ResultAdapter.MyViewHolder holder, final int position)
    {
        File file = new File(mDatas.get(position).getImagePath()) ;
        Glide
                .with(mContext)
                .load(file)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;

        public MyViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.item);
        }
    }
}
