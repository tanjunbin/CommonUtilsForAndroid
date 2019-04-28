# CommonUtilsForAndroid
Android 常用的工具类集合,为开发节约时间和成本<br>

### 特点

- 简单易用
- 灵活

### 贡献

在项目中引用:
 ```groovy
    // RxPermissions
    implementation 'com.github.tbruyelle:rxpermissions:0.10.2'
    // RxJava
    implementation 'io.reactivex.rxjava2:rxjava:2.2.5'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
    implementation 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
    // jackson
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.8.1'
  ```
### 使用
  1.下载Module :lib_utils_android.<br>
  2.并导入到自己的项目中.<br>
  3.需要在项目的build.gradle下allprojects->repositories 添加maven:<br>
  ```groovy
   repositories {
        maven { url 'https://jitpack.io' }
        maven { url "https://oss.sonatype.org/content/groups/public" }
   }
  ```
  4.在app->build.gradle 添加:
  ```groovy
  implementation project(':lib_utils_android')
  ```
        
 
 ## 工具简介
 > 由于篇幅太大,介绍部分工具的使用.我会在代码中详细的注释如何使用及参数含义,欢迎大家star
 #### 公共接口BaseInterface
 ```java
  public interface BaseInterface {
    void afterComplete(); //事件成功后调用

    void afterError(); //事件失败后调用
 }
 ```
 ##### 权限工具PermissionUtils
 > 大家都知道android 6.0 之后.都需要用户再一次确认权限.所以为大家封装了权限方法:
 

 ```java
 public interface PermissionCallBack extends BaseInterface{
 }
```

```java
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
 
 ```
 


### 备注

注释都比较详细,大家一看就懂什么意思.<br>如有疑问(或者有好的工具类):请加QQ:3030844193.

我们共同为码农们献出绵薄之力.<br>如果对你有所帮助,回来请给个star
