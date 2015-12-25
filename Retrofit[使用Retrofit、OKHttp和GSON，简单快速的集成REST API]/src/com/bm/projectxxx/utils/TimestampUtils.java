/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title TimestampUtils.java
 * @Prject HuaanFund
 * @Package com.bm.huaanfund.utils
 * @Description TODO
 * @author zhaocl  
 * @date 2015年4月8日 下午5:15:14
 * @version V1.0  
 */
package com.bm.projectxxx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title TimestampUtils.java
 * @Prject HuaanFund
 * @Package com.bm.huaanfund.utils
 * @Description TODO
 * @author zhaocl  
 * @date 2015年4月8日 下午5:15:14
 * @version V1.0  
 */
public class TimestampUtils {

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTimestamp() {
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");  
        Date date = new Date();  
        String time = format.format(date);  
        return time;
	}
	
}
