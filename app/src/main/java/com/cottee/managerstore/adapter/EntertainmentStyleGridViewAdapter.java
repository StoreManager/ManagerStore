package com.cottee.managerstore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.EntertainmentStyle;

import java.util.List;

/**
 * Created by Administrator on 2017-12-07.
 */

public class EntertainmentStyleGridViewAdapter extends BaseAdapter {
    private Context context;
    private static List<String > entertainment;
    public EntertainmentStyleGridViewAdapter(Context context,
                                             List<String  > entertainment) {
        this.context = context;
        this.entertainment = entertainment;
    }
    @Override
    public int getCount() {
        return entertainment.size();
    }

    @Override
    public Object getItem(int i) {
        return entertainment.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder=new ViewHolder();
            view= View.inflate(context, R.layout.fragment_style,null);
            viewHolder.tv_style=  view.findViewById(R.id.tv_storestyle);
            view.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.tv_style.setText(entertainment.get(i).toString());
        return view;
    }
    protected static class ViewHolder {

        TextView tv_style;
    }

}
