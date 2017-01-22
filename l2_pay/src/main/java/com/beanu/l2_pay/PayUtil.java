package com.beanu.l2_pay;

import android.app.Activity;

import com.beanu.l2_pay.alipay.AliPay;
import com.beanu.l2_pay.wxpay.WxPay;
import com.tencent.mm.opensdk.modelpay.PayReq;

/**
 * Created by lizhihua on 2017/1/18.
 */

public class PayUtil {
    public static void initWx(String wxAppId){
        WxPay.setAppId(wxAppId);
    }

    public static void pay(Activity activity, PayType type, String payParam, PayResultCallBack callBack){
        switch (type){
            case ALI:
                new AliPay(activity, payParam, callBack).doPay();
                break;
            case WX:
                new WxPay(activity, payParam, callBack).doPay();
                break;
        }
    }

    public static void aliPay(Activity activity, String payParam, PayResultCallBack callBack){
        new AliPay(activity, payParam, callBack).doPay();
    }

    public static void wxPay(Activity activity, String payParam, PayResultCallBack callBack){
        new WxPay(activity, payParam, callBack).doPay();
    }

    public static void wxPay(Activity activity, PayReq payReq, PayResultCallBack callBack){
        new WxPay(activity, payReq, callBack).doPay();
    }
}
