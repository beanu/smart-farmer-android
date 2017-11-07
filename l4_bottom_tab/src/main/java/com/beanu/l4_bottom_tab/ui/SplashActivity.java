package com.beanu.l4_bottom_tab.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 解决 app 启动白屏黑屏的问题
 * 秒开APP
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, StartActivity.class));
        finish();
    }

}
