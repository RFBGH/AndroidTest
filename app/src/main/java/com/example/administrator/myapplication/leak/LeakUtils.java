package com.example.administrator.myapplication.leak;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class LeakUtils {

    public static Observable<String> getTestLeakObs(){

        final PublishSubject<String>publishSubject = PublishSubject.create();

        new Thread(){

            @Override
            public void run() {

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishSubject.onNext("1");
            }
        }.start();

        return publishSubject
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(final String s) {
                        return Observable
                                .create(new Observable.OnSubscribe<String>() {
                                    @Override
                                    public void call(Subscriber<? super String> subscriber) {

                                        subscriber.onNext(s+"xxx");
                                        subscriber.onCompleted();
                                    }
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                });

    }

}
