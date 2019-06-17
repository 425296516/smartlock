package com.anlida.smartlock.base;

import android.content.Context;

import com.blankj.utilcode.util.NetworkUtils;
import com.anlida.smartlock.APP;
import com.anlida.smartlock.R;
import com.anlida.smartlock.network.exception.ApiErrorHelper;
import com.anlida.smartlock.network.exception.ApiException;


import org.reactivestreams.Subscription;

import java.io.IOException;

import io.reactivex.FlowableSubscriber;
import retrofit2.HttpException;

public abstract class FMSubscriber<T> implements FlowableSubscriber<T> {
    private Context context;

    public FMSubscriber() {
        context = APP.getContext();
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Integer.MAX_VALUE);
        if (!NetworkUtils.isConnected()) {
            this.onError(new ApiException(ApiErrorHelper.NET_ERROR_CODE, context.getResources().getString(R.string.no_internet)));
        }
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof HttpException || t instanceof IOException) {
            onNetError();
        } else if (t instanceof ApiException) {
            onApiError((ApiException) t);
        } else if (t instanceof NullPointerException) {
            onNullError();
        } else {
            ApiErrorHelper.handleException(context, t);
        }
    }

    @Override
    public void onComplete() {
    }

    public void onNetError() {
        ApiErrorHelper.handlerNetError(context);
    }

    protected void onApiError(ApiException e) {
        ApiErrorHelper.handleApiException(context, e);
    }

    protected void onNullError() {

    }
}
