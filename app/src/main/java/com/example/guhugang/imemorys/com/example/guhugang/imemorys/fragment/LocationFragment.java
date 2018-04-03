package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.guhugang.imemorys.DepartLocation;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.TaggedImageItem;

import java.util.List;

/**
 * Created by GuHuGang on 2018/2/3.
 */

public class LocationFragment extends Fragment {
    RecyclerView recyclerView;
    View rootView;
    HomeAdapter homeAdapter;
    RelativeLayout mRelativeLayout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.tag_fragment, null);
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
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.tag_list);
        mRelativeLayout=(RelativeLayout)getActivity().findViewById(R.id.id_bg_layout);
    }
    public void loadData(){
//        FindTagTask tagTask=new FindTagTask(getActivity());
//        tagTask.setGetAlbumList(new FindTagTask.GetAlbumList() {
//            @Override
//            public void getAlbumList(List<PhotoUpImageBucket<TaggedImageItem>> list) {
//                if(list!=null){
//                    homeAdapter=new HomeAdapter(getActivity(),list);
//                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
//                    recyclerView.setAdapter(homeAdapter);
//                    mRelativeLayout.setVisibility(View.INVISIBLE);
//
//                }
//            }
//        });
//        tagTask.execute("string");

        DepartLocation departLocation=new DepartLocation(getActivity());
        departLocation.scanImages(new DepartLocation.ScanCompleteCallBack() {
            @Override
            public void scanComplete(List<PhotoUpImageBucket<TaggedImageItem>> fpblist) {
                if(fpblist!=null){
                    homeAdapter=new HomeAdapter(getActivity(),fpblist,false);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(homeAdapter);
                    mRelativeLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
    public static LocationFragment newInstance() {
        LocationFragment fragment = new LocationFragment();
        Bundle bundle = new Bundle();
        return fragment;
    }
}
