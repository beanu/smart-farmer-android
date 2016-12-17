# smart-farmer-android
整合常用的业务模块，快速开发项目

本项目基于模块开发的思想，GUI层使用MVP框架以及整合最新的android开发技术，包括但不限于（Retrofit Rxjava OKhttp Glide LiteOrm......）,持续更新!

===
###架构图

![框架图](screenshot/frame.png)

**1.第一层** [Arad](https://github.com/beanu/Arad)基础类库
- Retrofit
- Rxjava
- Okhttp
- Glide
- LiteOrm
- avtivity fragment adapter基础类
- 上拉下拉基础Adapter，常用的util等

**2.第二层** 可依赖的第三方功能库(特点：可单独使用的工具型模块)
- [图片选择器](l2_imageselector)
- 二维码扫描
- 支付宝＋微信支付
- 推送（小米，华为，极光）
- [分享](l2_shareutil)

**3.第三层** 业务模块＋可抽离并可复用的模块
- 登陆模块
- 新闻模块
- 个人中心
- 设置中心
- 商品详情
- 购物车
- 订单模块
- 向导页
- *单列表页*
- *带有分类表头的列表页*
- *九宫格*
- *地图展示*
- *首页*
......

**4.第四层** 主APP布局
- 底部导航
- 侧滑
