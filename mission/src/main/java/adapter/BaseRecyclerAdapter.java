package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.yanqijs.imp.RecyclerImp;

import java.util.ArrayList;
import java.util.List;

/**
 * recyclerView 基础类，便于快速创建recycler
 * 集成后只需要设置layout，以及view事件即可
 * <p>
 * <p>
 * Created by yanqijs on 2016/3/7.
 *
 * @param <T> 数据类型
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHelper> {

    private List<T> mDatas;
    private BaseRecyclerHelper mViewHolder;
    private Context context;


    protected RecyclerImp onRecyclerImp;


    @Override
    public BaseRecyclerHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        mViewHolder = new BaseRecyclerHelper(LayoutInflater.from(context).inflate(getLayoutID(), null), onRecyclerImp);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHelper holder, int position) {
        init(holder, position, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public BaseRecyclerAdapter(List<T> mDatas, Context context, RecyclerImp onRecyclerImp) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        this.mDatas.addAll(mDatas);
        this.context = context;
        this.onRecyclerImp = onRecyclerImp;
    }

    //--------------------添加数据-------------------------

    public void addAll(List<T> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void add(T data) {
        this.mDatas.add(data);
        notifyDataSetChanged();
    }


    //-----------------------删除数据-----------------------
    public void delete(int postion) {
        this.mDatas.remove(postion);
    }

    public void deleteAll() {
        this.mDatas.clear();
    }

    //----------------------实现item构建的方法--------------------------

    public abstract void init(BaseRecyclerHelper helper, int position, T item);

    //-------------------------得到item布局----------------------------------

    protected abstract int getLayoutID();

    protected abstract void setOnRecyclerImp(RecyclerImp onRecyclerImp);
}
