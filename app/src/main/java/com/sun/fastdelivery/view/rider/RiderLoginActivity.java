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
import com.sun.fastdelivery.model.UserModel;
import com.sun.fastdelivery.presenter.LoginPresenter;
import com.sun.fastdelivery.utils.ToastUtils;
import com.sun.fastdelivery.utils.UserSpUtils;
import com.sun.fastdelivery.view.base.BaseActivity;
import com.sun.fastdelivery.view.base.ILoginView;
import com.sun.fastdelivery.view.user.UserMainActivity;
import com.sun.xuedian.multiedittext.MultiEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RiderLoginActivity extends BaseActivity<LoginPresenter, ILoginView> implements ILoginView{

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

    @OnClick(R.id.tvSendCode)
    public void sendCode(){
        mPresenter.sendCode();//请求调用验证码
    }

    @OnClick(R.id.btnLogin)
    public void login(){
        mPresenter.onLogin();//登录操作
    }

    @OnClick(R.id.tvGoRegister)
    public void goRegister(){
        Intent intent = new Intent(this, RiderRegisterActivity.class);
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
        setContentView(R.layout.activity_rider_login);
        ButterKnife.bind(this);
        mLoadingView = new ShapeLoadingDialog(this);
        mLoadingView.setLoadingText("登录中...");
        mLoadingView.setCanceledOnTouchOutside(false);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this, UserModel.TYPE_RIDER_USER);
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
    public void onLoginSuccess(User user) {
        ToastUtils.showToast("登录成功！");
        //将用户信息保存到本地中
        RiderUser riderUser = new RiderUser(user.getUserId(), user.getUserPhone(), user.getAllocatedToken());
        UserSpUtils.saveRiderUser(this, riderUser);
        //跳转到主界面中
        Intent intent = new Intent(this, RiderUserMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoadingView = null;
    }

}
