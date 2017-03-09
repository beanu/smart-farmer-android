package com.beanu.l3_login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.log.KLog;
import com.beanu.arad.utils.MessageUtils;
import com.beanu.l3_login.R;
import com.beanu.l3_login.SignInMode;
import com.beanu.l3_login.mvp.contract.RegisterContract;
import com.beanu.l3_login.mvp.model.RegisterModelImpl;
import com.beanu.l3_login.mvp.presenter.RegisterPresenterImpl;

/**
 * 注册第二步,获取验证码和密码
 */
public class Register2Activity extends ToolBarActivity<RegisterPresenterImpl, RegisterModelImpl> implements TextWatcher, View.OnClickListener, RegisterContract.View {

    TextView mBtnRegisterSend;
    EditText mEditRegisterCaptcha;
    EditText mEditRegisterPassword;
    Button mBtnRegisterNext;
    TextView mTxtRegisterTips;

    private String phone, code;
    private TimeCount timeCount;//60s倒计时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mBtnRegisterSend = (TextView) findViewById(R.id.btn_register_send);
        mEditRegisterCaptcha = (EditText) findViewById(R.id.edit_register_captcha);
        mEditRegisterPassword = (EditText) findViewById(R.id.edit_register_password);
        mBtnRegisterNext = (Button) findViewById(R.id.btn_register_next);
        mTxtRegisterTips = (TextView) findViewById(R.id.txt_register_tips);

        mBtnRegisterSend.setOnClickListener(this);
        mBtnRegisterNext.setOnClickListener(this);

        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");

        mTxtRegisterTips.setText(String.format(getString(R.string.register_tips), phone));

        mEditRegisterCaptcha.addTextChangedListener(this);
        mEditRegisterPassword.addTextChangedListener(this);

        timeCount = new TimeCount(60000, 1000);
        timeCount.start();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_register_send) {

            mPresenter.sendSMSCode(phone);
            timeCount.start();

        } else if (i == R.id.btn_register_next) {
            String capche = mEditRegisterCaptcha.getText().toString();
            String password = mEditRegisterPassword.getText().toString();

            if (capche.equals(code)) {
                //验证码正确

                switch (SignInMode.MODE) {
                    case 1:
                        mPresenter.register(phone, password, capche, null);

                        break;
                    case 2:
                        Intent intent = new Intent(this, Register3Activity.class);
                        intent.putExtra("phone", phone);
                        intent.putExtra("password", password);
                        intent.putExtra("yzm", capche);

                        startActivity(intent);

                        break;
                }


            }


        }
    }

    @Override
    public String setupToolBarTitle() {
        return "设置密码";
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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        String capche = mEditRegisterCaptcha.getText().toString();
        String password = mEditRegisterPassword.getText().toString();
        if (!"".equals(capche) && !"".equals(password) && capche.length() == 6 && password.length() >= 6) {
            mBtnRegisterNext.setEnabled(true);
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void registerSuccess() {
        //注册成功 去登录页面
        KLog.d("注册成功");

    }

    @Override
    public void registerFail(String msg) {
        MessageUtils.showShortToast(this, msg);
    }

    @Override
    public void obtainSMS(String smsCode) {
        code = smsCode;
    }

    //倒计时
    class TimeCount extends CountDownTimer {
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
