package com.bm.projectxxx.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 解决与ListView、ScrollView滑动冲突的ViewPager
 * 
 * @author eric
 * @since 2014-11-06
 * 
 */
public class CostomScrollViewPager extends ViewPager {

	/**
	 * 触摸的最小距离
	 */
	private int mTouchSlop = 0;

	/**
	 * 最后触摸点的X坐标
	 */
	private float mLastionMotionX = 0;

	/**
	 * 最后触摸点的Y坐标
	 */
	private float mLastionMotionY = 0;

	public CostomScrollViewPager(Context context) {
		super(context);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public CostomScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// getParent().requestDisallowInterceptTouchEvent(true);
		// 手指位置地点
		float x = ev.getX();
		float y = ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			mLastionMotionX = x;
			mLastionMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			final float detaX = mLastionMotionX - x;
			final float detaY = mLastionMotionY - y;
			// 左右滑动
			if (Math.abs(detaX) > Math.abs(detaY)) {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			// 上下滑动
			else {
				if (Math.abs(detaY) >= mTouchSlop) {
					getParent().requestDisallowInterceptTouchEvent(false);
				} else {
					getParent().requestDisallowInterceptTouchEvent(true);
				}
			}

			break;
		case MotionEvent.ACTION_UP:
			getParent().requestDisallowInterceptTouchEvent(true);
			mLastionMotionX = x;
			mLastionMotionY = y;
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

}
