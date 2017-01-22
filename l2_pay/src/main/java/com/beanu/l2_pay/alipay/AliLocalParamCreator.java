package com.beanu.l2_pay.alipay;

import android.text.TextUtils;

import com.beanu.l2_pay.util.OrderInfoUtil2_0;
import com.beanu.l2_pay.util.SignUtils;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by lizhihua on 2017/1/18.
 */

public class AliLocalParamCreator {
    //商户PID
    public static String PARTNER = "";
    //商户收款账号
    public static String SELLER = "";
    //通知地址
    public static String NOTIFY_URL = "";

    /**
     * 支付宝支付业务：入参app_id
     */
    public static String APPID = "";
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static String RSA2_PRIVATE = "";
    public static String RSA_PRIVATE = "";

    @Deprecated
    public static void init(String partner, String seller, String rsaPrivate, String notifyUrl) {
        PARTNER = partner;
        SELLER = seller;
        RSA_PRIVATE = rsaPrivate;
        NOTIFY_URL = notifyUrl;
    }

    public static void init_v2(String appId, String rsa2Private, String rsaPrivate, String notifyUrl) {
        APPID = appId;
        RSA2_PRIVATE = rsa2Private;
        RSA_PRIVATE = rsaPrivate;
        NOTIFY_URL = notifyUrl;
    }

    /**
     *  支付宝本地签名
     * @param subject 商品名称
     * @param body 商品描述
     * @param price 价格
     * @param tradeNo 商户订单号，该值在商户端应保持唯一（可自定义格式规范）, 为空则自动生成
     * @return signUrl
     */
    public static String create_v2(String subject, String body, String price, String tradeNo) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = !TextUtils.isEmpty(RSA2_PRIVATE);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, subject, body, price, NOTIFY_URL,tradeNo, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);

        return orderParam + "&" + sign;
    }

    /**
     *  支付宝本地签名
     * @param subject 商品名称
     * @param body 商品描述
     * @param price 价格
     * @param tradeNo 商户订单号，该值在商户端应保持唯一（可自定义格式规范）, 为空则自动生成
     * @return signUrl
     */
    @Deprecated
    public static String create(String subject, String body, String price, String tradeNo) {
        // 订单
        String orderInfo = localGenOrderInfo(subject, body, price, tradeNo);

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

    private static String localGenOrderInfo(String subject, String body, String price, String tradeNo) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + (TextUtils.isEmpty(tradeNo) ? genOutTradeNo() : tradeNo) + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + (TextUtils.isEmpty(NOTIFY_URL) ? "http://notify.msp.hk/notify.htm" : NOTIFY_URL)
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
