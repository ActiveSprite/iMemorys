package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

import com.example.guhugang.imemorys.PhotoUpImageItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class PhotoUpAlbumHelper extends AsyncTask<Object, Object, Object>{

	final String TAG = getClass().getSimpleName();
	Context context;
	ContentResolver cr;
	
	HashMap<String, PhotoUpImageBucket<PhotoUpImageItem>> bucketList = new HashMap<String, PhotoUpImageBucket<PhotoUpImageItem>>();
	private GetAlbumList getAlbumList;
	PhotoUpImageBucket allPictures;
	private PhotoUpAlbumHelper() {}
	public static PhotoUpAlbumHelper getHelper() {
		PhotoUpAlbumHelper instance = new PhotoUpAlbumHelper();
		return instance;
	}

	public void init(Context context) {
		if (this.context == null) {
			this.context = context;
			cr = context.getContentResolver();
			allPictures=new PhotoUpImageBucket();
			allPictures.imageList=new ArrayList();
			allPictures.bucketName="所有图片";
		}
	}

	boolean hasBuildImagesBucketList = false;
	void buildImagesBucketList() {
	
		String columns[] = new String[] { Media._ID, Media.BUCKET_ID,
				Media.PICASA_ID, Media.DATA, Media.DISPLAY_NAME, Media.TITLE,
				Media.SIZE, Media.BUCKET_DISPLAY_NAME };
		Cursor cur = cr.query(Media.EXTERNAL_CONTENT_URI, columns, null, null,
				Media.DATE_MODIFIED+" desc");
		if (cur.moveToFirst()) {
			
			int photoIDIndex = cur.getColumnIndexOrThrow(Media._ID);
			int photoPathIndex = cur.getColumnIndexOrThrow(Media.DATA);
			int bucketDisplayNameIndex = cur.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
			int bucketIdIndex = cur.getColumnIndexOrThrow(Media.BUCKET_ID);//得到分类的类别的id的那一列
			
			do {
				if(cur.getString(photoPathIndex).substring(
						cur.getString(photoPathIndex).lastIndexOf("/")+1,
						cur.getString(photoPathIndex).lastIndexOf("."))
						.replaceAll(" ", "").length()<=0)
				{
					
				}else {
					String _id = cur.getString(photoIDIndex);
					String path = cur.getString(photoPathIndex);
					String bucketName = cur.getString(bucketDisplayNameIndex);
					String bucketId = cur.getString(bucketIdIndex);
					PhotoUpImageBucket<PhotoUpImageItem> bucket = bucketList.get(bucketId);
					if (bucket == null) {
						bucket = new PhotoUpImageBucket();
						bucketList.put(bucketId, bucket);
						bucket.imageList = new ArrayList<PhotoUpImageItem>();
						bucket.bucketName = bucketName;
					}
					bucket.count++;
					PhotoUpImageItem imageItem = new PhotoUpImageItem();
					imageItem.setImageId(_id);
					imageItem.setImagePath(path);
//					imageItem.setThumbnailPath(thumbnailList.get(_id));
					bucket.imageList.add(imageItem);

					allPictures.imageList.add(imageItem);
					allPictures.count++;
					//Log.i(TAG, "PhotoUpAlbumHelper绫讳腑 鐨勨�斺�斻�媝ath="+thumbnailList.get(_id));
				}
			} while (cur.moveToNext());
		}
		cur.close();
		hasBuildImagesBucketList = true;
	}

	
	public List<PhotoUpImageBucket> getImagesBucketList(boolean refresh) {
		if (refresh || (!refresh && !hasBuildImagesBucketList)) {
			buildImagesBucketList();
		}
		List<PhotoUpImageBucket> tmpList = new ArrayList<PhotoUpImageBucket>();
			tmpList.add(allPictures);

		Iterator<Entry<String, PhotoUpImageBucket<PhotoUpImageItem>>> itr = bucketList.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<String, PhotoUpImageBucket<PhotoUpImageItem>> entry = (Entry<String, PhotoUpImageBucket<PhotoUpImageItem>>) itr
					.next();
			tmpList.add(entry.getValue());
		}
		return tmpList;
	}

	
	String getOriginalImagePath(String image_id) {
		String path = null;
		String[] projection = { Media._ID, Media.DATA };
		Cursor cursor = cr.query(Media.EXTERNAL_CONTENT_URI, projection,
				Media._ID + "=" + image_id, null, Media.DATE_MODIFIED+" desc");
		if (cursor != null) {
			cursor.moveToFirst();
			path = cursor.getString(cursor.getColumnIndex(Media.DATA));
		}
		return path;
	}
	public void destoryList()
	{
		
		bucketList.clear();
		bucketList = null;
	}

	public void setGetAlbumList(GetAlbumList getAlbumList) {
		this.getAlbumList = getAlbumList;
	}

	public interface GetAlbumList{
		public void getAlbumList(List<PhotoUpImageBucket<PhotoUpImageItem>> list);
	}

	@Override
	protected Object doInBackground(Object... params) {
		return getImagesBucketList((Boolean)(params[0]));
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		getAlbumList.getAlbumList((List<PhotoUpImageBucket<PhotoUpImageItem>>)result);
	}
	
}
