/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title ResourceTypes.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.bean
 * @Description TODO
 * @author zhaocl  
 * @date 2015年7月21日 下午3:44:07
 * @version V1.0  
 */
package com.bm.projectxxx.bean;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**  
 * Copyright © 2015 蓝色互动. All rights reserved.
 *
 * @Title ResourceTypes.java
 * @Prject Retrofit[使用Retrofit、OKHttp和GSON，简单快速的集成REST API]
 * @Package com.bm.projectxxx.bean
 * @Description 自定义资源类型注解
 * @author zhaocl  
 * @date 2015年7月21日 下午3:44:07
 * @version V1.0  
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface LayoutResourceID {
	
}
