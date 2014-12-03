package com.wb.wizapp.rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;

import android.net.Uri;

public class RestAPIHostFactory {

	private static Map<String, RestAPIHost> hostCache = new HashMap<String, RestAPIHost>();

	public synchronized static RestAPIHost getHost(String uri) {
		RestAPIHost host = hostCache.get(uri);

		if (host == null) {
			Uri u = Uri.parse(uri);
			host = new RestAPIHost(new HttpHost(u.getHost(), u.getPort(), u.getScheme()));
			hostCache.put(uri, host);
		}
		return host;
	}
}
