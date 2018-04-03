package com.example.guhugang.imemorys;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

public class ImageScanner {
	private Context mContext;

	public ImageScanner(Context context){
		this.mContext = context;
	}

	/**
	 * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
	 */
	public void scanImages(final ScanCompleteCallBack callback) {
		final Handler mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				callback.scanComplete((Cursor)msg.obj);
			}
		};

		new Thread(new Runnable() {

			@Override
			public void run() {
				Uri mImageUri = Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = mContext.getContentResolver();
				String columns[] = new String[] { Media._ID, Media.BUCKET_ID,
						Media.PICASA_ID, Media.DATA, Media.DISPLAY_NAME, Media.TITLE,
						Media.SIZE, Media.DATE_ADDED};
				String selection=Media.DATA+" like ?";
				String[] selectionargs={"%/storage/emulated/0/DCIM/%"};
				Cursor mCursor = mContentResolver.query(mImageUri,columns,selection, selectionargs,
						Media.DATE_MODIFIED+" desc");

				//利用Handler通知调用线程
				Message msg = mHandler.obtainMessage();
				msg.obj = mCursor;
				mHandler.sendMessage(msg);
			}
		}).start();

	}


	public static interface ScanCompleteCallBack{
		public void scanComplete(Cursor cursor);
	}


}
