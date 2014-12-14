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
	private Map<String, RouteeCallback> eventMap;

	private Router(Context context) {
		this.context = context;
		this.eventMap = new HashMap<String, RouteeCallback>();
	}

	public static Router getRouter() {
		if (underlying == null) {
			throw new RuntimeException("router should be used after init");
		}
		return underlying;
	}

	public void map(String uriString) {

		Uri uri = Uri.parse(uriString);
		String scheme = uri.getScheme();

		Routee r = Routee.valueOf(scheme.toUpperCase(Locale.US));
		if (r != null) {
			r.run(context, uri);
		}
	}

	public void registerEvent(String uriString, RouteeCallback callback) {
		Uri uri = Uri.parse(uriString);
		String event = uri.getHost();
		if (!eventMap.containsKey(event)) {
			eventMap.put(event, callback);
		}
	}

	protected void triggerEvent(Uri uri) {
		String event = uri.getHost();
		if (eventMap.containsKey(event)) {
			eventMap.get(event).run(context, uri);
		}
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
