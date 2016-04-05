package com.example.yanqijs.missiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import adapter.ChatAdapter;
import vo.ChatData;

public class ChatActivity extends BaseActivity {
    private ListView mListView;
    private ChatAdapter mChatAdapter;
    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListView();
        initListener();
        mListView.setSelection(mChatAdapter.getCount());
    }

    private void initListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatData chatData = new ChatData();
                chatData.setName("bb");
                chatData.setContent(mEditText.getText().toString() + "");
                chatData.setType(1);
                chatData.setImage(null);
                mChatAdapter.add(chatData);
                mEditText.setText("");
            }
        });
    }

    private void initListView() {
        mChatAdapter = new ChatAdapter(this);
        mListView.setAdapter(mChatAdapter);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_chat;
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv);
        mEditText = (EditText) findViewById(R.id.edit);
        mButton = (Button) findViewById(R.id.send);
    }
}
