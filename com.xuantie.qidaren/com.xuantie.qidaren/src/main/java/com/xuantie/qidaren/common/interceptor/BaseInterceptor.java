/** 
 * @description: 基础拦截器
 * @author   : xuhh
 * @date     : 2018/4/26
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.interceptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.servlet.HandlerInterceptor;

import com.xuantie.qidaren.model.base.BaseReturn;


/**
 * 
 * 基础拦截器
 * @author xuhh
 * @date 2018/4/26
 *
 */
public abstract class BaseInterceptor extends BaseReturn implements HandlerInterceptor {

	/**
	 * 线程池
	 */
	protected static ExecutorService threadPool = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
}
