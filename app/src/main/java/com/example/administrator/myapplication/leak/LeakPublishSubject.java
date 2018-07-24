package com.example.administrator.myapplication.leak;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Administrator on 2018/2/1 0001.
 */

public class LeakPublishSubject {

    private static LeakPublishSubject sInstance = new LeakPublishSubject();
    public static LeakPublishSubject getInstance(){
        return sInstance;
    }

    private int mCount = 0;
    private PublishSubject<String> mPublishSubject = PublishSubject.create();

    private LeakPublishSubject(){

        new Thread(){

            @Override
            public void run() {

                while(true){

                    mPublishSubject.onNext(mCount+"");
                    mCount++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }

    public Observable<String> getObservable(){
        return mPublishSubject.asObservable();
    }
}
