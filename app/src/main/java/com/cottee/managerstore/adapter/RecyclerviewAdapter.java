package com.cottee.managerstore.adapter;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.AddFoodActivity;
import com.cottee.managerstore.bean.FoodDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-12-21.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.RecVH> {
    public List<FoodDetail> products= AddFoodActivity.foodDetailList;

  public   int mEditMode = View.GONE;
    private MyItemClickListener myItemClickListener;
    //构造方法传入数据
    public RecyclerviewAdapter(List<FoodDetail >products, MyItemClickListener myItemClickListener){
        this.products=products;
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
    public List<FoodDetail> getProducts() {
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
    private boolean flag=false;
    public void setSelectedGone(){
        flag=true;
    }

    //  将数据放入相应的位置
    @Override
    public void onBindViewHolder(final RecVH holder, int position) {
       final FoodDetail foodDetail= products.get(holder.getAdapterPosition());
        holder.ivPic.setImageBitmap(BitmapFactory.decodeFile(products.get(position).getFoodImg()));
        holder.tvTitle.setText(products.get(position).getFoodName());
        holder.tv_price.setText(products.get(position).getPrice());
        holder.tv_description.setText(products.get(position).getDescription());

        if (mEditMode==View.GONE){
            holder.img_checkBox.setVisibility(View.GONE);
        }  else
        {
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

        public  RecVH(View itemView) {
            super(itemView);
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