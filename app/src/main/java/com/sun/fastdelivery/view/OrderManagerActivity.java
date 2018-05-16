package com.sun.fastdelivery.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.adapter.OnRecyclerViewItemClickListener;
import com.sun.fastdelivery.adapter.OrderListAdapter;
import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.User;
import com.sun.fastdelivery.presenter.OrderManagerPresenter;
import com.sun.fastdelivery.utils.ToastUtils;
import com.sun.fastdelivery.utils.UserSpUtils;
import com.sun.fastdelivery.view.base.BaseActivity;
import com.sun.fastdelivery.view.base.IOrderManagerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderManagerActivity extends BaseActivity<OrderManagerPresenter, IOrderManagerView> implements IOrderManagerView {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rvOrders)
    RecyclerView mRvOrders;
    @BindView(R.id.tvTip)
    TextView mTvTip;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    private OrderListAdapter mAdapter;
    private List<Order> mOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){

        mTvTitle.setText("订单管理");

        mRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                mPresenter.getOrderList();
            }
        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getOrderList();
            }
        });
        //解决recycleView和refreshLayout的滑动冲突
        mRvOrders.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        mAdapter = new OrderListAdapter(this, mOrders);
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Order order = mAdapter.getItem(pos);
                if (order.getStatus() == Order.STATUS_CREATE){
                    Intent intent = new Intent(OrderManagerActivity.this, PayOrderActivity.class);
                    intent.putExtra(Order.TAG, order);
                    startActivity(intent);
                }else {

                }
            }
        });
        mRvOrders.setLayoutManager(new LinearLayoutManager(this));
        mRvOrders.setAdapter(mAdapter);

    }

    @Override
    protected OrderManagerPresenter createPresenter() {
        return new OrderManagerPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {
        if (mRefreshLayout.isRefreshing()){
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public User getUser() {
        return UserSpUtils.getUser(this);
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
}
