package com.wb.wizapp.rest;

import org.json.JSONObject;

public class JsonObject extends JSONObject implements IJsonParsable {

	@Override
	public String toJsonString() {
		return this.toString();
	}

}
