package com.example.jsj.streaming;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by jsj on 2017-11-05.
 */

public class Web_view2 extends Activity {
    private static final String BASE = "http://203.237.81.68:3000/video/";
    private WebView mWebView;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_web_view);
        Intent intent =getIntent();
        String s = intent.getExtras().getString("title");
        String str="OS소개영상";

        mWebView =(WebView)findViewById(R.id.webView);



        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setSupportMultipleWindows(true);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);


        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebView.getSettings().setSupportMultipleWindows(false);






        mWebView.loadUrl(BASE+s);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());




    }



    private class WebViewClientClass extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }


}
