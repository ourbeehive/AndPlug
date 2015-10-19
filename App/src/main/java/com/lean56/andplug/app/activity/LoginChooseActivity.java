package com.lean56.andplug.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.lean56.andplug.activity.BaseActivity;
import com.lean56.andplug.app.R;

/**
 * Login Choose offers
 * user to choose guest login or with an account
 *
 * @author Charles
 */
public class LoginChooseActivity extends BaseActivity {

    // UI references.
    private Button mLoginGuestBtn;
    private Button mLoginAccountBtn;

    @Override
    protected int getContentView() {
        return R.layout.login_choose;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        mLoginGuestBtn = (Button) findViewById(R.id.btn_login_guest);
        mLoginGuestBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loginGuest();
            }
        });

        mLoginAccountBtn = (Button) findViewById(R.id.btn_login_account);
        mLoginAccountBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAccount();
            }
        });
    }

    private void loginGuest() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void loginAccount() {
        startActivity(new Intent(this, LoginActivity.class));
    }

}



