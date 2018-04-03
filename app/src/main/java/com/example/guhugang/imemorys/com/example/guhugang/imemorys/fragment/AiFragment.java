package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.guhugang.imemorys.AiListAdapter;
import com.example.guhugang.imemorys.FindTagTask;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.TaggedImageItem;
import com.example.guhugang.imemorys.TypedData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuHuGang on 2018/1/24.
 */

public class AiFragment extends Fragment {
    ListView listView;
    View rootView;
    List<TypedData> typedDataList;
    AiListAdapter aiListAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.last_fragment, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();;
        loadData();
    }
    public void initView(){
        typedDataList=new ArrayList<>();
        typedDataList.add(new TypedData(TypedData.TYPE_BASE));
        typedDataList.add(new TypedData(TypedData.TYPE_AI));
//        typedDataList.add(new TypedData(TypedData.TYPE_PERSON));
        listView=(ListView)getActivity().findViewById(R.id.id_last_list);
        ViewCompat.setNestedScrollingEnabled(listView, true);
    }
    public void loadData(){
        aiListAdapter=new AiListAdapter(getActivity(),typedDataList);
        listView.setAdapter(aiListAdapter);
    }
    public static AiFragment newInstance() {
        AiFragment fragment = new AiFragment();
        Bundle bundle = new Bundle();
        return fragment;
    }
}
