/**
 * @copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/26
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.exception;
/**
 * 业务异常
 * @author xuhh
 * @date 2018/4/26
 *
 */
public class BizException extends RuntimeException {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	private String errorCode = "";

	/**
	 * 错误信息
	 */
	private String errorMsg = "";

	/**
	 * 错误描述
	 */
	private String errorDesc = "";

	/**
	 * 构造器
	 */
	public BizException() {
		super();
	}

	/**
	 * 业务异常
	 * @param msg 异常信息
	 */
	public BizException(String msg) {
		super(msg);
	}

	/**
	 * 业务异常
	 * @param code 异常码
	 * @param msg  异常信息
	 */
	public BizException(String code, String msg) {
		super(msg);
		this.errorCode = code;
		this.errorMsg = msg;
	}

	/**
	 * 业务异常
	 * @param code 异常码
	 * @param msg  异常信息(客户端用户可见)
	 * @param desc 异常详细信息(后端日志可见)
	 */
	public BizException(String code, String msg, String desc) {
		super(desc);
		this.errorCode = code;
		this.errorMsg = msg;
		this.errorDesc = desc;
	}

	/**
	 * 构造器
	 * 
	 * @param cause 异常对象
	 */
	public BizException(Throwable cause) {
		super(cause);
	}

	/**  
	 * 获取 错误码  
	 * @return errorCode 错误码  
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**  
	 * 设置 错误码  
	 * @param errorCode 错误码  
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**  
	 * 获取 错误信息  
	 * @return errorMsg 错误信息  
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**  
	 * 设置 错误信息  
	 * @param errorMsg 错误信息  
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**  
	 * 获取 错误描述  
	 * @return errorDesc 错误描述  
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**  
	 * 设置 错误描述  
	 * @param errorDesc 错误描述  
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

}
