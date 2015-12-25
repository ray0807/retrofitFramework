/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title TestFragment.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.fragment
 * @Description TODO
 * @author zhaocl  
 * @date 2015年7月21日 下午2:50:58
 * @version V1.0  
 */
package com.bm.projectxxx.fragment;

import android.view.View;

import com.bm.projectxxx.R;

/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title TestFragment.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.fragment
 * @Description TODO
 * @author zhaocl  
 * @date 2015年7月21日 下午2:50:58
 * @version V1.0  
 */
public class TestFragment extends BaseFragmentWithTitleBar {

	@Override
	protected int inflateContentView() {
		return R.layout.fragment_test_layout;
	}

	@Override
	protected void findViews(View view) {
		
	}

	@Override
	
	protected void init() {
		setTitleText("Fragment");
		hideIvLeftOperate();
	}

	@Override
	protected void addListeners() {
		
	}

}
