package com.beanu.l3_login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l3_login.ui.LoginActivity;
import com.beanu.l3_login.ui.RegisterActivity;

public class SignIn2Activity extends ToolBarActivity implements View.OnClickListener {

    Button btn_login;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_sign_in2);

        btn_login = (Button) findViewById(R.id.btn_to_login);
        btn_register = (Button) findViewById(R.id.btn_to_register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_to_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_to_register) {

            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

    }
}
