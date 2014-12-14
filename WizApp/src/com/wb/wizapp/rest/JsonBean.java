package com.wb.wizapp.rest;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.wb.wizapp.IConstants;

public abstract class JsonBean implements IJsonParsable {

	private JSONObject toJson() {
		JSONObject json = null;
		try {
			Gson gson = new Gson();
			json = new JSONObject(gson.toJson(this));
		} catch (JSONException e) {
			Log.e(IConstants.LOG_TAG, "bad json parsable object:" + this.getClass().getName(), e);
		}
		return json;
	}

	public String toJsonString() {
		JSONObject json = toJson();
		if (json != null) {
			return toJson().toString();
		} else {
			return null;
		}
	}
}
