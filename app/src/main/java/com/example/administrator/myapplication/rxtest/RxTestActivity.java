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

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by Administrator on 2018/10/31 0031.
 */

public class RxTestActivity extends Activity{

    private TextView mTvTip;
    private Button mBtnTest;
    private PublishSubject<String> mPublishSubject = PublishSubject.create();
    private Subscription mSubscription;
    private int mCount = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rx_test);

        mTvTip = (TextView) findViewById(R.id.tv_tip);
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount++;
                mPublishSubject.onNext(mCount+"");
            }
        });

        mSubscription = mPublishSubject
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(final String s) {
                        return Observable
                                .create(new Observable.OnSubscribe<String>() {
                                    @Override
                                    public void call(Subscriber<? super String> subscriber) {
                                        subscriber.onNext(s+" "+Thread.currentThread().getName());
                                        subscriber.onCompleted();
                                    }
                                })
                                .subscribeOn(Schedulers.newThread());
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

//        mSubscription = mPublishSubject
//                .observeOn(Schedulers.newThread())
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String s) {
//                        return s+" "+s;
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mSubscription != null){
            mSubscription.unsubscribe();
        }
    }

    public static void start(Context context){
        Intent intent = new Intent(context, RxTestActivity.class);
        context.startActivity(intent);
    }
}
