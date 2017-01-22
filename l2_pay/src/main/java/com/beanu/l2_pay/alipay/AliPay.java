package com.beanu.l2_pay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.beanu.l2_pay.IPay;
import com.beanu.l2_pay.PayResultCallBack;
import com.beanu.l2_pay.PayType;

import java.util.Map;

/**
 * 支付宝支付
 * Created by lizhihua on 2017/1/18.
 */
public class AliPay implements IPay {
    private String mParams;
    private PayTask mPayTask;
    private PayResultCallBack mCallback;

    public AliPay(Activity context, String params, PayResultCallBack callback) {
        mParams = params;
        mCallback = callback;
        mPayTask = new PayTask(context);
    }

    //支付
    @Override
    public void doPay() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map<String, String> pay_result = mPayTask.payV2(mParams,true);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mCallback == null) {
                            return;
                        }

                        if(pay_result == null) {
                            mCallback.onPayError(PayType.ALI, PayResultCallBack.ERROR_RESULT, PayResultCallBack.ERROR_RESULT);
                            return;
                        }

                        String resultStatus = pay_result.get("resultStatus");
                        if(TextUtils.equals(resultStatus, "9000")) {    //支付成功
                            mCallback.onPaySuccess(PayType.ALI);
                        } else if(TextUtils.equals(resultStatus, "8000")) { //支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            mCallback.onPayDealing(PayType.ALI);
                        } else if(TextUtils.equals(resultStatus, "6001")) {		//支付取消
                            mCallback.onPayCancel(PayType.ALI);
                        } else if(TextUtils.equals(resultStatus, "6002")) {     //网络连接出错
                            mCallback.onPayError(PayType.ALI, PayResultCallBack.ERROR_NETWORK, resultStatus);
                        } else {        //支付错误
                            mCallback.onPayError(PayType.ALI, PayResultCallBack.ERROR_PAY, resultStatus);
                        }
                    }
                });
            }
        }).start();
    }
}
