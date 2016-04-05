package com.example.yanqijs.missiondemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import single.Mission;
import single.User;

public class MissionActivity extends BaseActivity {
    private FrameLayout mButton;
    private int mPostion;
    private RatingBar mRatingBar;
    private TextView nameTextView, ratingTextView, missionTimeTextView, contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mPostion = intent.getIntExtra("postion", -1);
        initView();
        initListener();
        mRatingBar.setRating(User.getInstance().getRating());
        nameTextView.setText(User.getInstance().getName());
        ratingTextView.setText(" " + User.getInstance().getRating() + " ");
        SimpleDateFormat sdf = new SimpleDateFormat("(yyyy-MM-dd) hh:mm:ss");
        Date date = new Date(Mission.getInstance().get(mPostion).getPublishTime());
        missionTimeTextView.setText(sdf.format(date));
        contentTextView.setText(Mission.getInstance().get(mPostion).getContent());

    }

    private void initListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MissionActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mButton = (FrameLayout) findViewById(R.id.frame_btn);
        mRatingBar = (RatingBar) findViewById(R.id.mission_ratingbar);
        nameTextView = (TextView) findViewById(R.id.mission_name);
        ratingTextView = (TextView) findViewById(R.id.rating_text);
        missionTimeTextView = (TextView) findViewById(R.id.time_textview);
        contentTextView = (TextView) findViewById(R.id.content_textview);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_mission;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
