package com.sun.fastdelivery.view.rider;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.User;
import com.sun.fastdelivery.model.UserModel;
import com.sun.fastdelivery.presenter.LoginPresenter;
import com.sun.fastdelivery.utils.ToastUtils;
import com.sun.fastdelivery.view.base.BaseActivity;
import com.sun.fastdelivery.view.base.ILoginView;
import com.sun.fastdelivery.view.rider.fragment.HomeFragment;
import com.sun.fastdelivery.view.rider.fragment.MineFragment;
import com.sun.fastdelivery.view.rider.fragment.OrderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RiderUserMainActivity extends AppCompatActivity{

    @BindView(R.id.tvHome)
    TextView mTvHome;
    @BindView(R.id.tvOrder)
    TextView mTvOrder;
    @BindView(R.id.tvMine)
    TextView mTvMine;

    private View mLastSelectedView;//最后一次点击的View
    private Fragment mLastShowFragment;//最后一次显示的Fragment

    private HomeFragment mHomeFragment;
    private OrderFragment mOrderFragment;
    private MineFragment mMineFragment;

    private long mLastClickTime;
    private long mMostExitTime = 2000;//两秒内点击两次则退出

    @OnClick({R.id.tvHome, R.id.tvOrder, R.id.tvMine})
    public void onBottomBarClick(View view){
        if (mLastSelectedView != view){
            mLastSelectedView.setSelected(false);//将上一次选中的View设置为非选中状态
            mLastSelectedView = view;
            mLastSelectedView.setSelected(true);//将当前的View设置为选中状态
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.hide(mLastShowFragment);
        ft.commit();
        ft = getFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.tvHome:
                mLastShowFragment = mHomeFragment;
                break;
            case R.id.tvOrder:
                if (mOrderFragment == null){
                    mOrderFragment = new OrderFragment();
                    ft.add(R.id.mainFrame, mOrderFragment);
                }
                mLastShowFragment = mOrderFragment;
                break;
            case R.id.tvMine:
                if (mMineFragment == null){
                    mMineFragment = new MineFragment();
                    ft.add(R.id.mainFrame, mMineFragment);
                }
                mLastShowFragment = mMineFragment;
                break;
        }
        ft.show(mLastShowFragment);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_user_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvHome.setSelected(true);
        mLastSelectedView = mTvHome;
        mHomeFragment = new HomeFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.mainFrame, mHomeFragment);
        ft.commit();
        mLastShowFragment = mHomeFragment;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastClickTime < mMostExitTime){
            finish();
        }else {
            ToastUtils.showToast("再按一次，退出程序！");
            mLastClickTime = System.currentTimeMillis();
        }
    }
}
