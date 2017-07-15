package com.beanu.l3_login.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.utils.RegexUtils;
import com.beanu.arad.utils.ToastUtils;
import com.beanu.l3_login.R;
import com.beanu.l3_login.mvp.contract.ChangePwdContract;
import com.beanu.l3_login.mvp.model.ChangePwdModelImpl;
import com.beanu.l3_login.mvp.presenter.ChangePwdPresenterImpl;

/**
 * 找回密码
 */
public class FindPwdActivity extends ToolBarActivity<ChangePwdPresenterImpl, ChangePwdModelImpl> implements TextWatcher, View.OnClickListener, ChangePwdContract.View {

    TextView mBtnRegisterSend;
    EditText mPhone;
    EditText mCaptcha;
    EditText mPassword;
    Button mBtnRegisterNext;

    private String smsCode;
    private TimeCount timeCount;//60s倒计时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);

        mBtnRegisterSend = (TextView) findViewById(R.id.btn_register_send);
        mPhone = (EditText) findViewById(R.id.edit_register_phone);
        mCaptcha = (EditText) findViewById(R.id.edit_register_captcha);
        mPassword = (EditText) findViewById(R.id.edit_register_password);
        mBtnRegisterNext = (Button) findViewById(R.id.btn_register_next);

        mBtnRegisterSend.setOnClickListener(this);
        mBtnRegisterNext.setOnClickListener(this);

        mCaptcha.addTextChangedListener(this);
        mPassword.addTextChangedListener(this);

        timeCount = new TimeCount(60000, 1000);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String phone = mPhone.getText().toString();
        String capche = mCaptcha.getText().toString();
        String password = mPassword.getText().toString();
        if (!TextUtils.isEmpty(phone) && RegexUtils.isMobileExact(phone) && !"".equals(capche) && !"".equals(password) && capche.length() == 6 && password.length() >= 8) {
            mBtnRegisterNext.setEnabled(true);
        } else {
            mBtnRegisterNext.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.btn_register_send) {

            String phone = mPhone.getText().toString();
            if (!TextUtils.isEmpty(phone) && RegexUtils.isMobileExact(phone)) {
                mPresenter.sendSMSCode(phone);
                timeCount.start();
            } else {
                ToastUtils.showShort("手机号不正确");
            }

        } else if (i == R.id.btn_register_next) {
            String capche = mCaptcha.getText().toString();
            String password = mPassword.getText().toString();
            String phone = mPhone.getText().toString();

            if (capche.equals(smsCode)) {
                //验证码正确
                mPresenter.findPassword(phone, password, capche);
            }

        }
    }

    @Override
    public String setupToolBarTitle() {
        return "找回密码";
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
    public void obtainSMS(String smsCode) {
        this.smsCode = smsCode;
    }

    @Override
    public void findPwdSuccess() {
        ToastUtils.showShort("密码修改成功");
        onBackPressed();
    }

    //倒计时
    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //开始倒计时
        @Override
        public void onTick(long millisUntilFinished) {
            mBtnRegisterSend.setText("重新发送(" + millisUntilFinished / 1000 + "s)");
            mBtnRegisterSend.setClickable(false);
        }

        //倒计时执行完毕
        @Override
        public void onFinish() {
            mBtnRegisterSend.setText("重新发送");
            mBtnRegisterSend.setClickable(true);
        }
    }
}
