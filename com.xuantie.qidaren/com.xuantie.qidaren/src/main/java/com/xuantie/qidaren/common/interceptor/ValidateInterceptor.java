/**
 * @company   : spider.com.cn
 * @poroject   : spider
 * @copyright : copyright (c) 2017
 * 
 * @description: TODO
 * @author   : pansq
 * @date     : 2017年2月17日 下午4:48:39
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.TypeReference;
//import com.github.pagehelper.util.StringUtil;
import com.xuantie.qidaren.common.exception.BizException;
import com.xuantie.qidaren.common.util.FastJson;
import com.xuantie.qidaren.common.util.Json;
import com.xuantie.qidaren.common.util.Logger;
import com.xuantie.qidaren.model.base.RequestBase;
import com.xuantie.qidaren.model.base.RequestParameter;
import com.xuantie.qidaren.model.base.ResultConstant;

/**
 * 接口访问参数拦截器
 * @author xuhh
 * @date 2018/4/26
 */
public class ValidateInterceptor extends BaseInterceptor {

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
			// 空判断
			if (reqparam == null||reqparam.getReqbase()==null||StringUtils.isBlank(reqparam.getReqbase().getToken())) {
				throw new BizException(ResultConstant.ER_PRAM_TOKEN_ILLEGAL,ResultConstant.ER_PRAM_TOKEN_ILLEGAL_MSG,
						String.format(ResultConstant.ER_PRAM_TOKEN_ILLEGAL_DESC, "{reqbase,token}"));
			}
			String userid = reqparam.getReqbase().getUserid(); //获取用户id
			//判断用户id为空
			if(StringUtils.isBlank(userid)){
				throw new BizException(ResultConstant.ER_PRAM_TOKEN_ILLEGAL,ResultConstant.ER_PRAM_TOKEN_ILLEGAL_MSG,
						String.format(ResultConstant.ER_PRAM_TOKEN_ILLEGAL_DESC, "{reqbase,token}"));
			}
			// 设置当前用户，需要再验证拦截器中设置
			request.getSession().setAttribute("userId", userid);
			
		} catch(BizException e){
			forwardErrorJson(request, e.getErrorCode(), e.getErrorMsg(), e.getErrorDesc());
			return false;
		} catch (Exception e) {
			Logger.error("接口访问参数拦截器-前置拦截异常：", e);
			forwardErrorJson(request, ResultConstant.EXCEPTION, ResultConstant.EXCEPTION_MSG, ResultConstant.EXCEPTION_DESC);
			return false;
		} finally {
			long endTime = System.currentTimeMillis();
			Logger.info("------接口访问参数拦截器end-" + (endTime - beginTime) + "(msec)------" + request.getRequestURI());
		}
		return true;
	}

	// 返回前拦截
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 重定向到错误控制器
	 * @author pansq
	 * @date 2017年2月24日 下午6:25:11 
	 * @param request
	 * @param errorcode
	 * @param errormsg
	 */
	public void forwardErrorJson(HttpServletRequest request, String respCode, String respMsg, String respDesc) {
		// 设置错误信息
		request.getSession().setAttribute("respStatus", false);
		request.getSession().setAttribute("respCode", respCode);
		request.getSession().setAttribute("respMsg", respMsg);
		request.getSession().setAttribute("respDesc", respDesc);
		// 重定向错误控制器
		try {
			request.getRequestDispatcher("/common/errorjson").forward(getRequest(), getResponse());
		} catch (ServletException e) {
			Logger.error("重定向到错误控制器异常：", e);
		} catch (IOException e) {
			Logger.error("重定向到错误控制器异常：", e);
		}
	}

}