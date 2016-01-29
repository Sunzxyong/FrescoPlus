package com.sunzxy.frescopluslib.core;

import android.graphics.Bitmap;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.request.ImageRequest;

/**
 * Created by zhengxiaoyong on 16/1/9.
 */
public class DefaultConfigCentre {

    private DefaultConfigCentre() {
    }
    public static final boolean DEFAULT_IS_DEBUG = false;

    public static final String DEFAULT_TAG = "fp_image";

    public static final int KB = 1024;

    public static final int MB = KB << 10;

    public static final Bitmap.Config DEFAULT_BITMAP_CONFIG = Bitmap.Config.ARGB_8888;

    public static final int DEFAULT_MAX_DISK_CACHE_SIZE = 60 * MB;

    public static final int DEFAULT_LOW_SPACE_DISK_CACHE_SIZE = 20 * MB;

    public static final int DEFAULT_VERY_LOW_SPACE_DISK_CACHE_SIZE = 8 * MB;

    public static final String DEFAULT_DISK_CACHE_DIR_NAME = "FPCache";

    public static final int DEFAULT_FADE_DURATION = 300;

    public static final ScalingUtils.ScaleType DEFAULT_SCALE_TYPE = ScalingUtils.ScaleType.CENTER_CROP;

    public static final float DEFAULT_RADIUS = 0;

    public static final int DEFAULT_PRIORITY = FrescoPriority.HIGH;

    public static final boolean DEFAULT_AUTO_ROTATE = false;

    public static final ImageRequest.RequestLevel DEFAULT_REQUEST_LEVEL = ImageRequest.RequestLevel.FULL_FETCH;


}
