package com.example.guhugang.imemorys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class PhotoActivity extends Activity{
	
	HackyViewPager expandedView;
	ArrayList<GridItem> imglist;
	PhotoAdapter adapter;
	protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.swicher_activity);
       expandedView=(HackyViewPager)findViewById(R.id.switchviewPager);
    }  
	
	 public void onStart(){
   	  super.onStart();
   	Intent intent = getIntent();
   	imglist = ( ArrayList<GridItem>) intent.getSerializableExtra("imagelist");
     int position=getIntent().getIntExtra("position", 0);
     adapter=new PhotoAdapter(imglist,this);
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
