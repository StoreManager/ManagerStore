package com.cottee.managerstore.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.ManageFoodDetail1Activity;
import com.cottee.managerstore.activity1.emp_login.EmpDetailMessageActivity;
import com.cottee.managerstore.bean.FoodDetail;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.handle.oss_handler.OssHandler;
import com.cottee.managerstore.manage.ProjectTypeDetailManager;
import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.utils.myt_oss.ConfigOfOssClient;
import com.cottee.managerstore.utils.myt_oss.DownloadUtils;
import com.cottee.managerstore.view.SaleFoodDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017-12-21.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.RecVH> {
    public List<FoodDetail.ItemListBean> products;
    private ProjectTypeDetailManager detailManager;
    private String discount_sing="1";
    private String discount_sale="0";
    public int mEditMode = View.GONE;
    private MyItemClickListener myItemClickListener;
    private Context context;

    //构造方法传入数据
    public RecyclerviewAdapter(Context context, List<FoodDetail.ItemListBean> products
            , MyItemClickListener myItemClickListener) {
        this.context = context;
        this.products = products;
        this.myItemClickListener = myItemClickListener;
    }

    //刷新布局
    public void notifyAdapter( List<FoodDetail.ItemListBean> products, boolean isAdd) {
        if (!isAdd) {
            this.products = products;
        } else {
            this.products.addAll(products);
        }
        notifyDataSetChanged();
    }

    public List<FoodDetail.ItemListBean>  getFoodDetailList() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    //创造ViewHolder
    @Override
    public RecVH onCreateViewHolder(ViewGroup parent, int viewType) {
        //把item的Layout转化成View传给ViewHolder
        detailManager=new ProjectTypeDetailManager(context,new LoginRegisterInformationHandle());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_detail, parent, false);
        return new RecVH(view);
    }
    //  将数据放入相应的位置f
    @Override
    public void onBindViewHolder(final RecVH holder, final int position) {
        final OssHandler ossHandler = new OssHandler(context,holder.ivPic);
        final FoodDetail.ItemListBean itemListBean = products.get(holder.getAdapterPosition());
        final String name = itemListBean.getName();
        final String class_id = itemListBean.getClass_id();
        final String item_id = itemListBean.getItem_id();
        final String univalence = itemListBean.getUnivalence();
        final String description = itemListBean.getDescription();
        final String discount = itemListBean.getDiscount();
        final String discount_singg= itemListBean.getDiscount_singe();
        final String photo =itemListBean.getPhoto();
        final File cache_image = new File(context.getCacheDir()
                , Base64.encodeToString(photo.getBytes(), Base64.DEFAULT));
        DownloadUtils.downloadFileFromOss(cache_image, ossHandler
                , ConfigOfOssClient.BUCKET_NAME, photo);
        holder.tvTitle.setText(name);
        holder.tv_price.setText(univalence);
        holder.tv_description.setText(description);
//        if (mEditMode == MYLIVE_MODE_CHECK) {
//            holder.img_checkBox.setVisibility(View.GONE);
//        } else {
//            holder.img_checkBox.setVisibility(View.VISIBLE);
//
//            if (itemListBean.isSelect()) {
//                holder.img_checkBox.setImageResource(R.mipmap.check);
//            } else {
//                holder.img_checkBox.setImageResource(R.mipmap.uncheck);
//            }
//        }
        if (mEditMode == View.GONE) {
//            holder.img_checkBox.setVisibility(View.GONE);
        } else {
            holder.img_checkBox.setVisibility(View.VISIBLE);

            if (itemListBean.isSelect()) {
                holder.img_checkBox.setImageResource(R.mipmap.check);
            } else {
                holder.img_checkBox.setImageResource(R.mipmap.uncheck);
            }

        }
        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.vip_sale:
                                discount_sing="1";
                                discount_sale="-1";
                                holder.tv_sale_detail_type.setText("会员折扣");
                                detailManager.projectDetailManageUpdate(name,discount_sing,discount_sale,class_id,item_id
                                        ,univalence,description,photo);
                                break;
                            case R.id.myself_sale:
                                discount_sing="1";
                                final EditText editText = new EditText(context);
                                new AlertDialog.Builder(context)
                                        .setTitle("请输入具体折扣")
                                        .setView(editText)
                                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick
                                                    (DialogInterface dialog,
                                                     int which) {
                                                holder.tv_sale_detail_type.setText("自定义折扣");
                                                detailManager.projectDetailManageUpdate(name,discount_sing,editText.getText()
                                                        .toString(),class_id,item_id,univalence,description,photo);
                                            }
                                        }).setNegativeButton("再想想",null)
                                        .show();
                                break;
                            case R.id.cancel_sale:
                                holder.tv_sale_detail_type.setText("无折扣");
                                detailManager.projectDetailManageUpdate(name,discount_sing,discount_sale,class_id,item_id
                                        ,univalence,description,photo);
                                break;
                        }
                        return false;

                    }

                });
                inflater.inflate(R.menu.popupmenu_sale,popupMenu.getMenu());
                popupMenu.show();
            }
        });
              if (discount_singg.equals("1")&&!discount.equals("-1"))
          {
            for (int i=0;i<products.size();i++)
            {
                holder.tv_sale_detail_type.setText("自定义折扣");
            }
         }
            if (discount_singg.equals("0")&&discount.equals("1"))
           {
            for (int i=0;i<products.size();i++)
            {
                holder.tv_sale_detail_type.setText("无折扣");
            }
            }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemClickListener != null) {
                    myItemClickListener.onItemClick(itemListBean,holder.getAdapterPosition());
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    //ViewHolder绑定控件
    public class RecVH extends RecyclerView.ViewHolder {
        private final ImageView ivPic;
       private final TextView tvTitle;
        private final TextView tv_price;
        private final TextView tv_description;
        private final ImageView img_checkBox;
//        private final TextView tv_discount;
        private final FrameLayout frame;
        private final TextView tv_sale_detail_type;

        public RecVH(View itemView) {
            super(itemView);
            img_checkBox = itemView.findViewById(R.id.img_checkBox);
            ivPic = itemView.findViewById(R.id.ivPic);
            tvTitle = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_description = itemView.findViewById(R.id.tv_description);
//            tv_discount = itemView.findViewById(R.id.tv_sale);
            frame = itemView.findViewById(R.id.frame);
            tv_sale_detail_type = itemView.findViewById(R.id.tv_sale_detail_type);


        }
    }

    public interface MyItemClickListener {
        void onItemClick(FoodDetail.ItemListBean item,int position);
    }


    public void setCheckBoxVisible(int visible) {
        mEditMode = visible;
        notifyDataSetChanged();
    }
}