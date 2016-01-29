package com.sunzxy.frescoplus;

import android.app.Application;
import android.graphics.Bitmap;

import com.sunzxy.frescopluslib.FrescoPlusInitializer;
import com.sunzxy.frescopluslib.core.FrescoPlusConfig;

/**
 * Created by zhengxiaoyong on 16/1/29.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FrescoPlusConfig config = FrescoPlusConfig.newBuilder(this)
                .setDebug(true)
                .setTag("Sunzxyong")
                .setBitmapConfig(Bitmap.Config.RGB_565)
                .setDiskCacheDir(this.getCacheDir())
                .setMaxDiskCacheSize(80)
                .build();
        FrescoPlusInitializer.getInstance().init(this,config);
    }
}
