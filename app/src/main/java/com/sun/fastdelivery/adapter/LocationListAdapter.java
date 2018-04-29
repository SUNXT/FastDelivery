package com.sun.fastdelivery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.sun.fastdelivery.R;

import java.util.List;

/**
 * Created by sunxuedian on 2018/4/29.
 */

public class LocationListAdapter extends BaseAdapter {

    private List<PoiItem> mData;
    private Context mContext;

    public LocationListAdapter(Context mContext, List<PoiItem> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (mData != null){
            return mData.size();
        }
        return 0;
    }

    @Override
    public PoiItem getItem(int position) {
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
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.location_list_item, null);
            viewHolder.mTvLocation = convertView.findViewById(R.id.tvLocation);
            viewHolder.mTvSnippet = convertView.findViewById(R.id.tvSnippet);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PoiItem item = getItem(position);
        viewHolder.mTvLocation.setText(item.toString());
        viewHolder.mTvSnippet.setText(item.getSnippet());
        return convertView;
    }

    class ViewHolder{
        TextView mTvLocation;
        TextView mTvSnippet;
    }
}
