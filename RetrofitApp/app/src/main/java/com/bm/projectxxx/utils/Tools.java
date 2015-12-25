package com.bm.projectxxx.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * 
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title Tools.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.utils
 * @Description 通用工具类
 * @author zhaocl  
 * @date 2015年7月15日 下午12:06:37
 * @version V1.0
 */
@SuppressLint("SimpleDateFormat")
public class Tools {

	/**
	 * 格式化价格(保留小数点后两位)
	 * 
	 * @param argStr
	 * @return
	 */
	public static String getFloatDotStr(String argStr) {
		float arg = Float.valueOf(argStr);
		DecimalFormat fnum = new DecimalFormat("##0.00");
		return fnum.format(arg);
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 网络是否可用
	 * @return boolean
	 * @date 2015年7月15日 下午12:07:56
	 */
	public static boolean IsInternetValidate(final Context context) {
		try {
			ConnectivityManager manger = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manger.getActiveNetworkInfo();
			return (info != null && info.isConnected());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 得到应用版本名
	 * @return String
	 * @date 2015年7月15日 下午12:08:15
	 */
	public static String getVersionName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;

	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 得到应用版本号
	 * @return int
	 * @date 2015年7月15日 下午12:08:55
	 */
	public static int getVersionCode(Context context) {
		int verCode = 0;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode;

	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 毫秒格式化
	 * @return String
	 * @date 2015年7月15日 下午12:09:46
	 */
	public static String millisToString(long millis) {
		boolean negative = millis < 0;
		millis = java.lang.Math.abs(millis);

		millis /= 1000;
		int sec = (int) (millis % 60);
		millis /= 60;
		int min = (int) (millis % 60);
		millis /= 60;
		int hours = (int) millis;

		String time;
		DecimalFormat format = (DecimalFormat) NumberFormat
				.getInstance(Locale.US);
		format.applyPattern("00");
		if (millis > 0) {
			time = (negative ? "-" : "")
					+ (hours == 0 ? 00 : hours < 10 ? "0" + hours : hours)
					+ ":" + (min == 0 ? 00 : min < 10 ? "0" + min : min) + ":"
					+ (sec == 0 ? 00 : sec < 10 ? "0" + sec : sec);
		} else {
			time = (negative ? "-" : "") + min + ":" + format.format(sec);
		}
		return time;
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 判断 多个字段的值否为空
	 * @return boolean
	 * @date 2015年7月15日 下午12:10:13
	 */
	public static boolean isNull(String... ss) {
		for (int i = 0; i < ss.length; i++) {
			if (null == ss[i] || ss[i].equals("")
					|| ss[i].equalsIgnoreCase("null")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 判断 一个字段的值否为空
	 * @return boolean
	 * @date 2015年7月15日 下午12:10:30
	 */
	public static boolean isNull(String s) {
		if (null == s || s.equals("") || s.equalsIgnoreCase("null")) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 判断sd卡是否存在
	 * @return boolean
	 * @date 2015年7月15日 下午12:11:26
	 */
	public static boolean isSDCardAvailable() {
		String status = Environment.getExternalStorageState();
		return status.equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 显示纯汉字的星期名称
	 * 
	 * @author TangWei 2013-10-25上午11:31:51
	 * @param i
	 *            星期：1,2,3,4,5,6,7
	 * @return
	 */
	public static String changeWeekToCN(int i) {
		switch (i) {
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		case 7:
			return "星期日";
		default:
			return "";
		}
	}

	/**
	 * 验证手机号码
	 * 
	 * @author TangWei
	 * @param phone
	 * @return
	 */
	public static boolean validatePhone(String phone) {
		if (isNull(phone))
			return false;
		if (phone.length() != 11) {
			return false;
		}
		String pattern = "^1[3,4,5,6,8]+\\d{9}$";
		return phone.matches(pattern);
	}

	/**
	 * 检查身份证是 否合法,15位或18位(或者最后一位为X).只是做了位数,没有具体到细节.
	 * 
	 * @param text
	 * @return
	 */
	public static boolean validateIdCard(String idCard) {
		if (isNull(idCard)) {
			return false;
		}
		return idCard.matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
	}

	/**
	 * 简单的验证一下银行卡号
	 * 
	 * @param bankCard
	 *            信用卡是16位，其他的是13-19位
	 * @return
	 */
	public static boolean validateBankCard(String bankCard) {
		if (isNull(bankCard))
			return false;
		String pattern = "^\\d{13,19}$";
		return bankCard.matches(pattern);
	}

	/**
	 * 验证邮箱
	 * 
	 * @author TangWei 2013-12-13下午2:33:16
	 * @param email
	 * @return
	 */
	public static boolean validateEmail(String email) {
		if (isNull(email))
			return false;
		String pattern = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
		return email.matches(pattern);
	}

	/**
	 * 隐藏系统键盘,光标依然正常显示;兼容了高低版本;
	 * 
	 * @param editText
	 */
	public static void hideSoftInputMethod(Activity activity, EditText editText) {
		activity.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		String methodName = null;
		if (currentVersion >= 16) {
			// 4.2
			methodName = "setShowSoftInputOnFocus";
		} else if (currentVersion >= 14) {
			// 4.0
			methodName = "setSoftInputShownOnFocus";
		}
		if (methodName == null) {
			editText.setInputType(InputType.TYPE_NULL);
		} else {
			Class<EditText> cls = EditText.class;
			Method setShowSoftInputOnFocus;
			try {
				setShowSoftInputOnFocus = cls.getMethod(methodName,
						boolean.class);
				setShowSoftInputOnFocus.setAccessible(true);
				setShowSoftInputOnFocus.invoke(editText, false);
			} catch (NoSuchMethodException e) {
				editText.setInputType(InputType.TYPE_NULL);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将100以内的阿拉伯数字转换成中文汉字（15变成十五）
	 * 
	 * @param round
	 *            最大值50
	 * @return >99的，返回“”
	 */
	public static String getHanZi1(int round) {
		if (round > 99 || round == 0) {
			return "";
		}
		int ge = round % 10;
		int shi = (round - ge) / 10;
		String value = "";
		if (shi != 0) {
			if (shi == 1) {
				value = "十";
			} else {
				value = getHanZi2(shi) + "十";
			}

		}
		value = value + getHanZi2(ge);
		return value;
	}

	/**
	 * 将0-9 转换为 汉字（ _一二三四五六七八九）
	 * 
	 * @param round
	 * @return
	 */
	private static String getHanZi2(int round) {
		String[] value = { "", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		return value[round];
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 根据Unicode编码完美的判断中文汉字和符号
	 * @return boolean
	 * @date 2015年7月15日 下午12:12:42
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @author 赵成龙
	 * @Description 完整的判断中文汉字和符号
	 * @return boolean
	 * @date 2015年7月15日 下午12:12:55
	 */
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!isChinese(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取屏幕分辨率
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
		int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
		// 1080 1920
		// 480 854
		// 720 1280
		/** 低档 */
		if (width == 480) {
			width = width - 80;
			height = height - 80;
		} else if (width == 720) {
			/** 中档 */
			width = width - 120;
			height = height - 120;
		} else if (width == 1080) {
			/** 高档 */
			width = width - 180;
			height = height - 180;
		} else if (width == 3840) {
			/** 2k */
			width = width - 300;
			height = height - 300;
		}
		int result[] = { width, height };
		return result;
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 */
	public static void backgroundAlpha(Activity activity, float bgAlpha) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		activity.getWindow().setAttributes(lp);
	}

	/**
	 * 中间加空格的字符串
	 * 
	 * @param str
	 */
	public static String appendSpace(String para) {
		int length = para.length();
		char[] value = new char[length << 1];
		for (int i = 0, j = 0; i < length; ++i, j = i << 1) {
			value[j] = para.charAt(i);
			value[1 + j] = ' ';
		}
		return new String(value);
	}

	/**
	 * 返回隐藏中间数字的手机号字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getPhoneString(String str) {
		if (str != null && !"".equals(str)) {
			return str.substring(0, str.length() - (str.substring(3)).length())
					+ " XXXX " + str.substring(7);
		}
		return "";
	}

	/**
	 * 返回隐藏中间数字的号字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getIdTypeString(String str) {
		String IdTypeStr = "";
		if (str.length() == 18) {
			IdTypeStr = str.substring(0,
					str.length() - (str.substring(8)).length())
					+ "********" + str.substring(16);
		} else if (str.length() == 15) {
			IdTypeStr = str.substring(0,
					str.length() - (str.substring(8)).length())
					+ "********" + str.substring(13);
		}
		return IdTypeStr;
	}

	/**
	 * 格式化金额
	 */
	public static String getFormatAsset(String s) {
		String b = "";
		String t = "";
		if (s.contains(".")) {
			if (Double.valueOf(s) == 0) {
				return "0";
			} else {
				int len = s.indexOf(".");
				b = s.substring(len);
				t = s.substring(0, len);
				if (t.length() > 3) {
					StringBuffer sb = new StringBuffer();
					char[] chars = t.toString().replace(" ", "").toCharArray();
					if (s.length() % 3 == 0) {
						for (int i = 0; i < chars.length; i++)
							if (i % 3 == 0 && i != 0) {
								sb.append(",");
								sb.append(chars[i]);
							} else {
								sb.append(chars[i]);
							}

					} else if (t.length() % 3 == 1) {
						for (int i = 0; i < chars.length; i++) {
							if (i % 3 == 1 && i != 0) {
								sb.append(",");
								sb.append(chars[i]);
							} else {
								sb.append(chars[i]);
							}
						}
					} else if (t.length() % 3 == 2) {
						for (int i = 0; i < chars.length; i++) {
							if (i % 3 == 2 && i != 0) {
								sb.append(",");
								sb.append(chars[i]);
							} else {
								sb.append(chars[i]);
							}
						}
					}
					if (b.length() == 2) {
						sb.append(b).append("0");
					} else {
						sb.append(b);
					}
					return sb.toString();
				} else {
					if (b.length() == 2) {
						return s + "0";
					} else {
						return s;
					}
				}
			}
		} else {
			if (s.length() > 3) {
				StringBuffer sb = new StringBuffer();
				char[] chars = s.toString().replace(" ", "").toCharArray();
				if (s.length() % 3 == 0) {
					for (int i = 0; i < chars.length; i++)
						if (i % 3 == 0 && i != 0) {
							sb.append(",");
							sb.append(chars[i]);
						} else {
							sb.append(chars[i]);
						}

				} else if (s.length() % 3 == 1) {
					for (int i = 0; i < chars.length; i++) {
						if (i % 3 == 1 && i != 0) {
							sb.append(",");
							sb.append(chars[i]);
						} else {
							sb.append(chars[i]);
						}
					}
				} else if (s.length() % 3 == 2) {
					for (int i = 0; i < chars.length; i++) {
						if (i % 3 == 2 && i != 0) {
							sb.append(",");
							sb.append(chars[i]);
						} else {
							sb.append(chars[i]);
						}
					}
				}
				sb.append(".00");
				return sb.toString();
			} else if (Double.valueOf(s) == 0) {
				return "0";
			} else if (s.length() <= 3) {
				return s + "0.00";
			}
		}
		return null;
	}

	/** 密码输入隐藏，没有回显效果 */
	public static String getStars(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append("*");
		}
		return sb.toString();
	}

	/**
	 * 数组降序
	 * 
	 * @param a
	 */
	public static void sort(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] > a[i]) {
					int t = a[j];
					a[j] = a[i];
					a[i] = t;
				}
			}
		}
	}

	/**
	 * 得到N天前的日期
	 * 
	 * @param num
	 * @return
	 */
	public static String getDate(int num) {
		long time = System.currentTimeMillis() + (1000L * 60 * 60 * 24 * num);
		String pattern = "yyyy-MM-dd";
		Date date = new Date();
		if (time > 0) {
			date.setTime(time);
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 得到N天前的日期
	 * 
	 * @param num
	 * @return
	 * @throws ParseException
	 */
	public static String getDate(String beginDate, int num) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
		long millionSeconds = 0;
		try {
			millionSeconds = sdf.parse(beginDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time = millionSeconds + (1000L * 60 * 60 * 24 * num);
		String pattern = "yyyy-MM-dd";
		Date date = new Date();
		if (time > 0) {
			date.setTime(time);
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 格式化时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static String getTime(String time) {
		long transactionDate = Long.parseLong(time);
		String pattern = "yyyy-MM-dd";
		Date date = new Date();
		if (transactionDate > 0) {
			date.setTime(transactionDate);
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 根据时间字符串来获得星期数据
	 * 
	 * @param weekDay
	 * @return
	 */
	public static String getWeekDay(String weekDay) {
		String weekStr = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(weekDay);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int day = c.get(Calendar.DAY_OF_WEEK);
			if (day == 1) {
				weekStr = "星期日";
			} else if (day == 2) {
				weekStr = "星期一";
			} else if (day == 3) {
				weekStr = "星期二";
			} else if (day == 4) {
				weekStr = "星期三";
			} else if (day == 5) {
				weekStr = "星期四";
			} else if (day == 6) {
				weekStr = "星期五";
			} else if (day == 7) {
				weekStr = "星期六";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return weekStr;
	}

	/**
	 * 输入框小数点后保留两位
	 * 
	 * @param editText
	 */
	public static void setPricePoint(final EditText editText) {
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > 2) {
						s = s.toString().subSequence(0,
								s.toString().indexOf(".") + 3);
						editText.setText(s);
						editText.setSelection(s.length());
					}
				}
				if (s.toString().trim().substring(0).equals(".")) {
					s = "0" + s;
					editText.setText(s);
					editText.setSelection(2);
				}

				if (s.toString().startsWith("0")
						&& s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						editText.setText(s.subSequence(0, 1));
						editText.setSelection(1);
						return;
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	static long lastClickTime;

	/**
	 * 防止多次点击重复页面的问题.传递机制
	 * 
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (timeD >= 0 && timeD <= 500) {
			return true;
		} else {
			lastClickTime = time;
			return false;
		}
	}

	/**
	 * 判断当前应用程序处于前台还是后台(不需要额外权限)
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					// Log.i("后台", appProcess.processName);
					return true;
				} else {
					// Log.i("前台", appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * 判断当前应用程序处于前台还是后台(需要额外权限)
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isApplicationBroughtToBackground(final Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}

}