package com.sun.fastdelivery.view.rider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mingle.widget.ShapeLoadingDialog;
import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.RiderUser;
import com.sun.fastdelivery.bean.User;
import com.sun.fastdelivery.presenter.RiderRegisterPresenter;
import com.sun.fastdelivery.utils.ToastUtils;
import com.sun.fastdelivery.utils.UserSpUtils;
import com.sun.fastdelivery.view.base.BaseActivity;
import com.sun.fastdelivery.view.base.IRiderRegisterView;
import com.sun.xuedian.multiedittext.MultiEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class RiderRegisterActivity extends BaseActivity<RiderRegisterPresenter, IRiderRegisterView> implements IRiderRegisterView {

    @BindView(R.id.etPhone)
    MultiEditText mEtPhone;
    @BindView(R.id.etCode)
    MultiEditText mEtCode;
    @BindView(R.id.tvSendCode)
    TextView mTvSendCode;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    /**
     * 点击发送验证码按钮
     */
    @OnClick(R.id.tvSendCode)
    public void sendCode(){
        mPresenter.sendCode();
    }

    /**
     * 点击注册按钮
     */
    @OnClick(R.id.btnRegister)
    public void register(){
        mPresenter.onRiderUserRegister();
    }

    /**
     * 跳转到登录界面
     */
    @OnClick(R.id.tvGoLogin)
    public void goLogin(){
        Intent intent = new Intent(this, RiderLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private ShapeLoadingDialog mLoadingView;//进度对话框
    private final int TIME = 60;//一次发送短信的时间
    private int mLeftTime = TIME;//倒计时
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mLeftTime --;
            if (mLeftTime == 0){
                mTvSendCode.setEnabled(true);
                mTvSendCode.setText("重新发送");
                mLeftTime = TIME;
            }else {
                mTvSendCode.setText(mLeftTime + "秒后重发");
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_register);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        mLoadingView = new ShapeLoadingDialog(this);
        mLoadingView.setLoadingText("加载中...");
        mLoadingView.setCanceledOnTouchOutside(false);
    }

    @Override
    protected RiderRegisterPresenter createPresenter() {
        return new RiderRegisterPresenter(this);
    }

    @Override
    public void showLoading() {
        mLoadingView.show();
    }

    @Override
    public void stopLoading() {
        mLoadingView.dismiss();
    }

    @Override
    public String getPhone() {
        return mEtPhone.getText();
    }

    @Override
    public String getCode() {
        return mEtCode.getText();
    }

    @Override
    public void onSendCodeSuccess() {
        ToastUtils.showToast("验证码已经发送到您的手机上，请注意查收！");
        mTvSendCode.setEnabled(false);
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onRegisterSuccess() {
        ToastUtils.showToast("注册成功！进行登录操作！");
        mPresenter.onLogin();
    }

    @Override
    public void onLoginSuccess(RiderUser user) {
        UserSpUtils.saveRiderUser(this, user);
        Intent intent = new Intent(this, RiderUserMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(String msg) {
        ToastUtils.showToast(msg);
    }
}
