package io.fchain.ui;

import android.os.Bundle;
import android.view.View;

import com.beanu.arad.base.BaseFragment;
import com.beanu.arad.base.BaseModel;
import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.base.BaseView;
import com.beanu.arad.utils.ToastUtils;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * 1.加入toolbar的操作
 * 2.视图不同状态的显示，加载中，加载失败，空数据，加载完成 四个状态。使用方式，用layout包含arad_loading.xml和自己的布局，自己的布局id定义为arad_content
 * 3. 如果activity包含多个fragment，并且需要根据fragment切换标题，在fragment中使用topbarLayout
 *
 * @author beanu
 */
public class QMUIFragment<T extends BasePresenter, E extends BaseModel> extends BaseFragment<T, E> implements BaseView {

    QMUITopBarLayout mTopBarLayout;

    private View arad_content;
    private View arad_loading;
    private View arad_loading_error;
    private View arad_loading_empty;
    private View.OnClickListener mOnRetryListener;

    protected QMUITipDialog mTipDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arad_content = view.findViewById(R.id.arad_content);
        arad_loading = view.findViewById(R.id.arad_loading);
        arad_loading_empty = view.findViewById(R.id.arad_loading_empty);
        arad_loading_error = view.findViewById(R.id.arad_loading_error);

        mTopBarLayout = view.findViewById(R.id.arad_toolbar);
        if (mTopBarLayout != null) {
            initTopBar(mTopBarLayout);
        }
    }

    public void initTopBar(QMUITopBarLayout topBarLayout) {
        //       mTopBarLayout.addLeftImageButton(R.drawable.qmui_icon_topbar_back, R.id.arad_toolbar_left_button);

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
            arad_loading_error.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnRetryListener != null) {
                        mOnRetryListener.onClick(view);
                    }
                }
            });
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
        mTipDialog = new QMUITipDialog.Builder(getContext())
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

        mTipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(type)
                .setTipWord(message)
                .create();
        mTipDialog.show();
        if (getActivity() != null) {
            getActivity().getWindow().getDecorView().postDelayed(() -> {
                if (mTipDialog != null) mTipDialog.dismiss();
            }, 1500);
        }

        return mTipDialog;
    }
    public void setOnRetryListener(View.OnClickListener onRetryListener) {
        mOnRetryListener = onRetryListener;
    }
}
