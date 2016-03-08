package com.sunzxy.frescopluslib.request.callback;

/**
 * Created by zhengxiaoyong on 16/1/10.
 */
public interface FPFetchCallback<T> {

    void onSuccess(T result);

    void onFailure(Throwable throwable);
}
