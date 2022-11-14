/**
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/30
 * @version  : v1.0
 */
package com.xuantie.qidaren.task;

import com.xuantie.qidaren.common.util.FastJson;
import com.xuantie.qidaren.common.util.Logger;
import com.xuantie.qidaren.model.base.AccessLogEntity;


/**
 * 
 * 记录接口访问日志
 * @author xuhh
 * @date 2018/4/30
 *
 */
public class KeepAccessLog implements Runnable {
	/**
	 * 接口名称
	 */
	private String interfaceName;
	/**
	 * 调用渠道
	 */
	private String channelNo;
	/**
	 * 渠道名称
	 */
	private String channelName;
	/**
	 * 调用结果
	 */
	private boolean result;
	/**
	 * 返回码
	 */
	private String resultCode;
	/**
	 * 返回提示
	 */
	private String resultMsg;
	/**
	 * 结果描述
	 */
	private String resultDesc;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 来源IP
	 */
	private String callerIP;
	/**
	 * 来源域名
	 */
	private String callerOrigin;
	/**
	 * 调用时间
	 */
	private String callTime;
	/**
	 * 输入参数
	 */
	private Object inputParams;

	@Override
	public void run() {

		//Logger.info("------[异步任务:]-记录接口访问日志-begin------");

		try {
			// 访问日志
			AccessLogEntity log = new AccessLogEntity();
			log.setInterfaceName(interfaceName);
			log.setChannelNo(channelNo);
			log.setChannelName(channelName);
			log.setResult(result);
			log.setResultCode(resultCode);
			log.setResultMsg(resultMsg);
			log.setResultDesc(resultDesc);
			log.setUserId(userId);
			log.setCallerIP(callerIP);
			log.setCallerOrigin(callerOrigin);
			log.setCallTime(callTime);
			log.setInputParams(FastJson.obj2json(inputParams));
			Logger.access(FastJson.obj2json(log));
		} catch (Exception e) {
			Logger.error("[异步任务]-记录接口访问日志异常：", e);
		}

		//Logger.info("------[异步任务:]-记录接口访问日志-end------");
	}

	//-------------------------get set--------------------------

	/**  
	 * 接口名称  
	 */
	public String getInterfaceName() {
		return interfaceName;
	}

	/**  
	 * 接口名称    
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	/**  
	 * 调用渠道  
	 */
	public String getChannelNo() {
		return channelNo;
	}

	/**  
	 * 调用渠道    
	 */
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	/**  
	 * 渠道名称  
	 */
	public String getChannelName() {
		return channelName;
	}

	/**  
	 * 渠道名称    
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**  
	 * 调用结果  
	 */
	public boolean isResult() {
		return result;
	}

	/**  
	 * 调用结果    
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**  
	 * 返回码  
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**  
	 * 返回码    
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**  
	 * 返回提示  
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**  
	 * 返回提示    
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	/**  
	 * 结果描述  
	 */
	public String getResultDesc() {
		return resultDesc;
	}

	/**  
	 * 结果描述    
	 */
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	/**  
	 * 用户id  
	 */
	public String getUserId() {
		return userId;
	}

	/**  
	 * 用户id    
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**  
	 * 来源IP  
	 */
	public String getCallerIP() {
		return callerIP;
	}

	/**  
	 * 来源IP    
	 */
	public void setCallerIP(String callerIP) {
		this.callerIP = callerIP;
	}

	/**  
	 * 来源域名  
	 */
	public String getCallerOrigin() {
		return callerOrigin;
	}

	/**  
	 * 来源域名    
	 */
	public void setCallerOrigin(String callerOrigin) {
		this.callerOrigin = callerOrigin;
	}

	/**  
	 * 调用时间  
	 */
	public String getCallTime() {
		return callTime;
	}

	/**  
	 * 调用时间    
	 */
	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	/**  
	 * 输入参数  
	 */
	public Object getInputParams() {
		return inputParams;
	}

	/**  
	 * 输入参数    
	 */
	public void setInputParams(Object inputParams) {
		this.inputParams = inputParams;
	}

}
