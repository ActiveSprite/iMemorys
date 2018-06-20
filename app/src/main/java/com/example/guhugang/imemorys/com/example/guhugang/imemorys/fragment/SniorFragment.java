package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.guhugang.example.guhugang.uploadfileservice.CollectImageItem;
import com.example.guhugang.example.guhugang.uploadfileservice.FacePictureBucket;
import com.example.guhugang.example.guhugang.uploadfileservice.LoadPicture;
import com.example.guhugang.example.guhugang.uploadfileservice.ShowCategoryActivity;
import com.example.guhugang.example.sqlite.DBDao;
import com.example.guhugang.imemorys.DepartLocation;
import com.example.guhugang.imemorys.FaceListAdapter;
import com.example.guhugang.imemorys.FindTagTask;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.ShowResultActivity;
import com.example.guhugang.imemorys.TaggedImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhug on 2018/6/6.
 */

public class SniorFragment extends Fragment {
    View rootView;
    SeniorListAdapter adapter;
    List<SeniorData>seniorDataList=new ArrayList<>(5);
    GridView gridView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.senior_fragment, null);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }
    private void loadData(){
        DepartLocation departLocation=new DepartLocation(getActivity());
        departLocation.scanImages(new DepartLocation.ScanCompleteCallBack() {
            @Override
            public void scanComplete(List<PhotoUpImageBucket<TaggedImageItem>> fpblist) {
                if(fpblist!=null&&fpblist.size()>0){
                    SeniorData data=new SeniorData();
                    data.title="地点";
                    data.path=fpblist.get(0).getImageList().get(0).getImagePath();
                    data.num=fpblist.size();
                    seniorDataList.add(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        LoadPicture loadPicture=new LoadPicture(getActivity());
        loadPicture.scanImages(new LoadPicture.ScanCompleteCallBack(){
            public void scanComplete(List<FacePictureBucket> fpblist){
                if(fpblist!=null&&fpblist.size()>0) {
                    SeniorData data=new SeniorData();
                    data.title="面孔";
                    data.path=fpblist.get(0).getImageList().get(0).getImagePath();
                    data.num=fpblist.size();
                    seniorDataList.add(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        FindTagTask tagTask=new FindTagTask(getActivity());
        tagTask.setGetAlbumList(new FindTagTask.GetAlbumList() {
            @Override
            public void getAlbumList(List<PhotoUpImageBucket<TaggedImageItem>> list) {
                if(list!=null&&list.size()>0){
                    SeniorData data=new SeniorData();
                    data.title="事物";
                    data.path=list.get(0).getImageList().get(0).getImagePath();
                    data.num=list.size();
                    seniorDataList.add(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        tagTask.execute("string");
        DBDao dbDao=new DBDao(getActivity());
        List<CollectImageItem>items= dbDao.selectCollectionItem();
        if(items!=null&&items.size()>0){
            SeniorData data=new SeniorData();
            data.title="收藏夹";
            data.path=items.get(0).getImagePath();
            data.num=items.size();
            seniorDataList.add(data);
            adapter.notifyDataSetChanged();
        }
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter=new SeniorListAdapter(seniorDataList,getActivity());
        gridView=(GridView) getActivity().findViewById(R.id.id_senior_list);
        gridView.setAdapter(adapter);
        final Intent intent_face=new Intent(getActivity(),ShowCategoryActivity.class);
        final  Intent intentCollection=new Intent(getActivity(),ShowResultActivity.class);
        final Intent intent_tag=new Intent(getActivity(),TaggedActivity.class);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (seniorDataList.get(i).title) {
                    case "面孔":
                     getActivity().startActivity(intent_face);
                     break;
                    case "收藏夹":
                        getActivity().startActivity(intentCollection);
                        break;
                    case "事物":
                        getActivity().startActivity(intent_tag);
                        break;
                }
            }

        });
        loadData();
    }
    public static SniorFragment newInstance() {
        SniorFragment fragment = new SniorFragment();
        Bundle bundle = new Bundle();
        return fragment;
    }
}
