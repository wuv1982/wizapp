package com.wb.wizapp.router;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

public class Router {

	private static final String SCHEME_ROUTER = "router";
	private static Router underlying;

	private final Context context;
	private final Map<String, RouteeCallback> routerMap;

	private Router(Context context) {
		this.context = context;
		routerMap = new HashMap<String, RouteeCallback>();
	}

	public static void load(Context context, List<Routee> routees) {
		underlying = new Router(context);

		for (Routee r : routees) {

		}
	}

	public static Router getRouter() {
		if (underlying == null) {
			throw new RuntimeException("router should be used after init");
		}
		return underlying;
	}

	public void map(String uriString, RouteeCallback callback) {

		routerMap.put(uriString, callback);

		// Uri uri = Uri.parse(uriString);
		// Intent intent = new Intent();
		//
		// String scheme = uri.getScheme();
		// if (scheme.equalsIgnoreCase("http")) {
		// // external browser
		// } else if (scheme.equals(SCHEME_ROUTER)) {
		// String host = uri.getHost();
		// intent.putExtras(getBundle(uri));
		//
		// if (host.equals("list")) {
		// String action = uri.getQueryParameter("action");
		// if (action.equals("refresh")) {
		//
		// } else if (action.equals("next")) {
		//
		// }
		// } else if (host.equals("item")) {
		// String action = uri.getQueryParameter("action");
		//
		// if (action.equals("add")) {
		//
		// } else if (action.equals("delete")) {
		//
		// } else if (action.equals("get")) {
		//
		// } else if (action.equals("update")) {
		//
		// }
		// }
		// }
		//
		// context.startActivity(intent);
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
