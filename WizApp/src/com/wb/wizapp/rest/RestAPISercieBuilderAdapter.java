package com.wb.wizapp.rest;

import org.apache.http.Header;
import org.json.JSONObject;

public class RestAPISercieBuilderAdapter<T extends JsonParsable> implements IRestAPIServiceBuilder<T> {

	@Override
	public T appendBody() {
		return null;
	}

	@Override
	public Header[] appendHeaders() {
		return null;
	}

	@Override
	public void onSuccess(JSONObject body) {

	}

	@Override
	public void onFailed(int statusCode, String body) {

	}

}
