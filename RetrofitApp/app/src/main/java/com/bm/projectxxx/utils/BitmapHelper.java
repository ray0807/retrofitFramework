package com.bm.projectxxx.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.net.Uri;

import com.bm.projectxxx.App;
import com.bm.projectxxx.utils.constant.Constant;

/**
 * 位图的基本操作类
 * 
 * @author Eric
 * 
 */
public class BitmapHelper {

	public static final int LONG_PHOTO_LIMIT = 2024;

	private static final File PHOTO_DIR = new File(App.getInstance().getDiskCacheDir()
			+ Constant.UPLOAD_PICTURE_PATH);

	public static String saveBitmap2file(Bitmap bmp, int quality) {
		CompressFormat format = Bitmap.CompressFormat.JPEG;
		// 高清图片 q = 60 , 中等 q = 50 , 最低效果 q = 40
		OutputStream stream = null;
		String path = PHOTO_DIR.getAbsolutePath() + File.separator + getMyUUID() + "--" + bmp.getWidth() + "x"
				+ bmp.getHeight() + ".jpg";
		try {
			stream = new FileOutputStream(path);
			bmp.compress(format, quality, stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return path;
	}

	// getUUID
	private static String getMyUUID() {
		UUID uuid = UUID.randomUUID();
		String uniqueId = uuid.toString();
		return uniqueId;
	}

	/**
	 * 获取网络图片（原图）
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getBitmapFromNetwork(String imageUrl) {
		Bitmap bitmap = null;
		try {
			URL url = new URL(imageUrl);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.connect();
			InputStream in = conn.getInputStream();
			BitmapFactory.Options options = new Options();
			options.inSampleSize = 1;
			bitmap = BitmapFactory.decodeStream(in, new Rect(0, 0, 0, 0), options);
			return bitmap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 写图片文件到SD卡
	 * 
	 * @throws IOException
	 */
	public static void saveImageToSD(Context ctx, String filePath, Bitmap bitmap, int quality) throws IOException {
		if (bitmap != null) {
			File file = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
			if (!file.exists()) {
				file.mkdirs();
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
			bitmap.compress(CompressFormat.JPEG, quality, bos);
			bos.flush();
			bos.close();
			if (ctx != null) {
				scanPhoto(ctx, filePath);
			}
		}
	}

	/**
	 * 让Gallery上能马上看到该图片
	 */
	private static void scanPhoto(Context ctx, String imgFileName) {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File file = new File(imgFileName);
		Uri contentUri = Uri.fromFile(file);
		mediaScanIntent.setData(contentUri);
		ctx.sendBroadcast(mediaScanIntent);
	}

	/**
	 * 根据图片的url获取文件名
	 * 
	 * @param format
	 * @return
	 */
	public static String getFileName(String url) {
		return url.substring(url.lastIndexOf(File.separator) + 1, url.lastIndexOf("."));
	}

	/**
	 * 
	 * @param pathName
	 *            本地图片路径
	 * @param reqWidth
	 *            手机屏幕宽度
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		FileInputStream is = null;
		try {
			is = new FileInputStream(new File(pathName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		BitmapFactory.decodeStream(is, null, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		// is只能用一次，第二次解析时为空
		InputStream stream = null;
		try {
			stream = new FileInputStream(new File(pathName));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}

		Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
		try {
			if (is != null) {
				is.close();
			}
			if (stream != null) {
				stream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
		// 源图片的宽度
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (width > reqWidth) {
			// 计算出实际宽度和目标宽度的比率
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = widthRatio;
		}
		return inSampleSize;
	}

}
