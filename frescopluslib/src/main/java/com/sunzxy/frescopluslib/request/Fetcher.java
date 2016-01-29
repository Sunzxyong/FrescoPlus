package com.sunzxy.frescopluslib.request;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.facebook.imagepipeline.image.ImageInfo;
import com.sunzxy.frescopluslib.request.callback.FPFetchCallback;
import com.sunzxy.frescopluslib.request.callback.FPLoadCallback;

/**
 * Created by zhengxiaoyong on 16/1/10.
 */
public interface Fetcher<T> {

    void fetch(T view, Uri uri);

    void fetch(T view, String uriPath);

    void fetch(T view, @DrawableRes int resId);

    void fetch(T view, @DrawableRes int resId, FPFetchCallback<ImageInfo> callback);

    void fetch(T view, Uri uri, FPFetchCallback<ImageInfo> callback);

    void fetch(T view, String uriPath, FPFetchCallback<ImageInfo> callback);

    void fetch(Uri uri, FPLoadCallback<Bitmap> callback);

    void fetch(String uriPath, FPLoadCallback<Bitmap> callback);
}
