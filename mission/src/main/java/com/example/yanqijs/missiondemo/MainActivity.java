package com.example.yanqijs.missiondemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yanqijs.base.BaseDialogFragment;
import com.example.yanqijs.http.HttpConnect;
import com.example.yanqijs.imp.RecyclerImp;

import org.json.JSONException;
import org.json.JSONObject;

import adapter.MissionAdapter;
import adapter.RecyclerAdapter;
import single.Mission;
import single.User;


public class MainActivity extends BaseActivity {
    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private MissionAdapter myAdapter;
    private TextView mymissionTextView;
    private SwipeRefreshLayout mRefreshLayout;
    private RelativeLayout mRelativeLayout;

    private RecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUser();
        initView();
        MyThread myThread = new MyThread();
        myThread.start();

//-----------------------------------
        //// TODO: 2016/3/7 完成adapter设置
        mRecyclerAdapter = new RecyclerAdapter(Mission.getInstance(), this, new RecyclerImp() {
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        //设置监听
        mRecyclerAdapter.setOnClickListener(new CardClick(this));
//-----------------------------------
        //创建返回键，并实现打开关/闭监听
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar
                , R.string.app_name, R.string.main_title);
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
        initRecyclerView();
        initListener();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject("{\"statusCode\":0,\"data\":{\"tags\":\"\",\"birthday\":\"\",\"portrait_url\":\"{\\\"max\\\":\\\"http:\\/\\/7xkuqd.com2.z0.glb.qiniucdn.com\\/0_56cea6875508b0e4739d22fc\\\",\\\"mid\\\":\\\"http:\\/\\/7xkuqd.com2.z0.glb.qiniucdn.com\\/0_56cea6875508b0e4739d22fc-500\\\",\\\"min\\\":\\\"http:\\/\\/7xkuqd.com2.z0.glb.qiniucdn.com\\/0_56cea6875508b0e4739d22fc-300\\\"}\",\"constellation\":\"\",\"sex\":\"2\",\"declaration\":\"\",\"status\":\"2\",\"c_time\":1456383624000,\"honorv\":1000,\"nick_name\":\"啊啊\",\"m_time\":1456383664000,\"mobile_phone\":\"15210426925\",\"address\":\"0\",\"age\":\"\",\"longitude\":0,\"user_id\":\"0_56cea6875508b0e4739d22fc\",\"latitude\":0,\"child_info\":\"\",\"freeauth_token\":\"3bf9a5b3-6c05-4e27-899a-a0ffd4549b10\",\"address_type\":\"0\",\"im_bind_token\":\"MKNRl9Z5021BztsHmy+gRa\\/xSMbaHcAeGleHV32JLttLt31I1cFpu6tb6VAlsK\\/KuP6odF\\/jo3N3WBXHCAvPhMPBCSY1XeacXiA4YWg5Ay\\/73r+lYROeDU0jonEqjY33\",\"ops\":\"0\"},\"statusDesc\":\"\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsobj(jsonObject);

    }

    private void initRecyclerView() {
//        myAdapter = new MissionAdapter(this);
//        recyclerView.setAdapter(myAdapter);
        recyclerView.setAdapter(mRecyclerAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        //默认为Ver纵向的
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        recyclerView = (RecyclerView) findViewById(R.id.main_recycle);
        fab = (FloatingActionButton) findViewById(R.id.main_fab);
        mymissionTextView = (TextView) findViewById(R.id.main_mymission);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_refresh);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.main_list);
    }

    private void initUser() {
        User.getInstance().setName("yQ");
        User.getInstance().setRating((float) 4.5);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


    @NonNull
    private void initListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PublishActivity.class);
                startActivity(i);
            }
        });

        mymissionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyMissionActivity.class);
                startActivity(intent);
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyThread myThread = new MyThread();
                myThread.start();
            }
        });
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_chat:
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                Intent intent1 = new Intent(MainActivity.this, JudgeActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        myAdapter.notifyDataSetChanged();
        mRecyclerAdapter.addAll(Mission.getInstance());
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            HttpConnect httpConnect = new HttpConnect();
            httpConnect.connectVolley(MainActivity.this);
            try {
                sleep(5000);
                mRefreshLayout.setRefreshing(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void jsobj(Object object) {
        Log.i("jsobj", object + "");
    }

    class CardClick implements View.OnClickListener {
        Context context;

        public CardClick(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mymission_item_content:
                    Toast.makeText(context, "a", Toast.LENGTH_LONG).show();
                    break;
                case R.id.mymission_cardview:
                    Intent intent = new Intent(MainActivity.this, MissionActivity.class);
                    intent.putExtra("postion", 0);
                    startActivity(intent);
                    break;
            }
        }
    }

}
