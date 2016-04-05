package com.tongban.im.activity.mission;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tb.api.TopicApi;
import com.tb.api.model.BaseEvent;
import com.tb.api.utils.ApiConstants;
import com.tb.api.utils.TransferCenter;
import com.tongban.corelib.widget.view.CircleImageView;
import com.tongban.im.R;
import com.tongban.im.activity.base.AppBaseActivity;
import com.tongban.im.activity.group.ChatActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class MissionDetailsActivity extends AppBaseActivity {

    @Bind(R.id.mission_name)
    TextView nameText;
    @Bind(R.id.mission_ratingbar)
    RatingBar mRatingBar;
    @Bind(R.id.rating_text)
    TextView ratingTextView;
    @Bind(R.id.time_textview)
    TextView timeTextView;
    @Bind(R.id.content_textview)
    TextView contentTextView;
    @Bind(R.id.frame_btn)
    FrameLayout chatBtn;
    @Bind(R.id.iv_user_icon)
    CircleImageView mCircleImageView;

    private int mCursor = 0;
    private int mPage = 10;
    private String mTopicId;
    //是否已得到数据
    private Boolean isGet = false;
    private BaseEvent.TopicInfoEvent mTopicInfoEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_mission_details;
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            Uri uri = getIntent().getData();
            mTopicId = uri.getQueryParameter(ApiConstants.KEY_TOPIC_ID);
            onRequest();
        }

    }

    /**
     * 点击按钮事件监听
     */
    @OnClick(R.id.frame_btn)
    public void onClick() {
        if (isGet) {
            TransferCenter.getInstance().startChat(mTopicInfoEvent.topic.getUser_info().getUser_id()
                    , mTopicInfoEvent.topic.getTopic_title());
        }
    }

    @Override
    public void onRequest() {
        mCursor = 0;
        TopicApi.getInstance().getTopicInfo(mTopicId, this);
    }

    /**
     * 任务详情事件回调
     *
     * @param obj
     */
    public void onEventMainThread(BaseEvent.TopicInfoEvent obj) {
        if (obj.topic != null) {
            if (obj.topic.getUser_info().getPortraitUrl() != null) {
                setUserPortrait(obj.topic.getUser_info().getPortraitUrl().getMin()
                        , mCircleImageView);
            }
            nameText.setText(obj.topic.getUser_info().getNick_name());
            //// TODO: 2016/3/3 时间没有参数可用
            timeTextView.setText("2016-1-2 12:30");
            contentTextView.setText(obj.topic.getTopicContent().getTopic_content_text());
            isGet = true;
            mTopicInfoEvent = obj;
        }

    }
}
