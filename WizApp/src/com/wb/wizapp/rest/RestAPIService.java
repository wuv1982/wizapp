package com.wb.wizapp.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.wb.wizapp.IConstants;

public class RestAPIService implements ResponseHandler<JSONObject> {

	public enum RestMethod {
		GET, PUT, POST, DELETE,
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.CONSTRUCTOR)
	protected static @interface Path {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.CONSTRUCTOR)
	protected static @interface EncodePath {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.CONSTRUCTOR)
	protected static @interface Method {
		RestMethod value();
	}

	protected final Context context;
	protected final IRestAPIServiceBuilder<? extends IJsonParsable> builder;
	private RestMethod method;
	private String path;

	/**
	 * simple rest API service
	 * 
	 * @param context
	 * @param url
	 * @param method
	 * @param builder
	 */
	public RestAPIService(Context context, String url, RestMethod method,
			IRestAPIServiceBuilder<? extends IJsonParsable> builder) {
		this.context = context;
		this.builder = builder;
		this.path = url;
		this.method = method;
	}

	/**
	 * @param context
	 *            ApplicationContext is better for memory
	 * @param builder
	 * @param params
	 *            for String format to path
	 */
	public RestAPIService(Context context, IRestAPIServiceBuilder<? extends IJsonParsable> builder, Object... params) {
		this.context = context;
		this.builder = builder;

		Annotation[] annotations = this.getClass().getDeclaredConstructors()[0].getDeclaredAnnotations();

		if (annotations != null) {
			for (Annotation ann : annotations) {
				Class<?> annType = ann.annotationType();
				Log.d(IConstants.LOG_TAG, ann.toString());
				if (annType == Path.class) {
					if (params != null) {
						this.path = String.format(((Path) ann).value(), params);
					} else {
						this.path = ((Path) ann).value();
					}
				} else if (annType == EncodePath.class) {
					if (params != null) {
						this.path = String.format(((EncodePath) ann).value(), params);
					} else {
						this.path = Uri.encode(((EncodePath) ann).value());
					}
				} else if (annType == Method.class) {
					this.method = ((Method) ann).value();
				}
			}
		}

		if (method == null || path == null) {
			throw new RuntimeException("not found method or path");
		}
	}

	public HttpRequest getRequest() {
		HttpRequest request = null;
		switch (method) {
			case GET:
				request = new HttpGet(path);
				break;

			case PUT:
				request = new HttpPut(path);
				setBody((HttpPut) request);

			case POST:
				request = new HttpPost(path);
				setBody((HttpPost) request);
				break;

			case DELETE:
				request = new HttpDelete(path);
				break;

			default:
				break;
		}

		request.addHeader("Content-Type", "application/json;charset=utf-8");
		request.addHeader("Accept-Language", "ja");

		String url = request.getRequestLine().getUri();
		String cookies = CookieManager.getInstance().getCookie(url);
		if (cookies != null) {
			request.addHeader("Cookie", cookies);
		}

		// add customized headers
		if (builder != null) {
			Header[] headers = builder.getHeaders();
			if (headers != null) {
				for (Header h : headers) {
					request.addHeader(h);
				}
			}
		}

		Log.d(IConstants.LOG_TAG, request.getRequestLine().toString());
		return request;
	}

	private void setBody(HttpEntityEnclosingRequest request) {
		if (builder != null) {
			IJsonParsable jsonBody = builder.getBody();
			if (jsonBody != null) {
				try {
					String jsonStr = jsonBody.toJsonString();
					if (jsonStr != null) {
						request.setEntity(new StringEntity(jsonStr, "UTF-8"));
					}
				} catch (UnsupportedEncodingException e) {
					Log.e(IConstants.LOG_TAG, "bad request body", e);
				}
			}
		}
	}

	@Override
	public JSONObject handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

		int status = response.getStatusLine().getStatusCode();

		JSONObject responseContent = null;
		try {
			HttpEntity responseEntity = response.getEntity();

			if (responseEntity != null) {
				String content = EntityUtils.toString(response.getEntity(), "UTF-8");
				responseContent = new JSONObject(content);
			}

			switch (status) {
				case HttpStatus.SC_OK:
					// save cookies in CookieManager
					StringBuffer cookies = new StringBuffer();
					for (Header h : response.getHeaders("Set-Cookie")) {
						Log.d(IConstants.LOG_TAG, h.toString());
						cookies.append(h.getValue()).append(";");
					}
					if (cookies.length() > 0) {
						String uri = getRequest().getRequestLine().getUri();
						CookieManager.getInstance().setCookie(uri, cookies.toString());
						CookieSyncManager.getInstance().sync();
					}

					onSuccess(responseContent);
					break;

				default:
					onFailed(status, responseContent);
					break;
			}

		} catch (Exception e) {
			Log.d(IConstants.LOG_TAG, "bad json response[" + status + "]", e);
			onException(e);
		}

		return responseContent;
	}

	public void onSuccess(JSONObject body) {
		// TODO common onSuccess handle
		Log.d(IConstants.LOG_TAG, "request succuess");

		// customize onSuccess handle
		if (builder != null) {
			builder.onSuccess(body);
		}
	}

	public void onFailed(int status, JSONObject body) {
		// TODO common onError handle
		Log.w(IConstants.LOG_TAG, "request failed:(" + status);

		// customize onError handle
		if (builder != null) {
			builder.onFailed(status, body);
		}
	}

	public void onException(Exception e) {
		Log.e(IConstants.LOG_TAG, "request failed:(", e);
		if (builder != null) {
			builder.onExcpetion(e);
		}
	}
}
