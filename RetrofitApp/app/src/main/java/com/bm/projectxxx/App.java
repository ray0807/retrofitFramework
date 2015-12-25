package com.bm.projectxxx;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.os.Environment;

import com.bm.projectxxx.fresco.ImagePipelineConfigFactory;
import com.bm.projectxxx.utils.cache.DataCache;
import com.bm.projectxxx.utils.constant.Constant;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

@SuppressLint("NewApi")
public class App extends Application {

	static App instance;
	
	public DataCache jsonCache = null;

	public final boolean isDebug = false;// 是否为调试模式

	public App() {
		instance = this;
	}

	public static synchronized App getInstance() {
		return instance;
	}

	public void onCreate() {
		super.onCreate();
		init();
	}

	private void init() {
//		CrashHandler crashHandler = CrashHandler.getInstance(); // 获取异常捕捉单例对象
//		crashHandler.init(getApplicationContext());

		createAllDir();
		// 初始化 Fresco，带上参数配置
		Fresco.initialize(this, ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(this));
	}
	
	public DataCache getJsonCache() {
		return jsonCache;
	}
	
	/**
	 * 
	 * @author 赵成龙
	 * @Description 创建相关目录
	 * @return void
	 * @date 2015年5月7日 下午1:02:16
	 */
	private void createAllDir() {
		// 创建系统缓存总目录
		File dirFile = new File(getDiskCacheDir());
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		// 创建图片上传的文件夹
		File PHOTO_DIR = new File(getDiskCacheDir()
				+ Constant.UPLOAD_PICTURE_PATH);
		if (!PHOTO_DIR.exists()) {
			PHOTO_DIR.mkdirs();
		}

		// 创建JSON缓存的文件夹
		File jsonCacheFile = new File(getDiskCacheDir()
				+ Constant.JSON_DATA_CACHE_PATH);
		if (!jsonCacheFile.exists()) {
			jsonCacheFile.mkdirs();
		}
		jsonCache = DataCache.get(jsonCacheFile);

		// 创建用户信息缓存的文件夹(保存到SharedPreferences目录)
		File userCacheFile = new File("/data/data/" + getPackageName().toString() + "/shared_prefs");
		if (!userCacheFile.exists()) {
			userCacheFile.mkdirs();
		}

		// 创建保存报错信息文件的文件夹
		File crashFile = new File(getDiskCacheDir()
				+ Constant.CRASH_ERROR_FILE_PATH);
		if (!crashFile.exists()) {
			crashFile.mkdirs();
		}

		// 创建图片缓存
		File imageCacheFile = new File(getDiskCacheDir()
				+ Constant.IMAGE_CACHE_PATH);
		if (!imageCacheFile.exists()) {
			imageCacheFile.mkdirs();
		}
	}
	
	/**
	 * 
	 * @author 赵成龙
	 * @Description 获取磁盘缓存路径
	 * @return String
	 * @date 2015年5月7日 下午1:09:06
	 */
	public String getDiskCacheDir() {
		String cachePath = null;
		if (sdCardIsAvailable()) {
			// 内存卡可用时，数据放在/sdcard/Android/data/<application package>/cache中
			if (hasExternalCacheDir()) {
				// 2.2版本才有将应用缓存转移到sd卡的功能
				cachePath = getExternalCacheDir().getPath();
			} else {
				// 2.2以前我们需要自己构造
				final String cacheDir = "/Android/data/" + getPackageName()
						+ "/cache/";
				cachePath = Environment.getExternalStorageDirectory().getPath()
						+ cacheDir;
			}
		} else {
			// SD卡不可用时，数据放在data/data/<application package>/cache中
			cachePath = getCacheDir().getPath();
		}
		return cachePath;
	}

	/**
	 * 清除app缓存
	 */
	public void clearAppCache() throws Exception {
		// 清除数据缓存，分别为files、SD卡、data、数据库
		clearCacheFolder(getFilesDir(), System.currentTimeMillis());
		clearCacheFolder(new File(getDiskCacheDir()),
				System.currentTimeMillis());
		clearCacheFolder(getCacheDir(), System.currentTimeMillis());
		// clearCacheFolder(new
		// File("/data/data/com.bm.lingtaozhisheng/databases"),
		// System.currentTimeMillis());
	}

	/**
	 * 清除缓存目录
	 * 
	 * @param dir
	 *            目录
	 * @param curTime
	 *            当前系统时间
	 * @return
	 */
	private int clearCacheFolder(File dir, long curTime) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {
				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, curTime);
					}
					if (child.lastModified() < curTime) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}

	private boolean hasExternalCacheDir() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	/**
	 * 检测sdcard是否可用
	 * 
	 * @return true为可用，否则为不可用
	 */
	public static boolean sdCardIsAvailable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
			      || !Environment.isExternalStorageRemovable()) {
			return true;
		}
		return false;
	}

}
