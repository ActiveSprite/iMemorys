package com.example.guhugang.imemorys;

import android.content.Intent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by GuHuGang on 2018/1/14.
 */

public class ShowTagActivity extends com.example.guhugang.example.guhugang.uploadfileservice.AlbumItemActivity {



    @Override
    public void setData() {
        Intent intent = getIntent();
        photoUpImageBucket = (PhotoUpImageBucket<PhotoUpImageItem>) intent.getSerializableExtra("imagelist");
        PhotoUpImageItem item=(PhotoUpImageItem)photoUpImageBucket.getImageList().get(0);
        File file=new File(item.getImagePath());
        Glide
                .with(this)
                .load(file)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(toolbar_bg);

    }

    @Override
    public void setToolbarTitle() {
        Intent intent = getIntent();
        String bucketName=intent.getStringExtra("bucketname");
        if(bucketName!=null)
            toolbar.setTitle(bucketName);
    }


}

