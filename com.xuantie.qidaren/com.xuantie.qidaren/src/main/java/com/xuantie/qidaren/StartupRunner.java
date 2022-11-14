/**
 * @company   : cn.com.spider
 * @poroject   : 报刊-个人中心接口
 * @copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author   : pansq
 * @date     : 2016年10月19日 上午2:37:03
 * @version  : v1.0
 */
package com.xuantie.qidaren;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.xuantie.qidaren.common.util.Logger;


/**
 * 
 * 启动加载配置
 * @author xuhh
 * @date 2018/4/27
 *
 */
@Component
public class StartupRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {

		 Logger.info("启动加载自定义的MyWebApplicationInitializer");  
		 // 初始化redis
		 //JedisManager.init();
	}
}
