TYUT_URP 第三方客户端
===================================  
## 功能实现
- [x] 学籍信息（实现）
- [x] 成绩（实现）
- [x] 自主实践（实现）
- [x] 学分绩点（实现）
- [ ] 本学期课表
- [x] 一键评教（实现）注:目前只针对中期评教
- [x] 通知公告（实现）注:UI需要改进
- [ ] 主题切换

## 项目范围
> * 网络请求
> * JSOUP解析
> * 验证码Cookie（自动登录等）
> * Session验证
> *  本地数据保存
> * Material Design风格

## 截图预览
![github](http://www.jin123d.com/wp-content/uploads/2015/09/device-2015-11-17-131737-169x300.png "github")    ![github](http://www.jin123d.com/wp-content/uploads/2015/09/device-2015-11-17-132020-169x300.png "github")  ![github](http://www.jin123d.com/wp-content/uploads/2015/09/device-2015-11-17-120028-169x300.png "github")  
![github](http://www.jin123d.com/wp-content/uploads/2015/09/32-169x300.png "github")  ![github](http://www.jin123d.com/wp-content/uploads/2015/09/device-2015-11-17-131940-169x300.png "github")  ![github](http://www.jin123d.com/wp-content/uploads/2015/09/QQ%E6%88%AA%E5%9B%BE20151117132823-170x300.png "github")  

## 开发进度
### v_0.96
> * （优化）优化查询线程

### v_0.95
> * （增加）增加侧边栏
> * （修复）修复一个因为逻辑错误导致无限重复登录的bug
> * （增加）关于界面

### v_0.92
> * （调整）主界面UI,根据material design
> * （调整）更换签名为正式版签名

### v_0.91

> * （修复）修复评教内容中文乱码的bug，
> * （增加）增加关于界面

### v_0.9
> * （增加）增加一键评教功能:10s就可以评估完成20门以上科目

### v_0.8
> * （修复）登录时获取数据超时处理

### v_0.7
> * （增加）本学期课表（尚未完成）
> * （增加）通知公告逻辑完成，UI需要继续调整

### v_0.6
> * （修改）主页面改为列表形式，加入图标
> * （修改）调整登录页面UI，调整登录button
> * （增加）成绩查询中加入不及格成绩的统计信息（尚未通过、曾经未通过）
> * （修改）登录页面的Toast一部分修改为Snackbar

### v_0.5
> * （增加）不及格成绩完成（尚不及格、曾不及格）
> * （修改）修改tabIndicatorColor和colorAccent
> * （修改）调整成绩查询页面UI

### v_0.4
> * （增加）取消Actionbar,替换为Toolbar
> * （增加）加入material design颜色处理
> * （增加）成绩查询中采用TabLayout 制作选项卡，选项卡制作完成，不及格成绩未完成
> * （增加）头部加入AppBarLayout，按照Google规范

### v_0.3
> * （增加）全局加入请求超时处理，防止卡死
> * （修复）修复一些可能导致崩溃的bug
> * （优化）自主实践查询中字体过大导致显示不完整的bug
> * （修复）全局ProgressBarDialog提示修正
> * （修复）进入APP后验证码获取时修复progressbar的正常显示
> * （增加）增加登录失败提示信息（如密码错误、验证码错误等）

### v_0.2
> * （增加）获取验证码时将验证码输入框置空
> * （更新）界面主题更换为DarkActionbar
> * （增加）退出登录加入提示
> * （增加）增加父级Activity
> * （增加）双击退出，防止误操作

### v_0.1
> * 登录urp
> * 获取及格成绩、个人信息、自主实践、学分绩点
> * 记住密码、自动登录功能
> * 退出登录功能
> * 及格成绩和自主实践采用baseAdapter

## 我的网站  
http://www.jin123d.com 

## 二维码下载
![github](http://www.jin123d.com/wp-content/uploads/2015/09/1447738775.png "github") 
-----------------------------------  