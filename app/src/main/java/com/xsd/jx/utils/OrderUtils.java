package com.xsd.jx.utils;

/**
 * Date: 2020/9/12
 * author: SmallCake
 */
public class OrderUtils {

//    public static void onAdapterEvent(BaseActivity activity, BaseQuickAdapter mAdapter, SwipeRefreshLayout refreshLayout, OnAdapterListener listener){
//        mAdapter.setOnItemClickListener((adapter, view, position) -> {
//            OrderBean item = (OrderBean) adapter.getItem(position);
//            int itemType = item.getItemType();
//            activity.goActivity(OrderInfoActivity.class,itemType);
//            switch (itemType){
//                case 0:
//                    break;
//                case 1:
//                    break;
//                case 2:
//                    break;
//                case 3:
//                    break;
//                case 4:
//                    break;
//                case 5:
//                    break;
//                case 6:
//                    break;
//                case 7:
//                    break;
//            }
//        });
//        mAdapter.addChildClickViewIds(R.id.tv_order_comment,R.id.tv_cancel);
//        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//            OrderBean item = (OrderBean) adapter.getItem(position);
//            switch (view.getId()){
//                case R.id.tv_order_comment:
//                    activity.goActivity(CommentActivity.class);
//                    break;
//                case R.id.tv_cancel:
//                    orderCancel(activity,mAdapter,item.getId(),position);
//                    break;
//            }
//        });
//        //加载更多
//        mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
//            if (listener!=null)listener.loadMore();
//        });
//        //下拉刷新
//        refreshLayout.setOnRefreshListener(() -> {
//            if (listener!=null)listener.onRefresh();
//        });
//    }

    //取消订单
//    public static void orderCancel(BaseActivity activity, BaseQuickAdapter mAdapter, int id, int position) {
//        activity.getDataProvider().order.cancel(id)
//                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
//                    @Override
//                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
//                        ToastUtil.showLong(baseResponse.getData().getMessage());
//                        mAdapter.remove(position);
//                    }
//                });
//
//    }
}
