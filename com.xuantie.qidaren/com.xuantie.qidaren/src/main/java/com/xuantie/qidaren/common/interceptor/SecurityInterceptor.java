/**
 * @company   : cn.com.spider
 * @poroject   : 报刊-个人中心接口
 * @copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author   : pansq
 * @date     : 2016年12月16日 上午9:18:49
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xuantie.qidaren.common.annotation.SecurityCheck;
import com.xuantie.qidaren.common.entity.SecurityCheckType;
import com.xuantie.qidaren.common.util.Config;
import com.xuantie.qidaren.common.util.Logger;
import com.xuantie.qidaren.common.util.SecurityUtil;
import com.xuantie.qidaren.common.util.StringUtil;
import com.xuantie.qidaren.common.exception.BizException;
import com.xuantie.qidaren.model.base.ResultConstant;

/**
 * 
 * 安全防护拦截器
 * @author pansq
 * @date 2016年12月16日 上午9:18:54 
 *
 */
public class SecurityInterceptor extends BaseInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Logger.info("------安全防护pre拦截器(preHandle)-begin------" + request.getRequestURI());
		long beginTime = System.currentTimeMillis();
		try {
			if (handler instanceof HandlerMethod) {
				// 获取安全检查注解
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				SecurityCheck scheckAnnotation = handlerMethod.getMethodAnnotation(SecurityCheck.class);
				// 判断是否配置安全检查注解并开启检查
				if (scheckAnnotation != null && scheckAnnotation.open()) {
					// 注解配置的安全检查类型数组
					SecurityCheckType[] checkTypes = scheckAnnotation.checkTypes();
					if (checkTypes != null && checkTypes.length > 0) {
						// 遍历检查类型
						for (SecurityCheckType ctype : checkTypes) {
							// IP检查
							if (SecurityCheckType.IP.equals(ctype)) {
								checkIP(request);
							}
							// 用户ID检查
							else if (SecurityCheckType.USERID.equals(ctype)) {
								// TODO: checkUserId();
							}
						}
					}
				}
			}
			
			return true;
		} catch (BizException e) {
			writeErrorJson(e.getErrorCode(), e.getErrorMsg(), e.getErrorDesc());
			return false;
		} catch (Exception e) {
			writeErrorJson(ResultConstant.EXCEPTION, ResultConstant.EXCEPTION_MSG, String.format(ResultConstant.EXCEPTION_DESC, "安全防护拦截器"),e);
			return false;
		} finally {
			long endTime = System.currentTimeMillis();
			Logger.info("------安全防护pre拦截器(preHandle)-end-" + (endTime - beginTime) + "(msec)------");
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		Logger.info("------安全防护post拦截器(postHandle)-begin------" + request.getRequestURI());
		long beginTime = System.currentTimeMillis();
		try {
			if (handler instanceof HandlerMethod) {
				// 获取安全检查注解
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				SecurityCheck scheckAnnotation = handlerMethod.getMethodAnnotation(SecurityCheck.class);

				// 判断是否配置安全检查注解并开启检查
				if (scheckAnnotation != null && scheckAnnotation.open()) {

					// 注解配置的安全检查类型数组
					SecurityCheckType[] checkTypes = scheckAnnotation.checkTypes();
					if (checkTypes != null && checkTypes.length > 0) {
						// 循环处理检查类型
						for (SecurityCheckType ctype : checkTypes) {
							// 获取调用结果
							boolean callResult = (boolean) getAttribute("callresult");
							// 获取检查规则
							String checkRule = StringUtils.isBlank(scheckAnnotation.rule()) ? Config.biz.getAccessLimitRule()
									: scheckAnnotation.rule();
							// IP检查
							if (SecurityCheckType.IP.equals(ctype)) {
								// 调用失败时调用限制记录+1
								if (scheckAnnotation.justLimitFailure()) {
									if (!callResult) {
										recordIP(request, checkRule);
									}
								}
								// 无论调用结果调用限制记录+1
								else {
									recordIP(request, checkRule);
								}
							}
							// 用户ID检查
							else if (SecurityCheckType.USERID.equals(ctype)) {
								// TODO: recordUserId();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			Logger.error("安全防护拦截器postHandle异常：", e);
		} finally {
			long endTime = System.currentTimeMillis();
			Logger.info("------安全防护post拦截器(postHandle)-end-" + (endTime - beginTime) + "(msec)------");
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub
	}

	//------------------------- biz -------------------------

	/**
	 * 记录IP
	 * @author pansq
	 * @date 2016年12月19日 上午11:08:43 
	 * @param request
	 * @param checkRule 检查规则
	 */
	private void recordIP(HttpServletRequest request, String checkRule) {

		// 是否是客户端调用(包括js和app)
		String callerType = (String) getAttributeWithDefault("callerType", "");
		boolean isClientQry = "js".equals(callerType) || "app".equals(callerType);

		// caller信息
		String callerIp = isClientQry ? StringUtil.getClientIP(request) : (String) getAttributeWithDefault("sourceIP", ""); // 调用IP,客户端js调用时从request中获取; 服务端调用时,从请求参数中获取。
		String interfaceName = SecurityUtil.getInterfaceName(request.getRequestURI()); // 调用接口标识

		// 判断是否account转发,如果由account转发,则需要从url中获取sourceip
		String isforward = request.getParameter("isforward");
		if ("y".equals(isforward)) {
			callerIp = request.getParameter("sourceip");
		}

		// 判断IP是否为空
		if (StringUtils.isBlank(callerIp)) {
			return;
		}
		// 判断IP是否为网关地址
		if (Config.biz.getGatewayIp().equals(callerIp)) {
			return;
		}
		// 判断IP是否配置了白名单
		if (Config.biz.getWhiteIpList().contains(callerIp)) {
			return;
		}
		// 检查规则验证 
		if (StringUtils.isBlank(checkRule)) {
			return;
		}

		// 循环检查规则并处理, 如：60$5|300$20|600$35
//		String[] rulesArray = checkRule.split("\\|");
//		if (rulesArray != null && rulesArray.length > 0) {
//			for (String item : rulesArray) {
//				if (StringUtils.isNotBlank(item)) {
//					String[] rule = item.split("\\$");
//					if (rule != null && rule.length == 2) {
//						// redis保存的key, 形如:SAS:secure:ip:interfacename:60:127.0.0.1
//						String key = SecurityUtil.ipRecordKey(callerIp, interfaceName, rule[0]);
//						int limitTime = Integer.parseInt(rule[0]); // 访问时间片
//						int limitCount = Integer.parseInt(rule[1]); // 访问频率上限
//
//						// 更新redis记录,使用key-value数据结构保存
//						long num = Jedis.client().incr(key);
//						// 如果是1,设置过期时间
//						if (num == 1) {
//							Jedis.client().expire(key, limitTime);
//						}
//
//						// 保存接口标识,使用set数据接口保存, 形如：SAS:secure:interfacenames
//						Jedis.client().sadd(SecurityUtil.interfaceNamesKey(), interfaceName);
//
//						// 如果访问频率超过限制,IP加入黑名单, 形如：SAS:secure:black:ip:127.0.0.1:interfacename
//						if (num >= limitCount) {
//							Jedis.client().setex(SecurityUtil.ipBlackKey(callerIp, interfaceName), Config.biz.getBlackIpExpiretime(), "y");
//						}
//					}
//				}
//			}
//		}

	}

	/**
	 * 检查IP
	 * @author pansq
	 * @date 2016年12月19日 上午12:06:51 
	 * @param request
	 * @return
	 */
	private void checkIP(HttpServletRequest request) throws BizException {

		// 是否是客户端调用(包括js和app)
		String callerType = (String) getAttributeWithDefault("callerType", "");
		boolean isClientQry = "js".equals(callerType) || "app".equals(callerType);

		// caller信息
		String callerIp = isClientQry ? StringUtil.getClientIP(request) : (String) getAttributeWithDefault("sourceIP", ""); // 调用IP,客户端js调用时从request中获取; 服务端调用时,从请求参数中获取。
		String interfaceName = SecurityUtil.getInterfaceName(request.getRequestURI()); // 调用接口标识

		// 判断是否account转发,如果由account转发,则需要从url中获取sourceip
		String isforward = request.getParameter("isforward");
		if ("y".equals(isforward)) {
			callerIp = request.getParameter("sourceip");
		}

		// 判断IP是否黑名单, 如果是黑名单则拦截
		boolean isBlack = false;
		if (StringUtils.isNotBlank(interfaceName)) {
			isBlack = SecurityUtil.isBlackIP(callerIp, interfaceName);
		}
		// IP为黑名单,拦截请求
		if (isBlack) {
			throw new BizException(ResultConstant.ER_SECURECHECK_NOPASS, ResultConstant.ER_SECURECHECK_NOPASS_MSG,
					String.format(ResultConstant.ER_SECURECHECK_NOPASS_DESC, callerIp, request.getRequestURI()));
		}
	}
}
