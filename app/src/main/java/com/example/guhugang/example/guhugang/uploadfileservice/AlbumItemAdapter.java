package com.example.guhugang.example.guhugang.uploadfileservice;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;
import com.example.guhugang.moreused.ShowGalleryActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumItemAdapter<T extends PhotoUpImageItem> extends BaseAdapter {
	private List<T> list;
	private LayoutInflater layoutInflater;

	 private int selectPic = -1;
	 private Context mcontext;
	 private Toast mToast;
//	 ZoomTutorial mZoomTutorial;
//	 ViewPager expandedView;
	public AlbumItemAdapter(List<T> list,Context context){
		this.list = list;
		this.mcontext=context;

		layoutInflater = LayoutInflater.from(context);
		
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	
	
	public void setNotifyDataChange(int id) {  
        selectPic = id;  
        super.notifyDataSetChanged();  
    }  
	
	
	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder=new Holder(); ;
		if (convertView == null) {
			holder.imageView = new ImageView(mcontext);
			holder.imageView.setLayoutParams(new ViewGroup.LayoutParams( 
			         LayoutParams.FILL_PARENT, 
			        (int) getRawSize(TypedValue.COMPLEX_UNIT_DIP, 90)));
			holder.imageView.setScaleType(ScaleType.CENTER_CROP);

		}else {
			holder.imageView = (ImageView) convertView;
		}
		

		
		File file = new File(list.get(position).getImagePath()) ;
		Log.i("position",String.valueOf(position));
		Log.i("paths",list.get(position).getImagePath());
		Glide
	    .with(mcontext)
	    .load(file)
	    .into(holder.imageView);
		holder.imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mcontext, ShowGalleryActivity.class);
				intent.putExtra("imagelist",(ArrayList)list);
				intent.putExtra("position", position);
				mcontext.startActivity(intent);
				//((Activity) mcontext).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
			}
		});

		return holder.imageView;
	}

	public float getRawSize(int unit, float value) { 
        Resources res = mcontext.getResources(); 
        return TypedValue.applyDimension(unit, value, res.getDisplayMetrics()); 
    } 

	
	class Holder{
		ImageView imageView;
		
	}
	
}