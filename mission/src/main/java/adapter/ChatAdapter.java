package adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yanqijs.missiondemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import vo.ChatData;

/**
 * Created by yanqijs on 2016/2/23.
 */
public class ChatAdapter extends BaseAdapter {
    private List<ChatData> datas = new ArrayList<>();
    private ViewHolder mViewHolder;
    private Context context;

    public ChatAdapter(Context context) {
        this.context = context;
        initData();
        initData();
        initRight();
        initRight();
        initData();
        initRight();
        initRight();
        initData();
        initRight();
    }

    private void initRight() {
        ChatData chatData = new ChatData();
        chatData.setName("bb");
        chatData.setContent("lkhjoiho");
        chatData.setType(1);
        chatData.setImage(null);
        datas.add(chatData);
    }

    private void initData() {
        ChatData chatData = new ChatData();
        chatData.setName("yq");
        chatData.setContent("adasdawd");
        chatData.setType(0);
        chatData.setImage(null);
        datas.add(chatData);
    }

    public void add(ChatData chatData) {
        datas.add(chatData);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        Log.i("ChatAdapter", "type:" + type + "     postion:" + position + "   view:" + convertView);
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            switch (type) {
                case 0:
                    Log.i("ChatAdapter", "left");
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_chat_left, null);
                    mViewHolder.textView = (TextView) convertView.findViewById(R.id.text);
                    mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
                    break;
                case 1:
                    Log.i("ChatAdapter", "right");
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_chat_right, null);
                    mViewHolder.textView = (TextView) convertView.findViewById(R.id.text);
                    mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
                    break;
                default:
                    break;
            }
            convertView.setTag(mViewHolder);

        } else {
            Log.i("ChatAdapter", "has");
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.textView.setText(datas.get(position).getContent());
        return convertView;
    }

    public class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
