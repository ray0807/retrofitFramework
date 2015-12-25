package com.bm.projectxxx.utils.constant;

import java.io.Serializable;

/**
 * 
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title URLs.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.utils.constant
 * @Description 网络接口的集合类，注意ROOT_URL和具体子URL直接的“/”符号要去掉，否则通不过编码
 * @author zhaocl  
 * @date 2015年7月14日 下午12:57:07
 * @version V1.0
 */
public class URLs implements Serializable {

	private static final long serialVersionUID = -4884584597210873945L;

	/**
	 * 根地址
	 */
	public static final String ROOT_URL = "http://back.zhayanwang.com/yingwangkeji";

	/**
	 * 首页六个随机标签
	 */
	public static final String HOME_LABEL_URL = "app/randomLabel.htm";

	/**
	 * 登录
	 */
	public static final String LOGIN_URL = "app/checkMobileLogin.htm";

	/**
	 * 更换头像
	 */
	public static final String UPDATE_HEAD_URL = "app/updateMemberName.htm";

	/**
	 * 发表手记
	 */
	public static final String SEND_FOCUS_URL = "app/sendFocus.htm";

}