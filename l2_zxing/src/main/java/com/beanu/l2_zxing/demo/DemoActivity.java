package com.beanu.l2_zxing.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.l2_zxing.CaptureActivity;
import com.beanu.l2_zxing.R;
import com.beanu.l2_zxing.ZxingUtil;

/**
 * DEMO 实例
 */
public class DemoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 0;
    private static final int REQUEST_IMAGE = 1;

    private TextView mTextView;
    private ImageView mImageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mTextView = (TextView) findViewById(R.id.txt_result);
        mImageView = (ImageView) findViewById(R.id.img_result);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn1:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.btn2:
                Intent intent_img = new Intent(Intent.ACTION_GET_CONTENT);
                intent_img.addCategory(Intent.CATEGORY_OPENABLE);
                intent_img.setType("image/*");
                startActivityForResult(intent_img, REQUEST_IMAGE);

                break;
            case R.id.btn3:
                Bitmap mBitmap = ZxingUtil.createImage("你是正确的", 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                // 不带logo的写法
                // Bitmap mBitmap = ZxingUtil.createImage("你是正确的", 400, 400, null);
                mImageView.setImageBitmap(mBitmap);
                break;
            case R.id.btn4:
                Intent intent1 = new Intent(this, MyCaptureActivity.class);
                startActivity(intent1);

                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {//处理扫描结果
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    String result = bundle.getString(ZxingUtil.RESULT_PARAM);
                    mTextView.setText("解析结果:" + result);
                }
            } else if (requestCode == REQUEST_IMAGE) {//处理图片扫描结果
                if (data != null) {
                    Uri uri = data.getData();
                    try {

                        ZxingUtil.analyzeBitmap(uri.getPath(), new ZxingUtil.AnalyzeCallback() {
                            @Override
                            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                                mTextView.setText("解析结果:" + result);
                            }

                            @Override
                            public void onAnalyzeFailed() {
                                mTextView.setText("解析结果失败");
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }
}
