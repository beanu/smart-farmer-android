package com.beanu.l3_login;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l3_login.ui.LoginFragment;
import com.beanu.l3_login.ui.RegisterFragment;
import com.beanu.l3_login.widget.ViewPagerIndicator;


/**
 * 登录页面
 */
public class SignInActivity extends ToolBarActivity implements View.OnClickListener {

    ImageView mImgLoginRegisterClose;
    ViewPagerIndicator mIndicatorLoginRegister;
    ViewPager mViewpagerLoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_sign_in);

        mImgLoginRegisterClose = (ImageView) findViewById(R.id.img_login_register_close);
        mIndicatorLoginRegister = (ViewPagerIndicator) findViewById(R.id.indicator_login_register);
        mViewpagerLoginRegister = (ViewPager) findViewById(R.id.viewpager_login_register);

        mImgLoginRegisterClose.setOnClickListener(this);

        LoginRegisterAdapter adapter = new LoginRegisterAdapter(getSupportFragmentManager());
        mViewpagerLoginRegister.setAdapter(adapter);
        mIndicatorLoginRegister.setViewPager(mViewpagerLoginRegister, 0);
    }


    @Override
    public String setupToolBarTitle() {
        return "登录";
    }

    @Override
    public boolean setupToolBarLeftButton(View leftButton) {
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        return true;
    }


    @Override
    public void onClick(View view) {

    }


    static class LoginRegisterAdapter extends FragmentPagerAdapter {

        public LoginRegisterAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return LoginFragment.newInstance();
                case 1:
                    return RegisterFragment.newInstance();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
