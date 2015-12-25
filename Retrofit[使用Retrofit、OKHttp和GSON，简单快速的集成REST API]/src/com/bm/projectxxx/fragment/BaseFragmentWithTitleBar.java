package com.bm.projectxxx.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bm.projectxxx.R;
import com.bm.projectxxx.bean.LayoutResourceID;
import com.bm.projectxxx.utils.StatusBarUtil;
import com.bm.projectxxx.views.CustomTitleBar;

/**
 * 
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title BaseFragment.java
 * @Prject iStory
 * @Package com.bm.istory.fragment
 * @Description 基类Fragment(带自定义标题栏)
 * @author 赵成龙  
 * @date 2015年6月9日 下午5:02:58
 * @version V1.0
 */
public abstract class BaseFragmentWithTitleBar extends Fragment {

	protected Context mContext;
	
	private View mView;
	
	// UI是否浸入转态栏
	protected boolean isStatusBarImmerged = true;
	
	/** 总布局. */
	public RelativeLayout rootLayout = null;

	/** 标题栏布局. */
	public CustomTitleBar customTitleBar;
	
	/** 主内容布局. */
	protected RelativeLayout contentLayout = null;

	protected abstract @LayoutResourceID int inflateContentView();

	protected abstract void findViews(View view);

	protected abstract void init();

	protected abstract void addListeners();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		// TitleBar
		customTitleBar = new CustomTitleBar(mContext);
		customTitleBar.setId(0x1002);
		// 最外层布局
		rootLayout = new RelativeLayout(mContext);
		// 内容布局
		contentLayout = new RelativeLayout(mContext);
		contentLayout.setPadding(0, 0, 0, 0);
		// 填入TitleBar
		rootLayout.addView(customTitleBar, new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	
		RelativeLayout.LayoutParams layoutParamsContent = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		layoutParamsContent.addRule(RelativeLayout.BELOW, customTitleBar.getId());
		rootLayout.addView(contentLayout, layoutParamsContent);

		// 填入内容
		if (inflateContentView() == 0) {
			throw new InflateException("LayoutResourceID must not be 0!");
		}
		View contentView = inflater.inflate(inflateContentView(), null);
		contentLayout.removeAllViews();
		contentLayout.addView(contentView,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return rootLayout;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mView = view;
		findViews(view);
		init();
		addListeners();
		checkIntegrated();
		super.onViewCreated(view, savedInstanceState);
	}
	
	/**
	 * @author 赵成龙
	 * @Description 检查一体化设置
	 * @return void
	 * @date 2015年7月21日 下午3:53:33
	 */
	private void checkIntegrated() {
		if (isStatusBarImmerged) {
			StatusBarUtil.initSystemBarWithImmerged(getActivity(), mView, android.R.color.transparent);
		}
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
	
	public void showToast(String s) {
		Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
	}

	public void showToast(int r) {
		Toast.makeText(mContext, r, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.in_right_left, R.anim.scale_small_out);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.in_right_left, R.anim.scale_small_out);
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
		customTitleBar.rootLayout.setBackgroundColor(getResources().getColor(
				android.R.color.transparent));
	}

	public void setTitleText(String title) {
		customTitleBar.mTvTitle.setText(title);
	}

	public void setTitleText(int titleRes) {
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
