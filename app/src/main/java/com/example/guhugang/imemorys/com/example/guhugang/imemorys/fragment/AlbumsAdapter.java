package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends BaseAdapter {

	private List<PhotoUpImageBucket<PhotoUpImageItem>> arrayList;
	private LayoutInflater layoutInflater;

	private Toast mToast;
	private Context context;
	private String TAG = AlbumsAdapter.class.getSimpleName();
	public AlbumsAdapter(Context context){
		this.context=context;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = layoutInflater.inflate(R.layout.ablums_adapter_item, parent, false);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.count = (TextView) convertView.findViewById(R.id.count);
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
		return convertView;
	}

	class Holder{
		ImageView image;
		TextView name;
		TextView count;
	}

	public void setArrayList(List<PhotoUpImageBucket<PhotoUpImageItem>> arrayList) {
		this.arrayList = arrayList;
	}
}
