## 支付组件

## 使用说明
* 此model 集合了支付宝支付和微信支付, 用法在 DemoActivity 中有详细用法

### 步骤一
* 支付宝: 如果使用本地生成支付参数的方式, 需要调用 AliLocalParamCreator.init() 或 AliLocalParamCreator.init_v2();
如果使用服务器签名方式, 则不需要调用

```java
    //旧版
    AliLocalParamCreator.init("partner","seller","rsa_private", "http://notify_url");

    //新版
    AliLocalParamCreator.init_v2("appId","rsa2_private","rsa_private", "http://notify_url");
```

* 微信: 需要设置 AppId

```java
    PayUtil.initWx("wxappid");
```

### 步骤二
* 支付宝:

```java
    //使用从服务器获取签名参数
    PayUtil.pay(activity, PayType.ALI, signUrl, payResultCallBack);


    //使用本地签名方式
    //旧版
    String signURL = AliLocalParamCreator.create("测试", "测试物品", "0.01", "tradeNo");
    //新版
    String signURL = AliLocalParamCreator.create_v2("测试", "测试物品", "0.01", "tradeNo");

    PayUtil.pay(activity, PayType.ALI, signUrl, payResultCallBack);
```

* 微信:

```java
    //使用 jsonParam 方式
    PayUtil.pay(activity, PayType.WX, jsonParam, payResultCallBack);


    //如果使用自己构造的 PayReq
    PayReq request = new PayReq();
    request.appId = "wxd930ea5d5a258f4f";
    request.partnerId = "1900000109";
    request.prepayId= "1101000000140415649af9fc314aa427",;
    request.packageValue = "Sign=WXPay";
    request.nonceStr= "1101000000140429eb40476f8896f4c9";
    request.timeStamp= "1398746574";
    request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";

    PayUtil.wxPay(activity, request, payResultCallBack);
```


* 注意:

> 1. 微信如果使用字符串方式调用微信支付, payParam 必须为 <font color=red size=5>json 格式</font>, 且必须包含 <font color=red size=5>sign、timestamp、partnerid、noncestr、prepayid、packageValue、appid</font> 等字段，且<font color=red size=5>大小写相同</font>。
> 如果不满足此要求, 请使用自己构造 PayReq 的方式调用微信支付

> 2. 微信支付必须打<font color=red size=5>正式包</font>才会有效果,如果发现微信支付没有被调起来, 请检查是否为<font color=red size=5>正式包</font>和检查 <font color=red size=5>AppId</font> 是否正确



### 步骤三
* 在 payResultCallBack 中处理支付结果

```java
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
        //当 errorCode 不足以获取更多信息时, 请通过 rawErrorCode 获取更多信息

        Toast.makeText(this, "支付失败 " + errorCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPayCancel(PayType type) {
        Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
    }
```

### 其它
* 支付宝SDK使用jar包引入, 更新时间: 2016/11/23
* 微信SDK使用gradle方式引入, 版本号: 1.0.2
* 参考文档

    > [支付宝支付](https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.SBsK7A&treeId=204&articleId=105296&docType=1)

    > [微信支付](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_1)

### 更新日志

    2017.1.22

        1. 修改 PayResultCallBack 接口, 所有方法如: onXXX() 皆 变为onPayXXX();
           onPayError 添加 rawErrorCode 参数, 方便查看原始错误码

        2. 修改 AliLocalParamCreator: init() 为旧版支付宝初始化方法, init_v2() 为新版支付宝初始化方法
           create() 为旧版支付宝签名方法, create_v2() 为新版支付宝签名方法
           create() 和 create_v2() 都添加 tradeNo 参数, 可自定义商户订单号, 为空则自动生成