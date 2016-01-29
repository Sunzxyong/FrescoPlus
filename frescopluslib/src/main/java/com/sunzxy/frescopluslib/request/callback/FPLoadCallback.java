package com.sunzxy.frescopluslib.request.callback;

import android.net.Uri;

/**
 * Created by zhengxiaoyong on 16/1/10.
 */
public interface FPLoadCallback<T> {

    void onSuccess(Uri uri, T result);

    void onFailure(Uri uri, Throwable throwable);

    void onCancel(Uri uri);
}
