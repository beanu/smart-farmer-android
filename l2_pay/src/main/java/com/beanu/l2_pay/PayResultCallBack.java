package com.beanu.l2_pay;

/**
 * Created by lizhihua on 2017/1/18.
 */

public interface PayResultCallBack {
    String ERROR_RESULT = "1";   //支付结果解析错误
    String ERROR_PAY = "2"; //支付出现错误
    String ERROR_NETWORK = "3";  //网络连接错误
    String NO_OR_LOW_WX = "4";   //未安装微信或微信版本过低
    String ERROR_PAY_PARAM = "5";  //支付参数错误

    /**
     * 支付成功
     * @param type 支付类型
     */
    void onPaySuccess(PayType type);

    /**
     * 正在处理中 小概率事件 此时以验证服务端异步通知结果为准
     * @param type 支付类型
     */
    void onPayDealing(PayType type);

    /**
     * 支付失败
     * @param type 支付类型
     * @param errorCode 错误码
     * @param rawErrorCode 原始错误码, 当 errorCode 不足以获取更多信息时, 请通过 rawErrorCode 获取更多信息
     */
    void onPayError(PayType type, String errorCode, String rawErrorCode);

    /**
     * 支付取消
     * @param type  支付类型
     */
    void onPayCancel(PayType type);
}
