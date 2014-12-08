package com.wb.wizapp.router;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.wb.wizapp.ui.DetailActivity;
import com.wb.wizapp.ui.MainActivity;
import com.wb.wizapp.ui.RouterWebViewActivity;
import com.wb.wizapp.ui.zxing.ScanActivity;

public enum Routee implements RouteeCallback {

	ROUTEE_BROWSER {
		@Override
		public void run(Context context, Uri uri) {
			String url = uri.getQueryParameter("url");
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			context.startActivity(intent);
		}
	},
	ROUTEE_WEBVIEW {
		@Override
		public void run(Context context, Uri uri) {
			Intent intent = new Intent(context, RouterWebViewActivity.class);
			intent.putExtras(Router.getBundle(uri));
			context.startActivity(intent);
		}
	},
	ROUTEE_ZXING {
		@Override
		public void run(Context context, Uri uri) {
			Intent intent = new Intent(context, ScanActivity.class);
			context.startActivity(intent);
		}
	},
	ROUTEE_REFRESH {
		@Override
		public void run(Context context, Uri uri) {
			RouterWebViewActivity activity = (RouterWebViewActivity) context;
			String url = uri.getQueryParameter("url");
			activity.refresh(url);
		}
	},
	ROUTEE_MAIN {
		@Override
		public void run(Context context, Uri uri) {
			Intent intent = new Intent(context, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtras(Router.getBundle(uri));
			context.startActivity(intent);
		}
	},
	ROUTEE_DETAIL {
		@Override
		public void run(Context context, Uri uri) {
			Intent intent = new Intent(context, DetailActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtras(Router.getBundle(uri));
			context.startActivity(intent);
		}
	};

	@Override
	public void run(Context context, Uri uri) {
		throw new RuntimeException("unkonw routee:" + uri.toString());
	}

	@Override
	public String scheme() {
		return this.name().toLowerCase(Locale.US);
	}
}
