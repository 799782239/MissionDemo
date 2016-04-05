package com.tongban.im.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import io.rong.imkit.widget.InputView;

/**
 * Created by yanqijs on 2016/2/29.
 */
public class BaseViewpager extends ViewPager {
    public BaseViewpager(Context context) {
        super(context);
    }

    public BaseViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                requestDisallowInterceptTouchEvent(true);
                break;
            default:
                break;
        }
        return true;
    }
}
