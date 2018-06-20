package com.example.guhugang.imemorys;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guhugang.example.guhugang.uploadfileservice.ShowCategoryActivity;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.TaggedActivity;
import com.example.guhugang.view.AiItemView;
import com.example.guhugang.view.BaseItemView;

import java.util.List;

/**
 * Created by GuHuGang on 2018/1/24.
 */

public class AiListAdapter extends BaseAdapter implements View.OnClickListener{
    List<TypedData> typedDataList;
    Context mContext;
    private LayoutInflater layoutInflater;
    public AiListAdapter(Context context,List<TypedData> typedDataList){
        this.mContext=context;
        this.typedDataList=typedDataList;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        if(typedDataList!=null)return typedDataList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (typedDataList!=null)return typedDataList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        switch (typedDataList.get(position).getType()) {
            case TypedData.TYPE_BASE:
                BaseItemView baseItemView=new BaseItemView(mContext,null);
                baseItemView.setLocationItemListener(this);
                baseItemView.setCollectionItemListener(this);
                return baseItemView;
            case TypedData.TYPE_AI:
                AiItemView aiItemView=new AiItemView(mContext,null);
                aiItemView.setItemOnclickListener(this);
                return aiItemView;
            case TypedData.TYPE_PERSON:
                convertView = layoutInflater.inflate(R.layout.person_ablm_item, parent, false);
                break;
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_location:
                Intent intent=new Intent(mContext,ShowLocationActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.id_collection:
                Intent intentCollection=new Intent(mContext,ShowResultActivity.class);
                mContext.startActivity(intentCollection);
                break;
            case R.id.id_face:
                Intent intent_face=new Intent(mContext,ShowCategoryActivity.class);
                mContext.startActivity(intent_face);
                break;
            case R.id.id_things:
                Intent intent_tag=new Intent(mContext,TaggedActivity.class);
                mContext.startActivity(intent_tag);
                break;
        }
    }

    class Holder{
        ImageView image;
        TextView name;
        TextView count;

    }
}
