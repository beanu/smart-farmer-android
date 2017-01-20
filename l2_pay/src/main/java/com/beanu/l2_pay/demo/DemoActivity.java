package com.beanu.l2_pay.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beanu.l2_pay.PayResultCallBack;
import com.beanu.l2_pay.PayType;
import com.beanu.l2_pay.PayUtil;
import com.beanu.l2_pay.R;
import com.beanu.l2_pay.alipay.AliLocalParamCreator;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener, PayResultCallBack {

    Button btnAli;
    Button btnWx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);


        //支付宝使用本地签名必须初始化
        //建议在 Application 的 onCreate 中调用
        AliLocalParamCreator.init("xxxxxxxxxx", "", "xxxxxxxxxxxxxxxxxx");

        //微信支付必须设置 appid
        //建议在 Application 的 onCreate 中调用
        PayUtil.initWx("xxxxxxxxxxxxxxxxx");

        btnAli = (Button) findViewById(R.id.btnAli);
        btnWx = (Button) findViewById(R.id.btnWx);

        btnAli.setOnClickListener(this);
        btnWx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAli:
                //使用本地参数生成器生成支付宝所需参数, 调起支付宝支付
                PayUtil.pay(this, PayType.ALI, AliLocalParamCreator.create("测试", "测试物品", "0.01"), this);
                break;
            case R.id.btnWx:
                //模拟从服务器获取
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String jsonParam = mockGetWxParamFromNetWork();

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                //使用字符串参数需要 字符串 中存在 如 mockGetWxParamFromNetWork() 所列字段
                                PayUtil.pay(DemoActivity.this, PayType.WX, jsonParam, DemoActivity.this);

                                //如果使用自己构造的 PayReq
                                //PayUtil.wxPay(DemoActivity.this, payReq, DemoActivity.this);
                            }
                        });
                    }
                }).start();
                break;
        }
    }

    //模拟从服务器获取微信支付参数, json格式
    private String mockGetWxParamFromNetWork() {
        StringBuilder builder = new StringBuilder();
        builder.append("{")
                .append("\"sign\":\"xxxxxxxxxxxxxxxx\"").append(",")
                .append("\"timestamp\":\"xxxxxxxxxxxxxx\"").append(",")
                .append("\"partnerid\":\"xxxxxxxxxxxxxx\"").append(",")
                .append("\"noncestr\":\"xxxxxxxxxxxxx\"").append(",")
                .append("\"prepayid\":\"xxxxxxxxxxxxxx\"").append(",")
                .append("\"packageValue\":\"xxxxxxxxxxx\"").append(",")
                .append("\"appid\":\"xxxxxxxxxxxxxxxxxxxxx\"")
                .append("}");

        try {
            Thread.sleep(2000);//模拟延时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    @Override
    public void onSuccess(PayType type) {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDealing(PayType type) {
        Toast.makeText(this, "支付中", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(PayType type, int errorCode) {
        Toast.makeText(this, "支付失败 " + errorCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(PayType type) {
        Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
    }
}
