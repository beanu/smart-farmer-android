package com.beanu.l2_pay.alipay;

import android.text.TextUtils;

import com.beanu.l2_pay.util.SignUtils;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by lizhihua on 2017/1/18.
 */

public class AliLocalParamCreator {
    //商户PID
    public static String PARTNER = "";
    //商户收款账号
    public static String SELLER = "";
    //商户私钥，pkcs8格式
    public static String RSA_PRIVATE = "";
    //通知地址
    public static String NOTIFY_URL = "";

    public static void init(String partner, String seller, String rsaPrivate, String notifyUrl){
        PARTNER = partner;
        SELLER = seller;
        RSA_PRIVATE = rsaPrivate;
        NOTIFY_URL = notifyUrl;
    }

    public static String create(String subject, String body, String price){
        // 订单
        String orderInfo = localGenOrderInfo(subject, body, price);

        // 对订单做RSA 签名
        String sign = localSign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        return orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
    }

    private static String localSign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    private static String localGenOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + genOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + (TextUtils.isEmpty(SELLER) ? "http://notify.msp.hk/notify.htm" : SELLER)
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private static String genOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    private static String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
