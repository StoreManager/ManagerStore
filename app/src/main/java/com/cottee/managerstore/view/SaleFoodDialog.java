package com.cottee.managerstore.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cottee.managerstore.R;

import java.util.SimpleTimeZone;

/**
 * Created by Administrator on 2018-03-13.
 */

public class SaleFoodDialog extends Dialog {

    private RadioGroup radioGroup_sale_id;
    private RadioButton sale_myself;
    private RadioButton sale_vip;
    private EditText ed_sale_edit;
    private Button btn_sale_cancel;
    private Button btn_sale_ok;
    private RadioButton sale_cancel;
    private int saleType=0;
    private  int SALE=1;
    private  int UNSALE=0;
    private String isSALE;
    private String  SALEMYSELF="myself";
    private String SALEVIP="vip";
    private String SALECANCEL="cancel";
    private String sale;
    private OnEditInputFinishedListener mListener; //接口

    public interface OnEditInputFinishedListener{
        void editInputFinished(String sale);
    }
    public SaleFoodDialog(@NonNull Context context,OnEditInputFinishedListener mListener) {
        super(context);
        this.mListener=mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog_salefood);
        initView();
        initEvent();
    }

    private void initView() {
        radioGroup_sale_id = findViewById(R.id.radioGroup_sale_id);
        sale_myself = findViewById(R.id.sale_myself);
        sale_vip = findViewById(R.id.sale_vip);
        ed_sale_edit = findViewById(R.id.ed_sale_edit);
        btn_sale_cancel = findViewById(R.id.btn_sale_cancel);
        btn_sale_ok = findViewById(R.id.btn_sale_ok);
        sale_cancel = findViewById(R.id.sale_cancel);
    }
    private void initEvent()
    {
        radioGroup_sale_id.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {



            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.sale_myself:
                        ed_sale_edit.setVisibility(View.VISIBLE);
                        saleType=SALE;
                        isSALE=SALEMYSELF;
                        break;
                    case R.id.sale_vip:
                        isSALE=SALEVIP;
                        saleType=SALE;
                        ed_sale_edit.setVisibility(View.GONE);
                        break;
                    case R.id.sale_cancel:
                        isSALE=SALECANCEL;
                        saleType=UNSALE;
                        ed_sale_edit.setVisibility(View.GONE);
                        break;
                }
            }
        });
        btn_sale_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sale = ed_sale_edit.getText().toString();
               if (isSALE=="myself")
               {
                if (sale.isEmpty())
                {
                    Toast.makeText(getContext(), "请输入折扣！", Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                     mListener.editInputFinished(sale);
//                    Toast.makeText(getContext(), "myself", Toast.LENGTH_SHORT)
//                            .show();
                }

               }
               else if (isSALE=="vip")
               {
                   Toast.makeText(getContext(), "vip", Toast.LENGTH_SHORT)
                           .show();
               }
               else if (isSALE=="cancel")
               {
                   Toast.makeText(getContext(), "cancel", Toast.LENGTH_SHORT)
                           .show();
               }
               dismiss();
            }
        });
    btn_sale_cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dismiss();
    }
});
    }


}
