package com.tongban.im.activity.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dd.CircularProgressButton;
import com.tb.api.AccountApi;
import com.tb.api.base.BaseApi;
import com.tb.api.model.BaseEvent;
import com.tb.api.model.user.OtherRegister;
import com.tb.api.utils.ApiConstants;
import com.tb.api.utils.TransferCenter;
import com.tongban.corelib.base.ActivityContainer;
import com.tongban.corelib.model.ApiErrorResult;
import com.tongban.corelib.utils.Constants;
import com.tongban.corelib.utils.SPUtils;
import com.tongban.corelib.utils.ToastUtil;
import com.tongban.corelib.widget.view.BaseDialog;
import com.tongban.corelib.widget.view.ClearEditText;
import com.tongban.im.R;
import com.tongban.im.activity.MainActivity;
import com.tongban.im.activity.base.AccountBaseActivity;
import com.tongban.im.common.Consts;
import com.tongban.umeng.listener.UMSocializeOauthBackListener;
import com.tongban.umeng.listener.UMSocializeOauthListenerImpl;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;

/**
 * 登录
 *
 * @author zhangleilei
 * @createTime 2015/7/16
 */
public class LoginActivity extends AccountBaseActivity implements
        UMSocializeOauthBackListener {

    @Bind(R.id.et_phone_num)
    ClearEditText etPhoneNum;
    @Bind(R.id.et_pwd)
    ClearEditText etPwd;
    @Bind(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @Bind(R.id.tv_new_user_register)
    TextView tvNewUserRegister;
    @Bind(R.id.btn_wechat_login)
    ImageView btnWechatLogin;
    @Bind(R.id.btn_login)
    CircularProgressButton btnLogin;

    private String mUser, mPwd;
    //是否其他设备登录进入
    private boolean isOther = false;
    //是否需要跳转到main界面
    private boolean mIsOpenMain;

    private UMSocializeOauthListenerImpl authListener;
    private String userInfoJson;
    private String type;
    // 是否暂停过
    private boolean isOnPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);
        authListener = new UMSocializeOauthListenerImpl(mContext, this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        btnLogin.setIndeterminateProgressMode(true);

        mIsOpenMain = getIntent().getBooleanExtra(ApiConstants.KEY_IS_MAIN, true);
        isOther = getIntent().getBooleanExtra(ApiConstants.KEY_OTHER_CLIENT, false);
        if (isOther) {
            BaseDialog.Builder dialog = new BaseDialog.Builder(mContext);
            dialog.setMessage("您的账号已在别的设备登录");
            dialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
            dialog.show();
        }
        mUser = SPUtils.get(mContext, SPUtils.NO_CLEAR_FILE, Constants.USER_ACCOUNT, "").toString();
        etPhoneNum.setText(mUser);
    }

    @OnTextChanged({R.id.et_phone_num, R.id.et_pwd})
    public void afterTextChanged(Editable s) {
        mUser = etPhoneNum.getText().toString();
        mPwd = etPwd.getText().toString();
        if (mUser.length() == 0 || mPwd.length() < 6) {
            btnLogin.setEnabled(false);
        } else {
            btnLogin.setEnabled(true);
        }
    }

    @OnEditorAction(R.id.et_pwd)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onClick(btnLogin);
        }
        return false;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnPause = false;
    }

    @OnClick({R.id.btn_login, R.id.tv_new_user_register, R.id.tv_forget_pwd, R.id.btn_wechat_login})
    public void onClick(View v) {
        // 登录
        if (v == btnLogin) {
            btnLogin.setProgress(50);
            btnLogin.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AccountApi.getInstance().login(mUser, mPwd, LoginActivity.this);
                }
            }, 2 * 1000);
        }
        // 注册
        else if (v == tvNewUserRegister) {
            TransferCenter.getInstance().startRegister();
        }
        // 忘记密码
        else if (v == tvForgetPwd) {
            startActivity(new Intent(mContext, FindPwdActivity.class));
        }
        // 微信登录
        else if (v == btnWechatLogin) {
            authListener.doWeCHatOauth();
        }
    }

    @Override
    public void onBackPressed() {
        ActivityContainer.getInstance().finishActivity();
    }

    /**
     * 登录成功
     */
    public void onEventMainThread(BaseEvent.UserLoginEvent obj) {
        if (!isOnPause) {
            hideProgress();
            btnLogin.setProgress(0);
            SPUtils.put(mContext, SPUtils.NO_CLEAR_FILE, Constants.USER_ACCOUNT, mUser);
            if (TextUtils.isEmpty(obj.user.getNick_name())) {
                TransferCenter.getInstance().startRegister(true);
            } else {
                connectIM();
            }
        }
    }

    public void onEventMainThread(BaseEvent.EditUserEvent obj) {
        finish();
    }

    /**
     * 接口出错
     *
     * @param obj
     */
    public void onEventMainThread(ApiErrorResult obj) {
        // 登录失败
        if (obj.getApiName().equals(AccountApi.LOGIN)) {
            btnLogin.setProgress(0);
        }
        // 授权登录失败
        else if (obj.getApiName().equals(AccountApi.OTHER_LOGIN)) {
            hideProgress();
            TransferCenter.getInstance().startOtherRegister(userInfoJson, type);
            finish();
        }
    }

    @Override
    public void oauthStart() {
        showProgress();
    }

    @Override
    public void oauthSuccess(final String userInfoJson, final String type) {
        this.userInfoJson = userInfoJson;
        this.type = type;
        OtherRegister otherRegister = JSON.parseObject(userInfoJson,
                new TypeReference<OtherRegister>() {
                });
        AccountApi.getInstance().otherLogin(otherRegister.getOpenId(), type, this);
    }


    @Override
    public void oauthFailure(String message) {
        hideProgress();
        ToastUtil.getInstance(mContext).showToast(message);
    }


    private int clickCount = 0;
    private long firstTime = 0;
    private long timeConsuming = 0;

    // 设置服务器地址 test
    public void setApiUrl(View v) {
        if (clickCount == 0) {
            firstTime = System.currentTimeMillis();
            clickCount++;
        } else if (clickCount == 1) {
            timeConsuming = System.currentTimeMillis();
            clickCount++;
        } else if (clickCount == 2) {
            if (timeConsuming - firstTime < 800) {
                final BaseDialog.Builder dialog = new BaseDialog.Builder(mContext);
                dialog.setPositiveButton("修改",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                BaseApi.getInstance().setHostUrl(mContext, dialog.getEditText());
                            }
                        });
                dialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                dialog.setIsEditMode(true);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                dialog.setEditText(BaseApi.getInstance().getHostUrl());
                clickCount = 0;
                timeConsuming = 0;
                firstTime = 0;
            }
        }
    }
}
