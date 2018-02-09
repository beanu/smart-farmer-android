package com.beanu.l3_common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.beanu.l3_common.R;

/**
 * Created by lizhi on 2017/9/11.
 * 单选按钮
 */

public class MyRadioButton extends AppCompatRadioButton {

    private String groupName;

    public MyRadioButton(Context context) {
        this(context, null);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.radioButtonStyle);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = null;
        try {
            ta = context.obtainStyledAttributes(attrs, new int[]{R.attr.group_name});
            this.groupName = ta.getString(0);
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (checked){
            uncheckSiblings();
        }
    }

    private void uncheckSiblings(){
        ViewParent parent = getParent();
        if (parent != null && parent instanceof ViewGroup){
            ViewGroup parentView = (ViewGroup) parent;
            int childCount = parentView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parentView.getChildAt(i);
                if (child != this && child instanceof MyRadioButton && TextUtils.equals(groupName, ((MyRadioButton) child).groupName)){
                    ((MyRadioButton) child).setChecked(false);
                }
            }
        }
    }

    public String getGroupName(){
        return groupName;
    }

    public void setGroupName(String groupName){
        this.groupName = groupName;
    }
}
