package com.example.administrator.myapplication.bitmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

public class CheckGifMemoryActivity extends Activity{

    public static void start(Context context){
        Intent intent = new Intent(context, CheckGifMemoryActivity.class);
        context.startActivity(intent);
    }

    private ImageView mIvGif1;
    private ImageView mIvGif2;
    private ImageView mIvGif3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check_gif_memory);

        mIvGif1 = (ImageView) findViewById(R.id.tv_gif1);
        mIvGif2 = (ImageView) findViewById(R.id.tv_gif2);
        mIvGif3 = (ImageView) findViewById(R.id.tv_gif3);

        Glide.with(this).load(R.drawable.airecording).into(mIvGif1);
//        Glide.with(this).load(R.drawable.aiwaiting).into(mIvGif2);
//        Glide.with(this).load(R.drawable.analyzing).into(mIvGif3);

        Observable
                .create(new Observable.OnSubscribe<GifDrawable>() {
                    @Override
                    public void call(Subscriber<? super GifDrawable> subscriber) {

                        try{
                            GifDrawable drawable =  Glide.with(CheckGifMemoryActivity.this)
                                    .load(R.drawable.airecording).asGif().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

                            subscriber.onNext(drawable);
                            subscriber.onCompleted();
                        }catch (Exception e){
                            subscriber.onError(e);
                        }

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GifDrawable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GifDrawable gifDrawable) {
                        gifDrawable.start();
                        mIvGif1.setImageDrawable(gifDrawable);
                    }
                });



//
//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.airecording);
//        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.aiwaiting);
//        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.analyzing);

//        mIvGif1.setImageDrawable(new BitmapDrawable(bitmap1));
//        mIvGif2.setImageDrawable(new BitmapDrawable(bitmap2));
//        mIvGif3.setImageDrawable(new BitmapDrawable(bitmap3));

    }
}
