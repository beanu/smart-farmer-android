## 二维码扫描

zxing 版本3.3.0

## 使用说明

### 步骤一
### 步骤二
### 步骤三


##自定义样式
覆盖同名布局文件zxing_code_scan.xml

##自定义扫描框大小
CameraManager.java文件中找到getFramingRect方法
修改宽和高
```java
int width = screenResolution.x * 3 / 5;
int height = screenResolution.y * 3 / 5;
```


## 其他