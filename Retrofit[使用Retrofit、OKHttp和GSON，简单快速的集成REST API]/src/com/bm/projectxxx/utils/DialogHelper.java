package com.bm.projectxxx.utils;

import android.content.Context;

import com.bm.projectxxx.views.CustomProgressDialog;

/**
 * dialog操作类
 * 
 * @author 赵成龙
 * @since 2014-12-30
 * 
 */
public class DialogHelper {
	private Context mContext;
	private CustomProgressDialog progressDialog = null;

	public DialogHelper(Context context) {
		mContext = context;
	}

	public void startProgressDialog() {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(mContext);
		}
		progressDialog.show();
	}

	public void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void setDialogMessage(String message) {
		progressDialog.setMessage(message);
	}

}
