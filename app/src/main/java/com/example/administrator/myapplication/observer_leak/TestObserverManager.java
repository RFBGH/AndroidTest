package com.example.administrator.myapplication.observer_leak;

import com.example.administrator.myapplication.common.RxUtils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action0;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class TestObserverManager {
    private static final TestObserverManager ourInstance = new TestObserverManager();

    public static TestObserverManager getInstance() {
        return ourInstance;
    }

    private List<ITestObserver> mObservers = new ArrayList<>();

    private TestObserverManager() {
    }

    public void addObserver(ITestObserver observer){
        mObservers.add(observer);
    }

    public void removeObserver(ITestObserver observer){
        mObservers.remove(observer);
    }

    public void test(){

        RxUtils.startActionsOnIo(new Action0() {
            @Override
            public void call() {
                List<ITestObserver> temps = new ArrayList<>();
                temps.addAll(mObservers);

                for(ITestObserver observer:temps){
                    observer.onTest();
                }
            }
        });
    }

}
