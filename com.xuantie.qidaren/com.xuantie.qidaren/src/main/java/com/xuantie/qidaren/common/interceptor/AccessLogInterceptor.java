/**
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/26
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;



import com.alibaba.fastjson.TypeReference;
import com.xuantie.qidaren.common.util.DateUtil;
import com.xuantie.qidaren.common.util.FastJson;
import com.xuantie.qidaren.common.util.Json;
import com.xuantie.qidaren.common.util.Logger;
import com.xuantie.qidaren.common.util.StringUtil;
import com.xuantie.qidaren.model.base.RequestBase;
import com.xuantie.qidaren.model.base.RequestParameter;
import com.xuantie.qidaren.model.base.ResponseParameter;
import com.xuantie.qidaren.task.KeepAccessLog;


/**
 * 接口访问日志记录拦截器
 * @author xuhh
 * @date 2018/4/26
 */
public class AccessLogInterceptor extends BaseInterceptor {

	// 前置拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Logger.info("------访问日志拦截器begin------" + request.getRequestURI());
		long beginTime = System.currentTimeMillis();
		try {
			// 判断是不是文件上传
			boolean isFileUpload = false;
			if (request instanceof MultipartHttpServletRequest) {
				isFileUpload = true;
			}

			// 参数映射成对象
			RequestParameter<Object> reqparam = new RequestParameter<Object>();

			// 非文件上传接口
			if (!isFileUpload) {
				String json = Json.getPayloadJson(request);
				reqparam = FastJson.json2obj(json, new TypeReference<RequestParameter<Object>>() {
				}.getType());
			}
			// 文件上传接口
			else {
				RequestBase reqbase = new RequestBase();
				reqbase.setToken(request.getParameter("token")); // 用户登录令牌
				reqparam.setReqbase(reqbase);
			}

			// 参数对应保存到session中,避免重复处理参数对象
			request.getSession().setAttribute("RequestParamObj", reqparam);

			//----------------------------------------------
			// 相关数据保存到session中,用于记录接口访问日志
			//----------------------------------------------
			request.getSession().setAttribute("interfaceName", request.getRequestURI()); // 接口名称
//			request.getSession().setAttribute("channelNo", reqparam.getReqbase().getReqorigin()); // 调用渠道
			// 来源IP
			String ip = StringUtil.getClientIP(request);
			request.getSession().setAttribute("callerIP", ip);
			request.getSession().setAttribute("sourceIP", request.getParameter("sourceip")); // 请求来自后台转发时,从请求url中读取真实来源ip
			// --如果url中和post参数中都包含sourceIP,使用post参数中的sourceIP
			/*if (StringUtils.isNotBlank(reqparam.getReqbase().getSourceip())) {
				request.getSession().setAttribute("sourceIP", reqparam.getReqbase().getSourceip());
			}*/
			// 来源域名
			String headorigin = request.getHeader("origin");
			String origin = StringUtils.isBlank(headorigin) ? request.getParameter("origin") : headorigin;
			request.getSession().setAttribute("callerOrigin", origin);
			request.getSession().setAttribute("callTime", DateUtil.getNow()); // 调用时间
			request.getSession().setAttribute("inputParams", reqparam); // 输入参数
		} catch (Exception e) {
			Logger.error("接口访问日志记录拦截器-前置拦截异常：", e);
		} finally {
			long endTime = System.currentTimeMillis();
			Logger.info("------访问日志拦截器end-" + (endTime - beginTime) + "(msec)------" + request.getRequestURI());
		}
		return true;
	}

	// 返回前拦截
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		// TODO Auto-generated method stub
	}

	// 返回后拦截
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

		// OPTIONS类型的请求跳过拦截器
		if (!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
			// 重置ThreadLocal返回对象的值
			ResponseParameter resObj = (ResponseParameter) request.getSession().getAttribute("respParamObj");
			if (resObj != null) {
				resObj.setRespparam(null);
				if (resObj.getRespbase() != null) {
					resObj.getRespbase().clean();

				}
			}

			// 添加访问日志记录任务
			keepAccessLog(request);
		}
	}

	/**
	 * 记录接口调用日志(异步)
	 * @author xuhh
	 * @date 2018/4/30
	 */
	private void keepAccessLog(HttpServletRequest request) {
		try {
			KeepAccessLog task = new KeepAccessLog();
			// 接口名称
			task.setInterfaceName((String) getAttributeWithDefault(request, "interfaceName", "")); //接口名称
			// 调用渠道
			String channelNo = (String) getAttributeWithDefault(request, "channelNo", "");
			task.setChannelNo(channelNo);
			// 渠道名称
//			task.setChannelName(configService.getConfigValue(BizConfigConstant.ORIGIN_PREFIX + channelNo));
			// 用户id
			task.setUserId((String) getAttributeWithDefault(request, "userId", ""));
			// 调用结果
			task.setResult((boolean) getAttributeWithDefault(request, "respStatus", true));
			// 返回码
			task.setResultCode((String) getAttributeWithDefault(request, "respCode", ""));
			// 返回提示,用户获得的接口结果描述
			task.setResultMsg((String) getAttributeWithDefault(request, "respMsg", ""));
			// 结果描述,接口错误时详细的结果描述
			task.setResultDesc((String) getAttributeWithDefault(request, "respDesc", ""));
			// 来源IP
			task.setCallerIP((String) getAttributeWithDefault(request, "callerIP", ""));
			// 来源域名
			task.setCallerOrigin((String) getAttributeWithDefault(request, "callerOrigin", ""));
			// 调用时间
			task.setCallTime((String) getAttributeWithDefault(request, "callTime", ""));
			// 输入参数
			task.setInputParams(getAttributeWithDefault(request, "inputParams", ""));
			// 异步任务提交线程池
			threadPool.submit(task);

		} catch (Exception e) {
			Logger.error("记录接口调用日志异常：", e);
		}
	}

	/**
	 * 获取attribute对象
	 * @author xuhh
	 * @date 2018/4/30 
	 * @param name 键
	 * @return
	 */
	private Object getAttributeWithDefault(HttpServletRequest request, String name, Object def) {
		Object obj = request.getSession().getAttribute(name);
		return obj == null ? def : obj;
	}
}
