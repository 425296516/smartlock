package com.anlida.smartlock.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.anlida.smartlock.MainActivity;
import com.anlida.smartlock.R;
import com.anlida.smartlock.ui.user.LoginActivity;
import com.anlida.smartlock.utils.DataWarehouse;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               /* if(TextUtils.isEmpty(DataWarehouse.getToken())){
                    LoginActivity.start(WelcomeActivity.this);
                }else{*/
                //MainActivity.start(WelcomeActivity.this);
                if (DataWarehouse.isLogin()) {
                    MainActivity.start(WelcomeActivity.this);
                    //startActivity(new Intent(WelcomeActivity.this, AuthenticActivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 1000);

    }
}
