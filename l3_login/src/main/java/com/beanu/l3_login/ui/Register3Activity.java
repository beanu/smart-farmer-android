package com.beanu.l3_login.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.support.log.KLog;
import com.beanu.l2_imageselector.GlideLoader;
import com.beanu.l3_login.R;
import com.beanu.l3_login.mvp.contract.RegisterContract;
import com.beanu.l3_login.mvp.model.RegisterModelImpl;
import com.beanu.l3_login.mvp.presenter.RegisterPresenterImpl;
import com.bumptech.glide.Glide;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;


/**
 * 注册第三步,设置头像和昵称
 */
public class Register3Activity extends ToolBarActivity<RegisterPresenterImpl, RegisterModelImpl> implements View.OnClickListener, RegisterContract.View {

    ImageView mImgRegisterAvatar;
    EditText mEditRegisterNickname;
    Button mBtnRegisterComplete;

    private String phone, password, yzm, nickname;
    private String imgPath;

    public static final int REQUEST_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        mImgRegisterAvatar = (ImageView) findViewById(R.id.img_register_avatar);
        mEditRegisterNickname = (EditText) findViewById(R.id.edit_register_nickname);
        mBtnRegisterComplete = (Button) findViewById(R.id.btn_register_complete);

        mImgRegisterAvatar.setOnClickListener(this);
        mBtnRegisterComplete.setOnClickListener(this);

        phone = getIntent().getStringExtra("phone");
        password = getIntent().getStringExtra("password");
        yzm = getIntent().getStringExtra("yzm");

        mEditRegisterNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    mBtnRegisterComplete.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.img_register_avatar) {
            showImageSelector();

        } else if (i == R.id.btn_register_complete) {
            nickname = mEditRegisterNickname.getText().toString();

            mPresenter.register(phone, password, yzm, nickname);


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (pathList != null && pathList.size() > 0) {
                imgPath = pathList.get(0);
                Glide.with(this).load(imgPath).centerCrop().into(mImgRegisterAvatar);
                //上传到服务器
                mPresenter.uploadAvatar(imgPath);
            }
        }

    }


    @Override
    public String setupToolBarTitle() {
        return "设置信息";
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


    //打开图片选择器
    private void showImageSelector() {

        ImgSelConfig config = new ImgSelConfig.Builder(this, new GlideLoader())
                // 是否多选, 默认true
                .multiSelect(false)

                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(R.drawable.ic_back)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#3F51B5"))

                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                .build();

        // 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }


    @Override
    public void registerSuccess() {
        //注册成功
        KLog.d("注册成功3");
    }

    @Override
    public void registerFail(String msg) {
        //注册失败
    }

    @Override
    public void obtainSMS(String smsCode) {
        //nothing
    }

}
