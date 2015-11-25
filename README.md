# AndPlug | Android Plug | [![Build Status](https://travis-ci.org/ourbeehive/AndPlug.png?branch=master)](https://travis-ci.org/ourbeehive/AndPlug)

## About
![Logo with Title](http://ourbeehive.github.io/AndPlug/logo_with_name.png)

AndPlug(Android Plug) is a complete project based on several common and useful libs like auto-update, location and message push, aimed to build an Android App with these libs quickly.  

## Module Libs

### App
App is the demo Android Application for AndPlug, show how to use the module libs and how to setup an App with the modules. [more](https://github.com/ourbeehive/AndPlug/App/blob/master/README_CN.md)

[![Google Play](https://developer.android.com/images/brand/en_app_rgb_wo_60.png)](https://play.google.com/store/apps/details?id=)

[![fir.im](http://ourbeehive.github.io/AndPlug/qrcode.png)](http://fir.im/AndPlug)
 
### Lib
Lib is the lib include some utils and widgets. [more](https://github.com/ourbeehive/AndPlug/Lib/blob/master/README.md)

### BaiduLoc
BaiduLoc is the lib support location provider powered by [Baidu locsdk](http://developer.baidu.com/map/index.php?title=android-locsdk). [more](https://github.com/ourbeehive/AndPlug/BaiduLoc/blob/master/README.md)

### BaiduMap
BaiduMap is the lib support map provider powered by [Baidu map sdk](http://developer.baidu.com/map/index.php?title=androidsdk). [more](https://github.com/ourbeehive/AndPlug/BaiduMap/blob/master/README.md)

```gradle
dependencies {
    compile 'com.lean56.andplug:BaiduMap:1.0.0@aar'
}
```
### GetuiPush
GetuiPush is the lib support message push provider powered by [Getui](http://www.getui.com/). [more](https://github.com/ourbeehive/AndPlug/Getui/blob/master/README.md)

### UmengFeedback
UmengFeedback is the lib support user feedback provider powered by [Umeng Feedback](http://www.umeng.com/component_feedback). [more](https://github.com/ourbeehive/AndPlug/UmengFeedback/blob/master/README.md)

### UmengUpdate
UmengUpdate is the lib support app auto update provider powered by [Umeng Update](http://www.umeng.com/component_update). [more](https://github.com/ourbeehive/AndPlug/UmengUpdate/blob/master/README.md)

```gradle
dependencies {
    compile 'com.lean56.andplug:UmengUpdate:1.0.1@aar'
}
```
### UniversalImage
UniversalImage is the lib support image cache network load and image post. [more](https://github.com/ourbeehive/AndPlug/UniversalImage/blob/master/README.md)

### VoiceIflytek
VoiceIflytek is the lib support tts provider powered by [Iflytek](http://www.xfyun.cn/). [more](https://github.com/ourbeehive/AndPlug/VoiceIflytek/blob/master/README.md)

## How to Publish

### Publish to maven and jcente
*  add local.properties with bintray.user = yourusername and bintray.apikey = yourapikey under root folder
*  edit module gradle build file add publish setting 
*  run `gradlew bintrayUpload` for global project upload or `gradlew :ModuleName:bintrayUpload` for subModule publish
*  the jar/doc will be publish in maven, and add to jcenter if you want

## License

    Copyright (C) 2015 OurBeehive(http://ourbeehive.github.io/)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


