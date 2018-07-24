package com.example.administrator.myapplication.leak;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.administrator.myapplication.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class LeakActivity3 extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leak);

        StateTestUtils stateTestUtils = getStateTestUtils();
        stateTestUtils.test();
    }

    public StateTestUtils getStateTestUtils(){
        return new StateTestUtils(){
            @Override
            public void test1() {
                Toast.makeText(LeakActivity3.this, "xxxx", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
