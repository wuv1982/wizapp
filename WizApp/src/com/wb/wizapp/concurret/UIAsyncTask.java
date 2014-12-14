package com.wb.wizapp.concurret;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.AsyncTask;

public abstract class UIAsyncTask<PR, PG, RS> extends AsyncTask<PR, PG, RS> {

	protected WeakReference<Context> wkCtx;

	public UIAsyncTask(Context ctx) {
		this.wkCtx = new WeakReference<Context>(ctx);
	}

}
