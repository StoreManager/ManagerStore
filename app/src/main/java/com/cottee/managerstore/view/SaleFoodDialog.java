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

    private RadioButton sale_myself;
    private RadioButton sale_vip;
    private EditText ed_sale_edit;
    private Button btn_sale_cancel;
    private Button btn_sale_ok;
    private int saleType=0;
    private  int SALE=1;
    private  int UNSALE=0;
    private String isSALE;
    private String  SALEMYSELF="myself";
    private String SALEVIP="vip";
    private String SALECANCEL="cancel";
    private String sale;
    private OnEditInputFinishedListener mListener; //接口
    private String  discount="1";//打折折扣
    private String  discount_sing="0";//sale sign
    private String sale_type="";
    public interface OnEditInputFinishedListener{
        void editInputFinished(String discount);
    }
    public SaleFoodDialog(Context context,OnEditInputFinishedListener mListener) {
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

        ed_sale_edit = findViewById(R.id.ed_sale_edit);
        btn_sale_cancel = findViewById(R.id.btn_sale_cancel);
        btn_sale_ok = findViewById(R.id.btn_sale_ok);
    }
    private void initEvent()
    {
        btn_sale_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sale = ed_sale_edit.getText().toString();
             mListener.editInputFinished(sale);
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
