package com.sunzxy.frescopluslib.request.callback;

/**
 * Created by zhengxiaoyong on 16/1/12.
 */
public interface FPCacheCallback<T> {
    /**
     * @param result The result of the image was found in disk cache.
     */
    void onResult(T result);
}
