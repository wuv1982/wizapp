package com.wb.wizapp.rest;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;

public interface IRestAPIServiceBuilder<T extends JsonParsable> {

	public T getBody();

	public Header[] getHeaders();

	public void onSuccess(JSONObject body);

	public void onFailed(int statusCode, JSONObject body);

	public void onExcpetion(Exception e);

	public void onPostResult(Activity act, JSONObject result);

}