package com.wb.wizapp.router;

import android.content.Context;
import android.net.Uri;

public interface RouteeCallback {

	public String scheme();

	public void run(Context context, Uri uri);
}
