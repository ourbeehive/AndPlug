package com.lean56.andplug.voiceiflytek;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.iflytek.cloud.*;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/**
 * Speecher powered by iflytck
 *
 * @author charles
 */
public class Speecher {

    private final static String TAG = Speecher.class.getSimpleName();

    private final static String APPID = "5354bcd5";
    SpeechUtility speechUtility;
    private static Speecher speecher;
    // Synthesizer
    private static SynthesizerListener synListener;
    private static SpeechSynthesizer synthesizer;
    // Recognizer
    private static RecognizerDialog recognizerDialog;
    // 语音听写对象
    private static SpeechRecognizer recognizer;

    private Speecher(Context context) {
        // 此接口在非主进程调用会返回 null 对象
        // 如需在非主进程使用语音功能，请使用参数：SpeechConstant.APPID +"=12345678," + SpeechConstant.FORCE_LOGIN +"=true"
        speechUtility = SpeechUtility.createUtility(context, SpeechConstant.APPID + "=" + APPID);
    }

    /**
     * Singleton pattern implementation
     *
     * @return
     */
    public static Speecher getSynthesizerInstance(Context context) {
        if (null == speecher) {
            speecher = new Speecher(context);
        }

        if (null == synthesizer || null == synListener) {
            initSynthesizer(context);
        }

        return speecher;
    }

    /**
     * Singleton pattern implementation
     *
     * @return
     */
    public static Speecher getRecognizerInstance(Context context, boolean dialogShow) {
        if (null == speecher) {
            speecher = new Speecher(context);
        }

        if (null == recognizer) {
            initRecognizer(context, dialogShow);
        }

        return speecher;
    }

    /**
     * init Synthesizer
     *
     * @param context
     */
    private static void initSynthesizer(Context context) {
        // 1.创建 SpeechSynthesizer 对象,第二个参数本地合成时传InitListener
        synthesizer = SpeechSynthesizer.createSynthesizer(context, null);
        // 2.合成参数设置，详见《科大讯飞MSC API手册(Android)》 SpeechSynthesizer 类
        synthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        synthesizer.setParameter(SpeechConstant.SPEED, "50");//设置语速
        synthesizer.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        synthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //如果不需要保存合成音频，注释该行代码
        // mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");

        //合成监听器
        synListener = new SynthesizerListener() {
            //会话结束回调接口，没有错误时， error为null
            @Override
            public void onCompleted(SpeechError error) {}

            //缓冲进度回调
            //percent为缓冲进度0~100， beginPos为缓冲音频在文本中开始位置， endPos表示缓冲音频在文本中结束位置， info为附加信息。
            @Override
            public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}

            //开始播放
            @Override
            public void onSpeakBegin() {}

            //暂停播放
            @Override
            public void onSpeakPaused() {}

            //播放进度回调
            //percent为播放进度0~100,beginPos为播放音频在文本中开始位置， endPos表示播放音频在文本中结束位置.
            @Override
            public void onSpeakProgress(int percent, int beginPos, int endPos) {}

            //恢复播放回调接口
            @Override
            public void onSpeakResumed() {}

            //会话事件回调接口
            @Override
            public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {}
        };
    }

    /**
     * init Recognizer and Params
     *
     * @param context
     * @param ui
     */
    private static void initRecognizer(Context context, boolean ui) {

        InitListener mInitListener = new InitListener() {

            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    Log.e(TAG, "初始化失败，错误码：" + code);
                }
            }
        };

        if (ui) {
            // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
            recognizerDialog = new RecognizerDialog(context, mInitListener);
        } else {
            // 1. SpeechRecognizer 对象
            recognizer = SpeechRecognizer.createRecognizer(context, mInitListener);
            // 2. 语音识别参数设置
            recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
            recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            recognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        }
    }

    // recognizer
    public void listening(RecognizerDialogListener recognizerDialogListener) {
        if (recognizerDialog.isShowing()) {
            recognizerDialog.dismiss();
        }
        // 显示听写对话框
        recognizerDialog.setListener(recognizerDialogListener);
        recognizerDialog.show();
    }

    public void deafness() {
        if (recognizerDialog.isShowing()) {
            recognizerDialog.dismiss();
        }
    }

    // synthesizer
    public int speaking(String text) {
        if (synthesizer.isSpeaking()) {
            shutup();
        }
        return synthesizer.startSpeaking(text, synListener);
    }

    public void shutup() {
        synthesizer.stopSpeaking();
    }

    public void destroy() {
        if (null != synthesizer) {
            if (synthesizer.isSpeaking()) {
                shutup();
            }
            synthesizer.destroy();
        }

        if (null != recognizer) {
            if (recognizer.isListening()) {
                recognizer.stopListening();
            }
            recognizer.destroy();
        }

        if (null != recognizerDialog) {
            if(recognizerDialog.isShowing()) {
                recognizerDialog.dismiss();
            }
            recognizerDialog.destroy();
        }
    }

}
