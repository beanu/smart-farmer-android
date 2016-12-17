## 第三方分享及登录 [ShareUtil](https://github.com/shaohui10086/ShareUtil)

## 使用说明

### 步骤一
在app的build.gradle里面，找到defaultConfig，在此节点下增加qq id的信息，目的是去修改AndroidManifest.xml中变量

```groovy
defaultConfig {
    ...

    manifestPlaceholders = [
            //替换成申请下来的qq_id
            qq_id: "123456789"
    ]

}
```

### 步骤二
配置各个平台申请下来的ID，以及分享回调（写在Application的onCreate方法中）

```java
ShareConfig config = ShareConfig.instance()
            .qqId(QQ_ID)
            .wxId(WX_ID)
            .weiboId(WEIBO_ID)
            // 下面两个，如果不需要登录功能，可不填写
            .weiboRedirectUrl(REDIRECT_URL)
            .wxSecret(WX_ID);
ShareManager.init(config);
```

### 步骤三
分享的时候 调用

```java
ShareUtil.shareImage(this, SharePlatform.QQ, "http://image.com", shareListener);
ShareUtil.shareText(this, SharePlatform.WX, "分享文字", shareListener);
ShareUtil.shareMedia(this, SharePlatform.QZONE, "title", "summary", "targetUrl", "thumb", shareListener);
```

### 步骤四
登陆的时候 调用

例如，微博登陆调用
```java
// LoginPlatform.WEIBO  微博登录
// LoginPlatform.WX     微信登录
// LoginPlatform.QQ     QQ登录
final LoginListener listener = new LoginListener() {
        @Override
        public void loginSuccess(LoginResult result) {
            //登录成功， 如果你选择了获取用户信息，可以通过
        }

        @Override
        public void loginFailure(Exception e) {
            Log.i("TAG", "登录失败");
        }

        @Override
        public void loginCancel() {
            Log.i("TAG", "登录取消");
        }
    };
LoginUtil.login(MainActivity.this, LoginPlatform.WEIBO, mLoginListener, isFetchUserInfo);
```


### 说明
* QQ不支持纯文字分享，会直接分享失败
* 使用Jar文件的版本如下：

    微信版本：3.1.1
    QQ版本：3.1.0 lite版
    微博版本: 3.1.4

* 分享的bitmap，会在分享之后被回收掉，所以分享之后最好不要再对该bitmap做任何操作。
* example 中的代码可以参考，但是不可运行，因为需要保证包名以及签名文件和你申请各个平台id所填写信息保持一致
* ShareListener的回调结果仅供参考，不可当做分享是否返回的依据，它并不是那么完全可靠，因为某些操作，例如微博分享取消，但是用户选择了保存草稿，这时候客户端并不会收到回调，所以也就不会调用ShareListener的onCancel

###参考
[三件事：Aar, Manifest和Activity-Alias](http://shaohui.me/2016/12/10/three_thing_about_aar_manifest_activity-alias/)