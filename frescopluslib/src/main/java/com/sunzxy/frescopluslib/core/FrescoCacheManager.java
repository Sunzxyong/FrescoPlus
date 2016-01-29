package com.sunzxy.frescopluslib.core;

import android.net.Uri;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.datasource.BaseBooleanSubscriber;
import com.facebook.datasource.DataSource;
import com.sunzxy.frescopluslib.logger.FrescoLogger;
import com.sunzxy.frescopluslib.request.callback.FPCacheCallback;
import com.sunzxy.frescopluslib.util.ParamCheckUtil;

import java.text.DecimalFormat;

/**
 * Created by zhengxiaoyong on 16/1/9.
 */
public class FrescoCacheManager {
    private static volatile FrescoCacheManager mInstance;

    private FrescoCacheManager() {
    }

    public static FrescoCacheManager getInstance() {
        if (mInstance == null) {
            synchronized (FrescoCacheManager.class) {
                if (mInstance == null) {
                    mInstance = new FrescoCacheManager();
                }
            }
        }
        return mInstance;
    }

    private long getHasUseDiskCacheSize() {
        return FrescoPlusCore.getImagePipelineFactory().getMainDiskStorageCache().getSize();
    }

    /**
     * @return has use disk cache space size,unit 'KB'.
     * Keep two decimal places,example:2.2222->2.22.
     */
    public String getHasUseDiskCacheSizeWithKB() {
        long size = getHasUseDiskCacheSize();
        if (size <= 0)
            return "0";
        return new DecimalFormat("0.00").format(size / 1024.0);
    }

    /**
     * @return has use disk cache space size,unit 'MB'.
     * Keep two decimal places,example:2.2222->2.22.
     */
    public String getHasUseDiskCacheSizeWithMB() {
        long size = getHasUseDiskCacheSize();
        if (size <= 0)
            return "0";
        return new DecimalFormat("0.00").format((size / 1024.0) / 1024.0);
    }

    /**
     * @param uri uri
     * @return true if the image was found in the disk cache,false otherwise.
     */
    public void isInDiskCache(final Uri uri, final FPCacheCallback<Boolean> callback) {
        FrescoLogger.getLogger().log(uri);
        ParamCheckUtil.checkNotNull(uri, "uri is null");
        DataSource<Boolean> dataSource = FrescoPlusCore.getImagePipeline().isInDiskCache(uri);
        if (dataSource == null)
            return;
        dataSource.subscribe(new BaseBooleanSubscriber() {
            @Override
            protected void onNewResultImpl(boolean isFoundInDisk) {
                if (callback != null)
                    callback.onResult(isFoundInDisk);
            }

            @Override
            protected void onFailureImpl(DataSource<Boolean> dataSource) {
                if (callback != null)
                    callback.onResult(false);
            }
        }, UiThreadImmediateExecutorService.getInstance());
    }

    /**
     * @param uri uri
     * @return true if the image was found in the bitmap memory cache,false otherwise.
     */
    public boolean isInBitmapMemoryCache(Uri uri) {
        FrescoLogger.getLogger().log(uri);
        ParamCheckUtil.checkNotNull(uri, "uri is null");
        return FrescoPlusCore.getImagePipeline().isInBitmapMemoryCache(uri);
    }

    /**
     * @param uri The uri of the image will to remove,include memory and disk cache.
     */
    public void removeFromMemoryAndDiskCache(Uri uri) {
        FrescoLogger.getLogger().log(uri);
        ParamCheckUtil.checkNotNull(uri, "uri is null");
        FrescoPlusCore.getImagePipeline().evictFromCache(uri);
    }

    /**
     * @param uri The uri of the image will to remove,only include memory cache.
     */
    public void removeFromMemoryCache(Uri uri) {
        FrescoLogger.getLogger().log(uri);
        ParamCheckUtil.checkNotNull(uri, "uri is null");
        FrescoPlusCore.getImagePipeline().evictFromMemoryCache(uri);
    }

    /**
     * @param uri The uri of the image will to remove,only include disk cache.
     */
    public void removeFromDiskCache(Uri uri) {
        FrescoLogger.getLogger().log(uri);
        ParamCheckUtil.checkNotNull(uri, "uri is null");
        FrescoPlusCore.getImagePipeline().evictFromDiskCache(uri);
    }

    /**
     * clear disk caches.
     */
    public void clearDiskCaches() {
        FrescoPlusCore.getImagePipeline().clearDiskCaches();
    }

    /**
     * clear the memory cache（include Bitmap cache and Encode image cache）.
     */
    public void clearMemoryCaches() {
        FrescoPlusCore.getImagePipeline().clearMemoryCaches();
    }
}
