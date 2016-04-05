package com.tongban.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.tb.api.model.topic.Topic;
import com.tongban.corelib.base.adapter.BaseAdapterHelper;
import com.tongban.corelib.base.adapter.IMultiItemTypeSupport;
import com.tongban.corelib.base.adapter.QuickAdapter;
import com.tongban.im.R;
import com.tongban.im.common.Consts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yanqijs on 2016/3/1.
 */
public class MyMissionAdapter extends QuickAdapter<Topic> {

    private Boolean isIng;

    public MyMissionAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    public MyMissionAdapter(Context context, List data, IMultiItemTypeSupport multiItemTypeSupport) {
        super(context, data, multiItemTypeSupport);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, Topic item) {
        if (item.getUser_info() != null) {
            if (item.getUser_info().getPortraitUrl() != null) {
                helper.setImageBitmap(R.id.iv_user_portrait,
                        item.getUser_info().getPortraitUrl().getMin(),
                        Consts.getUserDefaultPortrait());
                helper.setTag(R.id.iv_user_portrait,
                        Integer.MAX_VALUE, item.getUser_info().getUser_id());
            } else {
                helper.setImageBitmap(R.id.iv_user_portrait, Consts.getUserDefaultPortrait());
            }
            helper.setText(R.id.mymission_publisher, item.getUser_info().getNick_name() + "|"
                    + initTime(item.getC_time()));
        }
        helper.setText(R.id.mymission_item_content, item.getTopicContent().getTopic_content_text());
        if (item.getTopicContent().getTopic_img_url() != null) {
            helper.setVisible(R.id.mymission_image, View.VISIBLE);
            helper.setImageBitmap(R.id.mymission_image, item.getTopicContent().getTopic_img_url().get(0).getMid());
        } else {
            helper.setVisible(R.id.mymission_image, View.GONE);
        }
    }
    public String initTime(long time){
        String text;
        long currentTime=System.currentTimeMillis()/1000;
        time=time/1000;
        long tempTime=currentTime-time;
        double temp=2592000;
        Log.i("time",tempTime+"|"+currentTime+"|"+time);
        if (tempTime>=0&&tempTime<60){
            SimpleDateFormat sdf=new SimpleDateFormat("ss秒前");
            Date date=new Date(tempTime*1000);
            text=sdf.format(date);
        }else if (tempTime>=60&&tempTime<3600){
            SimpleDateFormat sdf=new SimpleDateFormat("mm分前");
            Date date=new Date(tempTime*1000);
            text=sdf.format(date);

        }else if(tempTime>=3600&&tempTime<86400){
            SimpleDateFormat sdf=new SimpleDateFormat("HH个小时前");
            tempTime=tempTime+57600;
            Date date=new Date(tempTime*1000);
            text=sdf.format(date);
        }else if(tempTime>=86400&&tempTime<temp){
            SimpleDateFormat sdf=new SimpleDateFormat("dd天前");
            Date date=new Date(tempTime*1000);
            text=sdf.format(date);
        }else{
            SimpleDateFormat sdf=new SimpleDateFormat("MM个月前");
            text=sdf.format(tempTime*1000);
        }
        return text;
    }
}
