/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title DialogActivity.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.activity
 * @Description TODO
 * @author zhaocl  
 * @date 2015年7月15日 下午4:16:19
 * @version V1.0  
 */
package com.bm.projectxxx.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.projectxxx.R;
import com.bm.projectxxx.utils.FastDialogUtils;

/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title DialogActivity.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.activity
 * @Description TODO
 * @author zhaocl  
 * @date 2015年7月15日 下午4:16:19
 * @version V1.0  
 */
public class DialogActivity extends BaseActivity implements OnClickListener {

	private Button button1;
	
	private Button button2;
	
	private Button button3;
	
	private Button button4;

	@Override
	public int inflateContentView() {
		return R.layout.activity_dialog_layout;
	}
	
	@Override
	public void findViews() {
		button1 = (Button) findViewById(R.id.btn_dialog1);
		button2 = (Button) findViewById(R.id.btn_dialog2);
		button3 = (Button) findViewById(R.id.btn_dialog3);
		button4 = (Button) findViewById(R.id.btn_dialog4);
	}

	@Override
	public void init() {
		setTitleText("对话框");
	}

	@Override
	public void addListeners() {
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_dialog1:
			FastDialogUtils.getInstance().createCustomDialog(this, "确认退出吗？", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showToast("已退出");
				}
			});
			break;
		case R.id.btn_dialog2:
			FastDialogUtils.getInstance().createSingleChoiceDialog(this, "从相册选取", "从相机拍摄", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showToast("从相册选取");
				}
			}, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showToast("从相机拍摄");
				}
			});
			break;
		case R.id.btn_dialog3:
			FastDialogUtils.getInstance().createHeadPopupWindow(this, button3, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showToast("拍照");
				}
			}, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showToast("从相册选取");
				}
			});
			break;
		case R.id.btn_dialog4:
			FastDialogUtils.getInstance().createSingleButtonDialog(this, "当前已是最新版本");
			break;
		default:
			break;
		}
	}
	
}
