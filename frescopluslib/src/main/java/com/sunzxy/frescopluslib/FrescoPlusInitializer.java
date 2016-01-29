package com.sunzxy.frescopluslib;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.sunzxy.frescopluslib.core.DefaultConfigCentre;
import com.sunzxy.frescopluslib.core.FrescoCacheStatsTracker;
import com.sunzxy.frescopluslib.core.FrescoPlusConfig;
import com.sunzxy.frescopluslib.core.FrescoPlusCore;
import com.sunzxy.frescopluslib.exception.FPNullPointerException;
import com.sunzxy.frescopluslib.widget.FrescoPlusView;


/**
 * Created by zhengxiaoyong on 16/1/5.
 */
public class FrescoPlusInitializer {
    private static volatile FrescoPlusInitializer mInstance = null;
    private Context mContext;
    private boolean isDebug;
    private String logTag;

    private FrescoPlusInitializer() {
    }

    public static FrescoPlusInitializer getInstance() {
        if (mInstance == null) {
            synchronized (FrescoPlusInitializer.class) {
                if (mInstance == null) {
                    mInstance = new FrescoPlusInitializer();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        init(context, null);
    }

    public void init(Context context, FrescoPlusConfig frescoPlusConfig) {
        if (context == null) {
            throw new FPNullPointerException("WDImage initialize error,cause:context is null");
        }
        mContext = !(context instanceof Application) ? context.getApplicationContext() : context;
        initialize(frescoPlusConfig);
    }

    private void initialize(FrescoPlusConfig config) {
        final FrescoPlusConfig frescoPlusConfig;
        if (config == null)
            config = FrescoPlusConfig.newBuilder(mContext).build();
        frescoPlusConfig = config;

        isDebug = frescoPlusConfig.isDebug();
        logTag = frescoPlusConfig.getLogTag();

        printWDImageConfigLog(frescoPlusConfig);

        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder()
                .setBaseDirectoryName(DefaultConfigCentre.DEFAULT_DISK_CACHE_DIR_NAME)
                .setBaseDirectoryPath(frescoPlusConfig.getDiskCacheDir())
                .setMaxCacheSize(frescoPlusConfig.getMaxDiskCacheSize() * DefaultConfigCentre.MB)
                .setMaxCacheSizeOnLowDiskSpace(DefaultConfigCentre.DEFAULT_LOW_SPACE_DISK_CACHE_SIZE)
                .setMaxCacheSizeOnVeryLowDiskSpace(DefaultConfigCentre.DEFAULT_VERY_LOW_SPACE_DISK_CACHE_SIZE)
                .build();

        ImagePipelineConfig pipelineConfig = ImagePipelineConfig.newBuilder(mContext)
                .setBitmapsConfig(frescoPlusConfig.getBitmapConfig())
                .setImageCacheStatsTracker(FrescoCacheStatsTracker.getInstance())
                .setDownsampleEnabled(true)
                .setResizeAndRotateEnabledForNetwork(true)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();

        FrescoPlusCore.init(mContext, pipelineConfig);
    }

    /**
     * Shuts FrescoPlusInitializer down.
     */
    public void shutDown() {
        FrescoPlusCore.shutDownDraweeControllerBuilderSupplier();
        FrescoPlusView.shutDown();
        ImagePipelineFactory.shutDown();
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public String getLogTag() {
        return logTag;
    }

    /**
     * print FrescoPlusConfig log
     * @param frescoPlusConfig
     */
    private void printWDImageConfigLog(FrescoPlusConfig frescoPlusConfig) {
        if (isDebug) {
            Log.d(FrescoPlusInitializer.getInstance().getLogTag(), "FrescoPlusInitializer init...Config:"
                    + "DiskCacheDir->" + frescoPlusConfig.getDiskCacheDir()
                    + ",MaxDiskCacheSize->" + frescoPlusConfig.getMaxDiskCacheSize()
                    + ",BitmapConfig->" + frescoPlusConfig.getBitmapConfig()
                    + ",IsDebug->" + frescoPlusConfig.isDebug()
                    + ",Tag->" + frescoPlusConfig.getLogTag());
        }
    }
}
