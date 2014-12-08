package com.wb.wizapp.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@TargetApi(Build.VERSION_CODES.KITKAT)
@SuppressLint({ "SetJavaScriptEnabled" })
public class RouterWebView extends WebView {

	public RouterWebView(Context context) {
		super(context);
		init(context);
	}

	public RouterWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public RouterWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		WebSettings settings = this.getSettings();
		settings.setJavaScriptEnabled(true);

		WebViewClient client = new RouterWebViewClient();
		this.setWebViewClient(client);

		WebChromeClient chrome = new WebChromeClient();
		this.setWebChromeClient(chrome);

		RouterWebView.setWebContentsDebuggingEnabled(true); // TODO newApi
	}

	public class RouterWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}
	}
}
