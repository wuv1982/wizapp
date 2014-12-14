package com.wb.wizapp.rest;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;

public interface IRestAPIServiceBuilder<T extends IJsonParsable> {

	public T getBody();

	public Header[] getHeaders();

	public void onSuccess(JSONObject body);

	public void onFailed(int statusCode, JSONObject body);

	public void onExcpetion(Exception e);

	public void onPostResult(Context act, JSONObject result);

}