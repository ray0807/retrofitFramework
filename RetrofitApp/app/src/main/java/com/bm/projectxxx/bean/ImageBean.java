package com.bm.projectxxx.bean;

import java.io.Serializable;

/**
 * 本地图片对象
 * @author 赵成龙
 */
public class ImageBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public int id; // 图片Id
    public String data; // 图片绝对路径
    
	public ImageBean(int id, String data) {
		this.id = id;
		this.data = data;
	}
    
}
