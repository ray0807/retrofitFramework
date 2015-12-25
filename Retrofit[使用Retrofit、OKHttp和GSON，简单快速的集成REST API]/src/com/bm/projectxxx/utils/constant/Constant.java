package com.bm.projectxxx.utils.constant;

/**
 * 
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title Constant.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.utils.constant
 * @Description 全局常量
 * @author 赵成龙  
 * @date 2015年5月7日 下午1:15:58
 * @version V1.0
 */

public class Constant {

	// 用户信息
	public final static String USER_INFO = "user_info";
	
	// 是否已登录
	public final static String IS_LOGIN = "is_login"; 

	// 保存着上传图片的路径
	public final static String UPLOAD_PICTURE_PATH = "/imageUpload/";

	// json格式数据缓存的路径
	public final static String JSON_DATA_CACHE_PATH = "/jsonCache/";

	// 保存报错信息文件的路径
	public final static String CRASH_ERROR_FILE_PATH = "/crash/";

	// 下载文件的路径
	public final static String DOWNLOAD_FILE_PATH = "/download/";
	
	// 图片缓存的路径
	public final static String IMAGE_CACHE_PATH = "/imageCache/";
	
	// 系统可用RAM内存大小
	public static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
	
	// 磁盘缓存大小限制
	public static final int MAX_DISK_CACHE_SIZE = 40 * 1024 * 1024; // 40M
	
	// RAM内存缓存大小限制
	public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;

	// 网络请求错误标识码
	public final static String NAME_PASSWORD_ERROR = "name_password_error"; // 用户名或者密码错误
	public final static String PARAM_ERROR = "param_error"; // 传入参数错误
	public final static String SERVER_ERROR = "server_error"; // 服务器异常
	public final static String URL_ERROR = "url_error"; // url有误
	public final static String UNKNOW_ERROR = "unknow_error"; // 未知异常
	public final static String DATA_ACCESS_ERROR = "data_access_error"; // 接口数据层异常
	public final static String DATA_REQUEST_ERROR = "app_request_error"; // 请求异常
	
}
