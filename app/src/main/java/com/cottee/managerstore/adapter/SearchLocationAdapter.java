package com.cottee.managerstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.SearchLocation;

import java.util.List;

/**
 * Created by user on 2017/11/16.
 */

public class SearchLocationAdapter extends ArrayAdapter<SearchLocation.UserBean>{
    private int resourceId;
    Context context;
    public SearchLocationAdapter(Context context, int textViewResourceId, List<SearchLocation.UserBean> objects) {
        super( context,textViewResourceId, objects );
        resourceId=textViewResourceId;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchLocation.UserBean userBean=getItem( position );
        View view= LayoutInflater.from( context ).inflate( resourceId,parent,false );
        TextView tv_searchLocationTitle = view.findViewById( R.id.tv_searchLocationTitle );
        TextView tv_searchLocationAddress = view.findViewById( R.id.tv_searchLocationAddress );
        tv_searchLocationTitle.setText( userBean.getTitle() );
        tv_searchLocationAddress.setText( userBean.getAddress() );
        return view;

    }

    //    private Context context;
//    private List<SearchLocation.UserBean> locationInfoList;
//
//
//    public SearchLocationAdapter(Context context, List<SearchLocation.UserBean> locationInfoList) {
//        this.context = context;
//        this.locationInfoList = locationInfoList;
//    }
//
//    @Override
//    public int getCount() {
//        return locationInfoList.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return locationInfoList.get( i );
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        View view;
//        ViewHolder viewHolder;
//        if(convertView==null){
//            view= View.inflate( context, R.layout.item_location,null );
//            viewHolder=new ViewHolder();
//            viewHolder.locationTitle=view.findViewById( R.id.tv_searchLocationTitle );
//            viewHolder.locationAddress=view.findViewById( R.id.tv_searchLocationAddress );
//            view.setTag( viewHolder );
//        }else {
//            view=convertView;
//            viewHolder= (ViewHolder) view.getTag();
//        }
//        viewHolder.locationTitle.setText( locationInfoList.get( i ).getTitle() );
//        viewHolder.locationAddress.setText( locationInfoList.get( i ).getAddress() );
//
//        return view;
//    }
//
//    class ViewHolder{
//        TextView locationTitle;
//        TextView locationAddress;
//    }
}
