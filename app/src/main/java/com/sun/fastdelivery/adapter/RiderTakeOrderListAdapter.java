package com.sun.fastdelivery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.GoodType;
import com.sun.fastdelivery.bean.Order;

import java.util.List;


/**
 * Created by sunxuedian on 2018/5/26.
 */

public class RiderTakeOrderListAdapter extends RecyclerView.Adapter<RiderTakeOrderListAdapter.MyViewHolder>{

    private OnRecyclerViewItemClickListener mItemClickListener;
    private List<Order> mData;
    private Context mContext;

    public RiderTakeOrderListAdapter(Context mContext, List<Order> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.rider_take_order_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = mData.get(position);
        holder.mTvTitle.setText(order.getCreateTime().split(" ")[0] + " " + GoodType.getName(order.getGoodType()));
        holder.mTvReceiveInfo.setText("寄件：" + order.getOrderShippingInfo().getDeparture());
        holder.mTvSendInfo.setText("收件：" + order.getOrderShippingInfo().getDestination());
        holder.mTvMoney.setText("￥" + order.getOrderPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mTvTitle;
        TextView mTvSendInfo;
        TextView mTvReceiveInfo;
        TextView mTvMoney;

        public MyViewHolder(final View itemView) {
            super(itemView);
            if (mItemClickListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(itemView, getPosition());
                    }
                });
            }

            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mTvSendInfo = itemView.findViewById(R.id.tvSendInfo);
            mTvReceiveInfo = itemView.findViewById(R.id.tvReceiveInfo);
            mTvMoney = itemView.findViewById(R.id.tvMoney);
        }
    }
}
