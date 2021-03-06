package com.tongban.im.adapter;

import android.content.Context;

import com.tb.api.model.user.User;
import com.tongban.corelib.base.adapter.BaseAdapterHelper;
import com.tongban.corelib.base.adapter.QuickAdapter;
import com.tongban.im.R;
import com.tongban.im.common.Consts;

import java.util.List;

/**
 * 成员列表-grid形式
 * Created by zhangleilei on 15/7/20.
 */
public class MemberGridAdapter extends QuickAdapter<User> {


    public MemberGridAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, User item) {
        helper.setText(R.id.tv_member_name, item.getNick_name());
        if (item.getPortraitUrl() != null) {
            helper.setImageBitmap(R.id.iv_member_icon, item.getPortraitUrl().getMin(),
                    Consts.getUserDefaultPortrait());
        } else {
            helper.setImageResource(R.id.iv_member_icon,Consts.getUserDefaultPortrait());
        }
    }
}
