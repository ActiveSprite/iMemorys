package com.example.guhugang.moreused;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.guhugang.example.guhugang.uploadfileservice.CollectImageItem;
import com.example.guhugang.example.guhugang.uploadfileservice.ReadText;
import com.example.guhugang.example.sqlite.DBDao;
import com.example.guhugang.imemorys.PhotoUpImageItem;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.imemorys.SwicherAdapter;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.ConstantState;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.MediaAdapter;
import com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment.PictureFragment;
import com.shizhefei.view.largeimage.LargeImageView;

import org.lasque.tusdk.TuSdkGeeV1;
import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.core.utils.image.BitmapHelper;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuEditMultipleComponent;
import org.lasque.tusdk.modules.components.TuSdkComponent;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by GuHuGang on 2017/5/21.
 */

public abstract class GalleryActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewpager;
    private int tag;
    private FrameLayout fl;
    BottomLayout bottom;
    View top;
    public ArrayList<PhotoUpImageItem> imglist=null;
    public int position;
    public int mLocationX;
    public int mLocationY ;
    public int mWidth;
    public int mHeight;
    String path;
    LinearLayout share;
    ReadText readtext;
    ArrayList<LargeImageView>mViews;
    MediaAdapter adapter;
    ContentResolver cr;



    DBDao dbDao;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.show_bigphoto);
        viewpager=(ViewPager) findViewById(R.id.vp);
        top=(View)findViewById(R.id.top_menu);
        bottom=(BottomLayout) findViewById(R.id.bottom_menu);
        top=(View)findViewById(R.id.top_menu);
        bottom.setlistener(this);
        cr =this.getContentResolver();
        TuSdk.init(this.getApplicationContext(), "c9178dd3040435a1-00-uuwxq1","debug");
        //  TuSdk.checkFilterManager(mFilterManagerDelegate);
        TuSdk.enableDebugLog(true);
        dbDao=new DBDao(this);
        fl=(FrameLayout) findViewById(R.id.fl);
        mViews=new ArrayList();


        ConstantState constantState=ConstantState.getInstance();
        constantState.setonEditModeListener(new ConstantState.onEditModeChangeListener() {
            @Override
            public void onModeChanged(boolean mode, String path) {
                if(!mode) {
                    bottom.setVisibility(View.INVISIBLE);
                    top.setVisibility(View.INVISIBLE);
                    Animation top_out = AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.top_moveout);
                    top.startAnimation(top_out);

                    Animation anim_out = AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.moveout);
                    bottom.startAnimation(anim_out);
                }
                else{
                    Animation anim_in = AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.movein);
                    bottom.setVisibility(View.VISIBLE);
                    top.setVisibility(View.VISIBLE);
                    Animation top_in = AnimationUtils.loadAnimation(GalleryActivity.this, R.anim.top_movein);
                    top.startAnimation(top_in);

                    bottom.startAnimation(anim_in);
                    GalleryActivity.this.path=path;

                }
            }
        });
        receiveparams();
        initview();
    }
    public abstract void receiveparams();
    public void initview(){
        for(int i=0;i<5;i++){
            LargeImageView imageView=new LargeImageView(this);
            mViews.add(imageView);
        }
        if(imglist!=null){
            adapter=new MediaAdapter(getSupportFragmentManager(),imglist);
            viewpager.setOffscreenPageLimit(0);
            viewpager.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            viewpager.setPageTransformer(true, new ViewPager.PageTransformer() {

                @Override
                public void transformPage(View page, float position) {
                    //rollingPage(page,position);//调用翻页效果
                    imitateQQ(page,position);
                    //raised3D(page,position);
                    //sink3D(page,position);
                }
            });
            viewpager.setAdapter(adapter);
            viewpager.setCurrentItem(position);
            //viewpager.setPageMargin((int)getResources().getDimensionPixelOffset(5));
        }
        share=(LinearLayout)findViewById(R.id.share);
    }
    public void rollingPage(View view,float position){
        if(position>=-1&&position<=1){
            view.setPivotX(0);
            if(position<0){
                view.setTranslationX(-position*view.getWidth());
                view.setRotationY(90*position);
                view.setScaleX(1-Math.abs(position));
            }
            else{
                view.setTranslationX(-position*view.getWidth());
            }

        }
    }

    public void sink3D(View view,float position){
        if(position>=-1&&position<=1){
            view.setPivotX(position<0?view.getWidth():0);
            view.setRotationY(-90*position);
        }
    }

    public void raised3D(View view,float position){
        if(position>=-1&&position<=1){
            view.setPivotX(position<0?view.getWidth():0);//设置要旋转的Y轴的位置
            view.setRotationY(90*position);//开始设置属性动画值
        }
    }



    public void imitateQQ(View view,float position){
        if(position>=-1&&position<=1){
            view.setPivotX(position>0?0:view.getWidth()/2);
            //view.setPivotY(view.getHeight()/2);
            view.setScaleX((float)((1-Math.abs(position)<0.5)?0.5:(1-Math.abs(position))));
            view.setScaleY((float)((1-Math.abs(position)<0.5)?0.5:(1-Math.abs(position))));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit:edit();break;
            case R.id.reserve: collect();break;
            case R.id.share: share();break;
            case R.id.delete: break;
            case R.id.more :break;

        }
    }

    @Override
    public void onPause(){
        super.onPause();
        //GalleryActivity.this.overridePendingTransition(R.anim.activity_out, 0);
    }
    public void edit(){
        TuSdkComponent.TuSdkComponentDelegate delegate = new TuSdkComponent.TuSdkComponentDelegate()
        {
            @Override
            public void onComponentFinished(TuSdkResult result, Error error, TuFragment lastFragment)
            {
                TLog.d("onEditMultipleComponentReaded: %s | %s", result, error);
            }
        };
        TuEditMultipleComponent component = null;
        component = TuSdkGeeV1.editMultipleCommponent(this, delegate);
        File file=new File(path);
        Bitmap bitmap = BitmapHelper.getBitmap(file);
        TuSdkResult result = new TuSdkResult();
        result.image = bitmap;
        component.setImage(result.image)
                // 设置系统照片
                .setImageSqlInfo(result.imageSqlInfo)
                // 设置临时文件
                .setTempFilePath(result.imageFile)
                // 在组件执行完成后自动关闭组件
                .setAutoDismissWhenCompleted(true)
                // 开启组件
                .showComponent();
        component.componentOption().editMultipleOption().setSaveToAlbum(true);

    }
    public void collect(){
        PictureFragment pictureFragment=(PictureFragment)adapter.currentFragment;
        String picture_path=pictureFragment.getPath();
        String picture_id=pictureFragment.getImageId();
        Log.i("picture_id",picture_id);
        CollectImageItem collectImageItem=new CollectImageItem();
        collectImageItem.setImageId(picture_id);
        collectImageItem.setImagePath(picture_path);
        dbDao.addCollection(collectImageItem);
        Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
        ArrayList<CollectImageItem> list=dbDao.selectCollectionItem();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                Log.i("id",list.get(i).getImageId());
            }
        }

    }

    public void share(){
        PictureFragment pictureFragment=(PictureFragment)adapter.currentFragment;
        String picture_path=pictureFragment.getPath();

        File file = new File(picture_path);//这里share.jpg是sd卡根目录下的一个图片文件
        Uri imageUri = Uri.fromFile(file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享图片"));

    }
    public void onDestroy(){
        super.onDestroy();
        ConstantState constantState=ConstantState.getInstance();
        constantState.setEditMode(false,null);
    }
}
