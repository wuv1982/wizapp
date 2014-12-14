package com.wb.wizapp.ui.zxing;

import android.app.Activity;

public final class ScanActivity extends Activity {
	// TODO dummy source
}

// import android.graphics.Bitmap;
// import android.util.Log;
//
// import com.google.zxing.Result;
// import com.google.zxing.client.android.CaptureActivity;
// import com.google.zxing.client.result.ParsedResult;
// import com.google.zxing.client.result.ParsedResultType;
// import com.google.zxing.client.result.ResultParser;
// import com.wb.wizapp.Constants;
//
// public final class ScanActivity extends CaptureActivity {
//
// @Override
// public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor)
// {
// // get result
// String format = rawResult.getBarcodeFormat().toString();
// Log.d(Constants.LOG_TAG, "format =" + format);
//
// ParsedResult parsedResult = ResultParser.parseResult(rawResult);
// ParsedResultType type = parsedResult.getType();
// Log.d(Constants.LOG_TAG, "type =" + type.name());
// String text = parsedResult.getDisplayResult();
// Log.d(Constants.LOG_TAG, "text =" + text);
//
// // start search activity
// switch (type) {
// case PRODUCT:
// // start search activity by keyword
// break;
// case TEXT:
// default:
// if (isUrl(text)) {
// // start search activity by url
// } else {
// // start search activity by keyword
// }
// }
//
// // close self
// finish();
// }
//
// private boolean isUrl(String text) {
// return text != null
// && (text.startsWith("HTTP://") || text.startsWith("http://") ||
// text.startsWith("HTTPS://") || text
// .startsWith("https://"));
// }
// }
