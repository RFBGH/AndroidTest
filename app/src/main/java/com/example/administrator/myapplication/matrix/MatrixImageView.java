package com.example.administrator.myapplication.matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class MatrixImageView extends View {

    private Bitmap bitmap;
    private Matrix matrix;

    public MatrixImageView(Context context) {
        super(context);

        init();
    }

    public MatrixImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public MatrixImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init(){

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        matrix = new Matrix();

        startAnimation();

////        matrix.setScale(1, 2);
//        int centerWidth = bitmap.getWidth()/2;
//        int centerHeight = bitmap.getHeight()/2;
//        matrix.postRotate(50, centerWidth, centerHeight);
//        matrix.postTranslate(100, 200);

//        matrix.postScale(1, 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 画出原图像
//        canvas.drawBitmap(bitmap, 0, 0, null);
        // 画出变换后的图像
        canvas.drawBitmap(bitmap, matrix, null);

        super.onDraw(canvas);
    }

    private void delay(int delay){

        try{
            Thread.sleep(delay);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void startAnimation(){

        Observable
                .create(new Observable.OnSubscribe<Matrix>() {
                    @Override
                    public void call(Subscriber<? super Matrix> subscriber) {

                        for(int i = 0; i < 20; i++){

                            for(int degree = 0; degree < 720; degree += 5){

                                Matrix matrix = new Matrix();

                                matrix.setRotate(degree, bitmap.getWidth()/2, bitmap.getHeight()/2);

                                float scale = (degree/10) * 0.028f;
                                matrix.postScale(scale, scale);

                                float tranX = getLeft()+getWidth()/2 + 100*degree/360*(float)Math.cos(Math.PI*degree/180);
                                float tranY = getTop()+getHeight()/2 + 100*degree/360*(float)Math.sin(Math.PI*degree/180);
                                matrix.postTranslate(tranX, tranY);

                                subscriber.onNext(matrix);
                                delay(50);
                            }
                        }

                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Matrix>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Matrix animationInfo) {
                        matrix = animationInfo;
                        invalidate();
                    }
                });

    }
}
