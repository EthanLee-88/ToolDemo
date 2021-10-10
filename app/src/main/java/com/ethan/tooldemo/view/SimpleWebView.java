package com.ethan.tooldemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SimpleWebView extends WebView {
    private static final String TAG = "SimpleWebView";

    public SimpleWebView(@NonNull Context context) {
        this(context, null);
    }

    public SimpleWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true); // 运行js脚本
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //允许Js弹窗
        settings.setLoadWithOverviewMode(true);// 自适应屏幕
        settings.setDisplayZoomControls(false); // 缩放控件的显示
        settings.setSupportZoom(true); // 支持缩放
        settings.setTextZoom(100); // 设置字体缩放
        addJavascriptInterface(new JavaToJs(), "javaToString"); // 提供给Js调用
        settings.setDefaultTextEncodingName("UTF-8"); //编码方式
        setWebChromeClient(mWebChromeClient);
        setWebViewClient(mWebViewClient);
        delWithCache();
    }

    private void delWithCache() {
        WebSettings settings = getSettings();
        // 设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 开启DOM storage API 功能
        settings.setDomStorageEnabled(true);
        // 开启database storage API功能
//        settings.setDatabaseEnabled(true);
//        String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + "WebViewCache";
//        Log.i("cachePath", cacheDirPath);
        // 设置数据库缓存路径
//        settings.setAppCachePath(cacheDirPath);
//        settings.setAppCacheEnabled(true);
        // 清除缓存
//        clearCache(true);

//        evaluateJavascript(); // 调用js端的代码，在 pagefinished后调用

        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);// 开启缓存第三方 cookie的支持
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); // 支持 http
        }
    }

    private class JavaToJs { // 提供给Js调用
        @JavascriptInterface
        public String getString() {
            return "";
        }
    }

    public boolean onBackPressed() {  // 处理返回键
        if (canGoBack()) {
            goBack();
            return true;
        } else {
            return false;
        }
    }

    public String getCookieString(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        String cookieStr = cookieManager.getCookie(url);
        Log.d(TAG, "cookieStr = " + cookieStr);
        return cookieStr;
    }

    public interface OnProgressChangedListener { // 监听加载进度改变
        void progress(int pro);
    }

    private OnProgressChangedListener mOnProgressChangedListener; // 监听加载进度改变

    public void setOnProgressChangedListener(OnProgressChangedListener onProgressChangedListener) { // 监听加载进度改变
        this.mOnProgressChangedListener = onProgressChangedListener;
    }

    private void setProgressChanged(int progress) { // 监听加载进度改变
        if (this.mOnProgressChangedListener == null) return;
        this.mOnProgressChangedListener.progress(progress);
    }

    public interface OnReceivedTitleListener {  // 获取网站标题
        void received(String title);
    }

    private OnReceivedTitleListener mOnReceivedTitleListener; // 获取网站标题

    public void setOnReceivedTitleListener(OnReceivedTitleListener onReceivedTitleListener) { // 获取网站标题
        this.mOnReceivedTitleListener = onReceivedTitleListener;
    }

    private void setTitleReceive(String title) { // 获取网站标题
        if (this.mOnReceivedTitleListener == null) return;
        this.mOnReceivedTitleListener.received(title);
    }

    private boolean isOverrideUrlLoading = false; // 是否处理新加载的 Url

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) { // 处理js中的 Alert对话框
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) { // 处理js中的 Confirm对话框
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) { // 处理 js中的 Prompt对话框
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) { // 获取加载进度
            super.onProgressChanged(view, newProgress);
            setProgressChanged(newProgress);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) { // 获取网页的 icon
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) { // 获取网页的标题
            super.onReceivedTitle(view, title);
            setTitleReceive(title);
        }
    };

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) { // 网页开始加载
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) { // 网页加载完毕
            super.onPageFinished(view, url);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) { //更新历史记录
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        @Override
        public void onLoadResource(WebView view, String url) { // 即将加载制定Url资源
            super.onLoadResource(view, url);
        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) { // 缩放发生改变
            super.onScaleChanged(view, oldScale, newScale);
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) { // 是否处理按键事件
            return super.shouldOverrideKeyEvent(view, event);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { // WebView是否处理新加载的Url
            if (isOverrideUrlLoading) loadUrl(url);
            return isOverrideUrlLoading;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (isOverrideUrlLoading) loadUrl(request.getUrl().getPath());
            return isOverrideUrlLoading;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) { // 遇到不可恢复的错误
            super.onReceivedError(view, request, error);
        }
    };
}
