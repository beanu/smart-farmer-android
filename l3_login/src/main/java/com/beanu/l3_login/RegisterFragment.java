package com.beanu.l3_login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * 注册页面
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {


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
                if (charSequence.length() >= 5) {
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
                sendSms(phone);

                break;
            case R.id.btn_register_wechat:

                break;
        }
    }


    private void enableButtonLoading() {

    }

    private void resetButton() {

    }


    //   业务层
    private void sendSms(final String phone) {
//
//        APIFactory.getInstance().sendSMSCode(phone, "yes").subscribe(new Subscriber<SMSCode>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onNext(SMSCode smsCode) {
//
//                Intent intent = new Intent(getActivity(), Register2Activity.class);
//                intent.putExtra("phone", phone);
//                intent.putExtra("code", smsCode.yzm);
//                startActivity(intent);
//            }
//        });
    }

}
