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

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.guhugang.imemorys.GridItem;
import com.example.guhugang.imemorys.ImageScanner;
import com.example.guhugang.imemorys.MainActivity;
import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.StickyGridAdapter;
import com.example.guhugang.imemorys.YMComparator;


public class PhotoFragment extends Fragment implements OnClickListener{

	private ProgressDialog mProgressDialog;
	private ImageScanner mScanner;
	private GridView mGridView;
	private List<GridItem> mGirdList = new ArrayList<GridItem>();
	private static int section = 1;
	ArrayList<PhotoUpImageItem> ImageItemList=new ArrayList<>();
	private Map<String, Integer> sectionMap = new HashMap<String, Integer>();
	private View rootView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		//引入我们的布局
		if(rootView==null){
			rootView=inflater.inflate(R.layout.photo_fragment, null);
		}
		//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;

	}
	public void scanImages(){
		mScanner = new ImageScanner(getActivity());

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
					String id=cursor.getString(cursor.getColumnIndex(Media._ID));
					long times = cursor.getLong(cursor
							.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
					GridItem mGridItem = new GridItem(path, paserTimeToYM(times));
					mGridItem.setId(id);
					mGirdList.add(mGridItem);

				}
				cursor.close();
				YMComparator se=new YMComparator();
				Comparator<GridItem> descComparator = Collections.reverseOrder(se);
				Collections.sort(mGirdList, descComparator);

				for(ListIterator<GridItem> it = mGirdList.listIterator(); it.hasNext();){
					GridItem mGridItem = it.next();
					PhotoUpImageItem Item=new PhotoUpImageItem();
					Item.setImagePath(mGridItem.getPath());
					Item.setImageId(mGridItem.getId());
					ImageItemList.add(Item);
					String ym = mGridItem.getTime();
					if(!sectionMap.containsKey(ym)){
						mGridItem.setSection(section);
						sectionMap.put(ym, section);
						section ++;
					}else{
						mGridItem.setSection(sectionMap.get(ym));
					}
				}

				mGridView.setAdapter(new StickyGridAdapter(getActivity(), mGirdList, mGridView,ImageItemList));

			}
		});
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mGridView = (GridView)getActivity().findViewById(R.id.asset_grid);
		ViewCompat.setNestedScrollingEnabled(mGridView, true);
		if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
			ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
			Log.e("permission","denied");
		}else{
			scanImages();
		};
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
	public static PhotoFragment newInstance() {
		PhotoFragment fragment = new PhotoFragment();
		Bundle bundle = new Bundle();
		return fragment;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode){
			case 1:
				if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
					scanImages();
				}else {
					Toast.makeText(getActivity(),"你拒绝了读取磁盘权限！！",Toast.LENGTH_SHORT).show();
					getActivity().finish();
				}
				break;
		}
	}
}