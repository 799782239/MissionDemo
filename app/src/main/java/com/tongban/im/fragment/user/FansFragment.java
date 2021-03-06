package com.tongban.im.fragment.user;


import android.view.View;
import android.widget.AdapterView;

import com.tb.api.UserCenterApi;
import com.tb.api.model.BaseEvent;
import com.tb.api.utils.TransferCenter;
import com.tongban.corelib.utils.Constants;
import com.tongban.corelib.widget.view.LoadMoreListView;
import com.tongban.corelib.widget.view.listener.OnLoadMoreListener;
import com.tongban.im.R;
import com.tongban.im.adapter.UserAdapter;
import com.tongban.im.common.Consts;
import com.tongban.im.fragment.base.AppBaseFragment;

import butterknife.Bind;
import butterknife.OnItemClick;

/**
 * 粉丝界面
 *
 * @author fushudi
 */
public class FansFragment extends AppBaseFragment implements
        OnLoadMoreListener,View.OnClickListener {

    @Bind(R.id.lv_fans_list)
    LoadMoreListView lvFansList;

    private UserAdapter mAdapter;
    private int mCursor = 0;
    private int mPageSize = 10;
    private String userID;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_fans;
    }

    @Override
    protected void initData() {
        mAdapter = new UserAdapter(mContext, R.layout.item_my_info_list, null);
        mAdapter.setOnClickListener(this);
        lvFansList.setAdapter(mAdapter);
        lvFansList.setPageSize(mPageSize);
        if (getArguments() != null) {
            userID = getArguments().getString(Constants.USER_ID);
            UserCenterApi.getInstance().fetchFansUserList(mCursor, mPageSize, userID, this);
            lvFansList.setOnLoadMoreListener(this);
        }
    }


    /**
     * 粉丝列表Event
     *
     * @param obj
     */
    public void onEventMainThread(BaseEvent.MyFansListEvent obj) {
        mCursor++;
        mAdapter.addAll(obj.myFansList);
        lvFansList.setResultSize(obj.myFansList.size());
    }

    /**
     * 关注Event
     *
     * @param obj
     */
    public void onEventMainThread(BaseEvent.FocusEvent obj) {

        UserCenterApi.getInstance().fetchFansUserList(mCursor, mPageSize, userID, this);
    }

    @OnItemClick(R.id.lv_fans_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TransferCenter.getInstance().startUserCenter(mAdapter.getItem(position).getUser_id());
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_follow:
                String focusId = v.getTag(Integer.MAX_VALUE).toString();
                UserCenterApi.getInstance().focusUser(true, focusId, this);
                break;
            case R.id.iv_user_icon:
                String userId = v.getTag(Integer.MAX_VALUE).toString();
                TransferCenter.getInstance().startUserCenter(userId);
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onLoadMore() {
        UserCenterApi.getInstance().fetchFansUserList(mCursor, mPageSize, userID, this);
    }
}
