package com.example.administrator.myapplication.bitmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.myapplication.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/21 0021.
 */

public class CheckBmpMemoryActivity extends Activity{

    public static void start(Context context){
        Intent intent = new Intent(context, CheckBmpMemoryActivity.class);
        context.startActivity(intent);
    }

    private ImageView mIvGif1;
    private ImageView mIvGif2;
    private ImageView mIvGif3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check_bmp_memory);

        mIvGif1 = (ImageView) findViewById(R.id.tv_gif1);
        mIvGif2 = (ImageView) findViewById(R.id.tv_gif2);
        mIvGif3 = (ImageView) findViewById(R.id.tv_gif3);

        AnimationDrawable animationDrawable1 = (AnimationDrawable)getResources().getDrawable(R.drawable.ai_assist_waiting);
        AnimationDrawable animationDrawable2 = (AnimationDrawable)getResources().getDrawable(R.drawable.ai_assist_recording);
        AnimationDrawable animationDrawable3 = (AnimationDrawable)getResources().getDrawable(R.drawable.ai_assist_analyzing);


        mIvGif1.setImageDrawable(animationDrawable1);
        animationDrawable1.start();
        mIvGif2.setImageDrawable(animationDrawable2);
        animationDrawable2.start();
        mIvGif3.setImageDrawable(animationDrawable3);
        animationDrawable3.start();
//        Observable
//                .create(new Observable.OnSubscribe<GifDrawable>() {
//                    @Override
//                    public void call(Subscriber<? super GifDrawable> subscriber) {
//
//                        try{
//                            GifDrawable drawable =  Glide.with(CheckBmpMemoryActivity.this)
//                                    .load(R.drawable.airecording).asGif().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
//
//                            subscriber.onNext(drawable);
//                            subscriber.onCompleted();
//                        }catch (Exception e){
//                            subscriber.onError(e);
//                        }
//
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<GifDrawable>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(GifDrawable gifDrawable) {
//                        gifDrawable.start();
//                        mIvGif1.setImageDrawable(gifDrawable);
//                    }
//                });



//
//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.airecording);
//        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.aiwaiting);
//        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.analyzing);

//        mIvGif1.setImageDrawable(new BitmapDrawable(bitmap1));
//        mIvGif2.setImageDrawable(new BitmapDrawable(bitmap2));
//        mIvGif3.setImageDrawable(new BitmapDrawable(bitmap3));

    }
}
