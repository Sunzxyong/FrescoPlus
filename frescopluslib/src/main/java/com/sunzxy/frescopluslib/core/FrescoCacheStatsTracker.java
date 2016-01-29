package com.sunzxy.frescopluslib.core;

import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.sunzxy.frescopluslib.logger.FrescoLogger;

/**
 * Created by zhengxiaoyong on 16/1/5.
 */
public class FrescoCacheStatsTracker implements ImageCacheStatsTracker {
    private static volatile FrescoCacheStatsTracker instance;
    private FrescoCacheStatsTracker() {
    }

    public static FrescoCacheStatsTracker getInstance() {
        if (instance == null) {
            synchronized (FrescoCacheStatsTracker.class) {
                if (instance == null) {
                    instance = new FrescoCacheStatsTracker();
                }
            }
        }
        return instance;
    }

    @Override
    public void onBitmapCachePut() {
        FrescoLogger.getLogger().log("onBitmapCachePut");
    }

    @Override
    public void onBitmapCacheHit() {
        FrescoLogger.getLogger().log("onBitmapCacheHit");
    }

    @Override
    public void onBitmapCacheMiss() {
        FrescoLogger.getLogger().log("onBitmapCacheMiss");
    }

    @Override
    public void onMemoryCachePut() {
        FrescoLogger.getLogger().log("onMemoryCachePut");
    }

    @Override
    public void onMemoryCacheHit() {
        FrescoLogger.getLogger().log("onMemoryCacheHit");
    }

    @Override
    public void onMemoryCacheMiss() {
        FrescoLogger.getLogger().log("onMemoryCacheMiss");
    }

    @Override
    public void onStagingAreaHit() {
        FrescoLogger.getLogger().log("onStagingAreaHit");
    }

    @Override
    public void onStagingAreaMiss() {
        FrescoLogger.getLogger().log("onStagingAreaMiss");
    }

    @Override
    public void onDiskCacheHit() {
        FrescoLogger.getLogger().log("onDiskCacheHit");
    }

    @Override
    public void onDiskCacheMiss() {
        FrescoLogger.getLogger().log("onDiskCacheMiss");
    }

    @Override
    public void onDiskCacheGetFail() {
        FrescoLogger.getLogger().log("onDiskCacheGetFail");
    }

    @Override
    public void registerBitmapMemoryCache(CountingMemoryCache<?, ?> bitmapMemoryCache) {

    }

    @Override
    public void registerEncodedMemoryCache(CountingMemoryCache<?, ?> encodedMemoryCache) {

    }
}
