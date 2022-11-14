package com.xuantie.qidaren.model.base;

import java.io.Serializable;

/**
 * 基本请求参数
 * 
 * @description:
 * @author add by xuhh
 * @date 2018/4/26
 *
 */
public class RequestBase implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8472772427717789976L;

	/**
	 * 时间戳
	 */
	private String timestamp;

	/**
	 * 用户登录获取的令牌token
	 */
	private String token;

	/**
	 * 调用者来源ip,服务器后台调用接口时必传
	 */
	private String sourceip = "";

	/**
	 * 鉴权标识
	 */
	private String clientauthflag;

	/**
	 * 请求来源
	 */
	private String reqorigin;
	
	/**
	 * 用户id
	 */
	private String userid;

	//----------------------get set---------------------------

	/**  
	 * 时间戳   
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**  
	 * 时间戳    
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**  
	 * 用户登录获取的令牌token   
	 */
	public String getToken() {
		return token;
	}

	/**  
	 * 用户登录获取的令牌token    
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**  
	 * 调用者来源ip服务器后台调用接口时必传   
	 */
	public String getSourceip() {
		return sourceip;
	}

	/**  
	 * 调用者来源ip服务器后台调用接口时必传    
	 */
	public void setSourceip(String sourceip) {
		this.sourceip = sourceip;
	}

	/**  
	 * 鉴权标识   
	 */
	public String getClientauthflag() {
		return clientauthflag;
	}

	/**  
	 * 鉴权标识    
	 */
	public void setClientauthflag(String clientauthflag) {
		this.clientauthflag = clientauthflag;
	}

	/**  
	 * 请求来源   
	 */
	public String getReqorigin() {
		return reqorigin;
	}

	/**  
	 * 请求来源    
	 */
	public void setReqorigin(String reqorigin) {
		this.reqorigin = reqorigin;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
    
	
}
