/**
 * 
 * @description: 鉴权拦截器
 * @author   : xuhh
 * @date     : 2018/4/30
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.TypeReference;

import com.xuantie.qidaren.common.util.AESUtil;
import com.xuantie.qidaren.common.util.Config;
import com.xuantie.qidaren.common.util.DateUtil;
import com.xuantie.qidaren.common.util.FastJson;
import com.xuantie.qidaren.common.util.Json;
import com.xuantie.qidaren.common.util.Logger;
import com.xuantie.qidaren.common.util.StringUtil;
import com.xuantie.qidaren.common.exception.BizException;
import com.xuantie.qidaren.model.base.ResultConstant;
import com.xuantie.qidaren.model.base.RequestParameter;

/**
 * 
 * 鉴权拦截器
 * 
 * @author xuhh
 * @date 2018/4/30
 *
 */
public class AuthenticateInterceptor  extends BaseInterceptor {

	/**
	 * 前置拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Logger.info("------鉴权拦截器begin------" + request.getRequestURI());
		long beginTime = System.currentTimeMillis();
		try {
			// 设置接口调用日志字段
			setAccessLogField(request, null, null);

			// 获取request payload
			String json = Json.getPayloadJson(request);
			if (StringUtils.isBlank(json)) {
				throw new BizException(ResultConstant.ER_HTTP_REQBODY_NULL, ResultConstant.ER_HTTP_REQBODY_NULL_MSG,
						String.format(ResultConstant.ER_HTTP_REQBODY_NULL_DESC, request.getRequestURI()));
			}

			// 参数映射成对象
			RequestParameter<Object> reqparam = FastJson.json2obj(json, new TypeReference<RequestParameter<Object>>() {
			}.getType());

			// 设置接口调用日志字段
			setAccessLogField(null, reqparam, null);

			// 1.验证鉴权标识
			String callerType = checkAuthIdentify(reqparam);
			getSession().setAttribute("callerType", callerType);

			// 2.验证域名,仅js调用时验证域名
			checkOrigin(request, Config.biz.isOpenOriginCheck(), callerType);

			// 3.验证IP,仅后台调用时验证IP
			checkIP(request, Config.biz.isOpenIPCheck(), callerType);

			// 4.验证token
			String userId = checkToken(reqparam, callerType);

			// 用户id写入request
			getSession().setAttribute("userId", userId);

			// 设置接口调用日志字段
			setAccessLogField(null, null, userId);

			return true;

		} catch (BizException e) {
			writeErrorJson(e.getErrorCode(), e.getErrorMsg(),e.getErrorDesc());
			return false;
		} catch (Exception e) {
			writeErrorJson(ResultConstant.EXCEPTION, ResultConstant.EXCEPTION_MSG, String.format(ResultConstant.EXCEPTION_DESC, "安全防护拦截器"),e);
			return false;
		} finally {
			long endTime = System.currentTimeMillis();
			Logger.info("------鉴权拦截器end-" + (endTime - beginTime) + "(msec)------");
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub
	}

	/**
	 * 设置接口访问日志字段
	 * @author xuhh
	 * @date 2018/4/30
	 * @param request
	 * @param reqparam
	 * @param userId
	 */
	private void setAccessLogField(HttpServletRequest request, RequestParameter<Object> reqparam, String userId) {
		try {
			if (request != null) {
				// 调用时间
				getSession().setAttribute("callTime", DateUtil.getNow());

				// 接口名称
				getSession().setAttribute("interfaceName", request.getRequestURI());

				// 来源IP
				String ip = StringUtil.getClientIP(request);
				getSession().setAttribute("callerIP", ip); // 调用者IP
				getSession().setAttribute("sourceIP", request.getParameter("sourceip")); // 来源IP,请求来自后台转发时,从请求url中读取真实来源ip

				// 来源域名
				String origin = StringUtils.isBlank(getRequest().getHeader("origin")) ? request.getParameter("origin")
						: getRequest().getHeader("origin");
				getSession().setAttribute("callerOrigin", origin);
			}
			if (reqparam != null) {
				// 调用渠道
				getSession().setAttribute("channelNo", reqparam.getReqbase().getReqorigin());

				// 调用者来源ip PS:如果url中和post参数中都包含sourceIP,使用post参数中的sourceIP
				if (StringUtils.isNotBlank(reqparam.getReqbase().getSourceip())) {
					getSession().setAttribute("sourceIP", reqparam.getReqbase().getSourceip());
				}

				// 输入参数
				getSession().setAttribute("inputParams", reqparam);
			}
			if (userId != null) {
				// 用户id
				getSession().setAttribute("userId", userId);
			}
		} catch (Exception e) {
			Logger.error("设置接口调用日志字段异常：", e);
		}
	}

	/**
	 * 验证token
	 * @author xuhh
	 * @date 2018/4/30
	 * @param reqparam
	 * @return
	 * @throws BizException
	 */
	private String checkToken(RequestParameter<Object> reqparam, String callerType) throws BizException {

		// 批处理调用时,无登录token,返回 "xxx_batch" 作为userid
		if ("batch".equalsIgnoreCase(callerType)) {
			return reqparam.getReqbase().getReqorigin() + "_batch";
		}
		// 验证用户登录标识(token)
		String token = reqparam.getReqbase().getToken();
		if (StringUtils.isBlank(token)) {
			throw new BizException(ResultConstant.ER_PRAM_EMPTY, ResultConstant.ER_PRAM_EMPTY_MSG,
					String.format(ResultConstant.ER_PRAM_EMPTY_DESC, "token"));
		}
		String userId = "122112";
		// 根据token获取用户id
	    //String userId = Jedis.client().get(Config.biz.getAuthTokenKeyPrefix() + token);
		if (StringUtils.isBlank(userId)) {
			throw new BizException(ResultConstant.ER_PRAM_TOKEN_ILLEGAL, ResultConstant.ER_PRAM_TOKEN_ILLEGAL_MSG,
					String.format(ResultConstant.ER_PRAM_TOKEN_ILLEGAL_DESC, "{" + token + "}") + ",找不到对应UserID");
		}
		return userId;
	}

	/**
	 * 验证IP
	 * @author pansq
	 * @date 2016年12月6日 下午2:25:17
	 * @param request
	 * @param callerType 调用类型{js, server, app}
	 * @throws BizException
	 */
	private void checkIP(HttpServletRequest request, boolean openIPCheck, String callerType) throws BizException {
		// 只有服务器端调用才验证IP, app代码调用不包含在内
		if (openIPCheck && "server".equals(callerType)) {
			String callerIP = StringUtil.getClientIP(request); // 调用客户端IP
			if (StringUtils.isBlank(Config.biz.getAllowedIP().get(callerIP))) {
				throw new BizException(ResultConstant.ER_IP_ILLEGAL, ResultConstant.ER_IP_ILLEGAL_MSG,
						String.format(ResultConstant.ER_IP_ILLEGAL_DESC, callerIP));
			}
		}
	}

	/**
	 * 验证域名
	 * @author pansq
	 * @date 2016年12月6日 下午1:17:26
	 * @param request
	 * @param callerType 调用类型{js, server, app}
	 * @throws BizException
	 */
	private void checkOrigin(HttpServletRequest request, Boolean openOriginCheck, String callerType) throws BizException {
		// 只有浏览器js调用才验证域名,app调用没有域名概念
		if (openOriginCheck && "js".equals(callerType)) {
			// 从request获取域名
			String origin = StringUtils.isBlank(getRequest().getHeader("origin")) ? request.getParameter("origin")
					: getRequest().getHeader("origin");
			if (StringUtils.isBlank(origin)) {
				throw new BizException(ResultConstant.ER_ORIGIN_ILLEGAL, ResultConstant.ER_ORIGIN_ILLEGAL_MSG,
						String.format(ResultConstant.ER_ORIGIN_ILLEGAL_DESC, origin));
			}
			// 域名弱验证
			if ("1".equals(Config.biz.getOriginLimitLevel())) {
				if (!origin.toLowerCase().contains("spider")) {
					throw new BizException(ResultConstant.ER_ORIGIN_ILLEGAL, ResultConstant.ER_ORIGIN_ILLEGAL_MSG,
							String.format(ResultConstant.ER_ORIGIN_ILLEGAL_DESC, origin));
				}
			}
			// 域名强验证
			else if ("2".equals(Config.biz.getOriginLimitLevel())) {
				String originNoProtocol = StringUtil.getOriginWithoutProtocol(origin); // 去除http协议的域名
				// 域名不在配置项中
				if (StringUtils.isBlank(Config.biz.getAllowedOrigin().get(originNoProtocol))) {
					throw new BizException(ResultConstant.ER_ORIGIN_ILLEGAL, ResultConstant.ER_ORIGIN_ILLEGAL_MSG,
							String.format(ResultConstant.ER_ORIGIN_ILLEGAL_DESC, origin));
				}
			}
		}
	}

	/**
	 * 验证鉴权标识
	 * @author pansq
	 * @date 2016年12月6日 上午11:27:09
	 * @param reqparam
	 * @return callerType {js, server, app}
	 * @throws BizException
	 */
	private String checkAuthIdentify(RequestParameter<Object> reqparam) throws BizException {

		// 获取参数
		String flag = reqparam.getReqbase().getClientauthflag(); // 加密鉴权标识
		String timestamp = reqparam.getReqbase().getTimestamp(); // 时间戳
		String reqorigin = reqparam.getReqbase().getReqorigin(); // 请求来源
        Logger.info("flag="+flag);
        Logger.info("timestamp="+timestamp);
        Logger.info("reqorigin="+reqorigin);
        
		// 鉴权参数验证
		if (StringUtils.isBlank(flag) || StringUtils.isBlank(timestamp) || timestamp.length() < 6 || StringUtils.isBlank(reqorigin)) {
			throw new BizException(ResultConstant.ER_PRAM_EMPTY, ResultConstant.ER_PRAM_EMPTY_MSG,
					String.format(ResultConstant.ER_PRAM_EMPTY_DESC, "{clientauthflag:" + flag + ",timestamp:" + timestamp + "}"));
		}

		// 未知来源
		if (StringUtils.isBlank(Config.biz.getRequestOriginFlag().get(reqorigin))) {
			throw new BizException(ResultConstant.ER_PRAM_ILLEGAL, ResultConstant.ER_PRAM_ILLEGAL_MSG,
					String.format(ResultConstant.ER_PRAM_ILLEGAL_DESC, "{reqorigin:" + reqorigin + "}"));
		}

		// 秘钥 形如: %$spider!#121212
		String encrypKey = Config.biz.getAuthEncrypKeyPrefix().concat(timestamp.substring(timestamp.length() - 6, timestamp.length()));

		// 解密鉴权参数
		String flagDeCode = "";
		try {
			flagDeCode = AESUtil.decryptAES(flag, encrypKey);
		} catch (Exception e) {
			throw new BizException(ResultConstant.ER_CRYPTO, ResultConstant.ER_CRYPTO_MSG,
					String.format(ResultConstant.ER_CRYPTO_DESC, "鉴权标识{" + flag + "}"));
		}

		// 验证鉴权参数是否为空或长度小于 spider!123
		if (StringUtils.isBlank(flagDeCode) || flagDeCode.length() < Config.biz.getAuthIdentify4client().length()) {
			throw new BizException(ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL, ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL_MSG,
					String.format(ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL_DESC, "{" + flagDeCode + "}解密后的鉴权标识长度"));
		}

		// 调用者类型
		String callerType = "js";
		if (flagDeCode.contains(Config.biz.getAuthIdentify4server())) {
			callerType = "server";
		} else if (flagDeCode.contains(Config.biz.getAuthIdentify4app())) {
			callerType = "app";
		} else if (flagDeCode.contains(Config.biz.getAuthIdentify4batch())) {
			callerType = "batch";
		}

		// 服务端后台调用时需要验证sourceip参数
		String sourceip = reqparam.getReqbase().getSourceip(); // 请求来源
		if ("server".equals(callerType) && StringUtils.isBlank(sourceip)) {
			throw new BizException(ResultConstant.ER_PRAM_EMPTY, ResultConstant.ER_PRAM_EMPTY_MSG, String.format(
					ResultConstant.ER_PRAM_EMPTY_DESC,
					"{clientauthflag:" + flag + ",timestamp:" + timestamp + ",reqorigin:" + reqorigin + ",sourceip:" + sourceip + "}"));
		}

		// 从鉴权参数截取鉴权标识,并且验证
		String identify = "";
		// js调用
		if ("js".equals(callerType)) {
			identify = flagDeCode.substring(0, Config.biz.getAuthIdentify4client().length()); // spider!123
			if (!Config.biz.getAuthIdentify4client().equalsIgnoreCase(identify)) {
				throw new BizException(ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL, ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL_MSG,
						String.format(ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL_DESC, "{" + flagDeCode + "}"));
			}
		}
		// server调用
		else if ("server".equals(callerType)) {
			identify = flagDeCode.substring(0, Config.biz.getAuthIdentify4server().length()); // spider!123!server
			if (!Config.biz.getAuthIdentify4server().equalsIgnoreCase(identify)) {
				throw new BizException(ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL, ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL_MSG,
						String.format(ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL_DESC, "{" + flagDeCode + "}"));
			}
		}
		// app调用
		else if ("app".equals(callerType)) {
			identify = flagDeCode.substring(0, Config.biz.getAuthIdentify4app().length()); // spider!123!app
			if (!Config.biz.getAuthIdentify4app().equalsIgnoreCase(identify)) {
				throw new BizException(ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL, ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL_MSG,
						String.format(ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL_DESC, "{" + flagDeCode + "}"));
			}
		}
		// batch调用
		else {
			identify = flagDeCode.substring(0, Config.biz.getAuthIdentify4batch().length()); // spider!123!batch
			if (!Config.biz.getAuthIdentify4batch().equalsIgnoreCase(identify)) {
				throw new BizException(ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL, ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL_MSG,
						String.format(ResultConstant.ER_PRAM_AUTHFLAG_ILLEGAL_DESC, "{" + flagDeCode + "}"));
			}
		}

		// 返回是否是客户端调用
		return callerType;
	}

}
