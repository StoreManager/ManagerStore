package com.cottee.managerstore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cottee.managerstore.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class EmployeeManageAdapter extends BaseAdapter {
    private Context context;
    private List<String> empName;
    private List<String> empId;
    private ViewHolder holder;

    public EmployeeManageAdapter(Context context, List<String> empName,List<String> empId) {
        this.context = context;
        this.empId = empId;
        this.empName = empName;
    }

    @Override
    public int getCount() {
        return empId.size();
    }

    @Override
    public Object getItem(int i) {
        return empId.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_employee_manage, null);
            holder.tv_emp_id= view.findViewById(R.id.tv_emp_id);
            holder.tv_emp_name=view.findViewById(R.id.tv_emp_name);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_emp_name.setText(empName.get(i));
        holder.tv_emp_id.setText(empId.get(i));


        return view;
    }

    protected static class ViewHolder{
        TextView tv_emp_id;
        TextView tv_emp_name;
    }
}
