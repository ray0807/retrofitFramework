/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title CustomTitleBar.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.views
 * @Description TODO
 * @author zhaocl  
 * @date 2015年7月20日 下午3:27:24
 * @version V1.0  
 */
package com.bm.projectxxx.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.projectxxx.R;

/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title CustomTitleBar.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.views
 * @Description 自定义TitleBar
 * @author zhaocl  
 * @date 2015年7月20日 下午3:27:24
 * @version V1.0  
 */
public class CustomTitleBar extends LinearLayout {
	
	private Context mContext;
	
	public LinearLayout rootLayout;
	
	public ImageView mIvLeftOperate;
	
	public TextView mTvLeftOperate;
	
	public TextView mTvTitle;
    
	public ImageView mIvRightOperate;
    
	public TextView mTvRightOperate;
    
    public CustomTitleBar(Context context) {
		super(context);
		init(context);
	}

	public CustomTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context) {
		mContext = context;
		LayoutInflater.from(mContext).inflate(R.layout.common_title_layout, this);
		rootLayout = (LinearLayout) findViewById(R.id.rl_top_title);
		mIvLeftOperate = (ImageView) findViewById(R.id.iv_back_operate);
		mTvLeftOperate = (TextView) findViewById(R.id.tv_left_operate);
		mTvTitle = (TextView) findViewById(R.id.tv_top_title);
		mIvRightOperate = (ImageView) findViewById(R.id.iv_right_operate);
		mTvRightOperate = (TextView) findViewById(R.id.tv_right_operate);
	}

	@Override
	public void setLayoutParams(android.view.ViewGroup.LayoutParams params) {
		super.setLayoutParams(params);
		
		// 不能直接用注掉的方法，否则可能报 LayoutParams 类型转换错误
		//rootLayout.setLayoutParams(params);
		ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) rootLayout.getLayoutParams();
		layoutParams.height = params.height;
		rootLayout.setLayoutParams(layoutParams);
	}
	
}
