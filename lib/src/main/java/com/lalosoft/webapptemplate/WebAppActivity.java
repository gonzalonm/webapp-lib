package com.lalosoft.webapptemplate;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by gonzalonm on 5/10/17
 */

public abstract class WebAppActivity extends AppCompatActivity {

    protected abstract String getRootUrl();

    protected abstract void onNetworkConnectionDisabled();

    private WebView webView;
    private String failingUrl = null;
    private Bundle savedInstanceState;

    // region Android Framework methods

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_webapp);
        webView = (WebView) findViewById(R.id.webview);

        loadWebApp();
    }

    @Override
    public void onBackPressed() {
        if (webView != null) {
            if (canGoBack(webView)) {
                webView.goBack();
            } else {
                onFinishApp(webView);
            }
        } else {
            onWebViewIsNull();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (webView != null) {
            webView.saveState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (webView != null) {
            webView.restoreState(savedInstanceState);
        }
    }

    // endregion

    // region override methods by subclasses

    protected boolean canGoBack(WebView webView) {
        return webView.canGoBack();
    }

    protected void onWebViewIsNull() {
        finish();
    }

    protected void onFinishApp(WebView webView) {
        finish();
    }

    protected void onPageStarted(WebView view, String url, Bitmap favicon) {}

    // endregion

    // region private implementation

    private void loadWebApp() {
        if (savedInstanceState == null) {
            loadData();
        }
    }

    private void loadData() {
        if (isNetworkAvailable()) {
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setSupportZoom(true);
            webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    WebAppActivity.this.onPageStarted(view, url, favicon);
                }

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    WebAppActivity.this.failingUrl = failingUrl;
                }
            });

            if (failingUrl != null) {
                webView.loadUrl(failingUrl);
                failingUrl = null;
            } else {
                webView.loadUrl(getRootUrl());
            }
        } else {
            onNetworkConnectionDisabled();
        }
    }

    /**
     * Checks the network availability
     *
     * @return true if there is network connection
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // endregion

}
