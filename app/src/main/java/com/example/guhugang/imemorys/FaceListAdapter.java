package com.example.guhugang.imemorys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guhugang.example.guhugang.uploadfileservice.FacePicture;
import com.example.guhugang.example.guhugang.uploadfileservice.FacePictureBucket;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.HomeAdapter;
import com.example.guhugang.moreused.ImageResizer;
import com.example.guhugang.moreused.MyRect;

import java.io.File;
import java.util.List;

/**
 * Created by GuHuGang on 2018/1/15.
 */

public class FaceListAdapter extends RecyclerView.Adapter<FaceListAdapter.MyViewHolder>{
    List<FacePictureBucket> mDatas;
    Context mContext;
    private LayoutInflater inflater;
    ImageResizer imageResizer;

    public FaceListAdapter(Context mContext,List<FacePictureBucket> fpblist){

        this.mContext=mContext;
        this.mDatas=fpblist;
        inflater=LayoutInflater. from(mContext);
        imageResizer=new ImageResizer();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.face_list_item,parent, false);
        FaceListAdapter.MyViewHolder holder= new FaceListAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String path=mDatas.get(position).getImageList().get(0).getImagePath();
        File file=new File(path);
        final MyRect rect=mDatas.get(position).getImageList().get(0).rect;

//        Glide.with(mContext)
//                .load(file)
//                .into(holder.imageView);
        imageResizer.AsyncGetBitmap(path, new ImageResizer.ResizerCompleteCallBack() {
            @Override
            public void resizerComplete(Bitmap bitmap) {
                int width=bitmap.getWidth();
                int height=bitmap.getHeight();
                if(rect.left<0){
                    rect.left=0;
                }
                if(rect.top<0){
                    rect.top=0;
                }
                if(rect.bottom>height){
                    rect.bottom=height;
                }
                if(rect.right>width){
                    rect.right=width;
                }
               Bitmap facebitmap= Bitmap.createBitmap(bitmap, rect.left,rect.top,rect.width(),rect.height(), null, false);
               holder.imageView.setImageBitmap(facebitmap);
            }
        });

        String facename=mDatas.get(position).getBucketName();
        if(facename!=null&& TextUtils.isEmpty(facename)) {
            holder.tv.setText(mDatas.get(position).getBucketName());
        }
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
    public int getItemCount() {
        if(mDatas==null)return 0;
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv;
        TextView num;
        public MyViewHolder(View view)
        {
            super(view);
            imageView=(ImageView) view.findViewById(R.id.id_face_example);
            tv = (TextView) view.findViewById(R.id.id_face_name);
            num=(TextView)view.findViewById(R.id.id_face_num);
        }
    }
}
