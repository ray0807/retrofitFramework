package com.bm.projectxxx.bean;

import java.io.Serializable;

/**
 * 
 * Copyright © 2015 蓝色互动. All rights reserved.
 * 
 * @Title :UserLoginInfo.java
 * @Prject :YingWangTechnology
 * @Package com.bm.yingwang.bean
 * @Description : 用户登陆实体
 * @author : zhangbin
 * @date 2015-2-2
 * @version : 1.0
 */
public class UserBean implements Serializable {

	private static final long serialVersionUID = 5557590223532891584L;

	public String id;// 用户ID
	public String nickName; // 用户昵称
	public String name;// 用用户姓名
	public String icon;// 用户头像
	public String height;// 用户身高
	public String sex;// 用户性别
	public String weight;// 用户体重
	public String memberType;//1为设计师 0为普通用户

	@Override
	public String toString() {
		return "UserLoginInfo [id=" + id + ", nickName=" + nickName + ", name="
				+ name + ", icon=" + icon + ", height=" + height + ", sex="
				+ sex + ", weight=" + weight + "]";
	}
}
