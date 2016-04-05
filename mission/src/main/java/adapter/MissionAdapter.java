package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yanqijs.missiondemo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import single.Mission;
import vo.MissionData;

/**
 * Created by yanqijs on 2016/2/19.
 */
public class MissionAdapter extends RecyclerView.Adapter<MissionAdapter.MyViewHolder> {
    private Context context;
    public CardClick mCardClick;

    public void setOnCardClick(CardClick mCardClick) {
        this.mCardClick = mCardClick;
    }

    public interface CardClick {
        void onItemClick(View view, int postion);
    }

    public MissionAdapter(Context context) {
        this.context = context;
        MissionData missionData = new MissionData();
        missionData.setContent("adswdasdwdas");
        missionData.setStatus(0);
        missionData.setContentImage(null);
        missionData.setPublisher("yQ");
        missionData.setPublishTime(1455850444);
        Mission.getInstance().add(missionData);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mymisson, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm");
        long currentTime = System.currentTimeMillis();
        long tempTime = currentTime - Mission.getInstance().get(position).getPublishTime();
        Date date = new Date(tempTime);
        holder.timeTextView.setText(Mission.getInstance().get(position).getPublisher() + " | " + sdf.format(date) + "分钟前");
        holder.statusTextView.setVisibility(View.GONE);
        holder.contentTextView.setText(Mission.getInstance().get(position).getContent());
        if (Mission.getInstance().get(position).getContentImage() == null) {
            holder.contentImageView.setVisibility(View.GONE);
        } else {
            holder.contentImageView.setVisibility(View.VISIBLE);
            holder.contentImageView.setImageBitmap(image(Mission.getInstance().get(position).getContentImage()));
        }
    }

    @Override
    public int getItemCount() {
        return Mission.getInstance().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView statusTextView, contentTextView, timeTextView;
        private ImageView contentImageView, touxiangImageView;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            statusTextView = (TextView) itemView.findViewById(R.id.mymission_success);
            contentTextView = (TextView) itemView.findViewById(R.id.mymission_item_content);
            timeTextView = (TextView) itemView.findViewById(R.id.mymission_publisher);
            contentImageView = (ImageView) itemView.findViewById(R.id.mymission_image);
            touxiangImageView = (ImageView) itemView.findViewById(R.id.mymission_touxiang);
            cardView = (CardView) itemView.findViewById(R.id.mymission_cardview);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mCardClick != null) {
                mCardClick.onItemClick(v, (getAdapterPosition()));
            }
        }
    }

    public Bitmap image(String file) {
        Bitmap imageBitmap = BitmapFactory.decodeFile(file);
        int px = dip2px(context, 96);
        int width = imageBitmap.getWidth();
        int height = imageBitmap.getHeight();

        float scWidth = ((float) px) / width;
        float scHeight = ((float) px) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scWidth, scHeight);
        Bitmap bitmap = Bitmap.createBitmap(imageBitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    public static int dip2px(Context context, float dipValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dipValue * scale + 0.5f);

    }
}
