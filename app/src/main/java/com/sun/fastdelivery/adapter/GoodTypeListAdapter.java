package com.sun.fastdelivery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.GoodTypeListBean;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/29.
 */

public class GoodTypeListAdapter extends BaseAdapter {

    private Context mContext;
    private List<GoodTypeListBean> mData;
    private int lastSelectedPos = 0;

    public GoodTypeListAdapter(Context mContext, List<GoodTypeListBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        if (mData != null){
            return mData.size();
        }
        return 0;
    }

    @Override
    public GoodTypeListBean getItem(int position) {
        if (mData != null){
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.good_type_list_item, null);
        TextView mTvType = convertView.findViewById(R.id.tvType);
        ImageView mIvType = convertView.findViewById(R.id.ivType);
        GoodTypeListBean item = getItem(position);
        mTvType.setText(item.getType().text);
        mIvType.setImageResource(item.getPicRes());
        mIvType.setSelected(item.isSelected());
        return convertView;
    }

    /**
     * 更新选中状态
     * @param pos
     */
    public void updateItemSelectStatus(int pos){
        getItem(lastSelectedPos).setSelected(false);
        getItem(pos).setSelected(true);
        lastSelectedPos = pos;
        notifyDataSetChanged();
    }
}
