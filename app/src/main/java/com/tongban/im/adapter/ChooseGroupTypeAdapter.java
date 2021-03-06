package com.tongban.im.adapter;

import android.content.Context;

import com.tb.api.model.group.GroupType;
import com.tongban.corelib.base.adapter.BaseAdapterHelper;
import com.tongban.corelib.base.adapter.QuickAdapter;
import com.tongban.im.R;

import java.util.List;

/**
 * 选择圈子类型adapter
 * Created by zhangleilei on 15/7/22.
 */
public class ChooseGroupTypeAdapter extends QuickAdapter<GroupType> {

    public ChooseGroupTypeAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, GroupType item) {

        helper.setBackgroundRes(R.id.fl_group_portrait,item.getGroupBGColor());
        helper.setImageBitmap(R.id.iv_group_portrait, item.getSrc());
        helper.setText(R.id.tv_group_type, item.getGroupTypeName());
        helper.setText(R.id.tv_group_desc, item.getGroupDesc());

    }
}
