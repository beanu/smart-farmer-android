package com.beanu.l2_zxing.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beanu.l2_zxing.R;
import com.beanu.l2_zxing.SimpleCaptureActivity;
import com.beanu.l2_zxing.ZxingUtil;

public class MyCaptureActivity extends SimpleCaptureActivity {

    private boolean isLightOpen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button btn_flight = (Button) findViewById(R.id.btn_flight);
        btn_flight.setVisibility(View.VISIBLE);
        btn_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLightOpen = !isLightOpen;
                ZxingUtil.isLightEnable(isLightOpen);
            }
        });
    }

    @Override
    public void handleResult(String resultString) {
        Toast.makeText(this, resultString, Toast.LENGTH_SHORT);
    }

}
