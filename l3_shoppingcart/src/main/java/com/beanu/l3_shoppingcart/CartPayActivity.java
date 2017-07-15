package com.beanu.l3_shoppingcart;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.beanu.arad.Arad;
import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.http.RxHelper;
import com.beanu.arad.utils.ToastUtils;
import com.beanu.l2_pay.PayResultCallBack;
import com.beanu.l2_pay.PayType;
import com.beanu.l2_pay.PayUtil;
import com.beanu.l3_common.model.api.API;
import com.beanu.l3_common.model.bean.EventModel;
import com.beanu.l3_common.util.Constants;
import com.beanu.l3_shoppingcart.model.APICartService;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * 购物车支付
 */
public class CartPayActivity extends ToolBarActivity implements View.OnClickListener, PayResultCallBack {

    RadioButton cbAli, cbWeixin;
    String orderId;
    int type;//0 直播课 1高清网课 2图书

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity_pay);

        orderId = getIntent().getStringExtra("orderId");
        type = getIntent().getIntExtra("type", 2);
        double priceSum = getIntent().getDoubleExtra("priceSum", 0);
        TextView txt_price = (TextView) findViewById(R.id.txt_price);
        txt_price.setText("¥" + priceSum);

        RelativeLayout rlAli = (RelativeLayout) findViewById(R.id.rlAli);
        RelativeLayout rlWeixin = (RelativeLayout) findViewById(R.id.rlWeixin);

        cbAli = (RadioButton) findViewById(R.id.cbAli);
        cbWeixin = (RadioButton) findViewById(R.id.cbWeixin);

        rlAli.setOnClickListener(this);
        rlWeixin.setOnClickListener(this);
        findViewById(R.id.btn_cart_pay).setOnClickListener(this);

        //微信支付 初始化
        PayUtil.initWx(Constants.WX_APPID);

    }

    @Override
    public String setupToolBarTitle() {
        return "支付";
    }

    @Override
    public boolean setupToolBarLeftButton(View leftButton) {
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        return true;
    }
//
//    @Override
//    protected void setStatusBar() {
//
//    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rlAli) {
            cbAli.setChecked(true);
            cbWeixin.setChecked(false);
        } else if (id == R.id.rlWeixin) {
            cbAli.setChecked(false);
            cbWeixin.setChecked(true);
        } else if (id == R.id.btn_cart_pay)

            if (cbAli.isChecked()) {
                //支付宝支付
                showProgress();
                API.getInstance(APICartService.class).requestAlipaySign(orderId)
                        .compose(RxHelper.<String>handleResult())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull String s) {
                                //启动支付宝
                                PayUtil.pay(CartPayActivity.this, PayType.ALI, s, CartPayActivity.this);

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                hideProgress();

                            }

                            @Override
                            public void onComplete() {
                                hideProgress();

                            }
                        });


            } else {
                //微信支付
                showProgress();


                API.getInstance(APICartService.class).requestWePaySign(orderId)
                        .compose(RxHelper.<Map<String, String>>handleResult())
                        .subscribe(new Observer<Map<String, String>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull Map<String, String> map) {

                                PayReq req = new PayReq();
                                req.appId = map.get("appid");
                                req.partnerId = map.get("partnerid");
                                req.prepayId = map.get("prepayid");
                                req.packageValue = map.get("packageValue");
                                req.nonceStr = map.get("noncestr");
                                req.timeStamp = map.get("timestamp");
                                req.sign = map.get("sign");

                                //调起微信支付
                                PayUtil.wxPay(CartPayActivity.this, req, CartPayActivity.this);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                hideProgress();

                            }

                            @Override
                            public void onComplete() {
                                hideProgress();

                            }
                        });

            }
    }

    @Override
    public void onPaySuccess(PayType payType) {
        Arad.bus.post(new EventModel.CartBuySuccess());

        ToastUtils.showShort("支付成功");
        ARouter.getInstance().build("/app/my/orderList").withInt("page", type).navigation();

        finish();
    }

    @Override
    public void onPayDealing(PayType type) {

    }

    @Override
    public void onPayError(PayType type, String errorCode, String rawErrorCode) {
        Toast.makeText(this, "支付失败 " + errorCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPayCancel(PayType type) {
        Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
    }
}
