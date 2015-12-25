package com.bm.projectxxx.utils;

import java.io.File;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

import com.bm.projectxxx.App;
import com.bm.projectxxx.utils.constant.Constant;

/**
 * 调用相机进行图片截取的操作类
 * 
 * @author Eric
 * 
 */
public class TakePhoto {

	private static final File PHOTO_DIR = new File(App.getInstance().getDiskCacheDir()
			+ Constant.UPLOAD_PICTURE_PATH);

	public File mCurrentPhotoFile = null;// 照相机拍照得到的图片
	private Context mContext = null;// 上下文对象
	private Activity mActivity = null;// 请求拍照或图库的activity
	// 请求照相机后，回调时的一个标识
	public static final int CAMERA_WITH_DATA = 3023;
	// 请求图库后，回调时的一个标识
	public static final int PHOTO_PICKED_WITH_DATA = 3021;

	public static final int PHOTORESOULT = 3022;

	public static final String URI_HEAD = "file://";
	public static final int PHOTO_RESULT = 3;

	// 构造函数
	public TakePhoto(Context context, Activity activity) {
		this.mContext = context;
		this.mActivity = activity;
	}

	/**
	 * 检查所选图片的路径是否存在
	 * 
	 * @param path
	 * @return
	 */
	public boolean checkFileExists(String path) {
		try {
			File file = new File(path);
			if (file.exists()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 请求照相机拍照
	public void doTakePhoto() {
		try {
			PHOTO_DIR.mkdirs();// 创建照片的存储目录
			mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			mActivity.startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(mActivity, "没有程序执行拍照操作", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	/**
	 * 请求照相机程序
	 */
	public void takeCropPhoto() {
		try {
			PHOTO_DIR.mkdirs();// 创建照片的存储目录
			mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名

			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
			mActivity.startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(mActivity, "没有程序执行拍照操作", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对拍照后的图片进行剪切 （调用图片剪辑程序 (实现拍照后缩放图片)）
	 * 
	 * @param f
	 *            保存剪切后的图片
	 */
	public void doCropTakePhoto(File f) {
		try {
			// 启动gallery去剪辑这个照片
			final Intent intent = getCropImageIntent(Uri.fromFile(f));
			mActivity.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (Exception e) {
			Toast.makeText(mContext, "失败", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	// 以随机的UUID为图片的名称
	public static String getPhotoFileName() {
		return UUID.randomUUID() + ".jpg";
	}

	// 将拍照完后的照片，存储在f文件对象中
	public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	// 请求Gallery程序
	public void doPickPhotoFromGallery() {
		try {
			PHOTO_DIR.mkdirs();// 创建照片的存储目录
			final Intent intent = getPhotoPickIntent();
			mActivity.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(mContext, "没有找到相应的程序执行该操作", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	// 封装请求Gallery的intent
	public static Intent getPhotoPickIntent() {
		// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		return intent;
	}

	// 封装请求Gallery的intent
	public static Intent getPhotoPickIntent(File f) {
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setDataAndType(Uri.fromFile(f), "image/*");// 设置要裁剪的图片
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		return intent;
	}

	/**
	 * 请求Gallery(图库)程序
	 */
	public void doPickPhotoFromGalleryByCrop() {
		try {
			PHOTO_DIR.mkdirs();// 创建照片的存储目录
			final Intent intent = getPhotoPickIntentCut();
			mActivity.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(mContext, "没有找到相应的程序执行该操作", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	// 背景尺寸
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 480);
		intent.putExtra("aspectY", 250);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 480);
		intent.putExtra("outputY", 250);
		// intent.putExtra("return-data", true);
		intent.putExtra("output", Uri.fromFile(mCurrentPhotoFile));// 保存到原文件
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		mActivity.startActivityForResult(intent, PHOTORESOULT);
	}

	/**
	 * 调用图片剪切程序
	 * 
	 * @param f
	 *            保存处理后的图片对象
	 * @return 封装请求Gallery(图库中)的intent
	 */
	public static Intent getPhotoPickIntentCut(File f) {
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setDataAndType(Uri.fromFile(f), "image/*");// 设置要裁剪的图片
		intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
		intent.putExtra("aspectX", 1); // 这两项为裁剪框的比例
		intent.putExtra("aspectY", 1); // x:y=1:1
		intent.putExtra("outputX", 180);
		intent.putExtra("outputY", 180);
		// 如果想设置成自定义的裁剪比例，需要设置noFaceDetection为true。
		// intent.putExtra("noFaceDetection", true);
		intent.putExtra("output", Uri.fromFile(f));// 保存到原文件
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		return intent;
	}

	// 调用图片剪切程序（add by max 2013-12-26）
	public static Intent getPhotoPickIntentCut() {
		// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");// 设置要裁剪的图片
		intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
		intent.putExtra("aspectX", 1); // 这两项为裁剪框的比例
		intent.putExtra("aspectY", 1); // x:y=1:1
		intent.putExtra("outputX", 180);
		intent.putExtra("outputY", 180);
		// 如果想设置成自定义的裁剪比例，需要设置noFaceDetection为true。
		// intent.putExtra("noFaceDetection", true);
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		intent.putExtra("return-data", true);
		return intent;
	}

	/**
	 * 调用图片剪辑程序
	 * 
	 * @param photoUri
	 *            保存处理后的图片数据
	 * @return 请求剪切程序的intent对象
	 */
	public static Intent getCropImageIntent(Uri photoUri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 180);
		intent.putExtra("outputY", 180);
		intent.putExtra("return-data", true);
		return intent;
	}

	// ========================================处理图片之上下、左右翻转，前景图==================================
	// android图片处理之山下、左右翻转，前景图
	public Bitmap createBitmapForWatermark(Bitmap src, Bitmap watermark) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		Bitmap newb = Bitmap.createBitmap(ww, wh, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);

		Matrix m = new Matrix();
		m.postScale(1, -1);// 上下翻转
		m.postRotate(-90);// 左右翻转
		Bitmap newBitmap = Bitmap.createBitmap(src, 0, 0, w, h, m, true);
		cv.drawBitmap(newBitmap, new Rect(0, 0, newBitmap.getWidth(), newBitmap.getHeight()), new Rect(0, 0, ww, wh),
				null);

		cv.drawBitmap(watermark, 0, 0, null);// 前景图或水印

		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newb;
	}

	public Bitmap createBitmapForTrunOver(Bitmap src, int i) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();

		Matrix m = new Matrix();
		m.postRotate(-90 * i);// 左右翻转
		Bitmap newBitmap = Bitmap.createBitmap(src, 0, 0, w, h, m, true);
		return newBitmap;
	}

	// 下面的方法则是用像素处理，速度会慢一点。
	Bitmap changePixel_180(Bitmap orgBitmap) {
		int pixels_width;
		int pixels_height;
		pixels_width = orgBitmap.getWidth();
		pixels_height = orgBitmap.getHeight();
		Bitmap newb = Bitmap.createBitmap(pixels_width, pixels_height, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		for (int i = 0; i < pixels_height; i++) {
			for (int j = 0; j < pixels_width; j++) {
				newb.setPixel(j, i, orgBitmap.getPixel(j, pixels_height - 1 - i));
			}
		}
		return newb;
	}

	Bitmap changePixel_90_r(Bitmap orgBitmap) {
		int pixels_width;
		int pixels_height;
		pixels_width = orgBitmap.getWidth();
		pixels_height = orgBitmap.getHeight();
		Bitmap newb = Bitmap.createBitmap(pixels_height, pixels_width, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		for (int i = 0; i < newb.getHeight(); i++) {
			for (int j = 0; j < newb.getWidth(); j++) {
				newb.setPixel(j, i, orgBitmap.getPixel(pixels_width - 1 - i, j));
			}
		}
		return newb;
	}

	// ========================================处理图片之上下、左右翻转，前景图==================================

	/**
	 * 获取图片路径（包括4.4）
	 * 
	 * @Title: getKITKATPath
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param context
	 * @param @param uri
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressLint("NewApi")
	public static String getKITKATPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
}
