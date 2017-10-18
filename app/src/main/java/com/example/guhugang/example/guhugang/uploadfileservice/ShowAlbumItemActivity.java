package com.example.guhugang.example.guhugang.uploadfileservice;

import android.content.Intent;

public class ShowAlbumItemActivity extends AlbumItemActivity {


    @Override
    public void setData() {
        Intent intent = getIntent();
        photoUpImageBucket = (FacePictureBucket) intent.getSerializableExtra("imagelist");
    }
}
