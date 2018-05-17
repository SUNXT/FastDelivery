package com.sun.fastdelivery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.fastdelivery.R;

/**
 * Created by sunxuedian on 2018/5/18.
 */

public class ArrivePlaceListAdapter extends RecyclerView.Adapter<ArrivePlaceListAdapter.MyViewHolder> {

    private Context mContext;
    private int count;

    public ArrivePlaceListAdapter(Context mContext, int count) {
        this.mContext = mContext;
        this.count = count;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.arrive_places_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position == 0){
            holder.icon.setImageResource(R.drawable.blue_oval_border);
        }else {
            holder.icon.setImageResource(R.drawable.gray_oval_border);
        }

        holder.textView.setText("快件到达 配送网点" + ++position);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            textView = itemView.findViewById(R.id.tvPlace);
        }
    }
}
