package com.example.guhugang.imemorys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageSwitcher;

import java.util.ArrayList;


public class SwitcherActivity extends Activity {

	    private ImageSwitcher imsw;
	    //private ImageView imageview;

	    private int mPosition;
		private int mLocationX;
		private int mLocationY;
		private int mWidth;
		private int mHeight;

		
		ViewPager expandedView;
		ArrayList<PhotoUpImageItem> imglist;
		SwicherAdapter adapter;
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState); 
	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	       setContentView(R.layout.swicher_activity);
	       expandedView=(ViewPager)findViewById(R.id.switchviewPager);
	    }  
	    public void onStart(){
	    	  super.onStart();
	    	Intent intent = getIntent();
	    	imglist = ( ArrayList<PhotoUpImageItem>) intent.getSerializableExtra("imagelist");
	      int position=getIntent().getIntExtra("position", 0);
	      adapter=new SwicherAdapter(imglist,this);
	      expandedView.setAdapter(adapter);
	      expandedView.setCurrentItem(position);
	      
	      }  
	    public void onBackPressed() {
			finish();
		}

		@Override
		protected void onPause() {
			super.onPause();
			if (isFinishing()) {
				overridePendingTransition(0, 0);
			}
		}


}