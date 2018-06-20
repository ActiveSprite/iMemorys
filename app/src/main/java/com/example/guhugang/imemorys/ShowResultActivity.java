package com.example.guhugang.imemorys;

import android.content.Intent;

import com.example.guhugang.example.guhugang.uploadfileservice.CollectImageItem;
import com.example.guhugang.example.sqlite.DBDao;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;
import com.example.guhugang.moreused.BaseResultActivity;

import java.util.List;

/**
 * Created by GuHuGang on 2017/11/27.
 */

public class ShowResultActivity extends com.example.guhugang.example.guhugang.uploadfileservice.AlbumItemActivity{

    @Override
    public void setData() {
        DBDao dbDao=new DBDao(this);
        List<CollectImageItem>items= dbDao.selectCollectionItem();
        Intent intent = getIntent();
        PhotoUpImageBucket bucket=new PhotoUpImageBucket();
        bucket.count=items.size();
        bucket.bucketName="收藏夹";
        bucket.setImageList(items);
        photoUpImageBucket =bucket;
    }

    @Override
    public void setToolbarTitle() {
        toolbar.setTitle("收藏夹");
    }
}
