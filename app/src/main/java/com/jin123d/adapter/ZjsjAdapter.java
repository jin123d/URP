package com.jin123d.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jin123d.models.ZjsjModels;
import com.jin123d.urp.R;

import java.util.List;

/**
 * Created by jin123d on 2015/9/14.
 */
public class ZjsjAdapter extends BaseAdapter {
    private List<ZjsjModels> lists;
    private Context context;

    public ZjsjAdapter(List<ZjsjModels> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getCount() == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.layoutnosuch, null);
            return view;
        } else {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.layout_zjsj, null);
                viewHolder.tv_mc = (TextView) convertView.findViewById(R.id.tv_mc);
                viewHolder.tv_bz = (TextView) convertView.findViewById(R.id.tv_bz);
                viewHolder.tv_xf = (TextView) convertView.findViewById(R.id.tv_xf);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            viewHolder.tv_mc.setText(lists.get(position).getMc());
            viewHolder.tv_bz.setText(lists.get(position).getBz());
            viewHolder.tv_xf.setText(lists.get(position).getXf());
            return convertView;
        }

    }

    private static class ViewHolder {
        TextView tv_mc;
        TextView tv_bz;
        TextView tv_xf;
    }
}