package io.fchain.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * 嵌入式viewpager
 *
 * @author Beanu
 */
public class NestViewPager extends ViewPager {

    public NestViewPager(@NonNull Context context) {
        super(context);
    }

    public NestViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;

        for (int i = 0; i < getChildCount(); i++) {

            View child = getChildAt(i);
            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }

        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
