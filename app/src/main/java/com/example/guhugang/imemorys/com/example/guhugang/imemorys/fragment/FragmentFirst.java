package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.ShowFileItemActivity;
import com.example.guhugang.moreused.ShareDeleteView;


public class FragmentFirst extends Fragment{

	private ListView listView;
	private AlbumsAdapter adapter;
	private PhotoUpAlbumHelper photoUpAlbumHelper;
	private List<PhotoUpImageBucket<PhotoUpImageItem>> list;
	private ShareDeleteView shareDeleteView;
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		//引入我们的布局

		if(rootView==null){
			rootView=inflater.inflate(R.layout.fragmentfirst, null);
		}
		//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;

	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		loadData();

	}
	public void initView(){

		listView=(ListView)getActivity().findViewById(R.id.list1);
		ViewCompat.setNestedScrollingEnabled(listView, true);
		onItemClick();
	}
	private void onItemClick(){
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent intent = new Intent(getActivity(),ShowFileItemActivity.class);
				intent.putExtra("imagelist", list.get(position));
				intent.putExtra("bucketname",list.get(position).getBucketName());
				startActivity(intent);
			}
		});
	}
	private void loadData(){
		photoUpAlbumHelper = PhotoUpAlbumHelper.getHelper();
		photoUpAlbumHelper.init(getActivity());
		photoUpAlbumHelper.setGetAlbumList(new PhotoUpAlbumHelper.GetAlbumList() {
			public void getAlbumList(List<PhotoUpImageBucket<PhotoUpImageItem>> list) {
				adapter = new AlbumsAdapter(getActivity(),listView,list);
				listView.setAdapter(adapter);
				//adapter.setArrayList(list);
				listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
				listView.setMultiChoiceModeListener(new MultiChoiceListener(listView,adapter,getActivity()));
				adapter.notifyDataSetChanged();
				FragmentFirst.this.list = list;
			}
		});
		photoUpAlbumHelper.execute(false);
	}

	public static FragmentFirst newInstance() {
		FragmentFirst fragment = new FragmentFirst();
		Bundle bundle = new Bundle();
		return fragment;
	}
}