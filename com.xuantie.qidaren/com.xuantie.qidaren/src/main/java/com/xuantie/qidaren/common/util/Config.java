/**
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/30
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuantie.qidaren.common.configmanage.BizConfig;

/**
 * 
 * 配置信息工具
 * @author xuhh
 * @date 2016年10月23日 上午2:23:03 
 *
 */
@Component
public class Config {

	/**
	 * Redis配置信息
	 */
	//public static RedisConfig redis;

	/**
	 * 业务配置信息
	 */
	public static BizConfig biz;

	/**  
	 * 设置 Redis配置信息  
	 * @param redis Redis配置信息  
	 */
	//@Autowired(required = true)
	//public void setRedis(RedisConfig redis) {
	//	Config.redis = redis;
	//}

	/**  
	 * 设置 业务配置信息  
	 * @param biz 业务配置信息  
	 */
	@Autowired(required = true)
	public void setBiz(BizConfig biz) {
		Config.biz = biz;
	}

}
