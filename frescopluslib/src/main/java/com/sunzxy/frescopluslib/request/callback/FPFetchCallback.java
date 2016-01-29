package com.sunzxy.frescopluslib.request.callback;

/**
 * Created by zhengxiaoyong on 16/1/10.
 */
public interface FPFetchCallback<T> {
    /**
     * Called after the image fetch success.
     *
     * @param result
     */
    void onSuccess(T result);

    /**
     * Called after the image fetch failed.
     *
     * @param throwable
     */
    void onFailure(Throwable throwable);
}
