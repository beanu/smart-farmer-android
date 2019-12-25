package io.fchain.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import io.fchain.ui.R;

/**
 * 输入框提示文案
 *
 * @author liuhuan
 */
public class CustomEditText extends ConstraintLayout {
    public CustomEditText(Context context) {
        super(context);
        initView(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 核心输入框
     */
    private EditText etInput;
    /**
     * 底部线条
     */
    private View viewLine;
    /**
     * 错误信息提示
     */
    private TextView tvError;
    /**
     * 清除按钮
     */
    private ImageView ivClear;


    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.ui_custom_edittext, null, false);
        etInput = rootView.findViewById(R.id.et_input);
        viewLine = rootView.findViewById(R.id.view_line);
        tvError = rootView.findViewById(R.id.tv_error_tips);
        ivClear = rootView.findViewById(R.id.ic_clear);

        addView(rootView);

    }

    /**
     * 是否展示底部线条
     *
     * @param isShow true 展示 false 隐藏
     * @return 链式调用
     */
    public CustomEditText showLine(boolean isShow) {
        if (isShow) {
            viewLine.setVisibility(VISIBLE);
        } else {
            viewLine.setVisibility(GONE);
        }
        return this;
    }


    /**
     * 是否展示清空按钮
     *
     * @param isShow true 展示 false 隐藏
     * @return 链式调用
     */
    public CustomEditText showClear(boolean isShow) {
        if (isShow) {
            ivClear.setVisibility(VISIBLE);
        } else {
            ivClear.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置清空按钮文案
     *
     * @param resId 资源文件id
     * @return 链式调用
     */
    public CustomEditText setImageResource(@DrawableRes int resId) {
        ivClear.setImageResource(resId);
        return this;
    }

    /**
     * 清空按钮点击事件
     *
     * @param l 点击事件
     * @return 链式调用
     */
    public CustomEditText setImageClick(@Nullable OnClickListener l) {
        ivClear.setOnClickListener(l);
        return this;
    }


    /**
     * 设置线条颜色
     *
     * @param color 色值
     * @return 链式调用
     */
    public CustomEditText setLineColor(@ColorInt int color) {
        viewLine.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置错误提示文案
     *
     * @param tips 错误提示文案
     * @return 链式调用
     */
    public CustomEditText setTipsText(String tips) {
        tvError.setText(tips);
        return this;
    }

    /**
     * 添加输入框输入监听
     *
     * @param watcher
     * @return
     */
    public CustomEditText setEditTextChange(TextWatcher watcher) {
        etInput.addTextChangedListener(watcher);
        return this;
    }

    /**
     * 设置输入框文案颜色
     * @param color
     * @return
     */
    public CustomEditText setEditTextColor(@ColorInt int color) {
        etInput.setTextColor(color);
        return this;
    }

    /**
     * 设置输入提示语
     * @param resId 字符串资源id
     * @return 链式调用
     */
    public CustomEditText setEditTextHint(@StringRes int resId){
        etInput.setHint(resId);
        return this;
    }

    /**
     * 设置输入提示语
     * @param hint 字符串
     * @return 链式调用
     */
    public CustomEditText setEditTextHint(CharSequence hint){
        etInput.setHint(hint);
        return this;
    }

    /**
     * 设置输入类型
     *
     * @param type 输入类型
     * @return
     */
    public CustomEditText setInputType(int type) {
        etInput.setInputType(type);
        return this;
    }

    /**
     * 获取输入文案
     *
     * @return 输入文案
     */
    public String getInputText() {
        Editable text = etInput.getText();
        if (null == text) {
            return "";
        }
        return text.toString();
    }
    public CustomEditText clearInput() {
        etInput.setText("");
        return this;
    }

    public int getImageVisibility() {
        return ivClear.getVisibility();
    }


}
