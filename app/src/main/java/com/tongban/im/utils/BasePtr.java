package com.tongban.im.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by yanqijs on 2016/2/29.
 */
public class BasePtr extends PtrFrameLayout{
    public BasePtr(Context context) {
        super(context);
    }

    public BasePtr(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePtr(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


}
