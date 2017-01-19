## 支付组件

## 使用说明
* 此model 集合了支付宝支付和微信支付, 用法在 DemoActivity 中有详细用法

### 步骤一
* 支付宝: 如果使用本地生成支付参数的方式, 需要调用 AliLocalParamCreator.init(), 建议在 Application 的 onCreate() 中调用。如果使用服务器签名方式, 则不需要调用

```java
    AliLocalParamCreator.init("xxxxxxxxx","xxxxxxxxxxx","xxxxxxx", "http://xxx.com");
```

* 微信: 需要设置 AppId, 建议在 Application 的 onCreate() 中调用

```java
    PayUtil.initWx("xxxxxxxxxxxxxxxxx");
```

### 步骤二
* 支付宝:

```java
    //使用从服务器获取签名参数
    PayUtil.pay(activity, PayType.ALI, payParam, payResultCallBack);

    //使用本地签名方式
    PayUtil.pay(activity, PayType.ALI, AliLocalParamCreator.create("测试", "测试物品", "0.01"), payResultCallBack);
```

* 微信:

```java
    PayUtil.pay(activity, PayType.WX, payParam, payResultCallBack);

    //如果使用自己构造的 PayReq
    PayUtil.wxPay(activity, payReq, payResultCallBack);
```

* 注意:

> 1. 微信使用字符串方式调用微信支付, payParam 必须为 json 格式, 且必须包含 sign、timestamp、partnerid、noncestr、prepayid、packageValue、appid 等字段，且大小写相同

> 2. 微信支付必须打正式包才会有效果,如果发现微信支付没有被调起来, 请检查是否为正式包和检查 AppId 是否正确



### 步骤三
* 在 payResultCallBack 中处理支付结果

```java
    @Override
    public void onSuccess(PayType type) {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDealing(PayType type) {
        Toast.makeText(this, "支付中", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(PayType type, int errorCode) { //errorCode 请查看 PayResultCallBack 中的说明
        Toast.makeText(this, "支付失败 "+ errorCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(PayType type) {
        Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
    }
```
