package com.example.guhugang.example.guhugang.uploadfileservice;

import com.example.guhugang.imemorys.PhotoUpImageItem;

/**
 * Created by GuHuGang on 2017/11/26.
 */

public class CollectImageItem extends PhotoUpImageItem {
    private String dest_path;

    public void setDest_path(String dest_path) {
        this.dest_path = dest_path;
    }

    public String getDest_path() {
        return dest_path;
    }
}
