package com.wb.wizapp.concurret;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.AsyncTask;

public abstract class UIAsyncTask<PR, PG, RS> extends AsyncTask<PR, PG, RS> {

	protected WeakReference<Activity> wkAct;

	public UIAsyncTask(Activity act) {
		this.wkAct = new WeakReference<Activity>(act);
	}

}
