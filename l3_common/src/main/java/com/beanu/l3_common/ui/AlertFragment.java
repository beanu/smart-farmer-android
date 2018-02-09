package com.beanu.l3_common.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.beanu.l3_common.R;
import com.beanu.l3_common.util.ViewUtil;

/**
 * @author lizhi
 * @date 2017/12/28.
 */

public class AlertFragment extends DialogFragment implements DialogInterface, View.OnClickListener {

    public static class Builder {
        private Context context;
        private CharSequence title;
        private CharSequence message;
        private CharSequence positiveBtnText;
        private CharSequence negativeBtnText;
        private OnClickListener onPositiveBtnClickLis;
        private OnClickListener onNegativeBtnClickLis;
        private OnCancelListener onCancelListener;
        private OnDismissListener onDismissListener;
        private OnShowListener onShowListener;
        private boolean cancelable;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(@StringRes int titleRes) {
            return setTitle(context.getString(titleRes));
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(@StringRes int messageRes) {
            return setMessage(context.getString(messageRes));
        }

        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        public Builder setPositiveButton(@StringRes int textRes, OnClickListener listener) {
            return setPositiveButton(context.getString(textRes), listener);
        }

        public Builder setPositiveButton(CharSequence text, OnClickListener listener) {
            this.positiveBtnText = text;
            this.onPositiveBtnClickLis = listener;
            return this;
        }

        public Builder setNegativeButton(@StringRes int textRes, OnClickListener listener) {
            return setNegativeButton(context.getString(textRes), listener);
        }

        public Builder setNegativeButton(CharSequence text, OnClickListener listener) {
            this.negativeBtnText = text;
            this.onNegativeBtnClickLis = listener;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener listener) {
            this.onCancelListener = listener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener listener) {
            this.onDismissListener = listener;
            return this;
        }

        public Builder setOnShowListener(OnShowListener listener) {
            this.onShowListener = listener;
            return this;
        }

        @SuppressWarnings("SameParameterValue")
        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public AlertFragment create() {
            AlertFragment fragment = new AlertFragment();
            fragment.title = title;
            fragment.message = message;
            fragment.positiveBtnText = positiveBtnText;
            fragment.negativeBtnText = negativeBtnText;
            fragment.onPositiveBtnClickLis = onPositiveBtnClickLis;
            fragment.onNegativeBtnClickLis = onNegativeBtnClickLis;
            fragment.onCancelListener = onCancelListener;
            fragment.onDismissListener = onDismissListener;
            fragment.onShowListener = onShowListener;
            fragment.setCancelable(cancelable);
            return fragment;
        }
    }

    private CharSequence title;
    private CharSequence message;
    private CharSequence positiveBtnText;
    private CharSequence negativeBtnText;
    private OnClickListener onPositiveBtnClickLis;
    private OnClickListener onNegativeBtnClickLis;
    private OnCancelListener onCancelListener;
    private OnDismissListener onDismissListener;
    private OnShowListener onShowListener;

    private TextView textTitle;
    private TextView textContent;
    private TextView textNegative;
    private TextView textPositive;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_fragment_alert, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        ViewUtil.bindClickListener(this, textNegative, textPositive);

        setContent(textTitle, title);
        setContent(textContent, message);
        setContent(textNegative, negativeBtnText);
        setContent(textPositive, positiveBtnText);
    }

    private void initView(View view) {
        textTitle = view.findViewById(R.id.text_title);
        textContent = view.findViewById(R.id.text_content);
        textNegative = view.findViewById(R.id.text_negative);
        textPositive = view.findViewById(R.id.text_positive);
    }

    private void setContent(TextView textView, CharSequence content) {
        if (TextUtils.isEmpty(content)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(content);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.text_negative) {
            if (onNegativeBtnClickLis != null) {
                onNegativeBtnClickLis.onClick(this, DialogInterface.BUTTON_NEGATIVE);
            }
            dismiss();

        } else if (i == R.id.text_positive) {
            if (onPositiveBtnClickLis != null) {
                onPositiveBtnClickLis.onClick(this, DialogInterface.BUTTON_POSITIVE);
            }
            dismiss();
        }
    }

    @Override
    public void cancel() {
        if (onCancelListener != null) {
            onCancelListener.onCancel(this);
        }
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(this);
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        if (onShowListener != null) {
            onShowListener.onShow(this);
        }
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        int result = super.show(transaction, tag);
        if (onShowListener != null) {
            onShowListener.onShow(this);
        }
        return result;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null){
            Window window = dialog.getWindow();
            if (window != null){
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }
}
