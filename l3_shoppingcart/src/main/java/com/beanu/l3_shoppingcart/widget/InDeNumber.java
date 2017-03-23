package com.beanu.l3_shoppingcart.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.beanu.l3_shoppingcart.R;

/**
 * 购物车 加减控件
 */
public class InDeNumber extends LinearLayout implements OnClickListener {

    private Button increase, decrease;
    private EditText editor;
    int currentNum = 1;
    OnChangeListener listener;

    public static interface OnChangeListener {
        public void notifyDataChanged(int num);
    }

    public InDeNumber(Context context) {
        super(context);
        init();
    }

    public InDeNumber(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cart_number_edit, this);
        init();
    }

    private void init() {
        editor = (EditText) findViewById(R.id.num_editor);
        decrease = (Button) findViewById(R.id.num_de);
        increase = (Button) findViewById(R.id.num_in);

        increase.setOnClickListener(this);
        decrease.setOnClickListener(this);
        refreshEditText(currentNum);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.num_de) {

            currentNum--;
            if (currentNum < 1)
                currentNum = 1;
        } else if (v.getId() == R.id.num_in) {

            currentNum++;
        }

        refreshEditText(currentNum);
        if (listener != null)
            listener.notifyDataChanged(currentNum);
    }

    private void refreshEditText(int num) {
        editor.setText(String.valueOf(num));
    }

    public void setListener(OnChangeListener listener) {
        this.listener = listener;
    }

    public void setNum(int num) {
        if (num >= 1) {
            editor.setText(String.valueOf(num));
            currentNum = num;
        }
    }

    public int getCurrentNum() {
        return currentNum;
    }

}