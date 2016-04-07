package adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.yanqijs.imp.RecyclerImp;
import com.example.yanqijs.missiondemo.R;

import java.util.List;

import single.Mission;
import vo.MissionData;

/**
 * Created by yanqijs on 2016/3/7.
 */
public class RecyclerAdapter extends BaseRecyclerAdapter<MissionData> {
    private View.OnClickListener onClickListener;


    public RecyclerAdapter(List<MissionData> mDatas, Context context, RecyclerImp onRecyclerImp) {
        super(mDatas, context, onRecyclerImp);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    protected int getLayoutID() {
        return R.layout.item_mymisson;
    }

    @Override
    protected void setOnRecyclerImp(RecyclerImp onRecyclerImp) {
        this.onRecyclerImp = onRecyclerImp;
    }

    @Override
    public void init(BaseRecyclerHelper helper, int position, MissionData item) {
        //// TODO: 2016/3/7 写入mission数据
        helper.setText(R.id.mymission_item_content, item.getContent());
        helper.setOnClick(R.id.mymission_item_content, onClickListener);
        helper.setVisible(R.id.mymission_success, View.GONE);
//        helper.onClick(R.id.mymission_cardview);

    }

}
