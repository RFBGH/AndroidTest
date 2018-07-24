package com.example.administrator.myapplication.observer_leak;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class TestObserverLeakActivity extends Activity implements ITestObserver{

    public static void start(Context context){
        Intent intent = new Intent(context, TestObserverLeakActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_oberver_leak);
        TestObserverManager.getInstance().addObserver(this);

        findViewById(R.id.btn_test)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TestObserverManager.getInstance().test();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        TestObserverManager.getInstance().removeObserver(this);
    }

    @Override
    public void onTest() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TestObserverLeakActivity.this, "test", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
