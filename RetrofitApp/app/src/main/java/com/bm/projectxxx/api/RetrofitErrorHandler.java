/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title RetrofitErrorHandler.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.api
 * @Description TODO
 * @author zhaocl  
 * @date 2015年3月24日 上午10:52:41
 * @version V1.0  
 */
package com.bm.projectxxx.api;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import android.os.Handler;
import android.os.Message;

/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title RetrofitErrorHandler.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.api
 * @Description 请求异常处理类()
 * @author 赵成龙  
 * @date 2015年3月24日 上午10:52:41
 * @version V1.0  
 */
public class RetrofitErrorHandler implements ErrorHandler {
	
	private Handler mHandler;
	
	public RetrofitErrorHandler(Handler handler) {
		mHandler = handler;
	}

	@Override
	public Throwable handleError(RetrofitError error) {
		Message msg = new Message();
		msg.obj = error;
		mHandler.sendMessage(msg);
		return error;
	}

}
