package com.wb.wizapp.router;

import java.util.Iterator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class IntentRouter {

	private static final String SCHEME_ROUTER = "router";

	public IntentRouter() {
	}

	public static Intent map(Uri uri) {
		Intent intent = new Intent();

		String scheme = uri.getScheme();
		if (scheme.equalsIgnoreCase("http")) {
			// external browser
		} else if (scheme.equals(SCHEME_ROUTER)) {
			String host = uri.getHost();
			intent.putExtras(toBundle(uri));
		}

		return intent;
	}

	private static Bundle toBundle(Uri uri) {
		Bundle bundle = new Bundle();
		if (!uri.getQuery().equals("")) {
			for (Iterator<String> it = uri.getQueryParameterNames().iterator(); it.hasNext();) {
				String key = it.next();
				String val = uri.getQueryParameter(key);
				if (val != null) {
					bundle.putString(key, val);
				}
			}
		}
		return bundle;
	}

}
