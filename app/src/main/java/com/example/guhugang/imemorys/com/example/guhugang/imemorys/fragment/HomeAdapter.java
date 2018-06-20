package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guhugang.example.guhugang.uploadfileservice.ShowCategoryActivity;
import com.example.guhugang.example.sqlite.DBDao;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.ShowFileItemActivity;
import com.example.guhugang.imemorys.ShowResultActivity;
import com.example.guhugang.imemorys.ShowTagActivity;
import com.example.guhugang.imemorys.TaggedImageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

/**
 * Created by GuHuGang on 2017/11/21.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
{
    Activity mContext;
    List<PhotoUpImageBucket<TaggedImageItem>> mDatas;
    List<Integer> heightList;
    private LayoutInflater inflater;
    DBDao dbDao;
    public boolean isneed=true;
//    RecyclerView recyclerView;
    public HomeAdapter(Activity mContext, List<PhotoUpImageBucket<TaggedImageItem>> mDatas, boolean isneed){
        this.mContext=mContext;
        this.mDatas=mDatas;
        this.isneed=isneed;
        inflater=LayoutInflater. from(mContext);
        dbDao=new DBDao(mContext);
//        heightList=new CopyOnWriteArrayList<>();
        if(isneed) {
            if (mDatas != null) {
                for (int i = 0; i < mDatas.size(); i++) {
                    int height = (int) (200 + Math.random() * 200);
                    heightList.add(height);
                }
            }
        }
    }
    public void changeData(List<PhotoUpImageBucket<TaggedImageItem>> mDatas){
        this.mDatas=mDatas;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.item_home,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        File file=new File(mDatas.get(position).getImageList().get(0).getImagePath());
        Glide.with(mContext)
                .load(file)
                .centerCrop()
                .into(holder.imageView);
        holder.tv.setText(mDatas.get(position).getBucketName());
        holder.num.setText(mDatas.get(position).getImageList().size()+"张");
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ShowTagActivity.class);
                intent.putExtra("imagelist", mDatas.get(position));
                intent.putExtra("bucketname",mDatas.get(position).getBucketName());
                mContext.startActivity(intent);
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
        TextView tv;
        TextView num;
        public MyViewHolder(View view)
        {
            super(view);
            imageView=(ImageView) view.findViewById(R.id.tag_img);
            tv = (TextView) view.findViewById(R.id.id_item);
            num=(TextView)view.findViewById(R.id.id_tag_num);
        }
    }
    public boolean fileIsExists(String strFlie) {
        try {
            File file = new File(strFlie);
            if (!file.exists()) {
               return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
