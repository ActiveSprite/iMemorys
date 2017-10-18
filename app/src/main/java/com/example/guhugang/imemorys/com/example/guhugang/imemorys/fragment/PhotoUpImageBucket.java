package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import com.example.guhugang.imemorys.PhotoUpImageItem;

import java.io.Serializable;
import java.util.List;


public class PhotoUpImageBucket<T extends PhotoUpImageItem> implements Serializable{
	
	public int count = 0;
	public String bucketName;
	public List<T> imageList;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public List<T > getImageList() {
		return imageList;
	}
	public void setImageList(List<T> imageList) {
		this.imageList = imageList;
	}
	
}
