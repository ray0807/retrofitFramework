package com.bm.projectxxx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bm.projectxxx.AppManager;
import com.bm.projectxxx.R;
import com.bm.projectxxx.bean.LayoutResourceID;
import com.bm.projectxxx.utils.DialogHelper;
import com.bm.projectxxx.utils.StatusBarUtil;
import com.bm.projectxxx.views.CustomTitleBar;

/**
 * 
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title BaseActivity.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.activity
 * @Description 基类Activity(带自定义标题栏)
 * @author zhaocl  
 * @date 2015年7月21日 下午4:14:58
 * @version V1.0
 */
public abstract class BaseActivity extends FragmentActivity {
	
	/** 全局的LayoutInflater对象  */
	public LayoutInflater mInflater;

	/** 总布局. */
	public RelativeLayout rootLayout = null;

	/** 标题栏布局. */
	public CustomTitleBar customTitleBar;
	
	/** 主内容布局. */
	protected RelativeLayout contentLayout = null;

	public DialogHelper mDialogHelper;
	
	// 状态栏与UI颜色是否一体化
	protected boolean isUseSystemBar = true;

	// UI是否浸入转态栏
	protected boolean isStatusBarImmerged = true;
	
	public abstract @LayoutResourceID int inflateContentView();

	public abstract void findViews();

	public abstract void init();

	public abstract void addListeners();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		mDialogHelper = new DialogHelper(this);

		mInflater = LayoutInflater.from(this);
		// TitleBar
		customTitleBar = new CustomTitleBar(this);
		customTitleBar.setId(0x1001);
		// 最外层布局
		rootLayout = new RelativeLayout(this);
		// 内容布局
		contentLayout = new RelativeLayout(this);
		contentLayout.setPadding(0, 0, 0, 0);
		// 填入TitleBar
		rootLayout.addView(customTitleBar, new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		customTitleBar.setVisibility(View.GONE);
	
		RelativeLayout.LayoutParams layoutParamsContent = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		layoutParamsContent.addRule(RelativeLayout.BELOW, customTitleBar.getId());
		rootLayout.addView(contentLayout, layoutParamsContent);

		// 设置ContentView
		super.setContentView(rootLayout, new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		customTitleBar.mIvLeftOperate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		setContentView(inflateContentView());
		findViews();
		init();
		addListeners();
		checkIntegrated();
	}
	
	/**
	 * 
	 * @author 赵成龙
	 * @Description 检查一体化设置
	 * @return void
	 * @date 2015年7月21日 下午1:05:34
	 */
	private void checkIntegrated() {
		if (isStatusBarImmerged) {
			StatusBarUtil.initSystemBarWithImmerged(this,
					android.R.color.transparent);
		}
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
		// overridePendingTransition(R.anim.in_left_right,
		// R.anim.out_left_right);
		overridePendingTransition(R.anim.scale_small_in, R.anim.out_left_right);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		// overridePendingTransition(R.anim.in_right_left,
		// R.anim.out_right_left);
		overridePendingTransition(R.anim.in_right_left, R.anim.scale_small_out);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		// overridePendingTransition(R.anim.in_right_left,
		// R.anim.out_right_left);
		overridePendingTransition(R.anim.in_right_left, R.anim.scale_small_out);
	}
	
	/**
	 * 描述：设置绝对定位的主标题栏覆盖到内容的上边.
	 *
	 * @param overlay the new title bar overlay
	 */
	public void setTitleBarOverlay(boolean overlay) {
		rootLayout.removeAllViews();
		if (overlay) {
			RelativeLayout.LayoutParams layoutParamsContent = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			layoutParamsContent.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			layoutParamsContent.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			rootLayout.addView(contentLayout, layoutParamsContent);
			
			RelativeLayout.LayoutParams layoutParamsTitle = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParamsTitle.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			rootLayout.addView(customTitleBar, layoutParamsTitle);
		} else {
			rootLayout.addView(customTitleBar, new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			RelativeLayout.LayoutParams layoutParamsContent = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			layoutParamsContent.addRule(RelativeLayout.BELOW, customTitleBar.getId());
			rootLayout.addView(contentLayout, layoutParamsContent);
		}
		
		checkIntegrated();
	}
	
	/**
	 * 描述：设置界面显示（忽略标题栏）.
	 *
	 * @param layoutResID the new content view
	 * @see android.app.Activity#setContentView(int)
	 */
	@Override
	public void setContentView(int layoutResID) {
		setAbContentView(layoutResID);
	}
	
	/**
	 * 描述：用指定资源ID表示的View填充主界面.
	 * @param resId  指定的View的资源ID
	 */
	public void setAbContentView(int resId) {
		setAbContentView(mInflater.inflate(resId, null));
	}
	
	public void setAbContentView(View contentView) {
		contentLayout.removeAllViews();
		contentLayout.addView(contentView,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
	
	public CustomTitleBar getCustomTitleBar() {
		return customTitleBar;
	}
	
	// 以下是关于 TitleBar 的一系列设置的公共方法
	// -----------------------------------------------------------------------------
	/**
	 * 
	 * @author 赵成龙
	 * @Description 隐藏标题栏
	 * @return void
	 * @date 2015年7月21日 下午2:41:44
	 */
	public void hideTitleBar() {
		customTitleBar.setVisibility(View.GONE);
	}
	
	/**
	 * 
	 * @author 赵成龙
	 * @Description 设置标题栏背景透明
	 * @return void
	 * @date 2015年7月21日 下午2:41:23
	 */
	public void setTitleBarTransparent() {
		customTitleBar.rootLayout.setBackgroundColor(getResources()
				.getColor(android.R.color.transparent));
	}
	
	public void setTitleText(String title) {
		customTitleBar.setVisibility(View.VISIBLE);
		customTitleBar.mTvTitle.setText(title);
	}
	
	public void setTitleText(int titleRes) {
		customTitleBar.setVisibility(View.VISIBLE);
		customTitleBar.mTvTitle.setText(titleRes);
	}
	
	public void setLeftOperateIcon(int resId) {
		customTitleBar.mIvLeftOperate.setImageResource(resId);
	}
	
	public void hideIvLeftOperate() {
		customTitleBar.mIvLeftOperate.setVisibility(View.GONE);
	}
	
	public void setLeftOperateText(String leftText) {
		customTitleBar.mTvLeftOperate.setVisibility(View.VISIBLE);
		customTitleBar.mTvLeftOperate.setText(leftText);
	}
	
	public void setLeftOperateText(int leftTextRes) {
		customTitleBar.mTvLeftOperate.setVisibility(View.VISIBLE);
		customTitleBar.mTvLeftOperate.setText(leftTextRes);
	}
	
	public void setRightOperateIcon(int resId) {
		customTitleBar.mIvRightOperate.setVisibility(View.VISIBLE);
		customTitleBar.mIvRightOperate.setImageResource(resId);
	}
	
	public void setRightOperateText(String rightText) {
		customTitleBar.mTvRightOperate.setVisibility(View.VISIBLE);
		customTitleBar.mTvRightOperate.setText(rightText);
	}
	
	public void setRightOperateText(int rightTextRes) {
		customTitleBar.mTvRightOperate.setVisibility(View.VISIBLE);
		customTitleBar.mTvRightOperate.setText(rightTextRes);
	}
	// -----------------------------------------------------------------------------

}
