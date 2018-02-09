package com.beanu.l3_post;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.beanu.arad.utils.ImageUtils;
import com.beanu.l3_common.ui.AlertFragment;
import com.beanu.l3_post.util.PictureFileUtils;
import com.cjt2325.cameralibrary.CaptureButton;
import com.cjt2325.cameralibrary.CaptureLayout;
import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;


/**
 * 点击拍照 长按录像
 *
 * @author lizhi
 * @date 2017/10/25
 */
public class CameraActivity extends AppCompatActivity {

    public static final String ARG_SAVE_PATH = "savePath";
    public static final String ARG_MIN_DURATION = "minDurationMs";
    public static final String ARG_MAX_DURATION = "maxDurationMs";

    public static final String OUT_DATA = "outData";

    public static void openCamera(@NonNull Activity activity, int requestCode, long minDurationMs, long maxDurationMs, String savePath) {
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(ARG_MIN_DURATION, minDurationMs);
        intent.putExtra(ARG_MAX_DURATION, maxDurationMs);
        intent.putExtra(ARG_SAVE_PATH, savePath);
        activity.startActivityForResult(intent, requestCode);
    }

    @Nullable
    public static AlbumFile getVideoOrPicture(Intent data) {
        if (data == null) {
            return null;
        }
        if (data.hasExtra(OUT_DATA)) {
            return data.getParcelableExtra(OUT_DATA);
        }
        return null;
    }

    private JCameraView jCameraView;
    //    private TextView textTime;
    private CaptureLayout captureLayout;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    private String savePath;
    private long durationLimit;
    private long minDuration;

    private Bitmap captureBmp;
    private Bitmap firstBmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);

        Intent intent = getIntent();
        savePath = intent.getStringExtra(ARG_SAVE_PATH);
        durationLimit = intent.getLongExtra(ARG_MAX_DURATION, 10000);
        minDuration = intent.getLongExtra(ARG_MIN_DURATION, 2000);

//        textTime = findViewById(R.id.text_time);
        jCameraView = findViewById(R.id.jcameraview);
        captureLayout = findViewById(R.id.capture_layout);
        //四舍五入
        captureLayout.setDuration((int) (durationLimit + 500));
        CaptureButton captureButton = findCaptureButton(captureLayout);
        if (captureButton != null) {
            captureButton.setMinDuration((int) minDuration);
        }

        //设置视频保存路径
        jCameraView.setSaveVideoPath(savePath + "video");
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);

        //JCameraView监听
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(final Bitmap bitmap) {
                releaseBitmap(captureBmp);
                captureBmp = bitmap;

                if (isFinishing()) {
                    return;
                }
                final ProgressDialog dialog = showProgressDialog();
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                                "picture-" + dateFormat.format(new Date()) + ".jpg");
                        ImageUtils.save(bitmap, file, Bitmap.CompressFormat.JPEG, true);
                        e.onNext(file.getAbsolutePath());
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                dialog.dismiss();
                                if (TextUtils.isEmpty(s)) {
                                    Toast.makeText(CameraActivity.this, "拍照失败", Toast.LENGTH_SHORT).show();
                                } else {
                                    handleResult(s, false);
                                }
                            }
                        });

            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                releaseBitmap(firstBmp);
                firstBmp = firstFrame;
                handleResult(url, true);
            }
        });

        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                CameraActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission(new Runnable() {
            @Override
            public void run() {
                jCameraView.onResume();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseBitmap(captureBmp);
        releaseBitmap(firstBmp);
    }

    private void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    private void handleResult(final String path, boolean isVideo) {
        final File file = new File(path);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        AlbumFile albumFile = PictureFileUtils.createAlbumFile(file, isVideo);

        if (isVideo) {
            Intent data = new Intent();
            data.putExtra(OUT_DATA, albumFile);
            setResult(RESULT_OK, data);
            finish();
        } else {
            Intent data = new Intent();
            data.putExtra(OUT_DATA, albumFile);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    private void rotateImage(int degree, File file) {
//        if (degree > 0) {
//            // 针对相片有旋转问题的处理方式
//            try {
//                BitmapFactory.Options opts = new BitmapFactory.Options();//获取缩略图显示到屏幕上
//                opts.inSampleSize = 2;
//                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
//                Bitmap bmp = PictureFileUtils.rotaingImageView(degree, bitmap);
//                PictureFileUtils.saveBitmapFile(bmp, file);
//                if (!bmp.isRecycled()) {
//                    bmp.recycle();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

//    private Observable<String> savePictureToGallery(final Bitmap bitmap) {
//        return Observable.just(bitmap)
//                .subscribeOn(Schedulers.io())
//                .flatMap(bytes -> {
//                    // 保存图片
//                    File appDir = new File(savePath + "camera");
//                    if (!appDir.exists()) {
//                        if (!appDir.mkdirs()) {
//                            return Observable.just("");
//                        }
//                    }
//                    final String fileName = "picture-" + dateFormat.format(new Date()) + ".jpg";
//                    File file = new File(appDir, fileName);
//                    FileOutputStream fos = null;
//                    try {
//                        fos = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                        fos.flush();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        return Observable.just("");
//                    } finally {
//                        if (!bitmap.isRecycled()) {
//                            bitmap.recycle();
//                        }
//                        CloseUtils.closeIOQuietly(fos);
//                    }
//
//                    // 其次把文件插入到系统图库
//                    String path = file.getAbsolutePath();
//                    try {
//                        MediaStore.Images.Media.insertImage(getContentResolver(),
//                                path, fileName, null);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    return Observable.just(path);
//                })
//                .onErrorReturnItem("")
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    private ProgressDialog showProgressDialog() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("准备中...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        return dialog;
    }

    @Nullable
    CaptureButton findCaptureButton(ViewGroup parent) {
        if (parent == null) {
            return null;
        }
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            if (view instanceof CaptureButton) {
                return (CaptureButton) view;
            }
        }
        return null;
    }

    private void requestPermission(final Runnable runnable) {
        final String[] permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        new RxPermissions(this).request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                            new AlertFragment.Builder(CameraActivity.this)
                                    .setTitle("提示")
                                    .setMessage("请授予拍照和录音相关权限")
                                    .setPositiveButton("确定", null)
                                    .create().show(getSupportFragmentManager(), "alert_permission");
                        } else {
                            boolean hasPermission = true;
                            for (String permission : permissions) {
                                if (PermissionChecker.checkSelfPermission(CameraActivity.this, permission) != PERMISSION_GRANTED) {
                                    hasPermission = false;
                                    break;
                                }
                            }
                            if (hasPermission) {
                                runnable.run();
                            } else {
                                new AlertFragment.Builder(CameraActivity.this)
                                        .setTitle("提示")
                                        .setMessage("请授予拍照和录音相关权限")
                                        .setPositiveButton("确定", null)
                                        .create().show(getSupportFragmentManager(), "alert_permission");
                            }
                        }
                    }
                });
    }
}
