package com.example.guhugang.imemorys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guhugang.example.guhugang.uploadfileservice.*;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;

public class ShowFileItemActivity extends com.example.guhugang.example.guhugang.uploadfileservice.AlbumItemActivity {



    @Override
    public void setData() {
        Intent intent = getIntent();
        photoUpImageBucket = (PhotoUpImageBucket) intent.getSerializableExtra("imagelist");
    }
}
