package com.xsd.jx.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.xsd.jx.listener.OnLocationListener;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Date: 2020/3/13
 * author: SmallCake
 * 地理编码：根据给定的地名，获得具体的位置信息（比如经度和纬度，以及地址的全称） {@link LocationUtils#getLoactionAddr(Activity, String)}
 * 反地理编码：根据给定的经度和纬度，获取具体的位置信息
 *
 * Android系统定位工具类：
 * LocationManager.GPS_PROVIDER类型得定位不移动不触发，
 * 故本工具类使用LocationManager.NETWORK_PROVIDER的方式进行定位，可根据需要改变定位方式
 *
 * 必要条件
 * 需要AndroidManifest.xml配置权限
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 * 代码中申请权限
 */
public class LocationUtils {
    /**
     * 获取定位Location信息
     * @param activity 上下文
     */
    public static void getLocationInfo(Activity activity, OnLocationListener listener){
        //1.获取定位服务
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        //2.获取当前位置信息中
        boolean gpsIsOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);//GPS定位是否打开
        if (gpsIsOpen) {
            startLocation(activity,locationManager,listener);
        }else {
            new AlertDialog.Builder(activity)
                    .setTitle("GPS服务")
                    .setMessage("请打开GPS！")
                    .setPositiveButton("去开启", (dialog, which) ->{
                        LocationUtils.openLocationSet(activity);
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
    }
    /**
     * 定位查询条件
     * 返回查询条件 ，获取目前设备状态下，最适合的定位方式
     */
    private static String getProvider(LocationManager loacationManager) {
        // 构建位置查询条件
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        //Criteria.ACCURACY_FINE,当使用该值时，在建筑物当中，可能定位不了,建议在对定位要求并不是很高的时候用Criteria.ACCURACY_COARSE，避免定位失败
        // 查询精度：高
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 是否查询海拨：否
        criteria.setAltitudeRequired(false);
        // 是否查询方位角 : 否
        criteria.setBearingRequired(false);
        // 是否允许付费：是
        criteria.setCostAllowed(false);
        // 电量要求：低
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        // 返回最合适的符合条件的provider，第2个参数为true说明 , 如果只有一个provider是有效的,则返回当前provider
        return loacationManager.getBestProvider(criteria, true);
    }
    private static void openLocationSet(Activity activity){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivityForResult(intent,0);
    }

    private static void startLocation(Activity activity,LocationManager locationManager,OnLocationListener listener) {
        L.e("gps已打开，开始获取定位权限....");
        //为获取地理位置信息时设置查询条件 是按GPS定位还是network定位
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        L.e("已获取定位权限,创建定位配置...");
        String bestProvider = LocationUtils.getProvider(locationManager);
        L.e("定位配置："+bestProvider+",  开始通过配置获取本地定位对象...");
        //定位方法，第二个参数指的是产生位置改变事件的时间间隔，单位为微秒，第三个参数指的是距离条件，单位为米
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (location==null)return;
                        if (listener!=null)listener.locationSuccess(location);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });

    }
    /**
     * 6.0动态申请权限
     */
    private static void requestPermission(Activity activity) {
        XXPermissions.with(activity)
                .permission(Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            ToastUtil.showLong("获取定位权限成功");
                        } else {
                            ToastUtil.showLong("获取权限成功，部分权限未正常授予");
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            ToastUtil.showLong("被永久拒绝授权，请手动授予定位权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(activity, denied);
                        } else {
                            ToastUtil.showLong("获取定位权限失败");
                        }
                    }
                });
    }
    /**
     * 地理编码
     * @param activity 上下文
     * @param addrName 地址名称
     * @return
     */
    public  static Address getLoactionAddr(Activity activity,String addrName){
        Geocoder geoCoder = new Geocoder(activity, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(addrName, 5);
            if (addresses==null||addresses.size()==0)return null;
            return addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
