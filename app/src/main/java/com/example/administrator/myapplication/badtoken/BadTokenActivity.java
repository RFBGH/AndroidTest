package com.example.administrator.myapplication.badtoken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/23 0023.
 */

public class BadTokenActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_badtoken);

        findViewById(R.id.btn_delay_start_activity)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        test();
                    }
                });
    }

    private void test(){

        Log.e("BadTokenActivity", "finish self");
        finish();
        Observable
                .create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        subscriber.onNext(null);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Object o) {

                        Log.e("BadTokenActivity", "startMainActivity");
                        Intent intent = new Intent(BadTokenActivity.this, MainActivity.class);
                        BadTokenActivity.this.startActivity(intent);
                    }
                });

    }

    public static void start(Context context){
        Intent intent = new Intent(context, BadTokenActivity.class);
        context.startActivity(intent);
    }
}
