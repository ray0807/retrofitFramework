/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title ZipUtil.java
 * @Prject HuaanFund
 * @Package com.bm.huaanfund.utils
 * @Description TODO
 * @author zhaocl  
 * @date 2015年4月9日 上午8:53:01
 * @version V1.0  
 */
package com.bm.projectxxx.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Copyright © 2015 蓝色互动. All rights reserved.
 * 
 * @Title ZipUtil.java
 * @Prject HuaanFund
 * @Package com.bm.huaanfund.utils
 * @Description 将一个字符串按照zip方式压缩和解压缩
 * 
 * @author 赵成龙
 * @date 2015年4月9日 上午8:53:01
 * @version V1.0
 */
public class ZipUtil {

	// 压缩
	public static byte[] compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes("UTF-8"));
		gzip.close();
		return out.toByteArray();
	}

	// 解压缩
	public static byte[] uncompress(byte[] data) throws IOException {
		if (data == null || data.length == 0) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}

}
