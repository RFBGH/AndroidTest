package com.example.administrator.myapplication.rxtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by Administrator on 2018/10/31 0031.
 */

public class RxTestActivity3 extends Activity{

    private TextView mTvTip;
    private TextView mTvTip2;
    private Button mBtnTest;
    private PublishSubject<String> mPublishSubject = PublishSubject.create();
    private PublishSubject<String> mPublishSubject2 = PublishSubject.create();
    private Subscription mSubscription;
    private Subscription mSubscription2;

    private ExecutorService mSingleEs = Executors.newSingleThreadExecutor();

    private int mCount = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_test);

        mTvTip = (TextView) findViewById(R.id.tv_tip);
        mTvTip2 = (TextView) findViewById(R.id.tv_tip2);

        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount++;
                mPublishSubject.onNext(mCount+"");
                mPublishSubject2.onNext(mCount+"");
            }
        });

//        mSubscription = mPublishSubject
//                .flatMap(new Func1<String, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(final String s) {
//                        return Observable
//                                .create(new Observable.OnSubscribe<String>() {
//                                    @Override
//                                    public void call(Subscriber<? super String> subscriber) {
//                                        subscriber.onNext(s+" "+s+" "+Thread.currentThread().getName());
//                                        subscriber.onCompleted();
//                                    }
//                                })
//                                .subscribeOn(Schedulers.newThread());
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        mTvTip.setText(s);
//                    }
//                });

        mSubscription = mPublishSubject
                .observeOn(Schedulers.from(mSingleEs))
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return Thread.currentThread().getName()+" "+s;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        mTvTip.setText(s);
                    }
                });

        mSubscription2 = mPublishSubject2
                .observeOn(Schedulers.from(mSingleEs))
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return Thread.currentThread().getName()+" "+s;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        mTvTip2.setText(s);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mSubscription != null){
            mSubscription.unsubscribe();
        }

        if(mSubscription2 != null){
            mSubscription2.unsubscribe();
        }
    }

    public static void start(Context context){
        Intent intent = new Intent(context, RxTestActivity3.class);
        context.startActivity(intent);
    }
}
