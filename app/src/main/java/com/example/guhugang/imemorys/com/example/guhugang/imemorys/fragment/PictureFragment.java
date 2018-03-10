package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.guhugang.imemorys.R;
import com.example.guhugang.moreused.ImageResizer;
import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.FileBitmapDecoderFactory;

import org.lasque.tusdk.TuSdkGeeV1;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.core.utils.image.BitmapHelper;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuEditMultipleComponent;
import org.lasque.tusdk.modules.components.TuSdkComponent;

import java.io.File;

/**
 * Created by GuHuGang on 2017/10/23.
 */

public class PictureFragment extends Fragment{
    LargeImageView layer1;
    SubsamplingScaleImageView layer2;
    ImageView v;
    String path;
    String ImageId;
    Handler handler;
    Animation animation;
    ImageResizer imageResizer;
    public static PictureFragment newInstance(String path,String imageId) {
        PictureFragment imageFragment = new PictureFragment();
        Bundle args = new Bundle();
        args.putString("image", path);
        args.putString("imageid",imageId);
        Log.i("my_path",path);
        imageFragment.setArguments(args);
        return imageFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //引入我们的布局
        View view=inflater.inflate(R.layout.show_large_image, container, false);
        v=(ImageView) view.findViewById(R.id.non);
        layer1=(LargeImageView)view.findViewById(R.id.layer_bottom);
        layer2=(SubsamplingScaleImageView)view.findViewById(R.id.layer_top);
        layer2.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
//        layer2.setMaxScale(10.0f);
//        layer2.setDoubleTapZoomDpi(180);
        path= getArguments().getString("image");
        ImageId=getArguments().getString("imageid");
        handler=new Handler();
//        initView();
        layer1.setTransitionName("picture");
        final File file = new File(path) ;
//        layer1.setImage(new FileBitmapDecoderFactory(file));
        loadBitmap();
//        loadBitmaptoImage();
        return view;
    }
    public String getPath(){
        return path;
    }
    public String getImageId(){return ImageId;}
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void loadBitmap(){
        final File file = new File(path) ;

        Glide.with(getActivity()).load(file).listener(new RequestListener<File, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, File model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, File model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                layer1.setImage((Drawable)resource);
                layer1.setImage(new FileBitmapDecoderFactory(file),(Drawable)resource);
                final ConstantState constantState = ConstantState.getInstance();
                layer1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(constantState.getEditMode()) {
                                constantState.setEditMode(false, path);
                                v.setBackgroundColor(Color.BLACK);
                            }else{
                                constantState.setEditMode(true,path);
                                v.setBackgroundColor(Color.WHITE);
                            }
                        }
                    });


                return false;
            }
        }).into(v);


//        Glide.with(getActivity()).load(file).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                if(resource!=null) {
//                    Drawable drawable = new BitmapDrawable(resource);
//                    final ConstantState constantState = ConstantState.getInstance();
//                    layer1.setImage(drawable);
//                    v.setVisibility(View.INVISIBLE);
//                    if (constantState.getEditMode()) {
//                        layer1.setBackgroundColor(Color.WHITE);
//                    } else {
//                        layer1.setBackgroundColor(Color.BLACK);
//                    }
//                    layer1.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            if(constantState.getEditMode()) {
//                                constantState.setEditMode(false, path);
//                                v.setBackgroundColor(Color.BLACK);
//                            }else{
//                                constantState.setEditMode(true,path);
//                                v.setBackgroundColor(Color.WHITE);
//                            }
//                        }
//                    });
//                }
//            }
//        });

    }

    public void loadBitmaptoImage(){
        final File file = new File(path) ;
        imageResizer=new ImageResizer();

        imageResizer.AsyncGetBitmap(path,new ImageResizer.ResizerCompleteCallBack(){

            @Override
            public void resizerComplete(Bitmap bitmap) {
                if(bitmap!=null) {
                    Drawable drawable = new BitmapDrawable(bitmap);
                    final ConstantState constantState = ConstantState.getInstance();
                    layer1.setImage(new FileBitmapDecoderFactory(file), drawable);
                    if (constantState.getEditMode()) {
                        layer1.setBackgroundColor(Color.WHITE);
                    } else {
                        layer1.setBackgroundColor(Color.BLACK);
                    }
                    layer1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(constantState.getEditMode()) {
                                constantState.setEditMode(false, path);
                                v.setBackgroundColor(Color.BLACK);
                            }else{
                                constantState.setEditMode(true,path);
                                v.setBackgroundColor(Color.WHITE);
                            }
                        }
                    });
                }
            }
        });
    }
    public void initView(){

        final File file = new File(path) ;


        imageResizer=new ImageResizer();

            //    .into(v);
        imageResizer.AsyncGetBitmap(path,new ImageResizer.ResizerCompleteCallBack(){

                    @Override
                    public void resizerComplete(Bitmap bitmap) {
                        if(bitmap!=null) {
                            //layer1.setImage(bitmap);
                            //handler.postDelayed(new ImageRunnable(file),1000);
                            //layer2.setImage(new FileBitmapDecoderFactory(file));
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            Bitmap bitmap1 = BitmapFactory.decodeFile(path, options);
                            int width=options.outWidth;
                            int height=options.outHeight;
                            layer2.setImage(ImageSource.uri(path).dimensions(width,height),ImageSource.bitmap(bitmap));
                            layer2.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                            v.setVisibility(View.INVISIBLE);
                            final ConstantState constantState=ConstantState.getInstance();
                            if(constantState.getEditMode()) {
                                layer2.setBackgroundColor(Color.WHITE);
                            }else{
                                layer2.setBackgroundColor(Color.BLACK);
                            }
                            layer2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(constantState.getEditMode()) {
                                        constantState.setEditMode(false, path);
                                        v.setBackgroundColor(Color.BLACK);
                                    }else{
                                        constantState.setEditMode(true,path);
                                        v.setBackgroundColor(Color.WHITE);
                                    }
                                }
                            });


                        }

                    }
                }

        );


    }

}
