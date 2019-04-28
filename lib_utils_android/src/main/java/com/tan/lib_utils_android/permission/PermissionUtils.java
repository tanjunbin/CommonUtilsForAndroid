package com.tan.lib_utils_android.permission;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.tan.lib_utils_android.libinterface.PermissionCallBack;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class PermissionUtils {

    /**
     * android.support.v4.app.FragmentActivity 申请权限
     * @param activity
     * @param callBack
     * @param permissions
     */
    @SuppressLint("CheckResult")
    public static void request(FragmentActivity activity, final PermissionCallBack callBack, String... permissions){
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean){
                    //申请的权限全部允许
                    callBack.afterComplete();
                }else{
                    //申请的权限失败
                    callBack.afterError();
                }
            }
        });
    }

    /**
     * android.support.v4.app.Fragment 申请权限
     * @param fragment
     * @param callBack
     * @param permissions
     */
    @SuppressLint("CheckResult")
    public static void request(Fragment fragment, final PermissionCallBack callBack, String... permissions){
        RxPermissions rxPermissions = new RxPermissions(fragment);
        rxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean){
                    //申请的权限全部允许
                    callBack.afterComplete();
                }else{
                    //申请的权限失败
                    callBack.afterError();
                }
            }
        });
    }
}
