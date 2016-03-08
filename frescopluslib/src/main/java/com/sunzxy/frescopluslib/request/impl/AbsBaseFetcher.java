package com.sunzxy.frescopluslib.request.impl;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.facebook.imagepipeline.image.ImageInfo;
import com.sunzxy.frescopluslib.request.Fetcher;
import com.sunzxy.frescopluslib.request.callback.FPFetchCallback;
import com.sunzxy.frescopluslib.request.callback.FPLoadCallback;
import com.sunzxy.frescopluslib.util.ParamCheckUtil;
import com.sunzxy.frescopluslib.util.UriUtil;

/**
 * Created by zhengxiaoyong on 16/1/10.
 */
public abstract class AbsBaseFetcher<T> implements Fetcher<T> {
    @Override
    public void fetch(T view, Uri uri) {
        ParamCheckUtil.checkNotNull(view);
        ParamCheckUtil.checkUriIsLegal(uri);
        beforeExecute(uri);
    }

    @Override
    public void fetch(T view, String uriPath) {
        fetch(view, UriUtil.parseUri(uriPath));
    }

    @Override
    public void fetch(T view, Uri uri, FPFetchCallback<ImageInfo> callback) {
        ParamCheckUtil.checkNotNull(view);
        ParamCheckUtil.checkUriIsLegal(uri);
        beforeExecute(uri);
    }

    @Override
    public void fetch(T view, String uriPath, FPFetchCallback<ImageInfo> callback) {
        fetch(view,UriUtil.parseUri(uriPath),callback);
    }

    @Override
    public void fetch(T view, @DrawableRes int resId) {
        fetch(view,UriUtil.parseUriFromResId(resId));
    }

    @Override
    public void fetch(T view, @DrawableRes int resId, FPFetchCallback<ImageInfo> callback) {
        fetch(view,UriUtil.parseUriFromResId(resId),callback);
    }

    @Override
    public void fetch(Uri uri, FPLoadCallback<Bitmap> callback) {
        ParamCheckUtil.checkUriIsLegal(uri);
        beforeExecute(uri);
    }

    @Override
    public void fetch(String uriPath, FPLoadCallback<Bitmap> callback) {
        fetch(UriUtil.parseUri(uriPath), callback);
    }

    protected abstract Object beforeExecute(Uri uri);

}
