/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title AESUtils.java
 * @Prject HuaanFund
 * @Package com.bm.huaanfund.utils
 * @Description TODO
 * @author zhaocl  
 * @date 2015年4月8日 下午5:49:42
 * @version V1.0  
 */
package com.bm.projectxxx.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

/**
 * Copyright © 2015 蓝色互动. All rights reserved.
 * 
 * @Title AESUtils.java
 * @Prject HuaanFund
 * @Package com.bm.huaanfund.utils
 * @Description AES加密解密工具
 * @author 赵成龙
 * @date 2015年4月8日 下午5:49:42
 * @version V1.0
 */
public class AESUtils {

	// 加密(header),加密后的byte[]要用Base64编码
	public static String encrypt(String data, String key) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			byte[] base64Key = Base64.decode(key, Base64.DEFAULT);
			SecretKeySpec keyspec = new SecretKeySpec(base64Key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, keyspec);
			byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
			return Base64.encodeToString(encrypted, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 加密(body)
	public static byte[] encrypt2(byte[] data, String key) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			byte[] base64Key = Base64.decode(key, Base64.DEFAULT);
			SecretKeySpec keyspec = new SecretKeySpec(base64Key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, keyspec);
			byte[] encrypted = cipher.doFinal(data);
			return encrypted;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 解密
	public static byte[] desEncrypt(byte[] data, String key) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			byte[] base64Key = Base64.decode(key, Base64.DEFAULT);
			SecretKeySpec keyspec = new SecretKeySpec(base64Key, "AES");
			cipher.init(Cipher.DECRYPT_MODE, keyspec);
			byte[] original = cipher.doFinal(data);
			return original;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
