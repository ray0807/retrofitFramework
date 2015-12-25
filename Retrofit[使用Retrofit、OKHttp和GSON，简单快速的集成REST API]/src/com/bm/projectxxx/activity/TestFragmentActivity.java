/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title TestFragmentActivity.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.activity
 * @Description TODO
 * @author zhaocl  
 * @date 2015年7月21日 下午2:44:57
 * @version V1.0  
 */
package com.bm.projectxxx.activity;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentTransaction;

import com.bm.projectxxx.R;
import com.bm.projectxxx.fragment.TestFragment;

/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title TestFragmentActivity.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.activity
 * @Description TODO
 * @author zhaocl  
 * @date 2015年7月21日 下午2:44:57
 * @version V1.0  
 */
public class TestFragmentActivity extends BaseActivity {
	
	@Override
	public int inflateContentView() {
		return R.layout.activity_test_fragment;
	}

	@Override
	public void findViews() {
		
	}

	@SuppressLint("Recycle")
	@Override
	public void init() {
		hideTitleBar();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.realtabcontent, new TestFragment());
		transaction.commitAllowingStateLoss();
	}

	@Override
	public void addListeners() {
		
	}

}
