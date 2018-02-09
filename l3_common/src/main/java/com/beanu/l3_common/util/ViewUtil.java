package com.beanu.l3_common.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.beanu.l3_common.R;

/**
 * @author lizhi
 * @date 2017/11/6.
 */

public class ViewUtil {
    public static void resetSize(View view, int width, int height){
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null){
            params = new ViewGroup.LayoutParams(width, height);
            view.setLayoutParams(params);
            return;
        }
        boolean hasChanged = false;
        if (params.width != width){
            params.width = width;
            hasChanged = true;
        }
        if (params.height != height){
            params.height = height;
            hasChanged = true;
        }
        if (hasChanged) {
            view.requestLayout();
        }
    }

    public static void resetMargin(View view, int margin){
        resetMargin(view, margin, margin, margin, margin);
    }

    public static void resetMargin(View view, int leftRight, int topBottom){
        resetMargin(view, leftRight, topBottom, leftRight, topBottom);
    }

    public static void resetMargin(View view, int left, int topBottom, int right){
        resetMargin(view, left, topBottom, right, topBottom);
    }

    public static void resetMargin(View view, int left, int top, int right, int bottom){
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null){
            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            marginParams.leftMargin = left;
            marginParams.topMargin = top;
            marginParams.rightMargin = right;
            marginParams.bottomMargin = bottom;
            view.setLayoutParams(marginParams);
            return;
        } else if (!(params instanceof ViewGroup.MarginLayoutParams)){
            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(params);
            marginParams.leftMargin = left;
            marginParams.topMargin = top;
            marginParams.rightMargin = right;
            marginParams.bottomMargin = bottom;
            view.setLayoutParams(marginParams);
            return;
        }
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
        boolean hasChanged = false;
        if (marginParams.leftMargin != left){
            marginParams.leftMargin = left;
            hasChanged = true;
        }
        if (marginParams.topMargin != top){
            marginParams.topMargin = top;
            hasChanged = true;
        }
        if (marginParams.rightMargin != right){
            marginParams.rightMargin = right;
            hasChanged = true;
        }
        if (marginParams.bottomMargin != bottom){
            marginParams.bottomMargin = bottom;
            hasChanged = true;
        }
        if (hasChanged){
            view.requestLayout();
        }
    }

    public static void resetPadding(View view, int padding){
        resetPadding(view, padding, padding, padding, padding);
    }

    public static void resetPadding(View view, int leftRight, int topBottom){
        resetPadding(view, leftRight, topBottom, leftRight, topBottom);
    }

    public static void resetPadding(View view, int left, int topBottom, int right){
        resetPadding(view, left, topBottom, right, topBottom);
    }

    public static void resetPadding(View view, int left, int top, int right, int bottom){
        if (view == null) {
            return;
        }
        view.setPadding(left, top, right, bottom);
    }

    public static void bindViewHolder(RecyclerView.ViewHolder holder, View view){
        if (view != null && holder != null){
            view.setTag(R.id.tag_view_holder, holder);
        }
    }

    public static void bindViewHolder(RecyclerView.ViewHolder holder, View...views){
        if (holder != null){
            for (View view : views) {
                if (view != null){
                    view.setTag(R.id.tag_view_holder, holder);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <VH extends RecyclerView.ViewHolder> VH getViewHolder(View view){
        if (view == null){
            return null;
        }
        return (VH)view.getTag(R.id.tag_view_holder);
    }

    public static void bindClickListener(View.OnClickListener listener, View view){
        if (view != null){
            view.setOnClickListener(listener);
        }
    }

    public static void bindClickListener(View.OnClickListener listener, View...views){
        for (View view : views) {
            if (view != null){
                view.setOnClickListener(listener);
            }
        }
    }

    public static void bindLongClickListener(View.OnLongClickListener listener, View view){
        if (view != null){
            view.setOnLongClickListener(listener);
        }
    }

    public static void bindLongClickListener(View.OnLongClickListener listener, View...views){
        for (View view : views) {
            if (view != null){
                view.setOnLongClickListener(listener);
            }
        }
    }

    public static void bindOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener, CompoundButton button){
        if (button != null){
            button.setOnCheckedChangeListener(listener);
        }
    }

    public static void bindOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener, CompoundButton...buttons){
        for (CompoundButton view : buttons) {
            if (view != null){
                view.setOnCheckedChangeListener(listener);
            }
        }
    }

    public static void setEnable(boolean enable, View view){
        if (view != null){
            view.setEnabled(enable);
        }
    }

    public static void setEnable(boolean enable, View...views){
        for (View view : views) {
            if (view != null){
                view.setEnabled(enable);
            }
        }
    }

    public static int getLeftMargin(View view){
        if (view == null){
            return 0;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null && params instanceof ViewGroup.MarginLayoutParams){
            return ((ViewGroup.MarginLayoutParams) params).leftMargin;
        }
        return 0;
    }

    public static int getTopMargin(View view){
        if (view == null){
            return 0;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null && params instanceof ViewGroup.MarginLayoutParams){
            return ((ViewGroup.MarginLayoutParams) params).topMargin;
        }
        return 0;
    }

    public static int getRightMargin(View view){
        if (view == null){
            return 0;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null && params instanceof ViewGroup.MarginLayoutParams){
            return ((ViewGroup.MarginLayoutParams) params).rightMargin;
        }
        return 0;
    }

    public static int getBottomMargin(View view){
        if (view == null){
            return 0;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null && params instanceof ViewGroup.MarginLayoutParams){
            return ((ViewGroup.MarginLayoutParams) params).bottomMargin;
        }
        return 0;
    }
}
