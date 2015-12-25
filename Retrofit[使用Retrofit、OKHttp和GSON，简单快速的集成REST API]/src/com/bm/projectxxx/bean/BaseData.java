package com.bm.projectxxx.bean;

import java.io.Serializable;

public class BaseData<T> implements Serializable {

    /**
     * 父类数据结构 map里面无限添加对象
     */
    private static final long serialVersionUID = 1L;
    
    /* 标志位 success成功 fail失败 */
    public String status;
    
    /* 总数据结构 */
    public MapData<T> data;
    
    /* 返回的信息 */
    public String msg;
    
}
