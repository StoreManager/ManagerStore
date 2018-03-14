package com.cottee.managerstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.FoodDetail;
import com.cottee.managerstore.utils.OssUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017-12-21.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.RecVH> {
    public List<FoodDetail> products;
    private List<String > saleList;
    public List<FoodDetail.ItemListBean> itemList=new ArrayList<>();
    public  int mEditMode = View.GONE;
    private MyItemClickListener myItemClickListener;
    private Context context;
    //构造方法传入数据
    public RecyclerviewAdapter(Context context,List<FoodDetail >products,List<String > saleList, MyItemClickListener myItemClickListener){
        this.context=context;
        this.products=products;
        this.saleList=saleList;
        this.myItemClickListener=myItemClickListener;
    }
    //刷新布局
    public void notifyAdapter(List<FoodDetail >products, boolean isAdd) {
        if (!isAdd) {
            this.products = products;
        } else {
            this.products.addAll(products);
        }
        notifyDataSetChanged();
    }
    public List<FoodDetail> getFoodDetailList() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    //创造ViewHolder
    @Override
    public RecVH onCreateViewHolder(ViewGroup parent, int viewType) {
        //把item的Layout转化成View传给ViewHolder
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyler,parent,false);
        return new RecVH(view);
    }
    //  将数据放入相应的位置f
    @Override
    public void onBindViewHolder(final RecVH holder, int position) {
        final FoodDetail foodDetail= products.get(holder.getAdapterPosition());
        String photo = products.get(position).getItem_list().get(position)
                .getPhoto();
        if (photo!=null)
        {
            OssUtils.downImagefromOss(context, photo);
            Glide.with(context)
                    .load(photo)
                    .into(holder.ivPic);
        }
        holder.tvTitle.setText(products.get(position).getItem_list().get(position).getName());
        holder.tv_price.setText(products.get(position).getItem_list().get(position).getUnivalence());
        holder.tv_sale_price.setText(saleList.get(position));
        holder.tv_description.setText(products.get(position).getItem_list().get(position).getDescription());


        if (mEditMode==View.GONE){
            holder.img_checkBox.setVisibility(View.GONE);
        }
        else{
            holder.img_checkBox.setVisibility(View.VISIBLE);
            if (foodDetail.isSelect())
            {
                holder.img_checkBox.setImageResource(R.mipmap.check);
            }
            else {
                holder.img_checkBox.setImageResource(R.mipmap.uncheck);
            }

        }

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    //ViewHolder绑定控件
    public  class RecVH extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivPic;
        TextView tvTitle;
        private final TextView tv_price;
        private final TextView tv_description;
        private final ImageView img_checkBox;
        private final TextView tv_sale_price;

        public  RecVH(View itemView) {
            super(itemView);
            tv_sale_price=itemView.findViewById(R.id.tv_sale_price);
            img_checkBox = itemView.findViewById(R.id.img_checkBox);
            ivPic=  itemView.findViewById(R.id.ivPic);
            tvTitle= itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_description = itemView.findViewById(R.id.tv_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (myItemClickListener!=null)
            {
                myItemClickListener.onItemClick(getAdapterPosition(),products);
            }
        }
    }
    public interface MyItemClickListener{
        void onItemClick(int position, List<FoodDetail> foodDetailList);
    }


    public void setCheckBoxVisible(int visible){
        mEditMode=visible;
        notifyDataSetChanged();
    }
}