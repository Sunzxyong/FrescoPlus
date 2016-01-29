package com.sunzxy.frescopluslib.widget;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sunzxy.frescopluslib.request.impl.FrescoPlusFetcher;
import com.sunzxy.frescopluslib.util.UriUtil;


/**
 * Created by zhengxiaoyong on 16/1/5.
 */
public class FrescoPlusView extends SimpleDraweeView {

    public FrescoPlusView(Context context) {
        super(context);
    }

    public FrescoPlusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrescoPlusView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public final void showImage(Uri uri) {
        FrescoPlusFetcher.newFetcher().build().fetch(this, uri);
    }

    public final void showImage(String uriPath) {
        showImage(UriUtil.parseUri(uriPath));
    }

    public final void showImage(@DrawableRes int resId) {
        FrescoPlusFetcher.newFetcher().build().fetch(this, resId);
    }

}