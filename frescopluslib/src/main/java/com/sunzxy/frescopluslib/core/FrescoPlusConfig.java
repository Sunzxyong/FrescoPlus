package com.sunzxy.frescopluslib.core;

import android.content.Context;
import android.graphics.Bitmap;

import com.sunzxy.frescopluslib.exception.FPIllegalArgumentException;
import com.sunzxy.frescopluslib.util.DiskCacheUtil;

import java.io.File;

/**
 * Created by zhengxiaoyong on 16/1/6.
 */
public class FrescoPlusConfig {
    private boolean isDebug;

    private String logTag;

    private Context mContext;

    private Bitmap.Config mBitmapConfig;

    private int mMaxDiskCacheSize;

    private File mDiskCacheDir;

    private FrescoPlusConfig(Builder builder) {
        isDebug = builder.isDebug;
        logTag = builder.logTag == null ? DefaultConfigCentre.DEFAULT_TAG : builder.logTag;
        mContext = builder.mContext;
        mBitmapConfig = builder.mBitmapConfig == null ? DefaultConfigCentre.DEFAULT_BITMAP_CONFIG : builder.mBitmapConfig;
        mMaxDiskCacheSize = builder.mMaxDiskCacheSize <= 0 ? DefaultConfigCentre.DEFAULT_MAX_DISK_CACHE_SIZE : builder.mMaxDiskCacheSize;
        mDiskCacheDir = builder.mDiskCacheDir == null ? getDefaultDiskCacheDir() : builder.mDiskCacheDir;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public String getLogTag() {
        return logTag;
    }

    public Bitmap.Config getBitmapConfig() {
        return mBitmapConfig;
    }

    public int getMaxDiskCacheSize() {
        return mMaxDiskCacheSize;
    }

    public File getDiskCacheDir() {
        return mDiskCacheDir;
    }

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    public static final class Builder {
        private boolean isDebug = DefaultConfigCentre.DEFAULT_IS_DEBUG;
        private String logTag = DefaultConfigCentre.DEFAULT_TAG;
        private Context mContext;
        private Bitmap.Config mBitmapConfig;
        private int mMaxDiskCacheSize;
        private File mDiskCacheDir;

        private Builder(Context context) {
            mContext = context;
        }

        public Builder setDebug(boolean debug) {
            isDebug = debug;
            return this;
        }

        public Builder setTag(String tag) {
            logTag = tag;
            return this;
        }
        /**
         * @param val The Bitmap Config,such as:RGB_565,ARGB_8888,ARGB_4444.
         * @return
         */
        public Builder setBitmapConfig(Bitmap.Config val) {
            mBitmapConfig = val;
            return this;
        }

        /**
         * @param val The disk cache max size,unit is MB
         * @return
         */
        public Builder setMaxDiskCacheSize(int val) {
            mMaxDiskCacheSize = val;
            return this;
        }

        /**
         * @param val The disk cache dir,example:context.getApplicationContext().getCacheDir().
         * @return
         */
        public Builder setDiskCacheDir(File val) {
            mDiskCacheDir = val;
            return this;
        }

        public FrescoPlusConfig build() {
            return new FrescoPlusConfig(this);
        }
    }

    public File getDefaultDiskCacheDir() {
        if(mContext == null)
            throw new FPIllegalArgumentException("Context can not be null");
        return DiskCacheUtil.getDiskLruCacheDir(mContext);
    }
}
