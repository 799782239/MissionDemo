package com.example.yanqijs.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yanqijs.missiondemo.R;

/**
 * dialog基础类
 * Created by yanqijs on 2016/3/7.
 *
 * @param <T>
 */
public abstract class BaseDialogFragment<T extends Dialog> extends DialogFragment {


    T mDialog;

    /**
     * 创建自定义dialog
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_mymisson, null);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initData();
        if (mDialog instanceof AlertDialog) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        }
        return mDialog;
    }

    public abstract void initData();
}
