package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.myapplication.badtoken.BadTokenActivity;
import com.example.administrator.myapplication.bitmap.CheckBmpMemoryActivity;
import com.example.administrator.myapplication.bitmap.CheckGifMemoryActivity;
import com.example.administrator.myapplication.fix_rx_subscriber.FixRxActivity;
import com.example.administrator.myapplication.leak.LeakActivity;
import com.example.administrator.myapplication.leak.LeakActivity2;
import com.example.administrator.myapplication.leak.LeakActivity3;
import com.example.administrator.myapplication.recyleview.TestRecycleViewActivity;
import com.example.administrator.myapplication.rxtest.RxTestActivity;
import com.example.administrator.myapplication.rxtest.RxTestActivity2;
import com.example.administrator.myapplication.rxtest.RxTestActivity3;

import rx.Observable;
import rx.Subscriber;
import rx.plugins.RxJavaHooks;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        RxJavaHooks.setOnObservableCreate(new OnSubscribeOnSubscribe());
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_matrix)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(MainActivity.this, MatrixActivity.class);
//                        MainActivity.this.startActivity(intent);

                        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                            @Override
                            public boolean queueIdle() {

                                Log.d("xxxxx", "idle now");
                                return false;//每次onresume只是执行一次
                            }
                        });
                    }
                });

        findViewById(R.id.btn_bitmap_cut)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        findViewById(R.id.pb_load_progress).setVisibility(View.GONE);
//                        Intent intent = new Intent(MainActivity.this, CutBitmapActivity.class);
//                        MainActivity.this.startActivity(intent);
                    }
                });

        findViewById(R.id.btn_test_rx)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RxTestActivity.start(MainActivity.this);
                    }
                });

        findViewById(R.id.btn_test_rx2)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RxTestActivity2.start(MainActivity.this);
                    }
                });

        findViewById(R.id.btn_test_rx3)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RxTestActivity3.start(MainActivity.this);
                    }
                });



//        mLvTest = (ListView)findViewById(R.id.lv_test);
//        mAdapter = new TestAdapter(this);
//        mLvTest.setAdapter(mAdapter);

        findViewById(R.id.btn_permission_test)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, PermissionTestActivity.class);
                        startActivity(intent);
                    }
                });

        findViewById(R.id.btn_leak)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, LeakActivity.class);
                        startActivity(intent);
                    }
                });

        findViewById(R.id.btn_leak2)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, LeakActivity2.class);
                        startActivity(intent);
                    }
                });

        findViewById(R.id.btn_leak3)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, LeakActivity3.class);
                        startActivity(intent);
                    }
                });

        findViewById(R.id.btn_delay_start_activity)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BadTokenActivity.start(MainActivity.this);
                    }
                });

        findViewById(R.id.btn_test_rx_error_subscriber)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FixRxActivity.start(MainActivity.this);
                    }
                });

        findViewById(R.id.btn_test_gif_memory)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckGifMemoryActivity.start(MainActivity.this);
                    }
                });

        findViewById(R.id.btn_test_bmp_memory)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBmpMemoryActivity.start(MainActivity.this);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {

                Log.d("xxxxx", "onResume idle now");
                return false;//每次onresume只是执行一次
            }
        });
    }

}
