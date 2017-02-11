package com.beanu.l3_login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.beanu.arad.base.ToolBarActivity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * 注册第三步,设置头像和昵称
 */
public class Register3Activity extends ToolBarActivity implements View.OnClickListener {

    ImageView mImgRegisterAvatar;
    EditText mEditRegisterNickname;
    Button mBtnRegisterComplete;

    private String phone, password, yzm, nickname;
    private String imgPath;

//    private Subscriber<User> mSubscriber;

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


//        mSubscriber = new Subscriber<User>() {
//            @Override
//            public void onCompleted() {
//
//                Intent intent = new Intent(Register3Activity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(User user) {
//
//                AppHolder.getInstance().setUser(user);
//                //保存到本地
//
//                Arad.preferences.putString(Constants.P_Name, user.getLogin_value());
//                Arad.preferences.putString(Constants.P_Password, password);
//                Arad.preferences.putString(Constants.P_User_Id, user.getUser_id());
//                Arad.preferences.putBoolean(Constants.P_ISFIRSTLOAD, false);
//                Arad.preferences.flush();
//            }
//        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_register_avatar:
                showImageSelector();
                break;
            case R.id.btn_register_complete:

                nickname = mEditRegisterNickname.getText().toString();
                registerComplete();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//
//            // Get Image Path List
//            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
//
//            if (pathList != null && pathList.size() > 0) {
//                imgPath = pathList.get(0);
//                Glide.with(this)
//                        .load(imgPath)
//                        .centerCrop()
//                        .into(mImgRegisterAvatar);
//            }
//
//
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mSubscriber != null && !mSubscriber.isUnsubscribed())
//            mSubscriber.unsubscribe();
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


    private void showImageSelector() {

//        ImageConfig imageConfig
//                = new ImageConfig.Builder(new GlideLoader())
//                // 如果在 4.4 以上，则修改状态栏颜色 （默认黑色）
//                .steepToolBarColor(getResources().getColor(R.color.blue))
//                // 标题的背景颜色 （默认黑色）
//                .titleBgColor(getResources().getColor(R.color.blue))
//                // 提交按钮字体的颜色  （默认白色）
//                .titleSubmitTextColor(getResources().getColor(R.color.white))
//                // 标题颜色 （默认白色）
//                .titleTextColor(getResources().getColor(R.color.white))
//                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
//                .crop(1, 1, 200, 200)
//                // 开启单选   （默认为多选）
//                .singleSelect()
//                // 开启拍照功能 （默认关闭）
//                .showCamera()
//                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
//                .filePath("/ImageSelector/Pictures")
//                .build();
//
//        ImageSelector.open(this, imageConfig);   // 开启图片选择器
    }

    //    业务
    private void registerComplete() {
        if (TextUtils.isEmpty(imgPath)) {
//            register(null).subscribe(mSubscriber);
        } else {
            uploadAvatar();
        }
    }

    //上传头像
    private void uploadAvatar() {

        File file = new File(imgPath);
        RequestBody body = RequestBody.create(MediaType.parse("file/*"), file);
//        APIFactory.getInstance()
//                .uploadAvatar(body)
//                .flatMap(new Func1<SimpleModel, Observable<User>>() {
//                    @Override
//                    public Observable<User> call(SimpleModel simpleModel) {
//                        return register(simpleModel.url);
//                    }
//                })
//                .subscribe(mSubscriber);
    }

    //注册
//    private Observable<User> register(String avatar) {
//        return APIFactory.getInstance().register(phone, password, yzm, nickname, avatar);
//    }

}
