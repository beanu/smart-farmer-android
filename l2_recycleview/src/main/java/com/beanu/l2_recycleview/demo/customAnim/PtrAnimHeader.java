package com.beanu.l2_recycleview.demo.customAnim;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.l2_recycleview.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * 自定义下拉动画
 * 1.下拉动画
 * 2.下拉到刷新之间的过渡动画
 * 3.刷新动画
 * Created by Beanu on 2016/12/17.
 */

public class PtrAnimHeader extends FrameLayout implements PtrUIHandler {

    private static final String TAG = PtrAnimHeader.class.getSimpleName();

    ImageView ivFirst, ivSecond, ivThird;
    TextView mTextView;

    private Matrix mMatrix = new Matrix();
    private AnimationDrawable mSecondAnimation;
    private AnimationDrawable mThirdAnimation;

    public PtrAnimHeader(Context context) {
        super(context);
        initView();
    }

    public PtrAnimHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PtrAnimHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.ptr_anim_header, this);

        ivFirst = (ImageView) headerView.findViewById(R.id.ivFirst);
        ivSecond = (ImageView) headerView.findViewById(R.id.ivSecond);
        ivThird = (ImageView) headerView.findViewById(R.id.ivThird);
        mTextView = (TextView) headerView.findViewById(R.id.tvMsg);

        mSecondAnimation = (AnimationDrawable) ivSecond.getDrawable();
        mThirdAnimation = (AnimationDrawable) ivThird.getDrawable();
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        pullStep0(0.0f);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        pullStep2();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        pullStep3();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();
        if (lastPos < mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                float scale = lastPos / Float.valueOf(mOffsetToRefresh);
                pullStep0(scale);
            }
        }
        Log.i(TAG, "mOffsetToRefresh =" + mOffsetToRefresh + ",currentPos =" + currentPos + ",lastPos =" + lastPos);
        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                pullStep4();
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                pullStep1(frame);
            }
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resetView();
    }


    private void pullStep0(float scale) {
        ivFirst.setVisibility(View.VISIBLE);
        ivSecond.setVisibility(View.INVISIBLE);
        ivThird.setVisibility(View.INVISIBLE);
        scaleImage(scale);
        mTextView.setText(R.string.cube_ptr_pull_down_to_refresh);
    }

    private void pullStep1(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            ivFirst.setVisibility(View.INVISIBLE);
            ivSecond.setVisibility(View.VISIBLE);
            ivThird.setVisibility(View.INVISIBLE);
            mSecondAnimation.start();
            mTextView.setText(R.string.cube_ptr_release_to_refresh);
        }
    }

    private void pullStep2() {
        ivFirst.setVisibility(View.INVISIBLE);
        ivSecond.setVisibility(View.INVISIBLE);
        ivThird.setVisibility(View.VISIBLE);
        cancelAnimationSecond();
        mThirdAnimation.start();
        mTextView.setText(R.string.cube_ptr_refreshing);
    }

    private void pullStep3() {
        ivFirst.setVisibility(View.INVISIBLE);
        ivSecond.setVisibility(View.INVISIBLE);
        ivThird.setVisibility(View.VISIBLE);
        cancelAnimationThird();
        mTextView.setText(R.string.cube_ptr_refresh_complete);
    }

    /**
     * 可刷新到不可刷新
     */
    private void pullStep4() {
        ivFirst.setVisibility(View.VISIBLE);
        ivSecond.setVisibility(View.INVISIBLE);
        ivThird.setVisibility(View.INVISIBLE);
        mTextView.setText(getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
    }

    private void scaleImage(float scale) {
        mMatrix.setScale(scale, scale, ivFirst.getWidth() / 2, ivFirst.getHeight() / 2);
        ivFirst.setImageMatrix(mMatrix);
    }

    private void resetView() {
        cancelAnimations();
    }

    private void cancelAnimations() {
        cancelAnimationSecond();
        cancelAnimationThird();
    }

    private void cancelAnimationSecond() {
        if (mSecondAnimation != null && mSecondAnimation.isRunning()) {
            mSecondAnimation.stop();
        }
    }

    private void cancelAnimationThird() {
        if (mThirdAnimation != null && mThirdAnimation.isRunning()) {
            mThirdAnimation.stop();
        }
    }

    public void setTitleTextColor(int color) {
        mTextView.setTextColor(color);
    }

}
