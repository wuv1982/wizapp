package com.wb.wizapp.rest;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;

public class RestAPISercieBuilderAdapter<T extends IJsonParsable> implements IRestAPIServiceBuilder<T> {

	@Override
	public T getBody() {
		return null;
	}

	@Override
	public Header[] getHeaders() {
		return null;
	}

	@Override
	public void onSuccess(JSONObject body) {

	}

	@Override
	public void onFailed(int statusCode, JSONObject body) {

	}

	@Override
	public void onExcpetion(Exception e) {

	}

	@Override
	public void onPostResult(Context act, JSONObject result) {

	}

}
