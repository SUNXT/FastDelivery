package com.sun.fastdelivery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.Order;

import java.util.List;

/**
 * Created by SUN on 2018/5/15.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private Context mContext;
    private List<Order> mData;

    public OrderListAdapter(Context mContext, List<Order> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public Order getItem(int pos){
        return mData.get(pos);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = mData.get(position);
        holder.mTvTime.setText(order.getCreateTime());
        holder.mTvReceiveInfo.setText("寄件：" + order.getOrderShippingInfo().getDeparture());
        holder.mTvSendInfo.setText("收件：" + order.getOrderShippingInfo().getDestination());
        String status = "已创建";
        switch (order.getStatus()){
            case Order.STATUS_CANCEL:
                holder.mTvStatus.setTextColor(mContext.getResources().getColor(R.color.red));
                status = "已取消";
                break;
            case Order.STATUS_PAY:
                holder.mTvStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                status = "已支付";
                break;
            case Order.STATUS_SENDING:
                holder.mTvStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                status = "派送中";
                break;
            case Order.STATUS_COMPLETE:
                holder.mTvStatus.setTextColor(mContext.getResources().getColor(R.color.black));
                status = "已送达";
                break;
        }
        holder.mTvStatus.setText(status);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mTvTime;
        TextView mTvSendInfo;
        TextView mTvReceiveInfo;
        TextView mTvStatus;

        MyViewHolder(final View itemView) {
            super(itemView);
            if (mOnItemClickListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(itemView, getPosition());
                    }
                });
            }
            mTvTime = itemView.findViewById(R.id.tvCreateTime);
            mTvSendInfo = itemView.findViewById(R.id.tvSendInfo);
            mTvReceiveInfo = itemView.findViewById(R.id.tvReceiveInfo);
            mTvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

}
