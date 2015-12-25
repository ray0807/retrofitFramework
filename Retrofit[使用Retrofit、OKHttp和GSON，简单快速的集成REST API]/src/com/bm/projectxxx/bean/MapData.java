package com.bm.projectxxx.bean;

import java.io.Serializable;
import java.util.List;

public class MapData<T> implements Serializable {

	/**
	 * MapData封装所有数据类型
	 */
	private static final long serialVersionUID = 2L;
	
	/* 数据List */
	public List<T> list;
	
	public UserBean member;

}
