package com.sun.fastdelivery.view.rider.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.RiderUser;
import com.sun.fastdelivery.presenter.RiderUserInfoPresenter;
import com.sun.fastdelivery.utils.ToastUtils;
import com.sun.fastdelivery.utils.UserSpUtils;
import com.sun.fastdelivery.view.base.BaseFragment;
import com.sun.fastdelivery.view.base.IRiderUserInfoView;
import com.sun.fastdelivery.view.rider.RiderLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sunxuedian on 2018/5/26.
 */

public class MineFragment extends BaseFragment<IRiderUserInfoView,RiderUserInfoPresenter> implements IRiderUserInfoView{

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.tvPhone)
    TextView mTvPhone;
    @BindView(R.id.tvMoney)
    TextView mTvMoney;

    @OnClick(R.id.btnLogout)
    public void logout(){
        UserSpUtils.riderUserLogout(getActivity());
        Intent intent = new Intent(getActivity(), RiderLoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.mine_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                mPresenter.getRiderUserInfo();
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getRiderUserInfo();
            }
        });

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public RiderUser getRiderUser() {
        return UserSpUtils.getRiderUser(getActivity());
    }

    @Override
    public void showRiderUser(RiderUser user) {
        mTvMoney.setText("￥" + user.getUserWalletAmount());
        mTvPhone.setText(user.getUserPhone());
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public RiderUserInfoPresenter createPresenter() {
        return new RiderUserInfoPresenter();
    }
}
