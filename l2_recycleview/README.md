TODO zhihua

## 下拉刷新+上拉更多

下拉 上拉 在项目中是使用最频繁的操作，因此我们拿出单独的library对其优化

下拉刷新 使用更容易扩展以及很流行的开源库[android-Ultra-Pull-To-Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh)
上拉更多 参考google io里面的写法，使用Adapter控制

目的：解放劳动力

## pull to refresh使用说明

### 经典下拉刷新

### 自定义下拉刷新


## Simplest RecycleView使用说明

### 步骤一
创建recycle view对应的item_layout.xml

### 步骤二
创建Adapter，继承BaseLoadMoreAdapter 或者BaseHeaderLoadMoreAdapter

### 步骤三
创建契约类，并自动生成MVP接口，以及M P的实现

### 步骤四
创建自己的Activity ,不需要写布局文件，继承SimplestRecycleViewActivity

完毕！


## 其他

[源码解析](http://a.codekk.com/detail/Android/Grumoon/android-Ultra-Pull-To-Refresh%20%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90)
