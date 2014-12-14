package com.wb.wizapp.rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;

import android.net.Uri;

public class RestAPIHostFactory {

	private static Map<String, RestAPIHost> hostCache = new HashMap<String, RestAPIHost>();

	public synchronized static RestAPIHost getHost(String url) {
		Uri uri = Uri.parse(url);
		String host = uri.getHost();
		RestAPIHost apiHost = hostCache.get(host);

		if (apiHost == null) {
			apiHost = new RestAPIHost(new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme()));
			hostCache.put(host, apiHost);
		}
		return apiHost;
	}
}
