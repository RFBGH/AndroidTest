package com.example.administrator.myapplication.fix_rx_subscriber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.myapplication.R;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/23 0023.
 */

public class FixRxActivity extends Activity{

    public static void start(Context context){
        Intent intent = new Intent(context, FixRxActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fix_rx_subscriber);


        findViewById(R.id.btn_rx_error_test)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Observable
                                .create(new Observable.OnSubscribe<Void>() {
                                    @Override
                                    public void call(Subscriber<? super Void> subscriber) {
                                        String s = null;
                                        s.length();
                                        subscriber.onCompleted();
                                    }
                                })
                                .subscribeOn(Schedulers.io())
                                .subscribe();

                    }
                });
    }
}

