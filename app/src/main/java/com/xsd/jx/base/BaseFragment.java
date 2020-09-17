package com.xsd.jx.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import com.xsd.jx.inject.DaggerCommonComponent;
import com.xsd.jx.inject.DataProvider;
import com.xsd.jx.inject.NetWorkMoudle;
import com.xsd.jx.job.JobInfoActivity;

import javax.inject.Inject;

/**
 * Date: 2020/1/3
 * author: SmallCake
 *
 * 子类getLayoutId输入当前布局资源文件名
 * 子类onBindView加载一些View基础视图和初始化信息
 * 子类onLazyLoad做耗时处理，如网络请求等
 * 配合ViewPager需要设置viewPager.setOffscreenPageLimit(fragmnets.size);来实现懒加载机制，
 * 否则onBindView重复执行而onLazyLoad只执行一次，导致onLazyLoad中的操作无法再次执行的情况
 *
 * 1.配置了懒加载 {@link BaseFragment#onLazyLoad()}
 * 2.注入了数据提供者{@link DataProvider}
 */
public abstract class BaseFragment extends Fragment {
    private boolean isPrepared;//该页面是否已经准备完毕
    private boolean isLazyLoaded;//该页面是否已经懒加载过
    @Inject
    protected DataProvider dataProvider;

    protected abstract int getLayoutId();
    protected abstract void onBindView(View view, ViewGroup container, Bundle savedInstanceState);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        onBindView(view, container, savedInstanceState);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerCommonComponent.builder().netWorkMoudle(new NetWorkMoudle(this.getContext())).build().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }
    private void lazyLoad(){
        if (getUserVisibleHint()&&isPrepared&&!isLazyLoaded){
            onLazyLoad();
            isLazyLoaded = true;
        }
    }
    @UiThread
    protected abstract void onLazyLoad();

    public void goActivity(Class<?> clz){
        startActivity(new Intent(this.getActivity(), clz));
    }
    public void goActivity(Class<?> clz,int type){
        Intent intent = new Intent(this.getActivity(), clz);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    public void goJobInfoActivity(int workId){
        goActivity(JobInfoActivity.class,workId);
    }


}
