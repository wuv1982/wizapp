package com.wb.wizapp.router;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

public class Router {

	private static Router underlying;

	private final Context context;
	private final Map<String, RouteeCallback> routerMap;

	private Router(Context context) {
		this.context = context;
		routerMap = new HashMap<String, RouteeCallback>();
	}

	// public static void load(Context context, List<Routee> routees) {
	// underlying = new Router(context);
	//
	// for (Routee r : routees) {
	//
	// }
	// }

	public static Router getRouter() {
		if (underlying == null) {
			throw new RuntimeException("router should be used after init");
		}
		return underlying;
	}

	public void map(String uriString) {

		Uri uri = Uri.parse(uriString);
		String scheme = uri.getScheme();

		Routee.valueOf(scheme.toUpperCase(Locale.US)).run(context, uri);
	}

	public void putEvent(String event, RouteeCallback callback) {
		if (!routerMap.containsKey(event)) {
			routerMap.put(event, callback);
		}
	}

	public void triggerEvent(String event, Uri uri) {
		routerMap.get(event).run(context, uri);
	}

	public static Bundle getBundle(Uri uri) {
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
