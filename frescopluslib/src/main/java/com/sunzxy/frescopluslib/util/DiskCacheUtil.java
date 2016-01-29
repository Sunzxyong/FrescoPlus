package com.sunzxy.frescopluslib.util;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.sunzxy.frescopluslib.exception.FPNullPointerException;

import java.io.File;

/**
 * Created by zhengxiaoyong on 16/1/12.
 */
public class DiskCacheUtil {
    public static File getDiskLruCacheDir(Context context) {
        if (context == null)
            throw new FPNullPointerException("context can not be null");
        if (!(context instanceof Application))
            context = context.getApplicationContext();
        File cacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cacheDir = getSDFreeSize() > 100 ? context.getExternalCacheDir() : context.getCacheDir();
        } else {
            cacheDir = context.getCacheDir();
        }
        return cacheDir;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getSDFreeSize() {
        File path = Environment.getExternalStorageDirectory();
        if (path != null && path.exists() && path.isDirectory()) {
            StatFs sf = new StatFs(path.getPath());
            long blockSize = sf.getBlockSizeLong();
            long freeBlocks = sf.getAvailableBlocksLong();
            return (freeBlocks * blockSize) / 1024 / 1024;
        }
        return -1;
    }
}
