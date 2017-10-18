package com.example.guhugang.example.guhugang.uploadfileservice;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

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
                adapter.setArrayList(fpblist);
                adapter.notifyDataSetChanged();
                ShowCategoryActivity.this.list = fpblist;
            }
        });
    }

    @Override
    public void onItemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShowCategoryActivity.this,ShowAlbumItemActivity.class);
                intent.putExtra("imagelist", list.get(position));
                startActivity(intent);
            }
        });
    }
}
