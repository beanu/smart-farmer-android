package io.fchain.ui;

import android.content.Context;
import android.text.InputType;
import android.text.SpannableString;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.qmuiteam.qmui.util.QMUILangHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;

/**
 * 全局通用的dialog
 *
 * @author Beanu
 */
public class FireflyDialog {

    /**
     * 带输入框的对话框 Builder
     */
    public static class InputDialogBuilder extends QMUIDialogBuilder<InputDialogBuilder> {
        private String mPlaceholder;
        private TransformationMethod mTransformationMethod;
        private EditText mEditText;
        private SpannableString mDescription;
        private int mInputType = InputType.TYPE_CLASS_TEXT;
        private CharSequence mDefaultText = null;
        private boolean startWithKeyboard = true;

        public InputDialogBuilder(Context context) {
            super(context);
        }

        /**
         * 设置输入框的 placeholder
         */
        public InputDialogBuilder setPlaceholder(String placeholder) {
            this.mPlaceholder = placeholder;
            return this;
        }

        /**
         * 设置输入框的 placeholder
         */
        public InputDialogBuilder setPlaceholder(int resId) {
            return setPlaceholder(getBaseContext().getResources().getString(resId));
        }

        public InputDialogBuilder setDefaultText(CharSequence defaultText) {
            mDefaultText = defaultText;
            return this;
        }

        /**
         * 设置 EditText 的 transformationMethod
         */
        public InputDialogBuilder setTransformationMethod(TransformationMethod transformationMethod) {
            mTransformationMethod = transformationMethod;
            return this;
        }

        /**
         * 设置 EditText 的 inputType
         */
        public InputDialogBuilder setInputType(int inputType) {
            mInputType = inputType;
            return this;
        }

        public InputDialogBuilder setDescription(String description) {
            mDescription = new SpannableString(description);
            return this;
        }

        public InputDialogBuilder setDescription(SpannableString description) {
            mDescription = description;
            return this;
        }

        public InputDialogBuilder setStartWithKeyboard(boolean startWithKeyboard) {
            this.startWithKeyboard = startWithKeyboard;
            return this;
        }

        @Override
        protected void onCreateContent(QMUIDialog dialog, ViewGroup parent, Context context) {

            View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_common, parent, false);
            mEditText = view.findViewById(R.id.dialog_edit_input);
            mEditText.setFocusable(true);
            mEditText.setFocusableInTouchMode(true);
            mEditText.setImeOptions(EditorInfo.IME_ACTION_GO);

            if (!QMUILangHelper.isNullOrEmpty(mDefaultText)) {
                mEditText.setText(mDefaultText);
            }

            if (mTransformationMethod != null) {
                mEditText.setTransformationMethod(mTransformationMethod);
            } else {
                mEditText.setInputType(mInputType);
            }

            if (mPlaceholder != null) {
                mEditText.setHint(mPlaceholder);
            }

            QMUISpanTouchFixTextView textView = view.findViewById(R.id.dialog_text);
            if (mDescription != null) {
                textView.setMovementMethodDefault();
                textView.setText(mDescription);
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.GONE);
            }

            parent.addView(view);
        }

        @Override
        protected void onAfter(QMUIDialog dialog, LinearLayout parent, Context context) {
            super.onAfter(dialog, parent, context);
            final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            dialog.setOnDismissListener(dialog1 -> inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0));
            if (startWithKeyboard) {
                mEditText.postDelayed(() -> {
                    mEditText.requestFocus();
                    inputMethodManager.showSoftInput(mEditText, 0);
                }, 300);
            }
        }

        public EditText getEditText() {
            return mEditText;
        }
    }
}
