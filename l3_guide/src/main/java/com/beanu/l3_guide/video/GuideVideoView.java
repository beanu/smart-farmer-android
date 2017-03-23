package com.beanu.l3_guide.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 启动页 视频View
 * Created by Beanu on 16/8/10.
 */
public class GuideVideoView extends VideoView {

    public GuideVideoView(Context context) {
        super(context);
    }

    public GuideVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GuideVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        } else {

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
