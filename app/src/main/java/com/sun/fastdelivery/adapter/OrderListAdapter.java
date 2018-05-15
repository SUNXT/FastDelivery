package com.sun.fastdelivery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_list_item, parent, false);
        if (mOnItemClickListener != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mOnItemClickListener.onItemClick(view, );
                }
            });
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

}
