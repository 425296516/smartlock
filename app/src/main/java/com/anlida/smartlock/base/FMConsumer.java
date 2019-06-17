package com.anlida.smartlock.base;

import io.reactivex.functions.Consumer;

public abstract class FMConsumer<T> implements Consumer<T> {
    @Override
    public void accept(T t) throws Exception {
        onNext(t);
    }

    public abstract void onNext(T t);
}
