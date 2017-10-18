package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TimeZone;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridView;

import com.example.guhugang.imemorys.GridItem;
import com.example.guhugang.imemorys.ImageScanner;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.StickyGridAdapter;
import com.example.guhugang.imemorys.YMComparator;


public class PhotoFragment extends Fragment implements OnClickListener{

	private ProgressDialog mProgressDialog;
	private ImageScanner mScanner;
	private GridView mGridView;
	private List<GridItem> mGirdList = new ArrayList<GridItem>();
	private static int section = 1;
	private Map<String, Integer> sectionMap = new HashMap<String, Integer>();
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		//引入我们的布局

		return inflater.inflate(R.layout.photo_fragment, container, false);

	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mGridView = (GridView)getActivity().findViewById(R.id.asset_grid);
		mScanner = new ImageScanner(getActivity());
		Log.i("time","hh");

		mScanner.scanImages(new ImageScanner.ScanCompleteCallBack() {
			{
				mProgressDialog = ProgressDialog.show(getActivity(), null, "正在加载...");
			}

			@Override
			public void scanComplete(Cursor cursor) {
				// 关闭进度条
				mProgressDialog.dismiss();

				while (cursor.moveToNext()) {
					// 获取图片的路径
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					long times = cursor.getLong(cursor
							.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
//					int photoplaceIndex=cursor.getColumnIndexOrThrow(Media.DESCRIPTION );
//					String place=cursor.getString(photoplaceIndex);
//					//String datetime=convert(time);
					Log.i("time",path);
					GridItem mGridItem = new GridItem(path, paserTimeToYM(times));
					mGirdList.add(mGridItem);

				}
				cursor.close();
				YMComparator se=new YMComparator();
				Comparator<GridItem> descComparator = Collections.reverseOrder(se);
				Collections.sort(mGirdList, descComparator);


				//Collections.sort(tmpList,descComparator);
				for(ListIterator<GridItem> it = mGirdList.listIterator(); it.hasNext();){
					GridItem mGridItem = it.next();
					String ym = mGridItem.getTime();
					if(!sectionMap.containsKey(ym)){
						mGridItem.setSection(section);
						sectionMap.put(ym, section);
						section ++;
					}else{
						mGridItem.setSection(sectionMap.get(ym));
					}
				}

				mGridView.setAdapter(new StickyGridAdapter(getActivity(), mGirdList, mGridView));

			}
		});
	}

	public static String paserTimeToYM(long time) {
		System.setProperty("user.timezone", "Asia/Shanghai");
		TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
		TimeZone.setDefault(tz);
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(new Date(time * 1000L));
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
}