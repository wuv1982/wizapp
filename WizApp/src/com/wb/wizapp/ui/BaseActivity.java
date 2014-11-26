package com.wb.wizapp.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Handler;
import android.util.Log;

import com.wb.wizapp.Constants;

public class BaseActivity extends Activity {

	private DialogFragment frg;
	private final int PROGRESS_TIMEOUT = 2 * 1000;

	public void ShowProgress() {
		Log.d(Constants.LOG_TAG, "ShowProgress");

		if (frg == null) {
			frg = new BaseProgressFragment();
		}

		if (!frg.isVisible()) {
			frg.show(getFragmentManager(), "progress");
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					HideProgress();
				}
			}, PROGRESS_TIMEOUT);
		}

	}

	public void HideProgress() {
		if (frg != null && frg.isVisible()) {
			Log.d(Constants.LOG_TAG, "HideProgress");
			frg.dismiss();
		}
	}
}
