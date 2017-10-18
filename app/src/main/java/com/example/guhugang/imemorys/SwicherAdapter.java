package com.example.guhugang.imemorys;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.guhugang.example.guhugang.uploadfileservice.ReadText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;


public class SwicherAdapter extends PagerAdapter {
	   ArrayList<PhotoUpImageItem> list;
	   View top;
	   View bottom;
	   ReadText readText;
	   //private byte[] mImageData = null;
	   int Currentpositon=-1;
	   int mWidth=-1, mHeight, mLocationX, mLocationY;
	   private Context mContext;
	   private LayoutInflater layoutInflater;
       LinearLayout recognize;
	   public void setpositon(int position){
		   this.Currentpositon=position;
	   }
	   public void setprams(int w,int h,int x,int y){
		   this.mWidth=w;
		   this.mHeight=h;
		   this.mLocationX=x;
		   this.mLocationY=y;
	   }
	   public SwicherAdapter(ArrayList<PhotoUpImageItem> list, Context context){
		   this.list=list;
		   this.mContext=context;
          // this.readText=new ReadText();
		   layoutInflater = LayoutInflater.from(context);

	   }
	   public void setview(View v1,View v2){
		   this.bottom=v1;
		   this.top=v2;
		   recognize=(LinearLayout)bottom.findViewById(R.id.share);
	   }
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(list!=null){
			return list.size();}
			else{
				return 0;
			}
		}
		public View instantiateItem(ViewGroup container, final int position) {

			final ZoomImageView imageView = new ZoomImageView(mContext,bottom,top);
//			imageView.setBackgroundColor(Color.BLACK);
//			if(position==this.Currentpositon&&mWidth!=-1){
//				imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
//				imageView.transformIn();
//				this.Currentpositon=-1;
//			}
			//imageView.setImageResource(sDrawables[position]);
			//imageLoader.displayImage("file://"+list.get(position).getImagePath(),imageView, options);
			//imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
			imageView.setScaleType(ScaleType.FIT_CENTER);

				File file = new File(list.get(position ).getImagePath());
				Log.i("textpath", list.get(position ).getImagePath());
				Log.i("number", String.valueOf(position));

				Glide
						.with(mContext)
						.load(file).thumbnail(0.1f)
						.into(imageView);

			//imageLoader.displayImage(list.get(position).getImagePath(), imageView,options);
			container.addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			recognize.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i("Tag","点击了阅读文本按钮");
					readText=new ReadText(Initbitmap(list.get(position-1).getImagePath()));
					Toast.makeText(mContext,list.get(position-1).getImagePath(),Toast.LENGTH_LONG).show();
					Log.i("textpath+last",list.get(position-1).getImagePath());
					try {
						readText.execute();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			return imageView;
		}

	byte[] Initbitmap(String s){
		Bitmap mImage=null;
		byte[] mImageData=null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		mImage = BitmapFactory.decodeFile(s, options);
		options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max(
				(double) options.outWidth /1024f,
				(double) options.outHeight /1024f)));
		options.inJustDecodeBounds = false;
		mImage = BitmapFactory.decodeFile(s, options);

		mImage= mImage.copy(Bitmap.Config.RGB_565, true);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//可根据流量及网络状况对图片进行压缩
		mImage.compress(Bitmap.CompressFormat.JPEG, 80, baos);
		mImageData = baos.toByteArray();
        return mImageData;
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
