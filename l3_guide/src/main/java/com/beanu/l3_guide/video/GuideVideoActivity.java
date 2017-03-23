package com.beanu.l3_guide.video;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.beanu.arad.Arad;
import com.beanu.arad.base.ToolBarActivity;
import com.beanu.l3_common.util.Constants;
import com.beanu.l3_guide.R;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.io.File;


/**
 * 视频向导页面
 */
public class GuideVideoActivity extends ToolBarActivity implements View.OnClickListener {

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    public static final String VIDEO_NAME = "welcome_video.mp4";
    private VideoView mVideoView;
    private Button buttonLeft, buttonRight;
    private TextView appName;
    private TextView txtJump;
    private ViewPager mViewPager;
    private InkPageIndicator mPageIndicator;

    File videoFile;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setContentView(R.layout.activity_guide_video);
        mContentView = findViewById(R.id.fullscreen_content);

        findView();
        initView();

        videoFile = getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists()) {
            videoFile = FileUtil.copyRawFileToDisk(this, VIDEO_NAME, R.raw.welcome_video);
        }
        playVideo(videoFile);
        playAnim();
    }


    private void findView() {
        mVideoView = (VideoView) findViewById(R.id.videoView);
        buttonLeft = (Button) findViewById(R.id.buttonLeft);
        buttonRight = (Button) findViewById(R.id.buttonRight);
        appName = (TextView) findViewById(R.id.appName);
        txtJump = (TextView) findViewById(R.id.txt_guide_jump);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_guide);
        mPageIndicator = (InkPageIndicator) findViewById(R.id.indicator_guide);
    }

    private void initView() {
        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);
        txtJump.setOnClickListener(this);

        GuideVideoAdapter guideAdapter = new GuideVideoAdapter(getLayoutInflater());
        mViewPager.setAdapter(guideAdapter);

        mPageIndicator.setViewPager(mViewPager);
    }

    private void playVideo(File videoFile) {
        mVideoView.setVideoPath(videoFile.getPath());
        mVideoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        });
    }

    private void playAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(appName, "alpha", 0, 1);
        anim.setDuration(4000);
        anim.setRepeatCount(1);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.start();
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                appName.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        delayedHide(100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoFile != null) {
            videoFile = null;
        }
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void showStatusBarWhenTrans() {
        //解决从全屏切换到非全屏时,状态栏的闪现的问题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLeft) {

//            showStatusBarWhenTrans();
//            Intent intent = new Intent(this, LoginRegisterActivity.class);
//            startActivity(intent);


        } else if (view == buttonRight) {

//            showStatusBarWhenTrans();
//            Intent intent = new Intent(this, LoginRegisterActivity.class);
//            startActivity(intent);

        } else if (view == txtJump) {

//            enableFirstLaunchFalse();
//
//            showStatusBarWhenTrans();
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();

        }
    }


    //    业务
    private void enableFirstLaunchFalse() {
        Arad.preferences.putBoolean(Constants.P_ISFIRSTLOAD, false);
        Arad.preferences.flush();
    }
}
