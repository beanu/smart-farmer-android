package io.fchain.ui;

import android.os.Bundle;
import android.view.View;

import com.beanu.arad.base.BaseActivity;
import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * 1.加入toolbar的操作
 * 2. 加入了全局的loading
 * toolbar 和 dialog 引入QMUI
 *
 * @author beanu
 */
public class QMUIActivity<T extends BasePresenter, E extends BaseModel> extends BaseActivity<T, E> implements BaseView {

    protected QMUITopBarLayout mTopBarLayout;

    private View arad_content;
    private View arad_loading;
    private View arad_loading_error;
    private View arad_loading_empty;
    private View.OnClickListener mOnRetryListener;

    protected QMUITipDialog mTipDialog;
    protected QMUITipDialog mMessageDialog;

    private boolean isDestroy;

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDestroy = false;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        mTopBarLayout = findViewById(R.id.arad_toolbar);
        arad_content = findViewById(R.id.arad_content);
        arad_loading = findViewById(R.id.arad_loading);
        arad_loading_empty = findViewById(R.id.arad_loading_empty);
        arad_loading_error = findViewById(R.id.arad_loading_error);

        if (mTopBarLayout != null) {
            mTopBarLayout.setBackgroundDividerEnabled(false);
            initTopBar(mTopBarLayout);
        }
    }


    /**
     * 初始化topbar
     *
     * @param topBarLayout topbar
     */
    public void initTopBar(QMUITopBarLayout topBarLayout) {
//       mTopBarLayout.addLeftImageButton(R.drawable.qmui_icon_topbar_back, R.id.arad_toolbar_left_button);

    }

    @Override
    protected void onDestroy() {
        isDestroy = true;
        super.onDestroy();
    }

    @Override
    public void contentLoading() {
        if (arad_loading != null) {
            arad_loading.setVisibility(View.VISIBLE);
        }
        if (arad_content != null) {
            arad_content.setVisibility(View.GONE);
        }
        if (arad_loading_empty != null) {
            arad_loading_empty.setVisibility(View.GONE);
        }
        if (arad_loading_error != null) {
            arad_loading_error.setVisibility(View.GONE);
        }
    }

    @Override
    public void contentLoadingComplete() {
        if (arad_loading != null) {
            arad_loading.setVisibility(View.GONE);
        }
        if (arad_content != null) {
            arad_content.setVisibility(View.VISIBLE);
        }
        if (arad_loading_empty != null) {
            arad_loading_empty.setVisibility(View.GONE);
        }
        if (arad_loading_error != null) {
            arad_loading_error.setVisibility(View.GONE);
        }
    }


    @Override
    public void contentLoadingError() {
        if (arad_loading != null) {
            arad_loading.setVisibility(View.GONE);
        }
        if (arad_content != null) {
            arad_content.setVisibility(View.GONE);
        }
        if (arad_loading_empty != null) {
            arad_loading_empty.setVisibility(View.GONE);
        }
        if (arad_loading_error != null) {
            arad_loading_error.setVisibility(View.VISIBLE);
            arad_loading_error.setOnClickListener(mOnRetryListener);
        }
    }

    @Override
    public void contentLoadingEmpty() {
        if (arad_loading != null) {
            arad_loading.setVisibility(View.GONE);
        }
        if (arad_content != null) {
            arad_content.setVisibility(View.GONE);
        }
        if (arad_loading_empty != null) {
            arad_loading_empty.setVisibility(View.VISIBLE);
        }
        if (arad_loading_error != null) {
            arad_loading_error.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgress() {

        //TODO 多语言
        mTipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("加载中...")
                .create();
        mTipDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mTipDialog != null) {
            mTipDialog.dismiss();
        }
    }


    @Override
    public void showProgressWithText(boolean show, String message) {
        mTipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(message)
                .create();
        mTipDialog.show();
    }

    @Override
    public void showMessage(@NonNull String message) {
        showMessage(message, QMUITipDialog.Builder.ICON_TYPE_INFO);
    }

    public QMUITipDialog showErrorMessage(@NonNull String message) {
        return showMessage(message, QMUITipDialog.Builder.ICON_TYPE_FAIL);
    }

    public QMUITipDialog showSuccessMessage(@NonNull String message) {
        return showMessage(message, QMUITipDialog.Builder.ICON_TYPE_SUCCESS);
    }

    private QMUITipDialog showMessage(@NonNull String message, int type) {
        if (mTipDialog != null && mTipDialog.isShowing()) {
            mTipDialog.dismiss();
        }
        mMessageDialog = new QMUITipDialog.Builder(this)
                .setIconType(type)
                .setTipWord(message)
                .create();
        mMessageDialog.show();
        getWindow().getDecorView().postDelayed(() -> {
            if (!isDestroy && mMessageDialog != null) {
                mMessageDialog.dismiss();
            }
        }, 1500);

        return mMessageDialog;
    }

    public void setOnRetryListener(View.OnClickListener onRetryListener) {
        mOnRetryListener = onRetryListener;
    }
}
