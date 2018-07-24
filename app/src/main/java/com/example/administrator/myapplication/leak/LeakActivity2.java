package com.example.administrator.myapplication.leak;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.administrator.myapplication.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class LeakActivity2 extends Activity{

    private MaterialDialog mMaterialDialog;

    @Override
    protected void onResume() {
        super.onResume();

        mMaterialDialog = new MaterialDialog.Builder(this)
                .title("tip")
                .content("xxxx")
                .positiveText("ok")
                .inputType(InputType.TYPE_NULL)
                .show();
    }

    @Override
    protected void onDestroy() {

        mMaterialDialog.onDetachedFromWindow();
        mMaterialDialog.dismiss();

        getWindow().setCallback(null);
        try{
            String[] arr = new String[]{"mContext", "mOnWindowDismissedCallback", "mLayoutInflater"};
            for (int i = 0; i < arr.length; i++) {
                String param = arr[i];
                try {
                    Field f = getClassField(getWindow().getClass(), param);

                    if(f == null){
                        continue;
                    }

                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }

                    f.set(getWindow(), null);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        super.onDestroy();
    }

    public static Field getClassField(Class aClass, String fieldName){

        Field field = null;

        while(aClass != null){

            try{
                field = aClass.getDeclaredField(fieldName);
            }catch (Exception e){
                e.printStackTrace();
            }

            if(field != null){
                break;
            }

            aClass = aClass.getSuperclass();
        }

        return field;
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f;
        Object obj_get;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        Log.d("ActivityUtil", "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext() + " dest_context=" + destContext);
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

}
