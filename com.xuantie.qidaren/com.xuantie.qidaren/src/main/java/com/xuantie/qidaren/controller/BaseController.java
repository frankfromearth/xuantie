/**
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/26
 * @version  : v1.0
 */
package com.xuantie.qidaren.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuantie.qidaren.common.exception.BizException;
import com.xuantie.qidaren.common.util.Logger;
import com.xuantie.qidaren.model.base.BaseReturn;
import com.xuantie.qidaren.model.base.ResponseParameter;
import com.xuantie.qidaren.model.base.ResultConstant;
import com.xuantie.qidaren.model.entity.UserEntity;
import com.xuantie.qidaren.service.IUserService;


/**
 * 基础controller
 * @author xuhh
 * @date 2018/4/26
 */
public class BaseController extends BaseReturn {

	@Autowired
	protected IUserService userService;
	

	/**
	 * 设置基础返回参数
	 * @param status 请求是否成功 true false
	 * @param returnCode 返回码
	 * @param returnMsg  返回码用户提示
	 */
	protected void setRespBase(boolean status, String returnCode, String returnMsg) {
		respParamObj.get().getRespbase().clean();
		respParamObj.get().getRespbase().setStatus(status);
		respParamObj.get().getRespbase().setReturncode(returnCode);
		respParamObj.get().getRespbase().setReturnmsg(returnMsg);

		// 结果保存到session中
		try {
			getSession().setAttribute("respStatus", status);
			getSession().setAttribute("respCode", returnCode);
			getSession().setAttribute("respMsg", returnMsg);
		} catch (Exception e) {
			getSession().setAttribute("respStatus", true);
		}
	}

	/**
	 * 设置分页返回参数
	 * @param total 总数据量
	 * @param page 当前页索引
	 * @param size 每页显示条数
	 */
	protected void setRespPage(long total, int page, int size) {
		respParamObj.get().getResppage().clean();
		respParamObj.get().getResppage().setTotal(total);
		respParamObj.get().getResppage().setPage(page);
		respParamObj.get().getResppage().setSize(size);
	}

	/**
	 * 设置业务返回参数
	 * @param obj
	 */
	protected void setRespBiz(Object obj) {
		respParamObj.get().setRespparam(null);
		respParamObj.get().setRespparam(obj);
	}
    

	/**
	 * 获取响应参数对象
	 * @return
	 */
	protected ResponseParameter getResponseObj() {
		// 保存对象引用到session中,用以在拦截器中重置返回对象的值
		getSession().setAttribute("respParamObj", respParamObj.get());
		ResponseParameter result = respParamObj.get();
		respParamObj.remove();
		return result;
	}

	/**
	 * 返回Json形式的错误响应
	 * @param errorCode 错误码
	 * @param errorMsg  错误用户提示
	 * @param errorDesc 错误详细描述
	 * @throws BizException 
	 */
	public ResponseParameter ErrorJson(String errorCode, String errorMsg, String errorDesc) {
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

			return respParamObj.get();

		} catch (Exception e) {
			Logger.error("返回Json形式的错误响应异常：", e);
		}
		return null;
	}
	
	

	/**
	 * 返回Json形式的错误响应
	 * @param errorCode 错误码
	 * @param errorMsg  错误用户提示
	 * @param errorDesc 错误详细描述
	 * @param e 异常对象
	 */
	public ResponseParameter ErrorJson(String errorCode, String errorMsg, String errorDesc, Exception e) {
		try {
			Logger.error(StringUtils.isBlank(errorDesc) ? errorMsg : errorDesc, e);

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

			return respParamObj.get();

		} catch (Exception e2) {
			Logger.error("返回Json形式的错误响应异常：", e);
		}
		return null;
	}
	

	/**
	 * 清理业务返回参数map
	 */
	protected void respBizParamClear() {
		respParamObj.get().clean();
	}
}
