package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.ShowFileItemActivity;
import com.example.guhugang.moreused.ShareDeleteView;

import java.util.List;

/**
 * Created by guhug on 2018/5/9.
 */

public class SelectorFragment extends Fragment {
    private ListView listView;
    private AlbumsAdapter adapter;
    private PhotoUpAlbumHelper photoUpAlbumHelper;
    private List<PhotoUpImageBucket<PhotoUpImageItem>> list;
    View rootView;
    Intent mIntent;
    private int resultCode = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //引入我们的布局
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragmentfirst, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        Log.i("fragment","created");
        return rootView;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIntent = new Intent();
        initView();
        loadData();
        Log.i("fragment","Activitycreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void initView(){
        listView=(ListView)getActivity().findViewById(R.id.list1);
        ViewCompat.setNestedScrollingEnabled(listView, true);
        onItemClick();
    }
    private void onItemClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String path=list.get(position).getImageList().get(0).getImagePath();
                String a[]=path.split("/");
                StringBuffer buffer=new StringBuffer();
                for (int i=0;i<a.length-1;i++){
                    buffer.append(a[i]+"/");
                }
                mIntent.putExtra("imagePath", buffer.toString());
                getActivity().setResult(resultCode,mIntent);
                getActivity().finish();
            }
        });
    }
    private void loadData(){
        photoUpAlbumHelper = PhotoUpAlbumHelper.getHelper();
        photoUpAlbumHelper.init(getActivity());
        photoUpAlbumHelper.setGetAlbumList(new PhotoUpAlbumHelper.GetAlbumList() {
            public void getAlbumList(List<PhotoUpImageBucket<PhotoUpImageItem>> list) {
                list.remove(0);
                adapter = new AlbumsAdapter(getActivity(),listView,list);
                listView.setAdapter(adapter);
                //adapter.setArrayList(list);
                listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
                listView.setMultiChoiceModeListener(new MultiChoiceListener(listView,adapter,getActivity()));
                adapter.notifyDataSetChanged();
                SelectorFragment.this.list = list;
            }
        });
        photoUpAlbumHelper.execute(false);
    }
    private void refreshData(){
        photoUpAlbumHelper = PhotoUpAlbumHelper.getHelper();
        photoUpAlbumHelper.init(getActivity());
        photoUpAlbumHelper.setGetAlbumList(new PhotoUpAlbumHelper.GetAlbumList() {
            public void getAlbumList(List<PhotoUpImageBucket<PhotoUpImageItem>> list) {
                list.remove(0);
                adapter.setArrayList(list);
                adapter.notifyDataSetChanged();
                SelectorFragment.this.list = list;
            }
        });
        photoUpAlbumHelper.execute(false);
    }
//    public static  SelectorFragment newInstance() {
//        SelectorFragment fragment = new  SelectorFragment();
//        Bundle bundle = new Bundle();
//        return fragment;
//    }
}
