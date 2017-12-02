package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.cottee.managerstore.R;


/**
 * Created by Administrator on 2017/11/19.
 */

public class RegisterFinishActivity extends Activity {

    private Button btbacklogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_finish);
        btbacklogin = (Button) findViewById(R.id.bt_backLogin);

        btbacklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterFinishActivity.this,
                        BossLoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
    }
}
