package com.beanu.l3_login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.beanu.arad.base.ToolBarFragment;
import com.beanu.arad.utils.MessageUtils;
import com.beanu.l3_login.mvp.contract.RegisterSMSContract;
import com.beanu.l3_login.mvp.model.RegisterSMSModelImpl;
import com.beanu.l3_login.mvp.presenter.RegisterSMSPresenterImpl;


/**
 * 注册页面
 */
public class RegisterFragment extends ToolBarFragment<RegisterSMSPresenterImpl, RegisterSMSModelImpl> implements View.OnClickListener, RegisterSMSContract.View {


    EditText mEditRegisterPhone;
    Button mBtnRegisterSendSms;
    ImageButton mBtnRegisterWechat;


    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mEditRegisterPhone = (EditText) view.findViewById(R.id.edit_register_phone);
        mBtnRegisterSendSms = (Button) view.findViewById(R.id.btn_register_send_sms);
        mBtnRegisterWechat = (ImageButton) view.findViewById(R.id.btn_register_wechat);

        mBtnRegisterSendSms.setOnClickListener(this);
        mBtnRegisterWechat.setOnClickListener(this);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditRegisterPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 11) {
                    mBtnRegisterSendSms.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register_send_sms:

                String phone = mEditRegisterPhone.getText().toString();
                mPresenter.sendSMSCode(phone);

                break;
            case R.id.btn_register_wechat:

                break;
        }
    }


    @Override
    public void verifyPhone(boolean correct) {
        if (!correct) {
            MessageUtils.showShortToast(getActivity(), "手机号不正确");
            mEditRegisterPhone.setText("");
        }
    }

    @Override
    public void verifyCode(boolean correct) {
        if (correct) {
            Intent intent = new Intent(getActivity(), Register2Activity.class);
//            intent.putExtra("phone", phone);
//            intent.putExtra("code", smsCode.yzm);
            startActivity(intent);
        } else {

        }
    }
}
