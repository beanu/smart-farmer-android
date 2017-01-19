package com.beanu.l2_pay;

/**
 * Created by lizhihua on 2017/1/18.
 */

public interface PayResultCallBack {
    int ERROR_RESULT = 1;   //支付结果解析错误
    int ERROR_PAY = 2;  //支付失败 对应支付宝错误代码: "4000", 对应微信错误代码: -1
    int ERROR_NETWORK = 3;  //网络连接错误
    int NO_OR_LOW_WX = 4;   //未安装微信或微信版本过低
    int ERROR_PAY_PARAM = 5;  //支付参数错误

    void onSuccess(PayType type); //支付成功
    void onDealing(PayType type);    //正在处理中 小概率事件 此时以验证服务端异步通知结果为准
    void onError(PayType type, int errorCode);   //支付失败
    void onCancel(PayType type);    //支付取消
}
