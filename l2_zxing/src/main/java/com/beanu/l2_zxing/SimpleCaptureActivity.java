package com.beanu.l2_zxing;

import android.graphics.Bitmap;

import com.google.zxing.Result;

/**
 * 在当前页面，自定义处理结果
 *
 * @author Beanu
 */
public abstract class SimpleCaptureActivity extends CaptureActivity {

    public abstract void handleResult(String resultString);

    /**
     * 处理结果
     */
    @Override
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();

        String resultString = result.getText();
        // Intent resultIntent = new Intent();
        // Bundle bundle = new Bundle();
        // bundle.putString("result", resultString);
        // resultIntent.putExtras(bundle);
        // this.setResult(RESULT_OK, resultIntent);

        handleResult(resultString);
    }
}
