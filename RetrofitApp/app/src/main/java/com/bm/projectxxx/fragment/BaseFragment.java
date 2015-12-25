package com.bm.projectxxx.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bm.projectxxx.utils.StatusBarUtil;

/**
 * 
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title BaseFragment.java
 * @Prject iStory
 * @Package com.bm.istory.fragment
 * @Description 基类Fragment
 * @author 赵成龙  
 * @date 2015年6月9日 下午5:02:58
 * @version V1.0
 */
public abstract class BaseFragment extends Fragment {

	protected Context mContext;
	
	private View mView;
	
	// UI是否浸入转态栏
	protected boolean isStatusBarImmerged = true;

	protected abstract View inflateContentView(LayoutInflater inflater, ViewGroup container);

	protected abstract void findViews(View view);

	protected abstract void init();

	protected abstract void addListeners();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		return inflateContentView(inflater,container);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mView = view;
		findViews(view);
		init();
		addListeners();
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (isStatusBarImmerged) {
			StatusBarUtil.initSystemBarWithImmerged(getActivity(), mView, android.R.color.transparent);
		}
	}
	
	public void showToast(String s) {
		Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
	}

	public void showToast(int r) {
		Toast.makeText(mContext, r, Toast.LENGTH_SHORT).show();
	}
	
}
