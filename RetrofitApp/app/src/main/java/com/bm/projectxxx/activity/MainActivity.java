package com.bm.projectxxx.activity;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.andexert.rippleeffect.RippleView;
import com.bm.projectxxx.AppManager;
import com.bm.projectxxx.R;
import com.bm.projectxxx.api.ApiClient;
import com.bm.projectxxx.bean.BaseData;
import com.bm.projectxxx.bean.RandomBean;
import com.bm.projectxxx.bean.UserBean;
import com.bm.projectxxx.picasso.PicassoFactory;
import com.bm.projectxxx.utils.RequestBodyUtils;
import com.bm.projectxxx.utils.TimestampUtils;
import com.bm.projectxxx.utils.constant.URLs;
import com.bm.projectxxx.views.CustomSquareImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnAutoRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;

/**
 * 
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title MainActivity.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.activity
 * @Description 主界面
 * @author 赵成龙  
 * @date 2015年5月21日 上午11:00:37
 * @version V1.0
 */
public class MainActivity extends BaseActivity implements OnClickListener {
	
	private PullToRefreshScrollView scrollView;
	
	private CustomSquareImageView civView;

	private Button dialogButton;

	private Button btnFresco;

	private List<RandomBean> listData = new ArrayList<>();
	
	private File headFile = new File(Environment.getExternalStorageDirectory().getPath()
			+ "/head.jpg");
	
	private File file = new File(Environment.getExternalStorageDirectory().getPath()
			+ "/test3.jpg");
	private File file1 = new File(Environment.getExternalStorageDirectory().getPath()
			+ "/test4.jpg");
	private File file2 = new File(Environment.getExternalStorageDirectory().getPath()
			+ "/test5.jpg");
	
	/**
	 * 当前图片集合
	 */
	private HashMap<String, String> picMap = new HashMap<String, String>();

	/**
	 * 判断是否已经点击过一次回退键
	 */
	private boolean isBackPressed = false;

	private Button overlayButton;

	private Button fragmentButton;

	@Override
	public int inflateContentView() {
		return R.layout.activity_main;
	}

	@Override
	public void findViews() {
		civView = (CustomSquareImageView) findViewById(R.id.civ_view);
		scrollView = (PullToRefreshScrollView) findViewById(R.id.ptr_scrollview);
		dialogButton = (Button) findViewById(R.id.btn_dialog);
		btnFresco = (Button) findViewById(R.id.btn_fresco);
		overlayButton = (Button) findViewById(R.id.btn_overlay);
		fragmentButton = (Button) findViewById(R.id.btn_fragment);
	}

	@Override
	public void init() {
		setTitleText(R.string.app_name);
		hideIvLeftOperate();
		setRightOperateIcon(R.mipmap.general_add_icon);
		scrollView.setMode(Mode.BOTH);

		// 图片加载模块
//		Picasso.with(this).setIndicatorsEnabled(true); // debug source
//		Picasso.with(this).load(R.drawable.test).placeholder(R.drawable.default_icon).into(civView);
//		Picasso.with(this).load("file:///android_asset/girl.jpg").placeholder(R.drawable.default_icon).into(civView);
//		Picasso.with(this).load(file).placeholder(R.drawable.default_icon).into(civView);
		PicassoFactory
				.createPicasso(this)
				.load("http://b.hiphotos.baidu.com/image/w%3D310/sign=98aaea046509c93d07f208f6af3cf8bb/9f510fb30f2442a7888e5dafd343ad4bd1130233.jpg")
				.placeholder(R.mipmap.default_icon).into(civView);

//		scrollView.autoRefreshing();
		getData();
//		login();
//		uploadHeadImage();
//		sendFocus();
//		huaanFundCheckVersion();
	}

	@Override
	public void addListeners() {
		customTitleBar.mIvRightOperate.setOnClickListener(this);
		dialogButton.setOnClickListener(this);
		scrollView.setOnRefreshListener(onRefreshListener);
		scrollView.setAutoRefreshListener(autoRefreshListener);
		btnFresco.setOnClickListener(this);
		overlayButton.setOnClickListener(this);
		fragmentButton.setOnClickListener(this);
	}
	
	private OnAutoRefreshListener autoRefreshListener = new OnAutoRefreshListener() {

		@Override
		public void onAutoRefreshing() {
			getData();
		}
	};

	private OnRefreshListener2<ScrollView> onRefreshListener = new OnRefreshListener2<ScrollView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					showToast("正在刷新");
					scrollView.onRefreshComplete();
				}
			}, 1000);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					showToast("加载更多");
					scrollView.onRefreshComplete();
				}
			}, 1000);
		}
	};

	private <T> void getData() {
		mDialogHelper.startProgressDialog();

//		ApiClient.getCustomApiClient(RandomBean.class, true).getLabelList(new Callback<BaseData<T>>() {
//
//			@Override
//			public void failure(RetrofitError retrofitError) {
//				mDialogHelper.stopProgressDialog();
//				scrollView.onRefreshComplete();
//			}
//
//			@SuppressWarnings({ "rawtypes", "unchecked" })
//			@Override
//			public void success(BaseData baseData, Response response) {
//				mDialogHelper.stopProgressDialog();
//				scrollView.onRefreshComplete();
//				if (baseData != null && baseData.status.equals("1")) {
//					baseData.data.list.get(0);
//					listData = baseData.data.list;
//					System.out.println("listData--->" + listData.get(0).id);
//					showToast("请求成功");
//				} else {
//					if (baseData.msg != null) {
//						showToast(baseData.msg);
//					}
//				}
//			}
//		});

		// 通用请求方式
		ApiClient.getCustomApiClient(RandomBean.class, true).customPostMethod(URLs.HOME_LABEL_URL, null, new Callback<BaseData<T>>() {

			@Override
			public void failure(RetrofitError retrofitError) {
				mDialogHelper.stopProgressDialog();
				scrollView.onRefreshComplete();
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void success(BaseData baseData, Response response) {
				mDialogHelper.stopProgressDialog();
				scrollView.onRefreshComplete();
				if (baseData != null && baseData.status.equals("1")) {
					baseData.data.list.get(0);
					listData = baseData.data.list;
					System.out.println("listData--->" + listData.get(0).id);
					showToast("请求成功");
				} else {
					if (baseData.msg != null) {
						showToast(baseData.msg);
					}
				}
			}
		});
	}

	private <T> void login() {
		mDialogHelper.startProgressDialog();
		// 通用请求方式
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("mobile", "15071054457");
		params.put("password", "123456");
		ApiClient.getCustomApiClient(UserBean.class).customPostMethod(URLs.LOGIN_URL, params, new Callback<BaseData<T>>() {

			@Override
			public void failure(RetrofitError retrofitError) {
				mDialogHelper.stopProgressDialog();
			}

			@SuppressWarnings("rawtypes")
			@Override
			public void success(BaseData baseData, Response response) {
				mDialogHelper.stopProgressDialog();
				if (baseData != null && baseData.status.equals("1")) {
					UserBean user = baseData.data.member;
					System.out.println("user--->" + user);
					showToast("登录成功");
				} else {
					if (baseData.msg != null) {
						showToast(baseData.msg);
					}
				}
			}
		});
	}

	private <T> void uploadHeadImage() {
		mDialogHelper.startProgressDialog();
		mDialogHelper.setDialogMessage("正在上传头像...");

//		ApiClient.getCustomApiClient(null).uploadHead("83", new TypedFile("multipart/form-data", headFile), new Callback<BaseData<T>>() {
//
//			@Override
//			public void failure(RetrofitError retrofitError) {
//				mDialogHelper.stopProgressDialog();
//			}
//
//			@SuppressWarnings("rawtypes")
//			@Override
//			public void success(BaseData baseData, Response response) {
//				mDialogHelper.stopProgressDialog();
//				if (baseData != null && baseData.status.equals("1")) {
//					showToast("上传成功");
//				} else {
//					if (baseData.msg != null) {
//						showToast(baseData.msg);
//					}
//				}
//			}
//		});

		// 通用多文件上传方式
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("memberId", "83");

		Map<String, TypedFile> fileMap = new HashMap<String, TypedFile>();
		fileMap.put("head", new TypedFile("multipart/form-data", headFile));
		ApiClient.getCustomApiClient(null).customMultiFileUpload(URLs.UPDATE_HEAD_URL, params, fileMap, new Callback<BaseData<T>>() {

			@Override
			public void failure(RetrofitError retrofitError) {
				mDialogHelper.stopProgressDialog();
			}

			@SuppressWarnings("rawtypes")
			@Override
			public void success(BaseData baseData, Response response) {
				mDialogHelper.stopProgressDialog();
				if (baseData != null && baseData.status.equals("1")) {
					showToast("上传成功");
				} else {
					if (baseData.msg != null) {
						showToast(baseData.msg);
					}
				}
			}
		});
	}

	private <T> void sendFocus() {
		picMap.put("0", file.getPath());
		picMap.put("1", file1.getPath());
		picMap.put("2", file2.getPath());

		mDialogHelper.startProgressDialog();
		mDialogHelper.setDialogMessage("正在发表手记...");

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("memberId", "83");
		params.put("content", "just for test");

//		ApiClient.getCustomApiClient(null).uploadImages(params, getFiles(), new Callback<BaseData<T>>() {
//
//			@Override
//			public void failure(RetrofitError retrofitError) {
//				mDialogHelper.stopProgressDialog();
//			}
//
//			@SuppressWarnings("rawtypes")
//			@Override
//			public void success(BaseData baseData, Response response) {
//				mDialogHelper.stopProgressDialog();
//				if (baseData != null && baseData.status.equals("1")) {
//					showToast("发表成功");
//				} else {
//					if (baseData.msg != null) {
//						showToast(baseData.msg);
//					}
//				}
//			}
//		});

		// 多文件通用上传方法
		ApiClient.getCustomApiClient(null).customMultiFileUpload(URLs.SEND_FOCUS_URL, params, getFiles(), new Callback<BaseData<T>>() {

			@Override
			public void failure(RetrofitError retrofitError) {
				mDialogHelper.stopProgressDialog();
			}

			@SuppressWarnings("rawtypes")
			@Override
			public void success(BaseData baseData, Response response) {
				mDialogHelper.stopProgressDialog();
				if (baseData != null && baseData.status.equals("1")) {
					showToast("发表成功");
				} else {
					if (baseData.msg != null) {
						showToast(baseData.msg);
					}
				}
			}
		});
	}

	private Map<String, TypedFile> getFiles() {
		Map<String, TypedFile> map = new IdentityHashMap<String, TypedFile>();

		for (Map.Entry<String, String> entry : picMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (!TextUtils.isEmpty(value)) {
				File f = new File(value);
				// 为避免 IdentityHashMap 相同 key 的 value 值被覆盖，一定要 new
				map.put("fileList[" + "'" + key + "'" + "]", new TypedFile("multipart/form-data", f));
			}
		}
		return map;
	}

	/**
	 * @Package com.bm.projectxxx.activity
	 * @Description
	 * @author 赵成龙
	 * @ChangedBy zhaocl 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.btn_fragment:
				intent = new Intent(MainActivity.this, TestFragmentActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_overlay:
				intent = new Intent(MainActivity.this, TestOverlayActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_dialog:
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(MainActivity.this, DialogActivity.class);
						startActivity(intent);
					}
				}, RippleView.DURATION);

				break;
			case R.id.iv_right_operate:
				intent = new Intent(MainActivity.this, LocalImageListActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_fresco:
				intent = new Intent(MainActivity.this, FrescoActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}
	
	/**
	 * @author 赵成龙
	 * @param <T>
	 * @Description 
	 * @return void
	 * @date 2015年4月15日 下午3:46:03
	 */
	private <T> void huaanFundCheckVersion() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("z_funcVersion", "100");
		params.put("z_funcCode", "b02");
		params.put("z_tradeType", "a");
		params.put("z_commerceId", "ha_lanse");
		params.put("z_timestamp", TimestampUtils.getCurrentTimestamp());
		params.put("clientType", "lanse_android");
		params.put("clientVersion", "0.9");

		TypedInput  body = new TypedByteArray("application/octet-stream", RequestBodyUtils.params2byte(params));

		ApiClient.getCustomApiClient(null).huaanFundTest(body, new Callback<T>() {

			@Override
			public void failure(RetrofitError retrofitError) {
				mDialogHelper.stopProgressDialog();
			}

			@Override
			public void success(T data, Response response) {
				mDialogHelper.stopProgressDialog();

			}
		});
	}

	private void doublePressBackToast() {
		if (!isBackPressed) {
			isBackPressed = true;
			Toast.makeText(this, "再次点击返回退出程序", Toast.LENGTH_SHORT).show();
		} else {
			AppManager.getAppManager().appExit();
		}

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				isBackPressed = false;
			}
		}, 2000);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			doublePressBackToast();
			return true;
		} else {
			return super.onKeyUp(keyCode, event);
		}
	}

	
}
