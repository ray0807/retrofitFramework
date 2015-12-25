package com.bm.projectxxx.utils;

import java.io.File;

public class FileUtils {
	/**
	 * FileUtils的实例。
	 */
	private static FileUtils mFileUtils;

	/**
	 * 获取FileUtils的实例。
	 * 
	 * @return FileUtils的实例。
	 */
	public static FileUtils getInstance() {
		if (mFileUtils == null) {
			mFileUtils = new FileUtils();
		}
		return mFileUtils;
	}

	// 删除该文件夹下的所有文件(不包括本身文件夹和子文件夹)
	public void deleteFiles(File file) {
		if (!file.exists()) {
			return;
		} else {
			if (file.isFile()) {
				file.delete();
				return;
			} else if (file.isDirectory()) {
				File[] childFile = file.listFiles();
				if (childFile == null || childFile.length == 0) {
					return;
				}

				for (File f : childFile) {
					f.delete();
				}
			}
		}
	}

}
