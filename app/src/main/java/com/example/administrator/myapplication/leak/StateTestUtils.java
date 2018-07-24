package com.example.administrator.myapplication.leak;

/**
 * Created by Administrator on 2018/4/13 0013.
 */

public class StateTestUtils {

    private static ITest sITest;

    public void test1(){

    }

    public void test() {

        test1();

        if(sITest == null) {
            sITest = new ITest() {

                @Override
                public void test() {

                }
            };
        }
    }

}
