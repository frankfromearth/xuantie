package com.xuantie.qidaren.common.configmanage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * 业务配置管理
 * @author xuhh
 * @date 2018/4/30 
 *
 */
@Component
@ConfigurationProperties(locations = "classpath:application-biz.yml")
public class BizConfig {
	/**
	 * 环境类型
	 */
	private String profileType;

	/**
	 * 是否开启域名验证,针对js端调用
	 */
	private boolean openOriginCheck;

	/**
	 * 域名限制等级
	 */
	private String originLimitLevel;

	/**
	 * 允许访问的域名
	 */
	private Map<String, String> allowedOrigin;

	/**
	 * 是否开启IP验证,针对后台调用
	 */
	private boolean openIPCheck;

	/**
	 * 允许访问的IP
	 */
	private Map<String, String> allowedIP;

	/**
	 * 鉴权标识,js调用
	 */
	private String authIdentify4client = "";

	/**
	 * 鉴权标识,后台调用
	 */
	private String authIdentify4server = "";

	/**
	 * 鉴权标识,app调用
	 */
	private String authIdentify4app = "";

	/**
	 * 鉴权标识,跑批程序调用,用于无用户登录token情况
	 */
	private String authIdentify4batch = "";

	/**
	 * 秘钥前缀
	 */
	private String authEncrypKeyPrefix = "";

	/**
	 * 用户token Redis Key 前缀
	 */
	private String authTokenKeyPrefix = "PP:usertoken:";

	/**
	 * 秘钥
	 */
	private String xuantieAESKey;

	/**
	 * 商户号
	 */
	private String xuantieMerid;

	/**
	 * 私钥
	 */
	private String xuantiePrikey;

	/**
	 * 域名
	 */
	private String xuantieHost;

	/**
	 * 调用站点标识
	 */
	private Map<String, String> callerSiteFlag;

	/**
	 * 调用站点类型
	 */
	private Map<String, String> callerSiteType;


	/**
	 * 请求来源标识
	 */
	private Map<String, String> requestOriginFlag;

	/**
	 * IP黑名单过期时间
	 */
	private int blackIpExpiretime = 86400;

	/**
	 * 网关地址
	 */
	private String gatewayIp;

	/**
	 * 白名单IP列表
	 */
	private List<String> whiteIpList;

	/**
	 * 访问频率限制规则
	 */
	private String accessLimitRule;

	//----------------------get set--------------------------

	/**  
	 * 环境类型  
	 */
	public String getProfileType() {
		return profileType;
	}

	/**  
	 * 是否开启域名验证针对js端调用  
	 */
	public boolean isOpenOriginCheck() {
		return openOriginCheck;
	}

	/**  
	 * 是否开启域名验证针对js端调用    
	 */
	public void setOpenOriginCheck(boolean openOriginCheck) {
		this.openOriginCheck = openOriginCheck;
	}

	/**  
	 * 域名限制等级  
	 */
	public String getOriginLimitLevel() {
		return originLimitLevel;
	}

	/**  
	 * 域名限制等级    
	 */
	public void setOriginLimitLevel(String originLimitLevel) {
		this.originLimitLevel = originLimitLevel;
	}

	/**  
	 * 允许访问的域名  
	 */
	public Map<String, String> getAllowedOrigin() {
		return allowedOrigin;
	}

	/**  
	 * 允许访问的域名    
	 */
	public void setAllowedOrigin(Map<String, String> allowedOrigin) {
		this.allowedOrigin = allowedOrigin;
	}

	/**  
	 * 是否开启IP验证针对后台调用  
	 */
	public boolean isOpenIPCheck() {
		return openIPCheck;
	}

	/**  
	 * 是否开启IP验证针对后台调用    
	 */
	public void setOpenIPCheck(boolean openIPCheck) {
		this.openIPCheck = openIPCheck;
	}

	/**  
	 * 允许访问的IP  
	 */
	public Map<String, String> getAllowedIP() {
		return allowedIP;
	}

	/**  
	 * 允许访问的IP    
	 */
	public void setAllowedIP(Map<String, String> allowedIP) {
		this.allowedIP = allowedIP;
	}

	/**  
	 * 鉴权标识js调用  
	 */
	public String getAuthIdentify4client() {
		return authIdentify4client;
	}

	/**  
	 * 鉴权标识js调用    
	 */
	public void setAuthIdentify4client(String authIdentify4client) {
		this.authIdentify4client = authIdentify4client;
	}

	/**  
	 * 鉴权标识后台调用  
	 */
	public String getAuthIdentify4server() {
		return authIdentify4server;
	}

	/**  
	 * 鉴权标识后台调用    
	 */
	public void setAuthIdentify4server(String authIdentify4server) {
		this.authIdentify4server = authIdentify4server;
	}

	/**  
	 * 环境类型    
	 */
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	/**  
	 * 秘钥前缀  
	 */
	public String getAuthEncrypKeyPrefix() {
		return authEncrypKeyPrefix;
	}

	/**  
	 * 秘钥前缀    
	 */
	public void setAuthEncrypKeyPrefix(String authEncrypKeyPrefix) {
		this.authEncrypKeyPrefix = authEncrypKeyPrefix;
	}

	/**  
	 * 用户tokenRedisKey前缀  
	 */
	public String getAuthTokenKeyPrefix() {
		return authTokenKeyPrefix;
	}

	/**  
	 * 用户tokenRedisKey前缀    
	 */
	public void setAuthTokenKeyPrefix(String authTokenKeyPrefix) {
		this.authTokenKeyPrefix = authTokenKeyPrefix;
	}

	

	/**  
	 * 调用站点类型  
	 */
	public Map<String, String> getCallerSiteType() {
		return callerSiteType;
	}

	/**  
	 * 调用站点类型    
	 */
	public void setCallerSiteType(Map<String, String> callerSiteType) {
		this.callerSiteType = callerSiteType;
	}

	/**  
	 * 调用站点标识  
	 */
	public Map<String, String> getCallerSiteFlag() {
		return callerSiteFlag;
	}

	/**  
	 * 调用站点标识    
	 */
	public void setCallerSiteFlag(Map<String, String> callerSiteFlag) {
		this.callerSiteFlag = callerSiteFlag;
	}

	/**  
	 * 请求来源标识  
	 */
	public Map<String, String> getRequestOriginFlag() {
		return requestOriginFlag;
	}

	/**  
	 * 请求来源标识    
	 */
	public void setRequestOriginFlag(Map<String, String> requestOriginFlag) {
		this.requestOriginFlag = requestOriginFlag;
	}

	/**  
	 * IP黑名单过期时间  
	 */
	public int getBlackIpExpiretime() {
		return blackIpExpiretime;
	}

	/**  
	 * IP黑名单过期时间    
	 */
	public void setBlackIpExpiretime(int blackIpExpiretime) {
		this.blackIpExpiretime = blackIpExpiretime;
	}

	/**  
	 * 网关地址  
	 */
	public String getGatewayIp() {
		return gatewayIp;
	}

	/**  
	 * 网关地址    
	 */
	public void setGatewayIp(String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}

	/**  
	 * 白名单IP列表  
	 */
	public List<String> getWhiteIpList() {
		return whiteIpList;
	}

	/**  
	 * 白名单IP列表    
	 */
	public void setWhiteIpList(List<String> whiteIpList) {
		this.whiteIpList = whiteIpList;
	}

	/**  
	 * 访问频率限制规则  
	 */
	public String getAccessLimitRule() {
		return accessLimitRule;
	}

	/**  
	 * 访问频率限制规则    
	 */
	public void setAccessLimitRule(String accessLimitRule) {
		this.accessLimitRule = accessLimitRule;
	}

	/**  
	 * 鉴权标识app调用   
	 */
	public String getAuthIdentify4app() {
		return authIdentify4app;
	}

	/**  
	 * 鉴权标识app调用    
	 */
	public void setAuthIdentify4app(String authIdentify4app) {
		this.authIdentify4app = authIdentify4app;
	}

	/**  
	 * 鉴权标识跑批程序调用用于无用户登录token情况   
	 */
	public String getAuthIdentify4batch() {
		return authIdentify4batch;
	}

	/**  
	 * 鉴权标识跑批程序调用用于无用户登录token情况    
	 */
	public void setAuthIdentify4batch(String authIdentify4batch) {
		this.authIdentify4batch = authIdentify4batch;
	}

}
