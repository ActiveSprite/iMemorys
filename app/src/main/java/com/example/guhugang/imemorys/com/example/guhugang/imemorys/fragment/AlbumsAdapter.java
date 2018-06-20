package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.moreused.MultiChoiceAdapter;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlbumsAdapter extends MultiChoiceAdapter {

	private List<PhotoUpImageBucket<PhotoUpImageItem>> arrayList;
	private LayoutInflater layoutInflater;
	private Toast mToast;
	private Context context;
	private ListView listView;
	private String TAG = AlbumsAdapter.class.getSimpleName();

	public AlbumsAdapter(Context context,ListView listView,List<PhotoUpImageBucket<PhotoUpImageItem>> arrayList){
		this.context=context;
		this.listView=listView;
		this.arrayList=arrayList;
		layoutInflater = LayoutInflater.from(context);
	};
	@Override
	public int getCount() {
		if(arrayList==null)return 0;
		return arrayList.size();
	}
	@Override
	public Object getItem(int position) {
		if(arrayList==null)return null;
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = layoutInflater.inflate(R.layout.ablums_adapter_item, parent, false);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.bg_left=(ImageView) convertView.findViewById(R.id.bg_left);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			holder.cb=(CheckBox) convertView.findViewById(R.id.cb);
//			holder.enter_logo=(ImageView) convertView.findViewById(R.id.enter_logo);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		holder.count.setText(""+arrayList.get(position).getCount());
		holder.name.setText(arrayList.get(position).getBucketName());
		File file = new File(arrayList.get(position).getImageList().get(0).getImagePath()) ;
		Glide
				.with(context)
				.load(file)
				.into(holder.image);
		if (mCheckable) {
			holder.cb.setVisibility(View.VISIBLE);
		} else {
			holder.cb.setVisibility(View.INVISIBLE);
		}
		holder.cb.setChecked(((ListView) parent).isItemChecked(position));
		return convertView;
	}

	class Holder{
		ImageView image;
		ImageView bg_left;
		ImageView bg_right;
//		ImageView enter_logo;
		TextView name;
		TextView count;
		CheckBox cb;
	}

	public void setArrayList(List<PhotoUpImageBucket<PhotoUpImageItem>> arrayList) {
		this.arrayList = arrayList;
	}
}
