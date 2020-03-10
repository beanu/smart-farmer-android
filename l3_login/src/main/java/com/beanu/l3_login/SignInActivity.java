package com.beanu.l3_login;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l3_login.ui.LoginFragment;
import com.beanu.l3_login.ui.RegisterFragment;
import com.beanu.l3_login.widget.ViewPagerIndicator;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * 登录页面
 */
@Route(path = "/login/signIn1")
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
    public void initTopBar(QMUITopBarLayout topBarLayout) {
        topBarLayout.setTitle("登录");
        topBarLayout.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
    }


    @Override
    public void onClick(View view) {

    }

    private static class LoginRegisterAdapter extends FragmentPagerAdapter {

        LoginRegisterAdapter(FragmentManager fm) {
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
