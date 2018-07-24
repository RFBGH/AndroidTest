package com.example.administrator.myapplication.leak;

import android.content.Context;
import android.util.Log;

import com.example.administrator.myapplication.R;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class LeakPresenter implements ILeakPresenter{

    private IView mView;
    private Subscription mSub;

    private static Observable.OnSubscribe<String> Subscriber = new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {

            for(int i = 0; i < 1000; i++){

                subscriber.onNext(i+"");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d("xxxx", "call: "+i);
            }
            subscriber.onCompleted();
        }
    };

    public LeakPresenter(IView view){
        mView = view;
    }

    @Override
    public void startLeak(Context context) {

        mSub = Observable
                .create(Subscriber)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        mView.onNext(s);
                    }
                });
    }

    @Override
    public void quit() {
        if(mSub != null){
            mSub.unsubscribe();
        }
        mView = null;
    }
}
