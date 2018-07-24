package com.example.administrator.myapplication.leak;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class LeakActivity extends Activity implements ILeakPresenter.IView{

    private ILeakPresenter mPresenter;
    private TextView mTvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leak);

        mTvNext = (TextView)findViewById(R.id.tv_next);
        mPresenter = new LeakPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.startLeak(this);
    }

    @Override
    public void onNext(String s) {
        mTvNext.setText(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.quit();
    }
}
