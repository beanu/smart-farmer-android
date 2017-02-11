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
import android.widget.TextView;


/**
 * 登录页面
 */
public class LoginFragment extends Fragment implements TextWatcher, View.OnClickListener {


    EditText mEditLoginPhone;
    EditText mEditLoginPassword;
    Button mBtnLoginLogin;
    TextView mTxtLoginForget;
    ImageButton mBtnLoginWeChat;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mEditLoginPhone = (EditText) view.findViewById(R.id.edit_login_phone);
        mEditLoginPassword = (EditText) view.findViewById(R.id.edit_login_password);
        mBtnLoginLogin = (Button) view.findViewById(R.id.btn_login_login);
        mTxtLoginForget = (TextView) view.findViewById(R.id.txt_login_forget);
        mBtnLoginWeChat = (ImageButton) view.findViewById(R.id.btn_login_weChat);

        mBtnLoginLogin.setOnClickListener(this);
        mTxtLoginForget.setOnClickListener(this);
        mBtnLoginWeChat.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditLoginPhone.addTextChangedListener(this);
        mEditLoginPassword.addTextChangedListener(this);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String phone = mEditLoginPhone.getText().toString();
        String password = mEditLoginPassword.getText().toString();
        if (!"".equals(phone) && !"".equals(password) && phone.length() >= 5 && password.length() >= 6) {
            mBtnLoginLogin.setEnabled(true);
        }


    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login:

                String phone = mEditLoginPhone.getText().toString();
                String password = mEditLoginPassword.getText().toString();
                login(phone, password);

                break;
            case R.id.txt_login_forget:
                break;
            case R.id.btn_login_weChat:
                break;
        }
    }

    //UI
    private void gotoMain() {
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        startActivity(intent);
//        getActivity().finish();
    }

    //业务
    private void login(String phone, String password) {
//        APIFactory.getInstance().login(phone, password).subscribe(new Subscriber<User>() {
//            @Override
//            public void onCompleted() {
//                gotoMain();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onNext(User user) {
//
//                AppHolder.getInstance().setUser(user);
//                //保存到本地
//                String password = mEditLoginPassword.getText().toString();
//
//                Arad.preferences.putString(Constants.P_Name, user.getLogin_value());
//                Arad.preferences.putString(Constants.P_Password, password);
//                Arad.preferences.putString(Constants.P_User_Id, user.getUser_id());
//                Arad.preferences.putBoolean(Constants.P_ISFIRSTLOAD, false);
//                Arad.preferences.flush();
//
//
//            }
//        });
    }
}
