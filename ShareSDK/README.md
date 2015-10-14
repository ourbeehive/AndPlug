# ShareSdk
ShareSdk is used for 3rd-part login/share which is powered by [ShareSDK](http://sharesdk.mob.com/#/sharesdk) of [MOB](http://www.mob.com/)

## Support
This lib support 
1.  QQ/Wechat/SinaWeibo login
2.  QQ/Wechat Friends/Wechat Moments/SMS/Email share and copy to clipboard is also supported

ShareSDK official support see [this](http://mob.com/#/downloadDetail/ShareSDK/android) 

## How to use
1. include ShareSDK into your app with gradle `compile 'com.lean56.andplug:share:1.0.0'` or just copy lib into your workspace
2. copy below code into <application> section of your app's AndroidManifest file
`
<!-- sharesdk UI Shell-->
<activity android:name="com.mob.tools.MobUIShell"
          android:screenOrientation="portrait"
          android:theme="@android:style/Theme.Translucent.NoTitleBar"
          android:configChanges="keyboardHidden|orientation|screenSize"
          android:windowSoftInputMode="stateHidden|adjustResize">
    <!-- listen the UI lifecycle and get/edit/customized ui widget/action -->
    <meta-data android:name="AuthorizeAdapter"
               android:value="com.lean56.andplug.share.ShareSDKAuthorizeAdapter"/>

    <!-- QQ callback, scheme = tencent + appid-->
    <intent-filter>
        <data android:scheme="tencent123456"/>
        <action android:name="android.intent.action.VIEW"/>
        <category android:name="android.intent.category.DEFAULT"/>
        <category android:name="android.intent.category.BROWSABLE"/>
    </intent-filter>
</activity>

<!--wechat callback -->
<activity android:name=".wxapi.WXEntryActivity"
          android:screenOrientation="portrait"
          android:theme="@android:style/Theme.Translucent.NoTitleBar"
          android:configChanges="keyboardHidden|orientation|screenSize"
          android:exported="true">
</activity>
`
3. add 

## Version
ShareSDK version v2.6.3
This Lib version v1.0.0

## Change Log
**v1.0**
 1. init release to jcenter
