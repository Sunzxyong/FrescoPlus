package com.sunzxy.frescopluslib.request.supplier;

import android.graphics.Bitmap;

import com.facebook.imagepipeline.request.BasePostprocessor;

/**
 * Created by zhengxiaoyong on 16/1/10.
 */
public abstract class BaseProcessorSupplier extends BasePostprocessor {
    @Override
    public String getName() {
        return "BaseProcessorSupplier";
    }

    @Override
    public void process(Bitmap bitmap) {
        super.process(bitmap);
        processBitmap(bitmap);
    }

    public abstract void processBitmap(Bitmap bitmap);

}
