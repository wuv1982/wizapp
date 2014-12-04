package com.wb.wizapp.rest;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.wb.wizapp.Constants;
import com.wb.wizapp.concurret.UIAsyncTask;
import com.wb.wizapp.concurret.UISyncTask;
import com.wb.wizapp.ui.BaseActivity;

public class RestAPIHost {
	protected final HttpHost host;
	protected final HttpClient httpClient;

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

	public void asyncCall(Activity act, final RestAPIService service) {
		new UIAsyncTask<Void, Void, JSONObject>(act) {
			@Override
			protected JSONObject doInBackground(Void... params) {
				return RestAPIHost.this.call(service);
			}

			@Override
			protected void onPostExecute(JSONObject result) {
				Activity act = wkAct.get();
				if (act != null && service.builder != null) {
					service.builder.onPostResult(act, result);
				}
			};
		}.execute();
	}

	public void uiSyncCall(BaseActivity act, final RestAPIService service) {
		new UISyncTask<Void, Void, JSONObject>(act) {
			@Override
			protected JSONObject doInBackground(Void... params) {
				return RestAPIHost.this.call(service);
			}

			@Override
			protected void onPostExecute(JSONObject result) {
				super.onPostExecute(result);

				BaseActivity act = wkAct.get();
				if (act != null && service.builder != null) {
					service.builder.onPostResult(act, result);
				}
			};
		}.execute();
	}

	public JSONObject call(final RestAPIService service) {
		JSONObject rs = null;
		try {
			rs = httpClient.execute(host, service.getRequest(), service);
			Log.d(Constants.LOG_TAG, "http request sent");
		} catch (Exception e) {
			Log.e(Constants.LOG_TAG, "http request failed", e);
			if (service.builder != null) {
				service.builder.onExcpetion(e);
			}
		}
		return rs;
	}
}
