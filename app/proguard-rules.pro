# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


#android-support
-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆
# 把混淆类中的方法名也混淆了
-useuniqueclassmembernames
# 优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification
# 不做预检验,preverify是proguard的四个步骤之一,Android不需要preverify,去掉这一步可以加快混淆速度
-dontpreverify
# 将文件来源重命名为“SourceFile”字符串
#-renamesourcefileattribute SourceFile
# 保留行号
-keepattributes SourceFile,LineNumberTable
-keep class * implements android.os.Parcelable {public static final android.os.Parcelable$Creator *;}
# 注解
-keepattributes *Annotation*
# 异常
-keepattributes Exceptions
# 泛型
-keepattributes Signature
# 反射
-keepattributes EnclosingMethod

-dontwarn android.support.**
-dontnote android.support.**
-keep class android.support.v7.widget.RoundRectDrawable { *; }

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}

-keepclassmembers,includedescriptorclasses public class * extends android.view.View {
    void set*(***);
    *** get*();
}
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
-keep public class * implements java.io.Serializable {*;}
-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#ImmersionBar
-keep class com.gyf.immersionbar.* {*;}
-dontwarn com.gyf.immersionbar.**
# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# banner 的混淆代码
-keep class com.youth.banner.** {*;}
 #BaseRecyclerViewAdapterHelper
 -keep class com.chad.library.adapter.** {*;}
 -keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
 -keep public class * extends com.chad.library.adapter.base.BaseViewHolder
 -keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
      <init>(...);
 }
 # Apollo
 -dontwarn com.esotericsoftware.kryo.**
 -dontwarn org.objenesis.instantiator.**
 -dontwarn org.codehaus.**
 -dontwarn java.nio.**
 -dontwarn java.lang.invoke.**
 -keep class com.lsxiao.apollo.generate.** { *; }
 # XPopup
 -dontwarn com.lxj.xpopup.widget.**
 -keep class com.lxj.xpopup.widget.**{*;}
 #greenDAO
 -keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
 public static java.lang.String TABLENAME;
 }
 -keep class **$Properties {*;}
 -dontwarn net.sqlcipher.database.**
 -dontwarn rx.**
 # databinding
 -dontwarn androidx.databinding.**
 -keep class androidx.databinding.** { *; }

-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
-keep class org.xz_sale.entity.**{*;}
# 网络请求接口需要排除混淆
-keep class com.xsd.jx.api.**{ *;}
# 日历控件
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}

