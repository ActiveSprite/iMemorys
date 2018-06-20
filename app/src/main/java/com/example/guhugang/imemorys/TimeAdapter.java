package com.example.guhugang.imemorys;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhug on 2018/5/15.
 */

public class TimeAdapter extends BaseAdapter implements Filterable {
    MyFilter mFilter;
    List<PhotoUpImageBucket<PhotoUpImageItem>>currentData;
    List<PhotoUpImageBucket<PhotoUpImageItem>>datas;
    Context mContext;
    public TimeAdapter(Context context,List<PhotoUpImageBucket<PhotoUpImageItem>>backupData){
        this.mContext=context;
        this.datas=backupData;
    }
    @Override
    public int getCount() {
        if(currentData!=null){
            return currentData.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(currentData!=null)return currentData.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_popup_list,parent,false);
        ImageView img=(ImageView)convertView.findViewById(R.id.item_img);
        TextView tag=(TextView)convertView.findViewById(R.id.item_text);
        if(currentData!=null) {
            Glide.with(mContext).load(currentData.get(position).getImageList().get(0).getImagePath()).into(img);
            tag.setText(currentData.get(position).getBucketName());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ShowTagActivity.class);
                intent.putExtra("imagelist", currentData.get(position));
                intent.putExtra("bucketname",currentData.get(position).getBucketName());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter ==null){
            mFilter = new MyFilter();
        }
        return mFilter;
    }
    class MyFilter extends Filter{
        //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<PhotoUpImageBucket<PhotoUpImageItem>> list ;
            if (TextUtils.isEmpty(charSequence)){//当过滤的关键字为空的时候，我们则显示所有的数据
                list  = datas;
            }else {//否则把符合条件的数据对象添加到集合中
                list = new ArrayList<>();
                if(datas!=null) {
                    for (PhotoUpImageBucket<PhotoUpImageItem> recomend : datas) {
                        if (recomend.getBucketName().contains(charSequence)) {
                            list.add(recomend);
                        }
                    }
                }
            }
            result.values = list; //将得到的集合保存到FilterResults的value变量中
            result.count = list.size();//将集合的大小保存到FilterResults的count变量中
            return result;
        }
        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            currentData = (List<PhotoUpImageBucket<PhotoUpImageItem>>)filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();//通知数据发生了改变
            }else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }
    }
}
