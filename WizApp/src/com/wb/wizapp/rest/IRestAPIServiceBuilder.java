package com.wb.wizapp.rest;

import org.apache.http.Header;
import org.json.JSONObject;

public interface IRestAPIServiceBuilder<T extends JsonParsable> {

	public static final int STATUS_CODE_EXCEPTION = -1;

	public T appendBody();

	public Header[] appendHeaders();

	public void onSuccess(JSONObject body);

	public void onFailed(int statusCode, String body);

}