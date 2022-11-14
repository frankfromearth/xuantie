/**
 * @company   : cn.com.spider
 * @poroject   : 报刊-个人中心接口
 * @copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author   : pansq
 * @date     : 2016年12月16日 上午9:57:49
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xuantie.qidaren.common.entity.SecurityCheckType;

/**
 * 
 * 安全检查
 * @author xuhh
 * @date 2018/4/30 
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecurityCheck {

	/**
	 * 是否开启检查
	 */
	boolean open() default false;

	/**
	 * 检查类型
	 */
	SecurityCheckType[] checkTypes();

	/**
	 * 仅访问失败时进行限制
	 */
	boolean justLimitFailure() default false;

	/**
	 * 访问频率限制规则, 例如:60$5|300$20, 含义:60秒内最多可访问5次;300秒内最多可访问20次
	 */
	String rule() default "";

}
