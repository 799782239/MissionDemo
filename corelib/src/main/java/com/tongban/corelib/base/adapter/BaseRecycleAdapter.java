package com.tongban.corelib.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yanqijs on 2016/3/4.
 */
public abstract class BaseRecycleAdapter <T,S extends BaseAdapterHelper>extends RecyclerView.Adapter {


    protected List<T> mData;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    //-----------------------------------
}
