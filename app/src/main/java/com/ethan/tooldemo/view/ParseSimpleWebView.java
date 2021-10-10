package com.ethan.tooldemo.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by Allen on 2016/7/15.
 * 原生webview 由于考虑安全原因屏蔽了 <input type="file"/>，代码已解除限制
 */
public class ParseSimpleWebView extends WebView{
    private static final boolean DEBUG = true;
    private static final String TAG = "SimpleWebView";
    private static final int REQUEST_CODE_FILE_PICKER = 127;

    private ProgressBar loadingProgressbar;
    private boolean showLoadingProgressBar;
    private ViewParent parent;
    //如果不是RelativeLayout，FrameLayout，需要偏移webview内容让progressBar来置顶
    private boolean needOffset;
    private boolean hideErrorPage; //隐藏错误页面，用Page not found代替
    private TextView errorTextView;
    private int progressHeight = 8;
    /** File upload callback for platform versions prior to Android 5.0 */
    protected ValueCallback<Uri> mFileUploadCallbackFirst;
    /** File upload callback for Android 5.0+ */
    protected ValueCallback<Uri[]> mFileUploadCallbackSecond;
    protected String mUploadableFileTypes = "*/*";
    protected int mRequestCodeFilePicker = REQUEST_CODE_FILE_PICKER;
    protected WeakReference<Activity> mActivity;
    protected WeakReference<Fragment> mFragment;
    protected WeakReference<Fragment> mSupportFragment;



    public ParseSimpleWebView(Context context) {
        this(context, null);
    }

    public ParseSimpleWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParseSimpleWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ParseSimpleWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public ParseSimpleWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        init();
    }

    public void showLoadingProgress(boolean show) {
        showLoadingProgressBar = show;
    }

    public void hideErrors(boolean hide) {
        hideErrorPage = hide;
    }

    private void init() {
        //initProgressBar();
        initContext();
        commonPreference();
        tf02Preference();
    }

    private void initContext() {
        Context context = getContext();
        if (context instanceof Activity) {
            mActivity = new WeakReference<Activity>((Activity)context);
        }
    }

    private void tf02Preference() {
        showLoadingProgress(true);
        hideErrors(true);
    }

    private void commonPreference() {
        // 加载错误时如果直接重新载入指定内容或页面，虽然
        // 看起来效果不错，但是会造成goBack()问题，没找到
        // 好的方法，所以采用一个简单的TextView盖在上面
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                //return true;
                return false; // handler by webview self
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showErrorView(false);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                if (DEBUG) Log.i(TAG, "Page load error");
                if (DEBUG) Log.i(TAG, "Error Code: " + errorCode);
                if (DEBUG) Log.i(TAG, "Error Description: " + description);
                if (DEBUG) Log.i(TAG, "Error Url: " + failingUrl);
                if (DEBUG) Log.i(TAG, "hideErrorPage = " + hideErrorPage);
                if (hideErrorPage) {
                    replaceErrorPage(view);
                } else {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
            }

            @Override
            @TargetApi(Build.VERSION_CODES.M)
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (hideErrorPage) {
                    replaceErrorPage(view);
                } else {
                    super.onReceivedError(view, request, error);
                }
            }

            @Override
            @TargetApi(Build.VERSION_CODES.M)
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                if (hideErrorPage) {
                    replaceErrorPage(view);
                } else {
                    super.onReceivedHttpError(view, request, errorResponse);
                }
            }

            private void replaceErrorPage(WebView view) {
                //用javascript隐藏系统定义的404页面信息
                try {
                    String data = "Page not found !";
                    String htmlData ="<html><body><div align=\"left\" ><h1>Load error, Please try again:)</h1></div></body></html>";
                    //view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
                    //view.loadUrl("about:blank");

//                    view.loadUrl("javascript: window.location.href=\"about:blank\"");

//                    view.stopLoading();
//                    view.clearView();
//                    view.loadData(htmlData, "text/html", "utf-8");

//                    view.loadDataWithBaseURL(null,htmlData, "text/html", "UTF-8",view.getUrl());
//                    view.invalidate();

                    showErrorView(true);

                } catch (Exception e) {
                    e.printStackTrace();
                    if (DEBUG) Log.d(TAG, "Replace error page error, Body not found");
                }
            }
        });
        //启用js
        getSettings().setJavaScriptEnabled(true);
        //不使用缓存
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //支持缩放
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);

        setSaveEnabled(true);
        getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT < 18) {
            getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);// 开启缓存第三方 cookie的支持
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); // 支持 http
        }

        //去掉缩按钮
        getSettings().setDisplayZoomControls(false);
        //设计自定义WebChromeClient，用来监听进度，重写文件弹出框等
        setWebChromeClient(mWebChromeClient);
        //监听view加载完成
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initProgressBar();
                initErrorView();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void initProgressBar() {
        loadingProgressbar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
//
        parent = getParent();
        if (parent instanceof RelativeLayout) {
            RelativeLayout group = (RelativeLayout) parent;
            loadingProgressbar.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    progressHeight));
            group.addView(loadingProgressbar);
        } else if (parent instanceof FrameLayout) {
            FrameLayout group = (FrameLayout) parent;
            loadingProgressbar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    progressHeight));
            group.addView(loadingProgressbar);
        } else {
            needOffset = true;
            loadingProgressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, progressHeight, 0, 0));
            addView(loadingProgressbar);
        }

        loadingProgressbar.setVisibility(GONE);
    }

    private void initErrorView() {
        if (errorTextView == null) {
            errorTextView = new TextView(getContext());
            errorTextView.setBackgroundColor(0xffffffff);
            errorTextView.setText("载入功能失败，请稍候重试:)");
            //errorTextView.setText("Load error, Please try again:)");
            errorTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            errorTextView.setTextSize(26);
            errorTextView.setVisibility(GONE);
            errorTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            ViewGroup p = (ViewGroup) getParent();
            p.addView(errorTextView);
        }
    }

    private void showErrorView(boolean show) {
        if (errorTextView == null) return;
        if (show) {
            errorTextView.setVisibility(VISIBLE);
            errorTextView.setClickable(true);

        } else {
            errorTextView.setVisibility(GONE);
            errorTextView.setClickable(false);
        }
    }

    private WebChromeClient mWebChromeClient =new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (showLoadingProgressBar && loadingProgressbar != null) {
                if (newProgress == 100) {
                    loadingProgressbar.setVisibility(GONE);
                    if (needOffset)
                        ((MarginLayoutParams)getLayoutParams()).setMargins(0,0,0,0);
                } else {
                    if (loadingProgressbar.getVisibility() != VISIBLE)
                        loadingProgressbar.setVisibility(VISIBLE);
                    if (needOffset) {
                        if (((MarginLayoutParams)getLayoutParams()).topMargin != progressHeight+4) {
                            ((MarginLayoutParams)getLayoutParams()).topMargin = progressHeight+4;
                        }
                    }
                    loadingProgressbar.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }


        // Upload file,  Code from AdvancedWebView.java
        // file upload callback (Android 2.2 (API level 8) -- Android 2.3 (API level 10)) (hidden method)
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, null);
        }

        // file upload callback (Android 3.0 (API level 11) -- Android 4.0 (API level 15)) (hidden method)
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openFileChooser(uploadMsg, acceptType, null);
        }

        // file upload callback (Android 4.1 (API level 16) -- Android 4.3 (API level 18)) (hidden method)
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileInput(uploadMsg, null, false);
        }

        // file upload callback (Android 5.0 (API level 21) -- current) (public method)
        @SuppressWarnings("all")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (Build.VERSION.SDK_INT >= 21) {
                final boolean allowMultiple = fileChooserParams.getMode() == FileChooserParams.MODE_OPEN_MULTIPLE;

                openFileInput(null, filePathCallback, allowMultiple);

                return true;
            }
            else {
                return false;
            }
        }






//        // Enable file upload
//        // For 3.0+ Devices (Start)
//        // onActivityResult attached before constructor
//        protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
//            if (DEBUG) Log.d(TAG, "openFileChooser API 3.0+");
//            mFileUploadCallbackFirst = uploadMsg;
//            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//            i.addCategory(Intent.CATEGORY_OPENABLE);
//            i.setType("image/*");
//            ((Activity)getContext()).startActivityForResult(Intent.createChooser(i, "File Browser"), REQUEST_CODE_FILE_PICKER);
//        }
//
//        // For Lollipop 5.0+ Devices
//        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
//            if (DEBUG) Log.d(TAG, "openFileChooser API 5.0+");
//            if (mFileUploadCallbackSecond != null) {
//                mFileUploadCallbackSecond.onReceiveValue(null);
//                mFileUploadCallbackSecond = null;
//            }
//            mFileUploadCallbackSecond = filePathCallback;
//
//            Intent intent = fileChooserParams.createIntent();
//            try {
//                ((Activity)getContext()).startActivityForResult(intent, REQUEST_CODE_FILE_PICKER);
//            } catch (ActivityNotFoundException e) {
//                mFileUploadCallbackSecond = null;
//                Toast.makeText(getContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
//                return false;
//            }
//            return true;
//        }
//
//        //For Android 4.1 only
//        protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//            if (DEBUG) Log.d(TAG, "openFileChooser API 4.1");
//            mFileUploadCallbackFirst = uploadMsg;
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("image/*");
//            ((Activity)getContext()).startActivityForResult(Intent.createChooser(intent, "File Browser"), REQUEST_CODE_FILE_PICKER);
//        }
//
//        protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
//            if (DEBUG) Log.d(TAG, "openFileChooser API other");
//            mFileUploadCallbackFirst = uploadMsg;
//            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//            i.addCategory(Intent.CATEGORY_OPENABLE);
//            i.setType("image/*");
//            ((Activity)getContext()).startActivityForResult(Intent.createChooser(i, "File Chooser"), REQUEST_CODE_FILE_PICKER);
//        }

    };

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (showLoadingProgressBar && needOffset && loadingProgressbar != null) {
            LayoutParams lp = (LayoutParams) loadingProgressbar.getLayoutParams();
            lp.x = l;
            lp.y = t;
            loadingProgressbar.setLayoutParams(lp);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setFragment(Fragment fragment) {
        mFragment = new WeakReference<Fragment>(fragment);
    }

    public void setSupportFragment(Fragment fragment) {
        mSupportFragment = new WeakReference<Fragment>(fragment);
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (requestCode == mRequestCodeFilePicker) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    if (mFileUploadCallbackFirst != null) {
                        mFileUploadCallbackFirst.onReceiveValue(intent.getData());
                        mFileUploadCallbackFirst = null;
                    }
                    else if (mFileUploadCallbackSecond != null) {
                        Uri[] dataUris = null;

                        try {
                            if (intent.getDataString() != null) {
                                dataUris = new Uri[] { Uri.parse(intent.getDataString()) };
                            }
                            else {
                                if (Build.VERSION.SDK_INT >= 16) {
                                    if (intent.getClipData() != null) {
                                        final int numSelectedFiles = intent.getClipData().getItemCount();

                                        dataUris = new Uri[numSelectedFiles];

                                        for (int i = 0; i < numSelectedFiles; i++) {
                                            dataUris[i] = intent.getClipData().getItemAt(i).getUri();
                                        }
                                    }
                                }
                            }
                        }
                        catch (Exception ignored) { }

                        mFileUploadCallbackSecond.onReceiveValue(dataUris);
                        mFileUploadCallbackSecond = null;
                    }
                }
            }
            else {
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst.onReceiveValue(null);
                    mFileUploadCallbackFirst = null;
                }
                else if (mFileUploadCallbackSecond != null) {
                    mFileUploadCallbackSecond.onReceiveValue(null);
                    mFileUploadCallbackSecond = null;
                }
            }
        }
    }

    @SuppressLint("NewApi")
    protected void openFileInput(final ValueCallback<Uri> fileUploadCallbackFirst,
                   final ValueCallback<Uri[]> fileUploadCallbackSecond, final boolean allowMultiple) {
        if (mFileUploadCallbackFirst != null) {
            mFileUploadCallbackFirst.onReceiveValue(null);
        }
        mFileUploadCallbackFirst = fileUploadCallbackFirst;

        if (mFileUploadCallbackSecond != null) {
            mFileUploadCallbackSecond.onReceiveValue(null);
        }
        mFileUploadCallbackSecond = fileUploadCallbackSecond;

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);

        if (allowMultiple) {
            if (Build.VERSION.SDK_INT >= 18) {
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }
        }

        i.setType(mUploadableFileTypes);

        if (mFragment != null && mFragment.get() != null && Build.VERSION.SDK_INT >= 11) {
            if (DEBUG) Log.d(TAG, "Fragment startActivity");
            mFragment.get().startActivityForResult(Intent.createChooser(i, "请选择文件"), mRequestCodeFilePicker);
        } else if (mSupportFragment != null && mSupportFragment.get() != null) {
            if (DEBUG) Log.d(TAG, "Fragment v4 startActivity");
            mSupportFragment.get().startActivityForResult(Intent.createChooser(i, "请选择文件"), mRequestCodeFilePicker);
        } else if (mActivity != null && mActivity.get() != null) {
            if (DEBUG) Log.d(TAG, "Activity startActivity");
            mActivity.get().startActivityForResult(Intent.createChooser(i, "请选择文件"), mRequestCodeFilePicker);
        }
    }
}
