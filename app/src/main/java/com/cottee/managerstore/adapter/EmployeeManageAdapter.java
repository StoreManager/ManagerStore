package com.cottee.managerstore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cottee.managerstore.R;

/**
 * Created by Administrator on 2018/1/4.
 */

public class EmployeeManageAdapter extends BaseAdapter {
    private Context context;

    public EmployeeManageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
             view = view.inflate(context, R.layout.item_employee_manage, null);
            view.setTag(view);
        }else {
            view.getTag();
        }



        return view;
    }
}
