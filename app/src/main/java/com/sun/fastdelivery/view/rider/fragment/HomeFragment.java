package com.sun.fastdelivery.view.rider.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.adapter.OnRecyclerViewItemClickListener;
import com.sun.fastdelivery.adapter.RiderTakeOrderListAdapter;
import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.User;
import com.sun.fastdelivery.presenter.OrderManagerPresenter;
import com.sun.fastdelivery.utils.ToastUtils;
import com.sun.fastdelivery.utils.UserSpUtils;
import com.sun.fastdelivery.view.base.BaseFragment;
import com.sun.fastdelivery.view.base.IOrderManagerView;
import com.sun.fastdelivery.view.rider.TakeOrderActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.media.CamcorderProfile.get;

/**
 * Created by sunxuedian on 2018/5/26.
 */

public class HomeFragment extends BaseFragment<IOrderManagerView, OrderManagerPresenter> implements IOrderManagerView {

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rvOrders)
    RecyclerView mRvOrders;
    @BindView(R.id.tvTip)
    TextView mTvTip;

    private List<Order> mOrders = new ArrayList<>();//订单的数据源
    private RiderTakeOrderListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){

        mAdapter = new RiderTakeOrderListAdapter(getActivity(), mOrders);
        mAdapter.setItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Order order = mOrders.get(pos);
                Intent intent = new Intent(getActivity(), TakeOrderActivity.class);
                intent.putExtra(Order.TAG, order);
                startActivity(intent);
            }
        });
        mRvOrders.setAdapter(mAdapter);
        mRvOrders.setLayoutManager(new LinearLayoutManager(getActivity()));

        //解决recycleView和refreshLayout的滑动冲突
        mRvOrders.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                mPresenter.getAllOrdersCanTake();
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getAllOrdersCanTake();
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
    public User getUser() {
        return UserSpUtils.getRiderUser(getActivity());
    }

    @Override
    public void showOrders(List<Order> orders) {
        if (orders == null || orders.isEmpty()){
            //显示提示
            mTvTip.setVisibility(View.VISIBLE);
            return;
        }

        mTvTip.setVisibility(View.GONE);
        mOrders.clear();
        mOrders.addAll(orders);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public OrderManagerPresenter createPresenter() {
        return new OrderManagerPresenter();
    }
}
