package com.tongban.im.impl;

import android.content.Context;
import android.view.View;

import com.tb.api.GroupApi;
import com.tb.api.model.group.Group;
import com.tb.api.utils.TransferCenter;
import com.tongban.im.R;

import io.rong.imkit.RongIM;

/**
 * 圈子监听实现类
 * Created by zhangleilei on 8/26/15.
 */
public class GroupListenerImpl implements View.OnClickListener {

    private Context mContext;

    public GroupListenerImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //加入圈子
            case R.id.btn_join:
                if (v.getTag() != null) {
                    Group group = (Group) v.getTag();
                    GroupApi.getInstance().joinGroup(group.getGroup_id(), group.getGroup_name(),
                            group.getUser_info().getUser_id(), group.isVerify_user(), null);
                }
                break;
            //打开圈子聊天页/详情页
            case R.id.rl_group_item:
                if (v.getTag() != null) {
                    Group group = (Group) v.getTag();
                    if (!group.isAllow_add()) {
                        RongIM.getInstance().startGroupChat(mContext, group.getGroup_id(),
                                group.getGroup_name());
                    } else {
                        TransferCenter.getInstance().startGroupInfo(group.getGroup_id(),
                                group.isAllow_add());
                    }
                }
                break;
        }
    }
}
