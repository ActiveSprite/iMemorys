package com.example.guhugang.example.guhugang.uploadfileservice;

import android.content.Intent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.guhugang.imemorys.PhotoUpImageItem;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class ShowAlbumItemActivity extends AlbumItemActivity {


    @Override
    public void setData() {
        Intent intent = getIntent();
        photoUpImageBucket = (FacePictureBucket) intent.getSerializableExtra("imagelist");
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
