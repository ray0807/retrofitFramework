package com.bm.projectxxx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.bm.projectxxx.AppManager;
import com.bm.projectxxx.R;
import com.bm.projectxxx.utils.DialogHelper;
import com.bm.projectxxx.utils.StatusBarUtil;

public abstract class BasicActivity extends FragmentActivity {

	public DialogHelper mDialogHelper;

	public abstract void findViews();

	public abstract void init();

	public abstract void addListeners();

	private int statusBarColorResource = 0;

	// 状态栏与UI颜色是否一体化
	protected boolean isUseSystemBar = true;
	
	// UI是否浸入转态栏
	protected boolean isStatusBarImmerged = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		mDialogHelper = new DialogHelper(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}

	public void showToast(String s) {
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}

	public void showToast(int r) {
		Toast.makeText(this, r, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void finish() {
		super.finish();
//		overridePendingTransition(R.anim.in_left_right, R.anim.out_left_right);
		overridePendingTransition(R.anim.scale_small_in, R.anim.out_left_right);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
//		overridePendingTransition(R.anim.in_right_left, R.anim.out_right_left);
		overridePendingTransition(R.anim.in_right_left, R.anim.scale_small_out);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
//		overridePendingTransition(R.anim.in_right_left, R.anim.out_right_left);
		overridePendingTransition(R.anim.in_right_left, R.anim.scale_small_out);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isUseSystemBar) {
			if (!isStatusBarImmerged) {
				if (statusBarColorResource == 0) {
					StatusBarUtil.initSystemBar(this,
							R.color.statusbar_general_color);
				} else {
					StatusBarUtil.initSystemBar(this, statusBarColorResource);
				}
			} else {
				StatusBarUtil.initSystemBarWithImmerged(this, android.R.color.transparent);
			}
		}
	}

	/**
	 * 设置状态栏背景色
	 * 
	 * @param resource
	 */
	public void setStatusBarColorResource(int resource) {
		statusBarColorResource = resource;
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

}
