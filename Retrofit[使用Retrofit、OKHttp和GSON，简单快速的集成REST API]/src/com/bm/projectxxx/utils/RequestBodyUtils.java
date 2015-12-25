/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title RequestBodyUtils.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.utils
 * @Description TODO
 * @author zhaocl  
 * @date 2015年4月15日 下午3:47:46
 * @version V1.0  
 */
package com.bm.projectxxx.utils;

import java.util.HashMap;

import com.google.gson.Gson;

/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title RequestBodyUtils.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.utils
 * @Description TODO
 * @author 赵成龙  
 * @date 2015年4月15日 下午3:47:46
 * @version V1.0  
 */
public class RequestBodyUtils {
	
	private static String signKey = "huaangateway";

	private static String dynamicKey = "Hcdjpaq9buQ4iqhuet7M4g==";
	
	private static HashMap<String, String> param;

	public static byte[] params2byte(HashMap<String, String> params) {
		try {
			param = params;
			Gson mGson = new Gson();
			String json = mGson.toJson(params);
			json = appendJsonParams(json, signKey);
			String md5Json = MD5Tools.encodeByMD5(signKey + json + signKey);
			String finalData = getStringLength(md5Json) + md5Json + json;
			byte[] zipData = ZipUtil.compress(finalData);
			return AESUtils.encrypt2(zipData, dynamicKey);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String appendJsonParams(String json, String sessionKey) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{\"inputList\":[" + json);
		buffer.append("], \"z_funcVersion\":\"100\", \"z_funcCode\":\"111\", \"z_tradeType\":\"a\", \"z_commerceId\": \"ha_lanse\", ");
		buffer.append("\"z_sessionKey\":\"" + sessionKey + "\",");
		buffer.append("\"z_timestamp\":\"" + param.get("z_timestamp") + "\"}");
		return buffer.toString();
	}
	
	private static String getStringLength(String sessionKey) {
		int length = sessionKey.length();
		if (length < 10) {
			return "0" + length;
		} else {
			return "" + length;
		}
	}
	
}
