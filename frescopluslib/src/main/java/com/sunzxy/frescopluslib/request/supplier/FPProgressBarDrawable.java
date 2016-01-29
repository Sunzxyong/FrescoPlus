package com.sunzxy.frescopluslib.request.supplier;

import android.graphics.Color;

import com.facebook.drawee.drawable.ProgressBarDrawable;

/**
 * Created by zhengxiaoyong on 16/1/11.
 */
public class FPProgressBarDrawable extends ProgressBarDrawable {
    private int mProgressBarColor = Color.parseColor("#FDD11A");
    private int mProgressBarPadding = 0;
    private int mProgressBarWidth = 15;

    public FPProgressBarDrawable() {
        this(-1);
    }

    public FPProgressBarDrawable(int progressBarColor) {
        setColor(progressBarColor == -1 ? mProgressBarColor : progressBarColor);
        setPadding(mProgressBarPadding);
        setBarWidth(mProgressBarWidth);
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
    }

    @Override
    public void setPadding(int padding) {
        super.setPadding(padding);
    }

    @Override
    public void setBarWidth(int barWidth) {
        super.setBarWidth(barWidth);
    }

}
