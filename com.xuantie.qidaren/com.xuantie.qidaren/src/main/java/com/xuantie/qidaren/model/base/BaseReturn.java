/**
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/30
 * @version  : v1.0
 */
package com.xuantie.qidaren.model.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xuantie.qidaren.common.util.Json;
import com.xuantie.qidaren.common.util.Logger;



/**
 * 基础返回
 * @author xuhh
 * @date 2018/4/30
 */
public class BaseReturn {

	/**
	 * 响应参数对象
	 */
	protected ThreadLocal<ResponseParameter> respParamObj = new ThreadLocal<ResponseParameter>() {
		public ResponseParameter initialValue() {
			return new ResponseParameter();
		}
	};

	/**
	 * 获取request对象
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取response对象
	 * @return
	 */
	protected HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	/**
	 * 获取session对象
	 * @return
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 获取attribute对象
	 * @param name 键
	 * @return
	 */
	protected Object getAttribute(String name) {
		return getSession().getAttribute(name);
	}

	/**
	 * 获取用户ID
	 * @return
	 */
	protected String getUserId() {
		return (String) getAttribute("userId");
	}

	/**
	 * 获取attribute对象
	 * @param name 键
	 * @return
	 */
	protected Object getAttributeWithDefault(String name, Object def) {
		Object obj = getSession().getAttribute(name);
		return obj == null ? def : obj;
	}

	/**
	 * @description: 拦截器验证失败输出错误json
	 * @param errorCode 错误编码
	 * @param errorMsg  错误描述
	 * @param errorDesc 错误详细信息
	 */
	public void writeErrorJson(String errorCode, String errorMsg, String errorDesc) {
		try {
			Logger.error(StringUtils.isBlank(errorDesc) ? errorMsg : errorDesc);

			// 设置错误信息
			getSession().setAttribute("respStatus", false);
			getSession().setAttribute("respCode", errorCode);
			getSession().setAttribute("respMsg", errorMsg);
			getSession().setAttribute("respDesc", errorDesc);

			// 设置基础返回参数
			respParamObj.get().getRespbase().clean();
			respParamObj.get().getRespbase().setStatus(false);
			respParamObj.get().getRespbase().setReturncode(errorCode);
			respParamObj.get().getRespbase().setReturnmsg(errorMsg);

			// 重定向错误控制器
			Json.writeJson(getRequest(), getResponse(), respParamObj.get());

		} catch (Exception e) {
			Logger.error("拦截器验证失败输出错误json异常：", e);
		}
	}

	/**
	 * @description: 拦截器验证失败输出错误json
	 * @param errorCode 错误编码
	 * @param errorMsg  错误描述
	 * @param errorDesc 错误详细信息
	 */
	public void writeErrorJson(String errorCode, String errorMsg, String errorDesc, Exception ex) {
		try {
			Logger.error(StringUtils.isBlank(errorDesc) ? errorMsg : errorDesc, ex);

			// 设置错误信息
			getSession().setAttribute("respStatus", false);
			getSession().setAttribute("respCode", errorCode);
			getSession().setAttribute("respMsg", errorMsg);
			getSession().setAttribute("respDesc", errorDesc);

			// 设置基础返回参数
			respParamObj.get().getRespbase().clean();
			respParamObj.get().getRespbase().setStatus(false);
			respParamObj.get().getRespbase().setReturncode(errorCode);
			respParamObj.get().getRespbase().setReturnmsg(errorMsg);

			// 重定向错误控制器
			Json.writeJson(getRequest(), getResponse(), respParamObj.get());

		} catch (Exception e) {
			Logger.error("拦截器验证失败输出错误json异常：", e);
		}
	}
}
