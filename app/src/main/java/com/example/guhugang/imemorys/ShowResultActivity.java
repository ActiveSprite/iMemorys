package com.example.guhugang.imemorys;

import com.example.guhugang.example.guhugang.uploadfileservice.CollectImageItem;
import com.example.guhugang.example.sqlite.DBDao;
import com.example.guhugang.moreused.BaseResultActivity;

/**
 * Created by GuHuGang on 2017/11/27.
 */

public class ShowResultActivity extends BaseResultActivity<CollectImageItem>{

    @Override
    public void setResultList() {
        DBDao dbDao=new DBDao(this);
        this.resultList=dbDao.selectCollectionItem();
    }
}
