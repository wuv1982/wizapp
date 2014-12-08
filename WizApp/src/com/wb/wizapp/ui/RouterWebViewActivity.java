package com.wb.wizapp.ui;

import android.os.Bundle;
import android.webkit.CookieSyncManager;

public class RouterWebViewActivity extends BaseActivity {

	private RouterWebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		webview = new RouterWebView(this);
		setContentView(webview);
	}

	@Override
	protected void onResume() {
		super.onResume();
		CookieSyncManager.getInstance().startSync();
	}

	@Override
	protected void onPause() {
		super.onPause();
		CookieSyncManager.getInstance().stopSync();
	}

	public void refresh(String url) {
		webview.loadUrl(url);
	}

}
