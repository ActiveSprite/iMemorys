package com.example.guhugang.imemorys;

import java.io.Serializable;

/**
 * Created by GuHuGang on 2017/12/19.
 */

public class TaggedImageItem extends PhotoUpImageItem implements Serializable {
    private String Tag;
    private String location;
    public String getTag() {
        return Tag;
    }
    public void setTag(String tag) {
        Tag = tag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
