package com.anlida.smartlock.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

public class RxLifecycleUtils {
    public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner owner) {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner));
    }

    public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner owner, Lifecycle.Event event){
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner,event));
    }
}
