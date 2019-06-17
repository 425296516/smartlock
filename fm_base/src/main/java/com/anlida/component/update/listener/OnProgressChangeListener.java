package com.anlida.component.update.listener;

import android.support.annotation.MainThread;

public interface OnProgressChangeListener {
    @MainThread
    void progress(long progress, long max);

    @MainThread
    void onComplete();
}
