package com.example.administrator.myapplication.recyleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/26 0026.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHold>{

    private List<String> mData;
    private Context mContext;
    public TestAdapter(Context context, List<String> data){

        mData = data;
        mContext = context;
    }

    @Override
    public TestHold onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView view = (TextView)LayoutInflater.from(mContext).inflate(R.layout.rv_item_test, parent, false);
        return new TestHold(view);
    }

    @Override
    public void onBindViewHolder(final TestHold holder, int position) {

        if(holder.mTextSub != null){
            holder.mTextSub.unsubscribe();
        }

        holder.mTvTest.setText("");
        holder.mTextSub = Observable
                .just(position)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return mData.get(integer);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        holder.mTvTest.setText(s);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class TestHold extends RecyclerView.ViewHolder{

        public TextView mTvTest;
        public Subscription mTextSub;

        public TestHold(TextView itemView) {
            super(itemView);

            mTvTest = itemView;
        }
    }
}
