package com.example.administrator.myapplication;

import rx.Observable;
import rx.functions.Func1;
import rx.internal.operators.OnSubscribeDetach;

/**
 * Created by Administrator on 2018/2/2 0002.
 */
class OnSubscribeOnSubscribe implements Func1<Observable.OnSubscribe, Observable.OnSubscribe> {
    @Override
    public Observable.OnSubscribe call(Observable.OnSubscribe onSubscribe) {
        return new OnSubscribeDetach(new DetachObservable(onSubscribe));
    }

    private static class DetachObservable extends Observable{
        protected DetachObservable(OnSubscribe f) {
            super(f);
        }
    }
}
