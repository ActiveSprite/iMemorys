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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.guhugang.example.guhugang.uploadfileservice.ReadText;
import com.example.guhugang.moreused.ImageResizer;
import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.FileBitmapDecoderFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;


public class SwicherAdapter extends PagerAdapter {
	   ArrayList<PhotoUpImageItem> list;
	   View top;
	   View bottom;
	   ReadText readText;
	   ImageResizer imageResizer;
	   ArrayList<LargeImageView> mViews;
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
	   public void setmViewList(ArrayList<LargeImageView> mViews){
		   this.mViews=mViews;
	   }
	   public SwicherAdapter(ArrayList<PhotoUpImageItem> list, Context context){
		   this.list=list;
		   this.mContext=context;
          // this.readText=new ReadText();
		   imageResizer=new ImageResizer();
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
			//final SubsamplingScaleImageView SubImageView = new SubsamplingScaleImageView(mContext);
			//if(mViews==null) return null;
			int i=position%5;
			final LargeImageView largeImageView=mViews.get(i);
			File file = new File(list.get(position ).getImagePath());
			largeImageView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
			largeImageView.setImage(new FileBitmapDecoderFactory(file));
			container.addView(largeImageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			//		container.addView(SubImageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//            SubImageView.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					if(bottom!=null&&top!=null) {
//						if(bottom.getVisibility()==View.GONE){
//							final Animation movein= AnimationUtils.loadAnimation(mContext, R.anim.movein);
//							bottom.startAnimation(movein);
//							bottom.setVisibility(View.VISIBLE);
//							final Animation top_movein= AnimationUtils.loadAnimation(mContext, R.anim.top_movein);
//							top.startAnimation(top_movein);
//							top.setVisibility(View.VISIBLE);
//						}else{
//							final Animation moveout= AnimationUtils.loadAnimation(mContext, R.anim.moveout);
//							bottom.startAnimation(moveout);
//							bottom.setVisibility(View.GONE);
//							final Animation top_moveout= AnimationUtils.loadAnimation(mContext, R.anim.top_moveout);
//							top.startAnimation(top_moveout);
//							top.setVisibility(View.GONE);
//						}
//
//					}
//				}
//			});
			return largeImageView;
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
			int i = position % 5;
			LargeImageView imageView = mViews.get(i);

			container.removeView(imageView);
		}
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	    public interface onCompleteBitmap{
		    public void onPositionArrived(int position);
	    }
	}
