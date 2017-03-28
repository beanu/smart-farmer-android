package com.beanu.sf.ui.layer2.pay;

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

public class PayActivity extends AppCompatActivity implements View.OnClickListener, PayResultCallBack {

    Button btnAli;
    Button btnWx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        //支付宝使用本地签名必须初始化
        AliLocalParamCreator.init_v2("alipay_appid", "rsa2_private", "rsa_private", "http://notify_url");

        //微信支付必须设置 appid
        PayUtil.initWx("wx_appid");

        btnAli = (Button) findViewById(R.id.btnAli);
        btnWx = (Button) findViewById(R.id.btnWx);

        btnAli.setOnClickListener(this);
        btnWx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      
        if (v.getId() == R.id.btnAli){
            //使用本地参数生成器生成支付宝所需参数, 调起支付宝支付
            PayUtil.pay(this, PayType.ALI, AliLocalParamCreator.create_v2("测试商品", "测试说明", "0.01", null), this);

        } else if (v.getId() == R.id.btnWx){

            //模拟从服务器获取
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String jsonParam = mockGetWxParamFromNetWork();

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            //使用字符串参数需要 字符串 中存在 如 mockGetWxParamFromNetWork() 所列字段
                            PayUtil.pay(PayActivity.this, PayType.WX, jsonParam, PayActivity.this);

                            //如果使用自己构造的 PayReq
                            //PayReq payReq = new PayReq();
                            //payReq.appId = "wxd930ea5d5a258f4f";
                            //payReq.partnerId = "1900000109";
                            //payReq.prepayId= "1101000000140415649af9fc314aa427",;
                            //payReq.packageValue = "Sign=WXPay";
                            //payReq.nonceStr= "1101000000140429eb40476f8896f4c9";
                            //payReq.timeStamp= "1398746574";
                            //payReq.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
                            //PayUtil.wxPay(DemoActivity.this, payReq, DemoActivity.this);
                        }
                    });
                }
            }).start();
        }
    }

    //模拟从服务器获取微信支付参数, json格式
    private String mockGetWxParamFromNetWork() {
        StringBuilder builder = new StringBuilder();
        builder.append("{")
                .append("\"sign\":\"7FFECB600D7157C5AA49810D2D8F28BC2811827B\"").append(",")
                .append("\"timestamp\":\"1398746574\"").append(",")
                .append("\"partnerid\":\"1900000109\"").append(",")
                .append("\"noncestr\":\"1101000000140429eb40476f8896f4c9\"").append(",")
                .append("\"prepayid\":\"1101000000140415649af9fc314aa427\"").append(",")
                .append("\"packageValue\":\"Sign=WXPay\"").append(",")
                .append("\"appid\":\"wxd930ea5d5a258f4f\"")
                .append("}");

        try {
            Thread.sleep(2000);//模拟延时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    @Override
    public void onPaySuccess(PayType type) {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPayDealing(PayType type) {
        Toast.makeText(this, "支付中", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPayError(PayType type, String errorCode, String rawErrorCode) {
        //errorCode 请查看 PayResultCallBack 中的说明
        Toast.makeText(this, "支付失败 " + errorCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPayCancel(PayType type) {
        Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
    }
}
