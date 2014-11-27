package com.wb.wizapp.concurret;

import java.lang.ref.WeakReference;

import android.os.AsyncTask;

import com.wb.wizapp.ui.BaseActivity;

public abstract class UISyncTask<PR, PG, RS> extends AsyncTask<PR, PG, RS> {

	private final WeakReference<BaseActivity> wkAct;

	public UISyncTask(BaseActivity act) {
		this.wkAct = new WeakReference<BaseActivity>(act);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		BaseActivity act = wkAct.get();
		if (act != null) {
			act.ShowProgress();
		}
	}

	@Override
	protected void onPostExecute(RS result) {
		super.onPostExecute(result);
		BaseActivity act = wkAct.get();
		if (act != null) {
			act.HideProgress();
		}
	}

}
