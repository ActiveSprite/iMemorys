package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.guhugang.example.sqlite.DBDao;
import com.example.guhugang.imemorys.FindTagTask;
import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.ShowFileItemActivity;
import com.example.guhugang.imemorys.TaggedImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuHuGang on 2017/12/22.
 */

public class SearchFragment extends Fragment {
    private ListView listView;
    DBDao dbDao;
    ArrayList<TaggedImageItem> taggedImageItemList=new ArrayList<>();
    FindTagTask findTagTask;
    SearchAdapter searchAdapter;
    SimpleSearchAdapter simpleSearchAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //引入我们的布局
        return inflater.inflate(R.layout.search_fragment, container, false);

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        //loadData();

    }
    public void initView(){

        listView=(ListView)getActivity().findViewById(R.id.list_search);
        listView.setTextFilterEnabled(true);
        Log.i("liucheng","Activity");
        dbDao=new DBDao(getActivity());
        taggedImageItemList=dbDao.selectTagByNull();
        simpleSearchAdapter = new SimpleSearchAdapter(getActivity(), taggedImageItemList);
        listView.setAdapter(simpleSearchAdapter);

    }

    private void loadData(String tag){

                //taggedImageItemList=dbDao.selectTagByNull();
        findTagTask=new FindTagTask(getActivity());
        findTagTask.execute(tag);
        findTagTask.setGetAlbumList(new FindTagTask.GetAlbumList() {
            @Override
            public void getAlbumList(List<PhotoUpImageBucket<TaggedImageItem>> list) {
                if(list!=null) {
                    searchAdapter = new SearchAdapter(getActivity(), list);
                    listView.setAdapter(searchAdapter);
                }
            }
        });


    }
    public void OnTagChanged(final String tag){

        if(!TextUtils.isEmpty(tag)&&listView!=null) {

            simpleSearchAdapter.getFilter().filter(tag);


        }else{
            //if(listView!=null) {
                listView.clearTextFilter();
            //}
        }
    }
}
