
# 匠心

# 工人段主要模块分为三大模块

 - 找活 {@link JobFragment} 页面基本在job包
 - 订单 {@link OrderFragment} 页面基本在order包
 - 我的 {@link MineFragment} 页面基本在mine包

1. 弹框统一管理，都在PopShowUtils工具类中

2. 新建的网络接口和实现都在com.xsd.jx.inject包中统一注入

3. 订单数据适配器OrderAdapter的加载更多，下拉刷新，点击事件在OrderUtils中统一处理

  

# 企业端四大模块

- 我的招工

- 工人考勤

- 工资结算

- 发布招工

  

1.【招工人】GetWorkersActivity

接口：server/index,   WorkerResponse 第一页会返回用户的招工工种WorkTypeBean

企业端首页适配器`WorkerAdapter`,适配对象`WorkerBean`



2.【我的招工】MyGetWorkersActivity

接口：server/work-list

适配器`MyWorkersAdapter` ,适配对象`MyGetWorkersResponse.ItemsBean`点击进入GetWorkersInfoActivity

​    状态：-1:不展示(有预付款项未付不显示给用户) ) 1:正在招 2:已招满/待开工(所有用户已确认) 3:工期中 4:待结算 5:待评价 6:已完成 7.已取消

```xml
addItemType(1, R.layout.item_mygetworkers_geting);
addItemType(2, R.layout.item_mygetworkers_full);
addItemType(3, R.layout.item_mygetworkers_working);
addItemType(4, R.layout.item_mygetworkers_waitpay);
addItemType(5, R.layout.item_mygetworkers_waitcomment);
addItemType(6, R.layout.item_mygetworkers_completion);
addItemType(7, R.layout.item_mygetworkers_cancel);
```



3.【招工详情】GetWorkersInfoActivity

适配器`GetWorkersInfoAdapter`,适配对象`WorkerBean`,它来之`MyGetWorkersResponse.ItemsBean`对象中的`WorkerBean`

状态：-1:不展示(有预付款项未付不显示给用户) ) 1:正在招 2:已招满/待开工(所有用户已确认) 3:工期中 4:待结算 5:待评价 6:已完成 7.已取消

```xml
addItemType(1, R.layout.item_getinfo_apply_workers);
addItemType(2, R.layout.item_getinfo_full);
addItemType(3, R.layout.item_getinfo_full);
addItemType(4, R.layout.item_getinfo_waitpay);
addItemType(5, R.layout.item_getinfo_waitcomment);
addItemType(6, R.layout.item_order_completion);
addItemType(7, R.layout.item_order_cancel);
addItemType(8, R.layout.item_worker_full);
```



