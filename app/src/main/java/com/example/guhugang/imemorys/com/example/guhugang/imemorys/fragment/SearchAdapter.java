package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.TaggedImageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuHuGang on 2017/12/22.
 */

public class SearchAdapter extends BaseAdapter implements Filterable {
    private List<PhotoUpImageBucket<TaggedImageItem>> arrayList;
    private List<PhotoUpImageBucket<TaggedImageItem>> backData;
    private LayoutInflater layoutInflater;
    private Toast mToast;
    private Context context;
    private MyFilter mFilter ;
    private boolean mCheckable;
    private String TAG = AlbumsAdapter.class.getSimpleName();
    public SearchAdapter(Context context,List<PhotoUpImageBucket<TaggedImageItem>> arrayList){
        this.context=context;
        this.arrayList=arrayList;
        Log.i("number",String.valueOf(arrayList.size()));
        layoutInflater = LayoutInflater.from(context);



    };
    @Override
    public int getCount() {
        if(arrayList==null)return 0;
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        if(arrayList==null)return null;
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SearchAdapter.Holder holder;

        if (convertView == null) {
            holder = new SearchAdapter.Holder();
            convertView = layoutInflater.inflate(R.layout.searched_item, parent, false);
            holder.image = (ImageView) convertView.findViewById(R.id.searched_img);

            holder.name = (TextView) convertView.findViewById(R.id.searched_name);
            holder.count = (TextView) convertView.findViewById(R.id.searched_count);


            convertView.setTag(holder);
        }else {
            holder = (SearchAdapter.Holder) convertView.getTag();
        }
        holder.count.setText(""+arrayList.get(position).getCount());
        holder.name.setText(arrayList.get(position).getBucketName());

        File file = new File(arrayList.get(position).getImageList().get(0).getImagePath()) ;
        Glide
                .with(context)
                .load(file)
                .into(holder.image);

        return convertView;
    }

    class Holder{
        ImageView image;
        TextView name;
        TextView count;

    }
    @Override
    public Filter getFilter() {
        if (mFilter ==null){
            mFilter = new MyFilter();
        }
        return mFilter;
    }
    //我们需要定义一个过滤器的类来定义过滤规则
    class MyFilter extends Filter {
        //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<PhotoUpImageBucket<TaggedImageItem>> list ;
            if (TextUtils.isEmpty(charSequence)){//当过滤的关键字为空的时候，我们则显示所有的数据
                list  = backData;
            }else {//否则把符合条件的数据对象添加到集合中
                list = new ArrayList<>();
                for (PhotoUpImageBucket<TaggedImageItem> recomend:backData){
                    if (recomend.getBucketName().contains(charSequence)){
                        list.add(recomend);
                    }

                }
            }
            result.values = list; //将得到的集合保存到FilterResults的value变量中
            result.count = list.size();//将集合的大小保存到FilterResults的count变量中

            return result;
        }
        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            arrayList = (List<PhotoUpImageBucket<TaggedImageItem>>)filterResults.values;
            if (filterResults.count>0){
                notifyDataSetChanged();//通知数据发生了改变
                Log.i("filte",String.valueOf(arrayList.size()));
            }else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }
    }


}
