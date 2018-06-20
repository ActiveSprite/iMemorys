package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guhugang.example.guhugang.uploadfileservice.Constant;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.moreused.MultiChoiceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuHuGang on 2017/11/11.
 */

public class MultiChoiceListener implements AbsListView.MultiChoiceModeListener {
    String TAG="tag";
    private AbsListView mListView;
    private TextView mTitleTextView;
    Context mContext;
    FrameLayout decorView;
    BottomNavigationView bottomView;
    private List<Integer> mSelectedItems = new ArrayList<>();
    MultiChoiceAdapter albumsAdapter;
    ActionMode actionMode;
    public MultiChoiceListener(AbsListView listView,MultiChoiceAdapter albumsAdapter,Context context){
        mListView = listView;
        this.albumsAdapter=albumsAdapter;
        this.mContext=context;
    }
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        mSelectedItems.add(position);
        mTitleTextView.setText("已选择 " + mListView.getCheckedItemCount() + " 项");
        albumsAdapter.notifyDataSetChanged();
        if(!checked){
           SparseBooleanArray array=mListView.getCheckedItemPositions();
           array.delete(position);
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        actionMode=mode;
        mode.getMenuInflater().inflate(R.menu.check_task_priority, menu);
        View multiSelectActionBarView = LayoutInflater.from(mContext)
                .inflate(R.layout.action_mode_bar, null);
        mode.setCustomView(multiSelectActionBarView);
        mTitleTextView = (TextView)multiSelectActionBarView.findViewById(R.id.photo_number);
        mTitleTextView.setText("已选择 0 项");
        albumsAdapter.setCheckable(true);
        albumsAdapter.notifyDataSetChanged();
        ConstantState state=ConstantState.getInstance();
        state.setState(true);
        decorView =(FrameLayout) ((Activity)mContext).getWindow().getDecorView();
        bottomView=(BottomNavigationView)decorView.findViewById(R.id.navigation_grid);
        if(bottomView!=null){
            bottomView.setVisibility(View.VISIBLE);
        }
        return true;
    }

    public ActionMode getActionMode() {
        return actionMode;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        Log.d(TAG, "onPrepareActionMode");
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Log.d(TAG, "onActionItemClicked");
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mSelectedItems.clear();
        ConstantState state=ConstantState.getInstance();
        state.setState(false);
        mListView.clearChoices();
        albumsAdapter.setCheckable(false);
        if(bottomView!=null){
            bottomView.setSelectedItemId(R.id.navigation_share);
            bottomView.setVisibility(View.INVISIBLE);
        }
        albumsAdapter.notifyDataSetChanged();
    }
}
