package com.tongban.im.fragment.mission;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tb.api.UserCenterApi;
import com.tb.api.model.BaseEvent;
import com.tb.api.utils.TransferCenter;
import com.tongban.im.R;
import com.tongban.im.adapter.MyMissionAdapter;
import com.tongban.im.fragment.base.AppBaseFragment;

import butterknife.Bind;

/**
 * Created by yanqijs on 2016/3/4.
 */
public class BaseMyMission extends AppBaseFragment {

    @Bind(R.id.listView)
    ListView mListView;
    private MyMissionAdapter myMissionAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_base_my_mission;
    }

    @Override
    protected void initData() {
        UserCenterApi.getInstance().fetchLaunchTopicList(0, 10, this);
        myMissionAdapter = new MyMissionAdapter(getActivity(), R.layout.item_my_misson, null);
        mListView.setAdapter(myMissionAdapter);
        initListener();
    }

    /**
     * listview事件监听
     * 跳转至详情
     */
    private void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (myMissionAdapter.getItem(position) != null) {
                    TransferCenter.getInstance().startMissionDetails(myMissionAdapter.getItem(position).getTopic_id(),
                            myMissionAdapter.getItem(position).getTopic_type());
                }
            }
        });
    }


    /**
     * 我的话题事件回调
     *
     * @param obj
     */
    public void onEventMainThread(BaseEvent.TopicListEvent obj) {
        Log.i("MyMission", "start");
        myMissionAdapter.clear();
        myMissionAdapter.addAll(obj.topicList);
    }


}
