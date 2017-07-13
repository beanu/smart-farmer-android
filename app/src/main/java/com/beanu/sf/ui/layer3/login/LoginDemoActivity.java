package com.beanu.sf.ui.layer3.login;

import android.os.Bundle;
import android.view.View;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l3_login.SignIn2Activity;
import com.beanu.l3_login.SignInActivity;
import com.beanu.sf.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginDemoActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login_1, R.id.btn_login_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_1:
                startActivity(SignInActivity.class);
                break;
            case R.id.btn_login_2:
                startActivity(SignIn2Activity.class);
                break;
        }
    }
}
