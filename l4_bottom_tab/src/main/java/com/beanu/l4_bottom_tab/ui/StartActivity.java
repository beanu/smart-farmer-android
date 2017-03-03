package com.beanu.l4_bottom_tab.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.AnimUtil;
import com.beanu.arad.utils.Base64Coder;
import com.beanu.l3_common.util.Constants;
import com.beanu.l4_bottom_tab.R;


/**
 * 启动页
 */
public class StartActivity extends AppCompatActivity {

    private View mContentView;

    private boolean animitorEnd, loginEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置状态栏透明状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_start);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();


        //查找View
        mContentView = findViewById(R.id.fullscreen_content);

        //执行动画行为
        playLogoAnim();

        //自动登录
        tryLogin();
    }


    // logo动画
    private void playLogoAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mContentView, "alpha", 0, 1);
        anim.setDuration(2000);
        anim.start();
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animitorEnd = true;
                gotoNextPage();
            }
        });
    }

    //进入下一页
    private void gotoNextPage() {
        if (animitorEnd && loginEnd) {
            boolean isFirst = Arad.preferences.getBoolean(Constants.P_ISFIRSTLOAD, true);
            if (!isFirst) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }

            AnimUtil.intentSlidIn(StartActivity.this);
            finish();
        }
    }

    //    业务
    private void tryLogin() {

        String phone = Arad.preferences.getString(Constants.P_ACCOUNT);
        String password = Arad.preferences.getString(Constants.P_PWD);
        password = Base64Coder.decodeString(password);

//        APIFactory.getInstance().login(phone, password).subscribe(new Subscriber<User>() {
//            @Override
//            public void onCompleted() {
//                loginEnd = true;
//                gotoNextPage();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                loginEnd = true;
//                gotoNextPage();
//            }
//
//            @Override
//            public void onNext(User user) {
//                AppHolder.getInstance().setUser(user);
//            }
//        });

        //TODO TEST
        loginEnd = true;
        gotoNextPage();
    }
}
