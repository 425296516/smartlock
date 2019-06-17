package com.anlida.smartlock.base;

import com.anlida.smartlock.APP;
import com.anlida.smartlock.network.exception.ApiErrorHelper;
import com.anlida.smartlock.network.exception.ApiException;

import java.io.IOException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

public abstract class FMErrorConsumer implements Consumer<Throwable> {

    @Override
    public void accept(Throwable t) throws Exception {
        if (t instanceof HttpException || t instanceof IOException) {
            onNetError();
        } else if (t instanceof ApiException) {
            onApiError((ApiException) t);
        } else {
            ApiErrorHelper.handleException(APP.getContext(), t);
        }
    }

    public final void onNetError() {
        ApiErrorHelper.handlerNetError(APP.getContext());
    }

    public void onApiError(ApiException e){
        ApiErrorHelper.handleApiException(APP.getContext(), e);
    }

}
