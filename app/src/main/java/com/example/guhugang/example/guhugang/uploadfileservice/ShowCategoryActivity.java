package com.example.guhugang.example.guhugang.uploadfileservice;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.guhugang.imemorys.FaceListAdapter;

import java.util.List;

/**
 * Created by GuHuGang on 2017/6/17.
 */

public class ShowCategoryActivity extends CategoryActivity<FacePictureBucket> {
    LoadPicture loadPicture;
    @Override
    public void loadData() {
       loadPicture=new LoadPicture(this);
        loadPicture.scanImages(new LoadPicture.ScanCompleteCallBack(){
            public void scanComplete(List<FacePictureBucket> fpblist){
                if(fpblist!=null) {
                    adapter = new FaceListAdapter(ShowCategoryActivity.this, fpblist);
                    listView.setAdapter(adapter);
                }
            }
        });
    }


}
