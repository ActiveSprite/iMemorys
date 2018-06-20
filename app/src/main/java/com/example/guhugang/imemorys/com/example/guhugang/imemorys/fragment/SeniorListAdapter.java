package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guhugang.example.guhugang.uploadfileservice.AlbumItemAdapter;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.view.CircleCheckBox;

import java.util.List;

/**
 * Created by guhug on 2018/6/6.
 */

public class SeniorListAdapter extends BaseAdapter {
    List<SeniorData> dataList;
    private LayoutInflater layoutInflater;
    Context mContext;
    public SeniorListAdapter(List<SeniorData> list,Context context){
        dataList=list;
        layoutInflater = LayoutInflater.from(context);
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return dataList==null?0:dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList==null?null:dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            convertView=layoutInflater.inflate(R.layout.senior_grid_item,parent,false);
            holder=new Holder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.senior_img);
            holder.title=(TextView) convertView.findViewById(R.id.id_senior_title);
            holder.number=(TextView)convertView.findViewById(R.id.id_senior_num);
            convertView.setTag(holder);
        }else {
            holder=(Holder)convertView.getTag();
        }
        String t=dataList.get(position).title;
        int n=dataList.get(position).num;
        holder.title.setText(t);
        holder.number.setText(String.valueOf(n));
        Glide.with(mContext).load(dataList.get(position).path).into(holder.imageView);
        return convertView;
    }


    class Holder{
        ImageView imageView;
        TextView title;
        TextView number;
    }
}
