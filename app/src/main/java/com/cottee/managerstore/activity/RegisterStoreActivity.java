package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.StoreListviewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RegisterStoreActivity extends Activity {

    private ListView lv_registerStore;

    private StoreListviewAdapter storeListviewAdapter;
    private List<String> storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registerstore);
        lv_registerStore = findViewById(R.id.lv_registerStore);
        storeList = new ArrayList<>();
         for (int i=0;i<2;i++)
         {
             storeList.add("管理"+i);
         }

        storeListviewAdapter = new StoreListviewAdapter(this, storeList);
        lv_registerStore.setAdapter(storeListviewAdapter);


    }
    public void addRregisterStore(View view)
    {
        Intent intent = new Intent(this,RegisterStoreInfoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
}
    