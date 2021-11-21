package com.ethan.tooldemo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ethan.tooldemo.view.MySimpleWebView;
import com.ethan.tooldemo.view.SimpleWebView;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        BridgeWebView simpleWebView = findViewById(R.id.id_simple_web_view);
//        simpleWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzIwMTAz" +
//                "MTMxMg%3D%3D&chksm=8eec91f3b99b18e5d58f81e88d2a28abee0e26e2f60582fbeb2a1d10cf47e" +
//                "0cd8f33cbab263e&idx=1&mid=2649496076&scene=21&sn=cc4d3c9b2bbb4" +
//                "91da27a02c971ca31d5#wechat_redirect");

//        simpleWebView.loadUrl("http://mpvideo.qpic.cn/0bf2hqabeaaagqaneku3nrpfapg" +
//                "dci6aaeqa.f10003.mp4?dis_k=3203c9cc69df27e3ecd3b2371b4e8047&dis_t=16" +
//                "37470996&vid=wxv_1303141291533942784&format_id=10003&support_redirect=0&mmversion=false");

//        simpleWebView.loadUrl("https://3g.163.com/v/video/VAOCU88TO.html");

//        simpleWebView.loadUrl("file:///android_asset/simple.html");
//        simpleWebView.postDelayed(() -> {
//            simpleWebView.loadUrl("javascript:callAndroid()");
//        }, 3000);
        initView(simpleWebView);
    }

    private void initView(BridgeWebView simpleWebView){
        simpleWebView.loadUrl("file:///android_asset/demo.html");
        simpleWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
                function.onCallBack("submitFromWeb exe, response data from Java");
            }
        });
    }
}
