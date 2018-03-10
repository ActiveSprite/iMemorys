package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.content.Context;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guhugang.example.guhugang.uploadfileservice.Constant;
import com.example.guhugang.imemorys.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuHuGang on 2017/11/11.
 */

public class MultiChoiceListener implements AbsListView.MultiChoiceModeListener {
    String TAG="tag";
    private ListView mListView;
    private TextView mTitleTextView;
    Context mContext;
    private List<Integer> mSelectedItems = new ArrayList<>();
    AlbumsAdapter albumsAdapter;
    public MultiChoiceListener(ListView listView,AlbumsAdapter albumsAdapter,Context context){
        mListView = listView;
        this.albumsAdapter=albumsAdapter;
        this.mContext=context;
    }
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        mSelectedItems.add(position);
        mTitleTextView.setText("已选择 " + mListView.getCheckedItemCount() + " 项");
        albumsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
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
        return true;
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
        albumsAdapter.setCheckable(false);
        albumsAdapter.notifyDataSetChanged();
    }
}
