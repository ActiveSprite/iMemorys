package com.example.guhugang.imemorys;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.guhugang.example.sqlite.DBDao;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpAlbumHelper;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.SearchAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by GuHuGang on 2017/12/22.
 */

public class FindTagTask extends AsyncTask<String,Void,Object> {
    final String TAG = getClass().getSimpleName();
    Context context;
    DBDao dbDao;
    private GetAlbumList getAlbumList;
    ArrayList<TaggedImageItem> taggedImageItemList=new ArrayList<>();
    HashSet<String> tag_result=new HashSet<>();
    HashMap<String, PhotoUpImageBucket<TaggedImageItem>> bucketList = new HashMap<String, PhotoUpImageBucket<TaggedImageItem>>();

    public FindTagTask(Context context){
        this.context=context;
        dbDao=new DBDao(context);
    }
    boolean hasBuildImagesBucketList = false;
    void buildImagesBucketList(String tag){
        taggedImageItemList=dbDao.selectTagByNull();
        if(taggedImageItemList==null)return;
        for(int i=0;i<taggedImageItemList.size();i++){
            if(!fileIsExists(taggedImageItemList.get(i).getImagePath())){
                dbDao.deleteAll(taggedImageItemList.get(i).getImagePath());
                taggedImageItemList.remove(i);
                i--;

            }
        }
        if(taggedImageItemList==null)return;
        for(TaggedImageItem item:taggedImageItemList){
            String Tags[]=item.getTag().split("_");//将搜索的标签分解
            locteString(Tags);
        }
        for(String set:tag_result){
            PhotoUpImageBucket<TaggedImageItem> bucket=bucketList.get(set);
            if(bucket==null){
                bucket=new PhotoUpImageBucket<>();
                bucketList.put(set, bucket);
          //    bucket.imageList = new ArrayList<TaggedImageItem>();
                bucket.bucketName =set;
            }
            bucket.imageList = new ArrayList<TaggedImageItem>( dbDao.selectByTag(set));
            if(bucket.imageList!=null)
             bucket.count=bucket.imageList.size();
        }


        hasBuildImagesBucketList = true;
    }
    void locteString(String args[]){//定位某个字符串是否包含输入的标签
        for(String e:args){
            if(e!=null&& !TextUtils.isEmpty(e))
            tag_result.add(e);
        }

    }
    public List<PhotoUpImageBucket> getImagesBucketList(String tag) {
        if ( !hasBuildImagesBucketList) {
            buildImagesBucketList(tag);
        }
        List<PhotoUpImageBucket> tmpList = new ArrayList<PhotoUpImageBucket>();
        Iterator<Map.Entry<String, PhotoUpImageBucket<TaggedImageItem>>> itr = bucketList.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, PhotoUpImageBucket<TaggedImageItem>> entry = (Map.Entry<String, PhotoUpImageBucket<TaggedImageItem>>) itr
                    .next();

            if(entry.getValue().imageList.size()>4)
              tmpList.add(entry.getValue());
        }
        return tmpList;
    }

    @Override
    protected Object doInBackground(String... params) {


        return getImagesBucketList(params[0]);
    }
    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        getAlbumList.getAlbumList((List<PhotoUpImageBucket<TaggedImageItem>>)result);
    }
    public void setGetAlbumList(GetAlbumList getAlbumList) {
        this.getAlbumList = getAlbumList;
    }

    public interface GetAlbumList{
        public void getAlbumList(List<PhotoUpImageBucket<TaggedImageItem>> list);
    }

    public boolean fileIsExists(String strFlie) {
        try {
            File file = new File(strFlie);
            if (!file.exists()) {
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
