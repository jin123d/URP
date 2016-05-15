package com.jin123d.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jin123d.models.CjModels;
import com.jin123d.urp.R;

import java.util.List;

/**
 * Created by jin123d on 2015/9/14.
 */
public class CjAdapter extends BaseAdapter {
    private List<CjModels> lists;
    private Context context;

    public CjAdapter(List<CjModels> lists, Context context) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_cj, null);
            viewHolder.tv_kcm = (TextView) convertView.findViewById(R.id.tv_kcm);
            viewHolder.tv_xf = (TextView) convertView.findViewById(R.id.tv_xf);
            viewHolder.tv_cj = (TextView) convertView.findViewById(R.id.tv_cj);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_kcm.setText(lists.get(position).getKcm());
        viewHolder.tv_cj.setText(lists.get(position).getCj());
        viewHolder.tv_xf.setText(lists.get(position).getXf());
        return convertView;
    }

    private static class ViewHolder {
        TextView tv_kcm;
        TextView tv_xf;
        TextView tv_cj;
    }
}
