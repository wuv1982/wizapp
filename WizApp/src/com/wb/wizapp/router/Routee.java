package com.wb.wizapp.router;

import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.wb.wizapp.IConstants;
import com.wb.wizapp.rest.IJsonParsable;
import com.wb.wizapp.rest.JsonBean;
import com.wb.wizapp.rest.JsonObject;
import com.wb.wizapp.rest.RestAPIHostFactory;
import com.wb.wizapp.rest.RestAPISercieBuilderAdapter;
import com.wb.wizapp.rest.RestAPIService;
import com.wb.wizapp.rest.RestAPIService.RestMethod;
import com.wb.wizapp.ui.DetailActivity;
import com.wb.wizapp.ui.MainActivity;
import com.wb.wizapp.ui.RouterWebViewActivity;
import com.wb.wizapp.ui.zxing.ScanActivity;

public enum Routee implements RouteeCallback {

	ROUTEE_BROWSER {
		@Override
		public void run(Context context, final Uri uri) {
			String url = uri.getQueryParameter("url");
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			context.startActivity(intent);
		}
	},
	ROUTEE_WEBVIEW {
		@Override
		public void run(Context context, final Uri uri) {
			Intent intent = new Intent(context, RouterWebViewActivity.class);
			intent.putExtras(Router.getBundle(uri));
			context.startActivity(intent);
		}
	},
	ROUTEE_ZXING {
		@Override
		public void run(Context context, final Uri uri) {
			Intent intent = new Intent(context, ScanActivity.class);
			context.startActivity(intent);
		}
	},
	ROUTEE_MAIN {
		@Override
		public void run(Context context, final Uri uri) {
			Intent intent = new Intent(context, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtras(Router.getBundle(uri));
			context.startActivity(intent);
		}
	},
	ROUTEE_DETAIL {
		@Override
		public void run(Context context, final Uri uri) {
			Intent intent = new Intent(context, DetailActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtras(Router.getBundle(uri));
			context.startActivity(intent);
		}
	},
	ROUTEE_LOAD {
		@Override
		public void run(Context context, final Uri uri) {
			RouterWebViewActivity activity = (RouterWebViewActivity) context;
			String url = uri.getQueryParameter("url");
			activity.load(url);
		}
	},
	ROUTEE_REFRESH {
		@Override
		public void run(Context context, final Uri uri) {
			RouterWebViewActivity activity = (RouterWebViewActivity) context;
			activity.refresh();
		}
	},
	ROUTEE_CLOSE {
		@Override
		public void run(Context context, final Uri uri) {
			Activity activity = (Activity) context;
			activity.finish();
		}
	},
	ROUTEE_TOAST {
		@Override
		public void run(Context context, final Uri uri) {
			String msg = uri.getQueryParameter("msg");
			String length = uri.getQueryParameter("length");
			int duration = Toast.LENGTH_SHORT;
			if (length.equals("long")) {
				duration = Toast.LENGTH_LONG;
			}
			Toast.makeText(context, msg, duration).show();
		}
	},
	ROUTEE_API_GET {
		@Override
		public void run(Context context, final Uri uri) {
			String url = uri.toString();
			RestAPIHostFactory.getHost(url).asyncCall(context,
					new RestAPIService(context, url, RestMethod.GET, new RestAPISercieBuilderAdapter<JsonBean>()));
		}
	},
	ROUTEE_API_DELETE {
		@Override
		public void run(Context context, final Uri uri) {
			String url = uri.toString();
			RestAPIHostFactory.getHost(url).asyncCall(context,
					new RestAPIService(context, url, RestMethod.DELETE, new RestAPISercieBuilderAdapter<JsonBean>()));
		}
	},
	ROUTEE_API_PUT {
		@Override
		public void run(Context context, final Uri uri) {
			String url = uri.getScheme() + "://" + uri.getHost() + "/" + uri.getPath();
			RestAPIHostFactory.getHost(url).asyncCall(context,
					new RestAPIService(context, url, RestMethod.PUT, new RestAPISercieBuilderAdapter<IJsonParsable>() {
						public IJsonParsable getBody() {
							JsonObject jsonObj = new JsonObject();
							Set<String> keys = uri.getQueryParameterNames();
							for (Iterator<String> it = keys.iterator(); it.hasNext();) {
								String key = it.next();
								String val = uri.getQueryParameter(key);
								try {
									jsonObj.put(key, val);
								} catch (JSONException e) {
									Log.w(IConstants.LOG_TAG, "Json Put:" + key + "=" + val);
								}
							}

							return jsonObj;
						};
					}));
		}

	},
	ROUTEE_API_POST {
		@Override
		public void run(Context context, final Uri uri) {
			String url = uri.getScheme() + "://" + uri.getHost() + "/" + uri.getPath();
			RestAPIHostFactory.getHost(url).asyncCall(context,
					new RestAPIService(context, url, RestMethod.POST, new RestAPISercieBuilderAdapter<IJsonParsable>() {
						public IJsonParsable getBody() {
							JsonObject jsonObj = new JsonObject();
							Set<String> keys = uri.getQueryParameterNames();
							for (Iterator<String> it = keys.iterator(); it.hasNext();) {
								String key = it.next();
								String val = uri.getQueryParameter(key);
								try {
									jsonObj.put(key, val);
								} catch (JSONException e) {
									Log.w(IConstants.LOG_TAG, "Json Put:" + key + "=" + val);
								}
							}

							return jsonObj;
						};
					}));
		}
	},

	ROUTEE_EVENT;

	@Override
	public void run(Context context, final Uri uri) {
		Log.i(IConstants.LOG_TAG, "trigger event: " + uri);
		Router.getRouter().triggerEvent(uri);
	}

	@Override
	public String scheme() {
		return this.name().toLowerCase(Locale.US);
	}
}
