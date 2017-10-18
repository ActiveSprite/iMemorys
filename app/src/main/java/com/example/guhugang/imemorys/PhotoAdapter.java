package com.example.guhugang.imemorys;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;


public class PhotoAdapter extends PagerAdapter {
    ArrayList<GridItem> list;
	   
   private Context mContext;
   public PhotoAdapter( ArrayList<GridItem> list,Context context){
	   this.list=list;
	   this.mContext=context;
   }
	@Override
	public int getCount() {		
		if(list!=null){
		return list.size();}
		else{
			return 0;
		}
	}
	public View instantiateItem(ViewGroup container, final int position) {

		final ZoomImageView imageView = new ZoomImageView(mContext);
		
		imageView.setScaleType(ScaleType.FIT_CENTER);
        File file = new File(list.get(position).getPath());
		Glide
	    .with(mContext)
	    .load(file).thumbnail(0.1f)
	    .into(imageView);
		container.addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		return imageView;
	}
	


	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}
