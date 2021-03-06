package com.tongban.im.activity.discover;

import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;

import com.tb.api.CommonApi;
import com.tb.api.TagType;
import com.tb.api.model.BaseEvent;
import com.tb.api.model.Tag;
import com.tb.api.utils.TransferCenter;
import com.tongban.corelib.utils.NetUtils;
import com.tongban.im.R;
import com.tongban.im.activity.base.SuggestionsBaseActivity;
import com.tongban.im.adapter.DiscoverTagListAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * 搜索首页
 *
 * @author zhangleilei
 * @createTime 2015/8/12
 */
public class SearchDiscoverActivity extends SuggestionsBaseActivity {

    @Bind(R.id.elv_tags)
    ExpandableListView mTagListView;

    private DiscoverTagListAdapter mAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_discover_search;
    }

    @Override
    protected void initData() {
        super.initData();
        isExpanded = false;
        onRequest();
        // 禁用折叠
        mTagListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }


    @Override
    protected int getMenuInflate() {
        return R.menu.menu_search_topic;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!NetUtils.isConnected(mContext)) {
            return false;
        }
        if (!TextUtils.isEmpty(query)) {
            suggestionsListView.setVisibility(View.GONE);
            TransferCenter.getInstance().startThemeSearchResult(true, query);
        }
        return false;
    }

    /**
     * 获取搜索标签成功的Event
     *
     * @param event
     */
    public void onEventMainThread(BaseEvent.FetchTags event) {
        Map<String, List<Tag>> datas = new HashMap<>();
        String[] type = {"童书", "玩具", "早教"};
        if ("5".equals(event.type) && event.tags != null && event.tags.size() > 0) {
            for (String key : type) {
                datas.put(key, new LinkedList<Tag>());
            }
            for (Tag tag : event.tags) {
                if ("1".equals(tag.getTag_subtype())) {
                    datas.get(type[0]).add(tag);
                } else if ("2".equals(tag.getTag_subtype())) {
                    datas.get(type[1]).add(tag);
                } else if ("3".equals(tag.getTag_subtype())) {
                    datas.get(type[2]).add(tag);
                }
            }
            mAdapter = new DiscoverTagListAdapter(mContext, datas, type);
            mTagListView.setAdapter(mAdapter);
            mTagListView.setVisibility(View.VISIBLE);
            // 默认展开
            for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                mTagListView.expandGroup(i);
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onRequest() {
        // 获取商品相关的标签
        CommonApi.getInstance().fetchTags(0, 12, TagType.PRODUCT_TAG, this);
    }
}