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

import static android.R.attr.id;


/**
 * Created by sunxuedian on 2018/5/26.
 */

public class RiderOrderListAdapter extends RecyclerView.Adapter<RiderOrderListAdapter.MyViewHolder>{

    private OnRecyclerViewItemClickListener mItemClickListener;
    private List<Order> mData;
    private Context mContext;

    public RiderOrderListAdapter(Context mContext, List<Order> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.rider_order_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = mData.get(position);
        holder.mTvTitle.setText(order.getCreateTime().split(" ")[0] + " " + GoodType.getName(order.getGoodType()));
        holder.mTvReceiveInfo.setText("寄件：" + order.getOrderShippingInfo().getDeparture());
        holder.mTvSendInfo.setText("收件：" + order.getOrderShippingInfo().getDestination());
        if (order.getStatus() == Order.STATUS_COMPLETE){
            holder.mTvArrivePlace.setText("已送达");
        }else {
            if (order.getOrderShippingInfo().getArrivePlace() == 0){
                holder.mTvArrivePlace.setText("暂无物流信息");
            }else {
                holder.mTvArrivePlace.setText("到达站点" + order.getOrderShippingInfo().getArrivePlace());
            }
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mTvTitle;
        TextView mTvSendInfo;
        TextView mTvReceiveInfo;
        TextView mTvArrivePlace;

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
            mTvArrivePlace = itemView.findViewById(R.id.tvArrivePlace);
        }
    }
}
