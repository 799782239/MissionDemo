package com.tongban.im.activity.user;


import android.support.v4.app.FragmentTransaction;

import com.tb.api.model.BaseEvent;
import com.tongban.corelib.utils.SPUtils;
import com.tongban.im.R;
import com.tongban.im.activity.base.AccountBaseActivity;
import com.tongban.im.common.Consts;
import com.tongban.im.fragment.user.InputChildInfoFragment;

/**
 * 设置宝宝信息界面
 *
 * @author fushudi
 */
public class ChildInfoActivity extends AccountBaseActivity {

    private InputChildInfoFragment mInputChildInfoFragment;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_child_info;
    }

    @Override
    protected void initData() {
        //填写宝宝信息界面
        mInputChildInfoFragment = new InputChildInfoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_replaced, mInputChildInfoFragment);
        transaction.commit();
    }

    /**
     * 宝宝信息Event
     *
     * @param obj
     */
    public void onEventMainThread(BaseEvent.ChildCreateEvent obj) {
        SPUtils.put(mContext,SPUtils.NO_CLEAR_FILE,Consts.CHILD_BIRTHDAY,obj.childBirthday);
        SPUtils.put(mContext,SPUtils.NO_CLEAR_FILE,Consts.CHILD_SEX,obj.childSex);
        SPUtils.put(mContext,SPUtils.NO_CLEAR_FILE, Consts.FIRST_SET_CHILD_INFO, false);
        connectIM();
    }

}
