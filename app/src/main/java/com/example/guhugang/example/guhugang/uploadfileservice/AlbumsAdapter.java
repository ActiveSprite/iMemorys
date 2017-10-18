package com.example.guhugang.example.guhugang.uploadfileservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.guhugang.imemorys.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends BaseAdapter {

	private List<FacePictureBucket> arrayList;
	private LayoutInflater layoutInflater;

	private Toast mToast;
	private Context context;
	private String TAG = AlbumsAdapter.class.getSimpleName();
	public AlbumsAdapter(Context context){
		this.context=context;
		layoutInflater = LayoutInflater.from(context);
		arrayList = new ArrayList<FacePictureBucket>();
		
	};
	@Override
	public int getCount() {
		if(arrayList!=null)
		return arrayList.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
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
		holder.count.setText(""+arrayList.get(position).imageList.size());

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

	public void setArrayList(List<FacePictureBucket> arrayList) {
		this.arrayList = arrayList;
	}
}
