package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guhugang.example.guhugang.uploadfileservice.ShowCategoryActivity;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.ShowResultActivity;
import com.huaye.circlemenu.CircleMenuLayout;
import com.shizhefei.view.largeimage.factory.FileBitmapDecoderFactory;

import java.util.ArrayList;
import java.util.List;

public class FragmentSelect extends Fragment implements OnClickListener{
    RecyclerView recyclerView;
    ArrayList<String> mDatas;
	CircleMenuLayout circleMenu;
	private List<Menu> menuList = new ArrayList<>();
    View rootView;
	private String[] mItemTexts = new String[]{"面孔图册", "文档图册", "证件识别",
			"扫描王", "收藏夹"};
	private int[] mItemImgs = new int[]{R.drawable.cat,
			R.drawable.ocr, R.drawable.id_card,
			R.drawable.saomiao, R.drawable.reverse};

    String mDataList[]=new String[]{"面孔图册","ocr文字识别","证件识别","扫描王","收藏夹"};
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		//引入我们的布局
		if(rootView==null){
			rootView=inflater.inflate(R.layout.fragment_select, null);
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
		initView();
		initData();
        //recyclerView.addItemDecoration();
	}
    public void initView(){
		circleMenu=(CircleMenuLayout)getActivity().findViewById(R.id.id_menu_layout);

	}
	protected void initData() {
		for (int i = 0; i < mItemTexts.length; i++) {
			Menu m = new Menu();
			m.imgId = mItemImgs[i];
			m.label = mItemTexts[i];
			menuList.add(m);
		}

		circleMenu.setMenus(menuList, new CircleMenuLayout.OnLoadResCallback() {

			@Override
			public void showItem(Object o, ImageView img, TextView txt) {
				Menu m = (Menu) o;
				Glide.with(getActivity()).load(m.imgId).into(img);
				txt.setText(m.label);
			}
		});
		circleMenu.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
			@Override
			public void itemClick(View view, int pos) {
                 switch (pos){
					 case 0:
						 Intent intent=new Intent(getActivity(), ShowCategoryActivity.class);
						 getActivity().startActivity(intent);
						 break;
					 case 1:
						 break;
					 case 2:
						 break;
					 case 3:
					 	 break;
					 case 4:
						 Intent intent_1=new Intent(getActivity(), ShowResultActivity.class);
						 getActivity().startActivity(intent_1);
						 break;
				 }
			}

			@Override
			public void itemCenterClick(View view) {

			}
		});
//        circleMenu.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
	}


	@Override
	public void onClick(View v) {

	}

	private static class Menu{
		public int imgId;
		public String label;
	}
	public static FragmentSelect newInstance() {
		FragmentSelect fragment = new FragmentSelect();
		Bundle bundle = new Bundle();
		return fragment;
	}
}
