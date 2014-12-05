package com.wb.wizapp.router.routees;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.wb.wizapp.ex.WizUnmatchingRouteeException;
import com.wb.wizapp.router.RouteeCallback;

public final class RouteeBrowser implements RouteeCallback {

	public static final String ROUTEE_SCHEME = "browser";

	@Override
	public void run(Context context, Uri uri) throws WizUnmatchingRouteeException {
		String url = uri.getQueryParameter("url");
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		context.startActivity(intent);
	}

}
