/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title StatusBarUtil.java
 * @Prject HuaanFund
 * @Package com.bm.huaanfund.utils
 * @Description TODO
 * @author zhaocl  
 * @date 2015年3月27日 下午5:37:42
 * @version V1.0  
 */
package com.bm.projectxxx.utils;

import java.lang.reflect.Field;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bm.projectxxx.R;
import com.bm.projectxxx.utils.SystemBarTintManager.SystemBarConfig;

/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title StatusBarUtil.java
 * @Prject HuaanFund
 * @Package com.bm.huaanfund.utils
 * @Description TODO
 * @author zhaocl  
 * @date 2015年3月27日 下午5:37:42
 * @version V1.0  
 */
public class StatusBarUtil {

	@TargetApi(19) 
	private static void setTranslucentStatus(Activity activity, boolean on) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	
	/**
	 * 
	 * @author 赵成龙
	 * @Description 状态栏与界面顶部一体化
	 * @return void
	 * @date 2015年6月9日 下午5:04:56
	 */
	public static void initSystemBar(Activity activity, int colorRes) {
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	        setTranslucentStatus(activity, true);
	        
	        // 创建状态栏的管理实例
	        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
	        // 激活状态栏设置
	        tintManager.setStatusBarTintEnabled(true);
	        // 设置状态栏背景色
	        tintManager.setStatusBarTintColor(activity.getResources().getColor(colorRes));
	        // 设置padding值
	        SystemBarConfig config = tintManager.getConfig();
	        // 获取当前activity的根视图
	        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
	        rootView.setPadding(0, config.getStatusBarHeight(), 0, 0);
	    }
	}
	
	/**
	 * 
	 * @author 赵成龙
	 * @Description Activity顶部浸入状态栏
	 * @return void
	 * @date 2015年6月9日 下午5:00:54
	 */
	public static void initSystemBarWithImmerged(Activity activity, int colorRes) {
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	        setTranslucentStatus(activity, true);
	        
	        // 创建状态栏的管理实例
	        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
	        // 激活状态栏设置
	        tintManager.setStatusBarTintEnabled(true);
	        // 设置状态栏背景色
	        tintManager.setStatusBarTintColor(activity.getResources().getColor(colorRes));
	       
	        View titleLayout = activity.findViewById(R.id.rl_top_title);
			if (titleLayout != null) {
				ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) titleLayout.getLayoutParams();
				layoutParams.height = activity.getResources().getDimensionPixelSize(R.dimen.space_title_bar) + getStatusbarHeigth(activity);
				titleLayout.setLayoutParams(layoutParams);
			}
	    }
	}
	
	/**
	 * 
	 * @author 赵成龙
	 * @Description Fragment顶部浸入状态栏
	 * @return void
	 * @date 2015年6月9日 下午5:00:07
	 */
	public static void initSystemBarWithImmerged(Activity activity, View view, int colorRes) {
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	    	setTranslucentStatus(activity, true);
	        
	        // 创建状态栏的管理实例
	        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
	        // 激活状态栏设置
	        tintManager.setStatusBarTintEnabled(true);
	        // 设置状态栏背景色
	        tintManager.setStatusBarTintColor(activity.getResources().getColor(colorRes));
	    	
	        View titleLayout = view.findViewById(R.id.rl_top_title);
			if (titleLayout != null) {
				ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) titleLayout.getLayoutParams();
				layoutParams.height = activity.getResources().getDimensionPixelSize(R.dimen.space_title_bar) + getStatusbarHeigth(activity);
				titleLayout.setLayoutParams(layoutParams);
			}
	    }
	}
	
	// 获取状态栏高度
	@SuppressWarnings("rawtypes")
	public static int getStatusbarHeigth(Activity activity) {
		Class c = null;
		try {
			c = Class.forName("com.android.internal.R$dimen");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Object obj = null;
		try {
			obj = c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		Field field = null;
		try {
			field = c.getField("status_bar_height");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		int x = 0;
		try {
			x = Integer.parseInt(field.get(obj).toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		int statusbarHeight = activity.getResources().getDimensionPixelSize(x);
		return statusbarHeight;
	}
	
}