package com.wb.wizapp.rest;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.util.Log;

import com.wb.wizapp.Constants;

public class RestAPIHost {
	private final HttpHost host;
	private final HttpClient httpClient;

	public RestAPIHost(final HttpHost host) {
		this(host, null);
	}

	public RestAPIHost(final HttpHost host, final String comstomiseUA) {
		this.host = host;

		httpClient = new DefaultHttpClient();
		HttpParams clientParams = httpClient.getParams();

		HttpClientParams.setRedirecting(clientParams, false);
		HttpConnectionParams.setConnectionTimeout(clientParams, 30000);
		HttpConnectionParams.setSoTimeout(clientParams, 30000);

		if (comstomiseUA != null) {
			final String customiseUserAgent = clientParams.getParameter(CoreProtocolPNames.USER_AGENT) + ";"
					+ comstomiseUA;
			clientParams.setParameter(CoreProtocolPNames.USER_AGENT, customiseUserAgent);
		}
	}

	public String generateRequestUrl(final RestAPIService service) {
		return host.toURI() + service.getRequest().getRequestLine().getUri();
	}

	public void asyncCall(final RestAPIService service) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				RestAPIHost.this.call(service);
				return null;
			}
		}.execute();
	}

	public void call(final RestAPIService service) {
		try {
			httpClient.execute(host, service.getRequest(), service);
			Log.d(Constants.LOG_TAG, "http request sent");
		} catch (Exception e) {
			Log.e(Constants.LOG_TAG, "http request failed", e);
			service.builder.onFailed(IRestAPIServiceBuilder.STATUS_CODE_EXCEPTION, null);
		}
	}
}
