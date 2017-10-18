package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import java.util.List;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.ShowFileItemActivity;


public class FragmentFirst extends Fragment {

	private GridView gridView;
	private AlbumsAdapter adapter;
	private PhotoUpAlbumHelper photoUpAlbumHelper;
	private List<PhotoUpImageBucket<PhotoUpImageItem>> list;
	//private Button btsearch;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		//引入我们的布局
		return inflater.inflate(R.layout.fragmentfirst, container, false);

	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		loadData();

	}
	public void initView(){
		adapter = new AlbumsAdapter(getActivity());
		gridView=(GridView)getActivity().findViewById(R.id.list1);
		gridView.setAdapter(adapter);
		onItemClick();
	}
	private void onItemClick(){
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent intent = new Intent(getActivity(),ShowFileItemActivity.class);
				intent.putExtra("imagelist", list.get(position));
				startActivity(intent);
			}
		});
	}
	private void loadData(){
		photoUpAlbumHelper = PhotoUpAlbumHelper.getHelper();
		photoUpAlbumHelper.init(getActivity());
		photoUpAlbumHelper.setGetAlbumList(new PhotoUpAlbumHelper.GetAlbumList() {
			public void getAlbumList(List<PhotoUpImageBucket<PhotoUpImageItem>> list) {
				adapter.setArrayList(list);
				adapter.notifyDataSetChanged();
				FragmentFirst.this.list = list;
			}
		});
		photoUpAlbumHelper.execute(false);
	}
}