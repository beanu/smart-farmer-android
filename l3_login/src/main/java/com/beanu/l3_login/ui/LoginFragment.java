package com.beanu.l3_login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.base.ToolBarFragment;
import com.beanu.arad.utils.ToastUtils;
import com.beanu.l2_shareutil.LoginUtil;
import com.beanu.l2_shareutil.login.LoginListener;
import com.beanu.l2_shareutil.login.LoginPlatform;
import com.beanu.l2_shareutil.login.LoginResult;
import com.beanu.l3_common.util.Constants;
import com.beanu.l3_login.R;
import com.beanu.l3_login.mvp.contract.LoginContract;
import com.beanu.l3_login.mvp.model.LoginModelImpl;
import com.beanu.l3_login.mvp.presenter.LoginPresenterImpl;


/**
 * 登录页面
 */
public class LoginFragment extends ToolBarFragment<LoginPresenterImpl, LoginModelImpl> implements TextWatcher, View.OnClickListener, LoginContract.View {

    EditText mEditLoginPhone;
    EditText mEditLoginPassword;
    Button mBtnLoginLogin;
    TextView mTxtLoginForget;
    ImageButton mBtnLoginWeChat;
    ImageButton mBtnLoginQQ;

    //第三方登录监听
    final LoginListener mLoginListener = new LoginListener() {
        @Override
        public void loginSuccess(LoginResult result) {
            //登录成功， 如果你选择了获取用户信息，可以通过

            String loginType = "0";
            if (result.getPlatform() == LoginPlatform.QQ) {
                loginType = "1";
            } else if (result.getPlatform() == LoginPlatform.WX) {
                loginType = "2";
            }
            hideProgress();


            showProgress();
            Arad.preferences.putString(Constants.P_LOGIN_OPENID, result.getToken().getOpenid());
            Arad.preferences.flush();

            mPresenter.login(null, null, loginType, result.getToken().getOpenid(), result.getUserInfo().getSex(), result.getUserInfo().getHeadImageUrl(), result.getUserInfo().getNickname());
        }

        @Override
        public void loginFailure(Exception e) {
            Log.i("TAG", "登录失败");

            hideProgress();
        }

        @Override
        public void loginCancel() {

            hideProgress();
            Log.i("TAG", "登录取消");
        }
    };

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
        mBtnLoginQQ = (ImageButton) view.findViewById(R.id.btn_login_QQ);

        mBtnLoginLogin.setOnClickListener(this);
        mTxtLoginForget.setOnClickListener(this);
        mBtnLoginWeChat.setOnClickListener(this);
        mBtnLoginQQ.setOnClickListener(this);


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
        int i = view.getId();
        if (i == R.id.btn_login_login) {
            String phone = mEditLoginPhone.getText().toString();
            String password = mEditLoginPassword.getText().toString();
            mPresenter.login(phone, password, "0", null, 0, null, null);


        } else if (i == R.id.txt_login_forget) {
            Intent intent = new Intent(getActivity(), FindPwdActivity.class);
            startActivity(intent);
        } else if (i == R.id.btn_login_weChat) {
            showProgress();
            LoginUtil.login(getActivity(), LoginPlatform.WX, mLoginListener, true);
        } else if (i == R.id.btn_login_QQ) {
            showProgress();
            LoginUtil.login(getActivity(), LoginPlatform.QQ, mLoginListener, true);
        }
    }

    /**
     * 去首页 推荐ARouter跳转方式
     * <p>
     * 目前l3 不能依赖l4，所以去上一级，可以通过路由的方式跳转
     */
    private void gotoMain() {

//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        startActivity(intent);
//        getActivity().finish();

//        ARouter.getInstance().build("/app/main").navigation();

    }


    @Override
    public void loginSuccess() {
        hideProgress();
        gotoMain();
        getActivity().finish();

    }

    @Override
    public void loginFailed(String error) {
        hideProgress();
        ToastUtils.showShort(error);
    }
}
