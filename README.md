
# 匠心

主要模块分为三大模块：
 - 找活 {@link JobFragment} 页面基本在job包
 - 订单 {@link OrderFragment} 页面基本在order包
 - 我的 {@link MineFragment} 页面基本在mine包

1.弹框统一管理，都在PopShowUtils工具类中
2.新建的网络接口和实现都在com.xsd.jx.inject包中统一注入
3.订单数据适配器OrderAdapter的加载更多，下拉刷新，点击事件在OrderUtils中统一处理

