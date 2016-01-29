package com.sunzxy.frescopluslib.logger;


import com.sunzxy.frescopluslib.FrescoPlusInitializer;

/**
 * Created by zhengxiaoyong on 16/1/12.
 */
public class FrescoLogger {
    private static volatile FrescoLogger instance;

    private FrescoLogger() {
    }

    public static FrescoLogger getLogger() {
        if (instance == null) {
            synchronized (FrescoLogger.class) {
                if (instance == null) {
                    instance = new FrescoLogger();
                }
            }
        }
        return instance;
    }

    public void log(Object t) {
        boolean isDebug = FrescoPlusInitializer.getInstance().isDebug();
        if (isDebug) {
            FrescoLogTracker.d(String.valueOf(t));
        }
    }

    public void log(String tag, Object t) {
        boolean isDebug = FrescoPlusInitializer.getInstance().isDebug();
        if (isDebug) {
            FrescoLogTracker.d(FrescoLogTracker.wrapTagIfNull(tag), String.valueOf(t));
        }
    }
}
