package com.cottee.managerstore.adapter;

import android.content.Context;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.handle.oss_handler.OssHandler;
import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.utils.myt_oss.ConfigOfOssClient;
import com.cottee.managerstore.utils.myt_oss.DownloadUtils;
import com.cottee.managerstore.view.ImageViewExtend;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class EmployeeManageAdapter extends BaseAdapter {
    private Context context;
    private List<String> empName;
    private List<String> empId;
    private List<String> empPhoto;
    private ViewHolder holder;
    private File cache_image;

    public EmployeeManageAdapter(Context context, List<String> empName,List<String> empId,List<String> empPhoto) {
        this.context = context;
        this.empId = empId;
        this.empName = empName;
        this.empPhoto = empPhoto;
    }
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
            holder.imv_employee_header = view.findViewById(R.id.imv_employee_header);
            holder.imv_employee_header.setmDrawShapeType(ImageViewExtend.SHAPE_CIRCLE);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_emp_name.setText(empName.get(i));
        holder.tv_emp_id.setText(empId.get(i));
        OssHandler ossHandler = new OssHandler(context,holder.imv_employee_header);
        cache_image = new File(context.getCacheDir(), Base64.encodeToString(empPhoto.get(i).getBytes(), Base64.DEFAULT));
        System.out.println("传进来的objectKey："+empPhoto.get(i));
        System.out.println("传进来的缓存："+cache_image);
        if(empPhoto.get(i).equals("")){
            holder.imv_employee_header.setImageResource(R.mipmap.emp_header);
        }
        if(cache_image.exists()){
            ToastUtils.showToast(context,"缓存空的");
        }
        DownloadUtils.downloadFileFromOss(cache_image, ossHandler, ConfigOfOssClient.BUCKET_NAME, empPhoto.get(i));

        return view;
    }

    protected static class ViewHolder{
        TextView tv_emp_id;
        TextView tv_emp_name;
        ImageViewExtend imv_employee_header;
    }
}
