/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title TestOverlayActivity.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.activity
 * @Description TODO
 * @author zhaocl  
 * @date 2015年7月21日 下午2:19:03
 * @version V1.0  
 */
package com.bm.projectxxx.activity;

import com.bm.projectxxx.R;

/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title TestOverlayActivity.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.activity
 * @Description TODO
 * @author zhaocl  
 * @date 2015年7月21日 下午2:19:03
 * @version V1.0  
 */
public class TestOverlayActivity extends BaseActivity {
	
	@Override
	public int inflateContentView() {
		return R.layout.activity_test_overlay;
	}
	
	@Override
	public void findViews() {
		
	}

	@Override
	public void init() {
		setTitleText("Overlay");
		setTitleBarOverlay(true);
		setTitleBarTransparent();
	}

	@Override
	public void addListeners() {
		
	}

}
