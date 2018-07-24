package com.example.administrator.myapplication.bitmapcut;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/10 0010.
 */

public class CutBitmapActivity extends Activity{


    private TextView mTvTest;
    private LinearLayout mLlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cut_bitmap);

        mTvTest = (TextView)findViewById(R.id.tv_test);
        mLlContent = (LinearLayout)findViewById(R.id.ll_content);
    }

    @Override
    protected void onResume() {
        super.onResume();

        startCutBitmap();
    }

    public static Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(screenshot);
        canvas.translate(-v.getScrollX(), -v.getScrollY());//我们在用滑动View获得它的Bitmap时候，获得的是整个View的区域（包括隐藏的），如果想得到当前区域，需要重新定位到当前可显示的区域
        v.draw(canvas);// 将 view 画到画布上
        return screenshot;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void startCutBitmap(){

        Observable
                .create(new Observable.OnSubscribe<BitmapDrawable>() {
                    @Override
                    public void call(Subscriber<? super BitmapDrawable> subscriber) {


//                        mTvTest.getBackground()
                        Bitmap bitmap = drawableToBitmap(mTvTest.getBackground());

                        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                                .getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(output);

                        final int color = 0xff424242;
                        final Paint paint = new Paint();

                        int centerX = bitmap.getWidth()/2;
                        int centerY = bitmap.getHeight()/2;

                        final Rect rect = new Rect(centerX-10, centerY-10, centerX+10, centerY+10);

                        final RectF rectF = new RectF(rect);

                        paint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);
                        paint.setColor(color);

                        canvas.drawOval(rectF, paint);

                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(bitmap, rect, rect, paint);

                        subscriber.onNext(new BitmapDrawable(output));
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BitmapDrawable>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(BitmapDrawable bitmapDrawable) {

                        ImageView imageView = new ImageView(CutBitmapActivity.this);
                        imageView.setImageDrawable(bitmapDrawable);
                        mLlContent.addView(imageView);
                    }
                });

    }
}
