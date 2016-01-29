package com.sunzxy.frescopluslib.request.supplier;

import android.graphics.drawable.Animatable;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.sunzxy.frescopluslib.logger.FrescoLogger;
import com.sunzxy.frescopluslib.request.callback.FPFetchCallback;

/**
 * Created by zhengxiaoyong on 16/1/10.
 */
public class FetchImageControllerListenerSupplier extends BaseControllerListener<ImageInfo> {
    private FPFetchCallback<ImageInfo> FPFetchCallback;

    private FetchImageControllerListenerSupplier(FPFetchCallback<ImageInfo> callback) {
        this.FPFetchCallback = callback;
    }

    public static FetchImageControllerListenerSupplier newInstance(FPFetchCallback<ImageInfo> callback) {
        return new FetchImageControllerListenerSupplier(callback);
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
        super.onFailure(id, throwable);
        if(FPFetchCallback ==null)
            return ;
        FrescoLogger.getLogger().log(throwable.getLocalizedMessage());
        FPFetchCallback.onFailure(throwable);
    }

    @Override
    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
        super.onFinalImageSet(id, imageInfo, animatable);
        if(FPFetchCallback ==null || imageInfo == null)
            return;
        FrescoLogger.getLogger().log("Bitmap:[height="+imageInfo.getHeight()+",width="+imageInfo.getWidth()+"]");
        FPFetchCallback.onSuccess(imageInfo);
    }
}
