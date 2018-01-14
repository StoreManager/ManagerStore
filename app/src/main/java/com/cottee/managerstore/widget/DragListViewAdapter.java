package com.cottee.managerstore.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Ralap on 2016/5/10.
 */
public abstract class DragListViewAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> projectManageList;

    public DragListViewAdapter(Context context, List<T> dataList){
        this.mContext = context;
        this.projectManageList = dataList;
    }

    @Override
    public int getCount() {
        return projectManageList == null ? 0 : projectManageList.size();
    }

    @Override
    public T getItem(int position) {
        return projectManageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(position, convertView, parent);
    }

    public abstract View getItemView(int position, View convertView, ViewGroup parent);

    public void swapData(int from, int to){
        Collections.swap(projectManageList, from, to);
        notifyDataSetChanged();
    }

    public void deleteData(int index) {
        projectManageList.remove(index);
        notifyDataSetChanged();
    }

    public void addData(int location, T data) {
        projectManageList.add(location, data);
        notifyDataSetChanged();
    }

    public void setDataList(List<T> dataList) {
        projectManageList = dataList;
        notifyDataSetChanged();
    }

    public List<T> getDataList(){
        return projectManageList;
    }
}
