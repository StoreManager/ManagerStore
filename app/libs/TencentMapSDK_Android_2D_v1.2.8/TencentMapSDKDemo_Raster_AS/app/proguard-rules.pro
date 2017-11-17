# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/wangxiaokun/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#Tencent map sdk
-libraryjars libs/TencentMapSDK_Raster_v1.2.4_4af1d6f.jar
-keep class com.tencent.mapsdk.**{*;}
-keep class com.tencent.tencentmap.**{*;}

#Tencent locate sdk
-libraryjars libs/TencentLocationSDK_vTencentLocationSDK_v4.5.8.jar
-keep class com.tencent.map.**{*;}
-keep class com.tencent.tencentmap.**{*;}
-keep class ct.**{*;}
-dontwarn ct.**


# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**

-dontwarn org.eclipse.jdt.annotation.**

-dontwarn android.location.Location

-dontwarn com.tencent.**
-dontnote ct.**
