package com.example.administrator.myapplication.recyleview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.BatchingListUpdateCallback;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/26 0026.
 */

public class TestRecycleViewActivity extends Activity{

    private RecyclerView mRvTest;

    private LinearLayoutManager mLinearLayoutManager;
    private TestAdapter mAdapter;
    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_recycleview);
        findViewById(R.id.btn_test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testNormal();
            }
        });
        findViewById(R.id.btn_test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testInsert();
            }
        });
        findViewById(R.id.btn_test3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDiff();
            }
        });
        findViewById(R.id.btn_test4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testInsertDetail();
            }
        });

        for(int i = 0; i < 50 ;i++){
            mData.add("test"+i);
        }

        mRvTest = (RecyclerView)findViewById(R.id.rv_test);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setRecycleChildrenOnDetach(true);
        mRvTest.setLayoutManager(mLinearLayoutManager);
        mRvTest.getRecycledViewPool().setMaxRecycledViews(0, 20);

        mAdapter = new TestAdapter(this, mData);
        mRvTest.setAdapter(mAdapter);
    }

    private void testNormal(){

//        for(int i = 0; i < 5; i++){
//            mData.add(3, "add Item "+i);
//        }

        mData.add(mData.size()-2, "add item");
//        mData.add("add item");
//        mData.add("add item");
//        mData.add("add item");

        mAdapter.notifyDataSetChanged();
        mLinearLayoutManager.scrollToPosition(mData.size()-1);
    }

    int addCount = 0;
    private void testInsert(){

        List<String> oldList = new ArrayList<>();
        oldList.addAll(mData);

        int oldSize = mData.size();
        mData.add(0, "add item start"+addCount);
        mData.add(mData.size()-2, "add item end"+addCount);
//        mData.add("add item");
//        mData.add("add item");
//        mData.add("add item");

        mAdapter.notifyItemRangeInserted(oldSize, mData.size()-oldSize);

        for(int i = 0; i < oldList.size(); i++){

            String oldStr = oldList.get(i);
            String newStr = mData.get(i);
            if(!oldStr.equals(newStr)){
                mAdapter.notifyItemRangeChanged(i, mData.size() - i);
            }
        }

        mLinearLayoutManager.scrollToPosition(mData.size()-1);

        addCount++;
    }

    private void testDiff(){

        List<String> oldList = new ArrayList<>();
        oldList.addAll(mData);


        mData.add(0, "add item start"+addCount);
        mData.add(mData.size()-2, "add item end"+addCount);
        mData.add(mData.size()-2, "add item end"+addCount);
        mData.add(mData.size()-2, "add item end"+addCount);
        mData.add(mData.size()-2, "add item end"+addCount);


        // 是否发生变动
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(new MsgDiffCallBack(oldList, mData), false);
        diffResult.dispatchUpdatesTo(new BatchingListUpdateCallback(new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {
                mAdapter.notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                mAdapter.notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                mAdapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count, Object payload) {
                mAdapter.notifyItemRangeChanged(position, count, payload);
            }
        }));

        mLinearLayoutManager.scrollToPosition(mData.size()-1);

        addCount++;
    }

    private void testInsertDetail(){

        int start = mData.size()-2;

        mData.add(mData.size()-2, "add item end"+addCount);
        mData.add(mData.size()-2, "add item end"+addCount);
        mData.add(mData.size()-2, "add item end"+addCount);
        mData.add(mData.size()-2, "add item end"+addCount);


        // 是否发生变动
//        DiffUtil.DiffResult diffResult =
//                DiffUtil.calculateDiff(new MsgDiffCallBack(oldList, mData), true);
//        diffResult.dispatchUpdatesTo(mAdapter);
        mAdapter.notifyItemRangeInserted(start, 4);

        mLinearLayoutManager.scrollToPosition(mData.size()-1);

        addCount++;
    }

    public static void start(Context context){
        Intent intent = new Intent(context, TestRecycleViewActivity.class);
        context.startActivity(intent);
    }

    public static class MsgDiffCallBack extends DiffUtil.Callback {

        private List<String> mOldList;
        private List<String> mNewList;

        public MsgDiffCallBack(List<String> oldList, List<String> newList){
            mOldList = oldList;
            mNewList = newList;
        }

        @Override
        public int getOldListSize() {
            return mOldList.size();
        }

        @Override
        public int getNewListSize() {
            return mNewList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            String oldStr = mOldList.get(oldItemPosition);
            String newStr = mNewList.get(newItemPosition);
            return oldStr.equals(newStr);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return true;
        }
    }
}
