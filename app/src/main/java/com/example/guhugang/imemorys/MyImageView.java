package com.example.guhugang.imemorys;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView {
	private OnMeasureListener onMeasureListener;
	
	public void setOnMeasureListener(OnMeasureListener onMeasureListener) {
		this.onMeasureListener = onMeasureListener;
	}

	public MyImageView(Context context, AttributeSet attrs) {

		super(context, attrs);
		this.setBackgroundColor(Color.parseColor("#cccccc"));
	}

	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setBackgroundColor(Color.parseColor("#cccccc"));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);


		if(onMeasureListener != null){
			onMeasureListener.onMeasureSize(getMeasuredWidth(), getMeasuredHeight());
		}
	}

	public interface OnMeasureListener{
		public void onMeasureSize(int width, int height);
	}
	
}
