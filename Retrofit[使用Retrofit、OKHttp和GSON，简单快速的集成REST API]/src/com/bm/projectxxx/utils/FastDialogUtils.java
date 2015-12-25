/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title FastDialogUtils.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.utils
 * @Description TODO
 * @author zhaocl  
 * @date 2015年7月16日 上午10:24:06
 * @version V1.0  
 */
package com.bm.projectxxx.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.andexert.rippleeffect.RippleView;
import com.bm.projectxxx.R;
import com.bm.projectxxx.views.CustomDialog;

/**
 * Copyright © 2015 蓝色互动. All rights reserved.
 * 
 * @Title FastDialogUtils.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.utils
 * @Description 对话框快速搭建
 * @author zhaocl
 * @date 2015年7月16日 上午10:24:06
 * @version V1.0
 */
public class FastDialogUtils {

	/**
	 * FastDialogUtils的实例。
	 */
	private static FastDialogUtils mFastDialogUtils;

	/**
	 * 获取FastDialogUtils的实例。
	 * 
	 * @return FastDialogUtils的实例。
	 */
	public static FastDialogUtils getInstance() {
		if (mFastDialogUtils == null) {
			mFastDialogUtils = new FastDialogUtils();
		}
		return mFastDialogUtils;
	}

	private PopupWindow mHeadPopupWindow;

	/**
	 * 
	 * @author 赵成龙
	 * @Description 通用对话框（取消+确认）
	 * @return void
	 * @date 2015年7月16日 上午11:11:09
	 */
	public void createCustomDialog(Context context, String dialogContent,
			final View.OnClickListener positiveCallBack) {
		createCustomDialog(context, null, dialogContent, null, null,
				positiveCallBack);
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 通用对话框（取消+确认）
	 * @return void
	 * @date 2015年7月16日 上午11:10:32
	 */
	public void createCustomDialog(Context context, String title,
			String dialogContent, String nagetive, String positive,
			final View.OnClickListener positiveCallBack) {
		final CustomDialog dialog = new CustomDialog(context,
				R.style.custom_dialog_style,
				R.layout.custom_common_dialog_layout);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(true);
		dialog.show();

		if (!TextUtils.isEmpty(title)) {
			((TextView) dialog.findViewById(R.id.tv_warn_title)).setText(title);
		}

		if (!TextUtils.isEmpty(dialogContent)) {
			((TextView) dialog.findViewById(R.id.tv_dialog_content))
					.setText(dialogContent);
		}

		if (!TextUtils.isEmpty(nagetive)) {
			((TextView) dialog.findViewById(R.id.tv_cancel)).setText(nagetive);
		}

		if (!TextUtils.isEmpty(positive)) {
			((TextView) dialog.findViewById(R.id.tv_ok)).setText(positive);
		}

		dialog.findViewById(R.id.rv_ok).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								if (positiveCallBack != null) {
									positiveCallBack.onClick(null);
								}
								dialog.dismiss();
							}
						}, RippleView.DURATION);

					}
				});
		dialog.findViewById(R.id.rv_cancel).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								dialog.dismiss();
							}
						}, RippleView.DURATION);
					}
				});
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 通用对话框（2选1）
	 * @return void
	 * @date 2015年7月16日 上午11:23:26
	 */
	public void createSingleChoiceDialog(Context context, String choice1,
			String choice2, final View.OnClickListener callBack1,
			final View.OnClickListener callBack2) {
		final CustomDialog dialog = new CustomDialog(context,
				R.style.custom_dialog_style,
				R.layout.custom_single_choice_dialog_layout);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(true);
		dialog.show();

		if (!TextUtils.isEmpty(choice1)) {
			((TextView) dialog.findViewById(R.id.tv_choice1)).setText(choice1);
		}

		if (!TextUtils.isEmpty(choice2)) {
			((TextView) dialog.findViewById(R.id.tv_choice2)).setText(choice2);
		}

		dialog.findViewById(R.id.rv_choice1).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								if (callBack1 != null) {
									callBack1.onClick(null);
								}
								dialog.dismiss();
							}
						}, RippleView.DURATION);
					}
				});

		dialog.findViewById(R.id.rv_choice2).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								if (callBack2 != null) {
									callBack2.onClick(null);
								}
								dialog.dismiss();
							}
						}, RippleView.DURATION);
					}
				});
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 通用对话框（苹果样式）
	 * @return void
	 * @date 2015年7月17日 上午10:58:25
	 */
	public void createHeadPopupWindow(Activity activity, View parent,
			View.OnClickListener callBack1, View.OnClickListener callBack2) {
		createHeadPopupWindow(activity, parent, null, null, callBack1,
				callBack2);
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 通用对话框（苹果样式）
	 * @return void
	 * @date 2015年7月16日 上午11:24:03
	 */
	public void createHeadPopupWindow(Activity activity, View parent,
			String choice1, String choice2, View.OnClickListener callBack1,
			View.OnClickListener callBack2) {
//		if (mHeadPopupWindow == null) {
//			initHeadPopupWindow(activity, choice1, choice2, callBack1,
//					callBack2);
//		}
		initHeadPopupWindow(activity, choice1, choice2, callBack1, callBack2);
		if (!mHeadPopupWindow.isShowing()) {
			mHeadPopupWindow.showAtLocation(parent, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0);
			// 打开蒙层效果
			WindowManager.LayoutParams lp = activity.getWindow()
					.getAttributes();// 1. 设置半透明主题
			lp.alpha = 0.5f;// 2. 设置window的alpha值 (0.0 - 1.0)
			activity.getWindow().setAttributes(lp);
		} else {
			mHeadPopupWindow.dismiss();
		}
	}

	@SuppressWarnings("deprecation")
	private void initHeadPopupWindow(final Activity context, String choice1,
			String choice2, final View.OnClickListener callBack1,
			final View.OnClickListener callBack2) {
		LayoutInflater mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View dialog = mLayoutInflater.inflate(
				R.layout.dialog_head_select_layout, null);
		mHeadPopupWindow = new PopupWindow(dialog, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mHeadPopupWindow.setAnimationStyle(R.style.popupwindow_animation);
		mHeadPopupWindow.setFocusable(true);
		mHeadPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mHeadPopupWindow.setOutsideTouchable(true);
		mHeadPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// 关闭蒙层效果
				WindowManager.LayoutParams lp = context.getWindow()
						.getAttributes();// 1.设置完全透明主题
				lp.alpha = 1.0f;// 2. 设置window的alpha值 (0.0 - 1.0)
				context.getWindow().setAttributes(lp);
			}
		});

		if (!TextUtils.isEmpty(choice1)) {
			((TextView) dialog.findViewById(R.id.tv_choice1)).setText(choice1);

		}

		if (!TextUtils.isEmpty(choice2)) {
			((TextView) dialog.findViewById(R.id.tv_choice2)).setText(choice2);

		}

		dialog.findViewById(R.id.rv_choice1).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								if (callBack1 != null) {
									callBack1.onClick(null);
								}
								mHeadPopupWindow.dismiss();
							}
						}, RippleView.DURATION);
					}
				});

		dialog.findViewById(R.id.rv_choice2).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								if (callBack2 != null) {
									callBack2.onClick(null);
								}
								mHeadPopupWindow.dismiss();
							}
						}, RippleView.DURATION);
					}
				});
		TextView tvHeadCancel = (TextView) dialog
				.findViewById(R.id.tv_personal_head_cancel);
		tvHeadCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mHeadPopupWindow.dismiss();
					}
				}, RippleView.DURATION);
			}
		});
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 通用对话框（单个button）
	 * @return void
	 * @date 2015年7月17日 上午9:56:41
	 */
	public void createSingleButtonDialog(Context context, String dialogContent) {
		createSingleButtonDialog(context, null, dialogContent, null);
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 通用对话框（单个button）
	 * @return void
	 * @date 2015年7月17日 上午9:56:14
	 */
	public void createSingleButtonDialog(Context context, String title,
			String dialogContent, String positive) {
		final CustomDialog dialog = new CustomDialog(context,
				R.style.custom_dialog_style,
				R.layout.custom_single_button_dialog_layout);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(true);
		dialog.show();

		if (!TextUtils.isEmpty(title)) {
			((TextView) dialog.findViewById(R.id.tv_warn_title)).setText(title);
		}

		if (!TextUtils.isEmpty(dialogContent)) {
			((TextView) dialog.findViewById(R.id.tv_dialog_content))
					.setText(dialogContent);
		}

		if (!TextUtils.isEmpty(positive)) {
			((TextView) dialog.findViewById(R.id.tv_ok)).setText(positive);
		}

		dialog.findViewById(R.id.rv_ok).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								dialog.dismiss();
							}
						}, RippleView.DURATION);
					}
				});
	}

}
