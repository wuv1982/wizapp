package com.wb.wizapp.router;

import android.content.Context;
import android.net.Uri;

import com.wb.wizapp.ex.WizUnmatchingRouteeException;

public interface RouteeCallback {

	public void run(Context context, Uri uri) throws WizUnmatchingRouteeException;
}
