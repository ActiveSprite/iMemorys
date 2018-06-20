package com.example.guhugang.example.guhugang.uploadfileservice;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.guhugang.example.sqlite.DBDao;
import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.SeletActivity;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.MultiChoiceListener;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PhotoUpImageBucket;
import com.example.guhugang.imemorys.utils.PictureUtils;
import com.example.guhugang.moreused.ShowGalleryActivity;
import com.example.guhugang.view.CommonPopupWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AlbumItemActivity<T extends PhotoUpImageBucket> extends AppCompatActivity {
    private GridView gridView;
    public T photoUpImageBucket;
    private AlbumItemAdapter adapter;
    public Toolbar toolbar;
    private BottomNavigationView navigation;
    private MultiChoiceListener multiChoiceListener;
    DBDao dbDao;
    View parent;
    Button delete;
    Button cancel;
    private CommonPopupWindow window;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_share:
                    sharePictures();
                    return true;
                case R.id.navigation_move:
                    openSelector();
                    return true;
                case R.id.navigation_delete:
                    PopupWindow win=window.getPopupWindow();
                    win.setAnimationStyle(R.style.animRotate);
                    window.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.album_item_images);
        toolbar = (Toolbar) findViewById(R.id.toolbar_file);
        toolbar.setTitle("文件夹");
        setToolbarTitle();
        toolbar.setTitleTextAppearance(this,R.style.Theme_ToolBar_Base_Subtitle);
        //设置导航图标要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initPopupWindow();
        navigation=(BottomNavigationView) findViewById(R.id.navigation_grid);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        init();
        parent=findViewById(R.id.container);
        dbDao=new DBDao(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh(){
        if(photoUpImageBucket!=null) {
           for(int i=0;i<photoUpImageBucket.getImageList().size();i++){
               String path=((PhotoUpImageItem)photoUpImageBucket.getImageList().get(i)).getImagePath();
               if(!PictureUtils.fileIsExists(path)){
                   photoUpImageBucket.getImageList().remove(i);
                   adapter.changeItenms(photoUpImageBucket.getImageList());
               }
           }

        }
    }
    private void init(){
        setData();
        gridView = (GridView) findViewById(R.id.album_item_gridv);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AlbumItemActivity.this, ShowGalleryActivity.class);
				intent.putExtra("imagelist",(ArrayList)photoUpImageBucket.getImageList());
				intent.putExtra("position", i);
				AlbumItemActivity.this.startActivity(intent);
//				ShowGalleryActivity.startWithElement(AlbumItemActivity.this,(ArrayList<PhotoUpImageItem>)list,position,v);
            }
        });
        if(photoUpImageBucket!=null) {
            adapter = new AlbumItemAdapter(photoUpImageBucket.getImageList(), AlbumItemActivity.this);
            gridView.setAdapter(adapter);
            gridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
            multiChoiceListener=new MultiChoiceListener(gridView,adapter,this);
            gridView.setMultiChoiceModeListener(multiChoiceListener);
        }
    }
    private void initPopupWindow() {
        // get the height of screen
        // create popup window
        window=new CommonPopupWindow(this, R.layout.popup_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view=getContentView();
                delete=(Button)view.findViewById(R.id.id_delete);
                cancel=(Button)view.findViewById(R.id.id_cancel);
            }
            @Override
            protected void initEvent() {
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteItems();
                        window.getPopupWindow().dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        window.getPopupWindow().dismiss();
                    }
                });
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance=getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp=getWindow().getAttributes();
                        lp.alpha=1.0f;
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        getWindow().setAttributes(lp);
                    }
                });
            }
        };
    }


    public abstract void setData();
    public abstract void setToolbarTitle();
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
  private void deleteItems(){
        SparseBooleanArray array=gridView.getCheckedItemPositions();
        for(int i=0;i<array.size();i++){
            int position=array.keyAt(i);
            position-=i;
            PhotoUpImageItem item=(PhotoUpImageItem) (photoUpImageBucket.getImageList().get(position));
            dbDao.deleteAll(item.getImagePath());
            photoUpImageBucket.getImageList().remove(position);
        }
      adapter.changeItenms(photoUpImageBucket.getImageList());
      multiChoiceListener.getActionMode().finish();
  }
 private void sharePictures(){
     SparseBooleanArray array=gridView.getCheckedItemPositions();
     ArrayList <Uri> uriList=new ArrayList<>();
     for(int i=0;i<array.size();i++){
         int position=array.keyAt(i);
         PhotoUpImageItem item=(PhotoUpImageItem) (photoUpImageBucket.getImageList().get(position));
         File file = new File(item.getImagePath());//这里share.jpg是sd卡根目录下的一个图片文件
         Uri imageUri = Uri.fromFile(file);
         uriList.add(imageUri);
     }
     PictureUtils.SharePictures(this,uriList);
 }
 private void openSelector(){
     int requestCode = 0;
     Intent mIntent = new Intent();
     mIntent.setClass(AlbumItemActivity.this,
            SeletActivity.class);
     startActivityForResult(mIntent, requestCode);
 }
private void movePictures(String newPath){
    SparseBooleanArray array=gridView.getCheckedItemPositions();
    for(int i=0;i<array.size();i++){
        int position=array.keyAt(i);
        PhotoUpImageItem item=(PhotoUpImageItem) (photoUpImageBucket.getImageList().get(position));
        PictureUtils.moveFile(this,item.getImagePath(),newPath+PictureUtils.getName(item.getImagePath()));
    }
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String path = data.getStringExtra("imagePath");

            if (path != null && !path.isEmpty()) {
                movePictures(path);
                deleteItems();
                Toast.makeText(this,"移动成功",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
