package com.sunzxy.frescopluslib.logger;

import android.text.TextUtils;
import android.util.Log;

import com.sunzxy.frescopluslib.FrescoPlusInitializer;


/**
 * Created by zhengxiaoyong on 16/1/12.
 */
public class FrescoLogTracker {

    public static void d(String message) {
        Log.d(FrescoPlusInitializer.getInstance().getLogTag(), packMessage(message));
    }

    public static void d(String tag, String message) {
        Log.d(wrapTagIfNull(tag), packMessage(message));
    }

    public static void v(String message) {
        Log.v(FrescoPlusInitializer.getInstance().getLogTag(), packMessage(message));
    }

    public static void v(String tag, String message) {
        Log.v(wrapTagIfNull(tag), packMessage(message));
    }

    public static void i(String message) {
        Log.i(FrescoPlusInitializer.getInstance().getLogTag(), packMessage(message));
    }

    public static void i(String tag, String message) {
        Log.i(wrapTagIfNull(tag), packMessage(message));
    }

    public static void e(String message) {
        Log.e(FrescoPlusInitializer.getInstance().getLogTag(), packMessage(message));
    }

    public static void e(String tag, String message) {
        Log.e(wrapTagIfNull(tag), packMessage(message));
    }

    public static void w(String message) {
        Log.w(FrescoPlusInitializer.getInstance().getLogTag(), packMessage(message));
    }

    public static void w(String tag, String message) {
        Log.w(wrapTagIfNull(tag), packMessage(message));
    }

    public static String wrapTagIfNull(String tag) {
        return TextUtils.isEmpty(tag) ? FrescoPlusInitializer.getInstance().getLogTag() : tag;
    }
    private static StackTraceElement generateSTE(Thread thread,int index){
        return thread.getStackTrace()[index];
    }
    private static String packMessage(String message) {
        StringBuilder builder = new StringBuilder();
        Thread thread = Thread.currentThread();
        StackTraceElement element = generateSTE(thread,6);
        if (element == null)
            return ""+message;
        String threadName = thread.getName();
        builder.append("ThreadName:")
                .append(threadName)
                .append("->")
                .append(element.getClassName())
                .append("->")
                .append(element.getMethodName())
                .append("[line:")
                .append(element.getLineNumber())
                .append("],message:")
                .append(message);
        return builder.toString();
    }
}

