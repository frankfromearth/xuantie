/**
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/30
 * @version  : v1.0
 */
package com.xuantie.qidaren.model.base;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 接口访问日志实体
 * @author pansq
 * @date 2016年12月14日 下午5:15:01 
 *
 */
public class AccessLogEntity {

	/**
	 * 接口名称
	 */
	@JSONField(ordinal = 1)
	private String interfaceName;

	/**
	 * 调用渠道
	 */
	@JSONField(ordinal = 2)
	private String channelNo;

	/**
	 * 渠道名称
	 */
	@JSONField(ordinal = 3)
	private String channelName;

	/**
	 * 调用结果
	 */
	@JSONField(ordinal = 4)
	private boolean result;

	/**
	 * 返回码
	 */
	@JSONField(ordinal = 5)
	private String resultCode;

	/**
	 * 返回提示
	 */
	@JSONField(ordinal = 6)
	private String resultMsg;

	/**
	 * 结果描述
	 */
	@JSONField(ordinal = 7)
	private String resultDesc;

	/**
	 * 用户id
	 */
	@JSONField(ordinal = 8)
	private String userId;

	/**
	 * 来源IP
	 */
	@JSONField(ordinal = 9)
	private String callerIP;

	/**
	 * 来源域名
	 */
	@JSONField(ordinal = 10)
	private String callerOrigin;

	/**
	 * 调用时间
	 */
	@JSONField(ordinal = 11)
	private String callTime;

	/**
	 * 输入参数
	 */
	@JSONField(ordinal = 12)
	private String inputParams;

	/**  
	 * 接口名称  
	 */
	@JSONField(ordinal = 13)
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
	public String getInputParams() {
		return inputParams;
	}

	/**  
	 * 输入参数    
	 */
	public void setInputParams(String inputParams) {
		this.inputParams = inputParams;
	}

}
