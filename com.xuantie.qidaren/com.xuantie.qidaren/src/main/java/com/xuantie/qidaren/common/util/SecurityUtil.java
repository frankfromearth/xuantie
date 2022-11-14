/**
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/30
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.util;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * 安全相关工具类
 * @author xuhh
 * @date 2016年12月18日 下午9:23:24 
 *
 */
public class SecurityUtil {

	/**
	 * 判断是否是IP黑名单
	 * @param checkIp 待检查IP
	 * @param interfaceName 接口名称
	 * @return
	 */
	public static boolean isBlackIP(String checkIp, String interfaceName) {
		// 待检查ip为空
		if (StringUtils.isBlank(checkIp)) {
			return false;
		}
		// 待检查ip存在于白名单列表中
		if (Config.biz.getWhiteIpList().contains(checkIp)) {
			return false;
		}
		// 从redis中查询ip黑名单记录
//		String blackFlag = Jedis.client().get(ipBlackKey(checkIp, interfaceName));
//		if (StringUtils.isNotBlank(blackFlag) && "y".equalsIgnoreCase(blackFlag)) {
//			return true;
//		}
		return false;
	}

	

	/**
	 * 从uri中获取接口名称
	 * @author pansq
	 * @date 2016年12月18日 下午11:15:27 
	 * @param uri
	 * @return
	 */
	public static String getInterfaceName(String uri) {
		if (StringUtils.isBlank(uri)) {
			return "";
		}
		String[] uriArr = uri.split("\\?");
		if (uriArr != null && uriArr.length > 0) {
			return uriArr[0].replace("/", "");
		}
		return "";
	}

}
