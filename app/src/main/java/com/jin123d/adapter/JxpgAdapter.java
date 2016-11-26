package com.jin123d.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jin123d.models.JxpgModels;
import com.jin123d.urp.R;

import java.util.List;

/**
 * Created by jin123d on 2015/11/7.
 **/
public class JxpgAdapter extends BaseAdapter {

    private List<JxpgModels> lists;
    private Context context;

    public JxpgAdapter(Context context, List<JxpgModels> lists) {
        this.context = context;
        this.lists = lists;
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
            convertView = inflater.inflate(R.layout.layout_jxpg, null);
            viewHolder.tv_pgnr = (TextView) convertView.findViewById(R.id.tv_kcm);
            viewHolder.tv_phr = (TextView) convertView.findViewById(R.id.tv_xf);
            viewHolder.tv_sfpg = (TextView) convertView.findViewById(R.id.tv_cj);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_pgnr.setText(lists.get(position).getPgnr());
        viewHolder.tv_phr.setText(lists.get(position).getBprm());
        viewHolder.tv_sfpg.setText(lists.get(position).getSfpg());
        return convertView;
    }

    private static class ViewHolder {
        TextView tv_pgnr;
        TextView tv_phr;
        TextView tv_sfpg;
    }
}
