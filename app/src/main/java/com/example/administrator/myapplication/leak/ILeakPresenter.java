package com.example.administrator.myapplication.leak;

import android.content.Context;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public interface ILeakPresenter {

    void startLeak(Context context);

    void quit();

    interface IView{

        void onNext(String s);

    }
}
