package com.example.guhugang.imemorys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.HomeAdapter;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoFragment;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.SearchFragment;
import com.example.guhugang.imemorys.utils.PictureUtils;
import com.example.guhugang.view.CommonPopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import co.lujun.androidtagview.TagContainerLayout;

/**
 * Created by GuHuGang on 2018/2/9.
 */

public class SeachActivity extends AppCompatActivity {
    Toolbar toolbar;
    TagContainerLayout hisContainerLayout;
    TagContainerLayout timeContainerLayout;
    TagContainerLayout locContainerLayout;
    TagContainerLayout thingContainerLayout;
    List<PhotoUpImageBucket<TaggedImageItem>> currentData;
    List<PhotoUpImageBucket<PhotoUpImageItem>>TimaDatas;
    private CommonPopupWindow.LayoutGravity layoutGravity;
    EditText searchView;
    private ListView dataList;
    private TimeAdapter timeAdapter;
    TagAdapter adapter;
    private List<PhotoUpImageBucket<TaggedImageItem>> datas=new ArrayList<>();
    HashMap<String, PhotoUpImageBucket<PhotoUpImageItem>> bucketList = new HashMap<String, PhotoUpImageBucket<PhotoUpImageItem>>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_activty);
        toolbar = (Toolbar) findViewById(R.id.search_bar_toolbar);
        //设置导航图标要在setSupportActionBar方法之后
        toolbar.setTitle("");
        toolbar.setTitleTextAppearance(this,R.style.Theme_ToolBar_Base_Subtitle);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        setTagData();
        loadTimeData();
    }
    public void initView(){
        adapter=new TagAdapter();
        dataList=(ListView) findViewById(R.id.id_datalist);

        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SeachActivity.this,ShowTagActivity.class);
                intent.putExtra("imagelist", currentData.get(position));
                intent.putExtra("bucketname",currentData.get(position).getBucketName());
                SeachActivity.this.startActivity(intent);
            }
        });
        //        dataList.setTextFilterEnabled(true);
        loadData();
        hisContainerLayout=(TagContainerLayout)findViewById(R.id.history_tag);
        timeContainerLayout=(TagContainerLayout)findViewById(R.id.time_tag);
        locContainerLayout=(TagContainerLayout)findViewById(R.id.local_tag);
        thingContainerLayout=(TagContainerLayout)findViewById(R.id.things_tag);
        searchView=(EditText)findViewById(R.id.id_search);
        layoutGravity=new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.ALIGN_LEFT| CommonPopupWindow.LayoutGravity.TO_BOTTOM);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(charSequence)){
                    dataList.setVisibility(View.INVISIBLE);
                    dataList.clearTextFilter();
                }else{
                    dataList.setVisibility(View.VISIBLE);
                    if(PictureUtils.HasDigit(charSequence.toString())) {
                        searchByTime();
                    }else{
                        dataList.setAdapter(adapter);
                        Filter filter = adapter.getFilter();
                        filter.filter(charSequence);
                    }

                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void setTagData(){
        List<String>tags1=new ArrayList<>();
        tags1.add("我自己");
        tags1.add("武汉");
        tags1.add("长沙");
        hisContainerLayout.setTags(tags1);
        timeContainerLayout.setTags("上个月","今年夏天","2018.4");
        locContainerLayout.setTags("长沙","武汉");
        thingContainerLayout.setTags("文本","风景","树");
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search_toolbar,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_search:
                searchByTime();
                break;
        }
        return true;
    }
    public void searchByTime(){
        String content = searchView.getText().toString().trim();
        String pattern = "\\d{1,4}\\D\\d{1,2}\\D\\d{1,2}.";
        boolean isMatch = Pattern.matches(pattern, content);
        String result = content.replaceAll("\\D","/");
        dataList.setAdapter(timeAdapter);
        Filter filter = timeAdapter.getFilter();
        filter.filter(result);
    }
  public void onPause(){
        super.onPause();
        overridePendingTransition(0,0);
  }
  public void loadData(){
      FindTagTask tagTask=new FindTagTask(this);
      tagTask.setGetAlbumList(new FindTagTask.GetAlbumList() {
          @Override
          public void getAlbumList(List<PhotoUpImageBucket<TaggedImageItem>> list) {
              if(list!=null){
                 for(PhotoUpImageBucket<TaggedImageItem>bucket:list){
                     datas=list;
                     adapter.notifyDataSetChanged();
                 }
              }
          }
      });
      tagTask.execute("string");
  }
  public void loadTimeData(){
      ImageScanner mScanner = new ImageScanner(this);

      mScanner.scanImages(new ImageScanner.ScanCompleteCallBack() {

          @Override
          public void scanComplete(Cursor cursor) {
              // 关闭进度条
              while (cursor.moveToNext()) {
                  // 获取图片的路径
                  String path = cursor.getString(cursor
                          .getColumnIndex(MediaStore.Images.Media.DATA));
                  String id=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                  long times = cursor.getLong(cursor
                          .getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                  PhotoUpImageItem item=new PhotoUpImageItem();
                  item.setImagePath(path);
                  item.setImageId(id);
                  item.time=paserTimeToYM(times);
                  Log.i("time",item.time);
                  PhotoUpImageBucket<PhotoUpImageItem> bucket = bucketList.get(item.time);
                  if (bucket == null) {
                      bucket = new PhotoUpImageBucket();
                      bucketList.put(item.time, bucket);
                      bucket.imageList = new ArrayList<PhotoUpImageItem>();
                      bucket.bucketName = item.time;
                  }
                  bucket.count++;
                  bucket.imageList.add(item);
              }
              cursor.close();
              List<PhotoUpImageBucket<PhotoUpImageItem>> tmpList = new ArrayList<PhotoUpImageBucket<PhotoUpImageItem>>();
              Iterator<Map.Entry<String, PhotoUpImageBucket<PhotoUpImageItem>>> itr = bucketList.entrySet().iterator();
              while (itr.hasNext()) {
                  Map.Entry<String, PhotoUpImageBucket<PhotoUpImageItem>> entry = (Map.Entry<String, PhotoUpImageBucket<PhotoUpImageItem>>) itr
                          .next();
                  tmpList.add(entry.getValue());
              }
              TimaDatas=tmpList;
              timeAdapter=new TimeAdapter(SeachActivity.this,TimaDatas);
          }
      });

  }

    public static String paserTimeToYM(long time) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/");
        return format.format(new Date(time * 1000L));
    }
  class TagAdapter extends BaseAdapter implements Filterable {

      MyFilter mFilter;
      @Override
      public int getCount() {
          if(currentData!=null){
              return currentData.size();
          }else {
              return 0;
          }
      }

      @Override
      public Object getItem(int position) {
          if(currentData!=null)return currentData.get(position);
          return null;
      }

      @Override
      public long getItemId(int position) {
          return position;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
          convertView= LayoutInflater.from(SeachActivity.this).inflate(R.layout.item_popup_list,parent,false);
          ImageView img=(ImageView)convertView.findViewById(R.id.item_img);
          TextView tag=(TextView)convertView.findViewById(R.id.item_text);
          if(currentData!=null) {
              Glide.with(SeachActivity.this).load(currentData.get(position).getImageList().get(0).getImagePath()).into(img);
              tag.setText(currentData.get(position).getBucketName());
          }
              return convertView;
      }

      @Override
      public Filter getFilter() {
          if (mFilter ==null){
              mFilter = new MyFilter();
          }
          return mFilter;
      }
      class MyFilter extends Filter{
          //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
          @Override
          protected FilterResults performFiltering(CharSequence charSequence) {
              FilterResults result = new FilterResults();
              List<PhotoUpImageBucket<TaggedImageItem>> list ;
              if (TextUtils.isEmpty(charSequence)){//当过滤的关键字为空的时候，我们则显示所有的数据
                  list  = datas;
              }else {//否则把符合条件的数据对象添加到集合中
                  list = new ArrayList<>();
                  if(datas!=null) {
                      for (PhotoUpImageBucket<TaggedImageItem> recomend : datas) {
                          if (recomend.getBucketName().contains(charSequence)) {
                              list.add(recomend);
                          }
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
              currentData = (List<PhotoUpImageBucket<TaggedImageItem>>)filterResults.values;
              if (filterResults.count>0){
                  notifyDataSetChanged();//通知数据发生了改变
              }else {
                  notifyDataSetInvalidated();//通知数据失效
              }
          }
      }
  }
}
