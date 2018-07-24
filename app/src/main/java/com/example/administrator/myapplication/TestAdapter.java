package com.example.administrator.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class TestAdapter extends BaseAdapter{

    private Context mContext;

    public TestAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TestView testView = null;
        if(view == null){
            testView = (TestView)LayoutInflater.from(mContext).inflate(R.layout.view_test, viewGroup, false);
        }else{
            testView = (TestView)view;
        }

        testView.setText(i+"");
        return testView;
    }
}
