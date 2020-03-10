package com.beanu.l3_login.ui;

import android.content.Intent;
import android.os.Bundle;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l3_login.R;
import com.beanu.l3_login.SignIn2Activity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class RegisterActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    @Override
    public void initTopBar(QMUITopBarLayout topBarLayout) {
        topBarLayout.setTitle("注册");
        topBarLayout.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, SignIn2Activity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
