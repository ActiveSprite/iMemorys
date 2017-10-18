package com.example.guhugang.example.guhugang.uploadfileservice;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.example.guhugang.imemorys.R;

import java.util.List;

/**
 * Created by GuHuGang on 2017/6/17.
 */

public abstract class CategoryActivity<A> extends Activity {
    GridView listView;
    public AlbumsAdapter adapter;
    //private PhotoUpAlbumHelper photoUpAlbumHelper;
    public List<A> list;
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.fragmentfirst);
          initView();
          loadData();
    }
    public void initView(){
        // 	mResultText = ((EditText)getActivity().findViewById(R.id.iat_text));
        // 	record=(ImageButton)getActivity().findViewById(R.id.record);
        adapter = new AlbumsAdapter(this);
        //	record.setOnClickListener(this);

        listView=(GridView)findViewById(R.id.list1);
        if(adapter!=null)
        listView.setAdapter(adapter);
        //btsearch=(Button)getActivity().findViewById(R.id.bt_seaerch);
        //btsearch.setOnClickListener(this);
        onItemClick();
    }
    public abstract void loadData();
    public abstract void onItemClick();

}
