package com.xuantie.qidaren.model.base;

/**
 * 接口返回码&返回描述
 * 
 * @description:
 * @author add by xuhh
 * @date 2018/4/26
 *
 */
public class ResultConstant {

	// Tips: 1. DESC 描述Code的手实际意义
	// 2. MSG 返回给用户的提示

	// ----------------------------1xxxx : 成功---------------------------------

	/**
	 * 成功
	 */
	public static final String SUCCESS = "10000";
	public static final String SUCCESS_DESC = "成功";

	// ----------------------------2xxxx : 失败----------------------------------

	/**
	 * 失败
	 */
	public static final String ERROR = "20000";
	public static final String ERROR_DESC = "失败";

	public static final String ERROR_LOGIN_MSG = "用户名或密码错误，请重新输入！";
	
	public static final String ERROR_LOGIN_OVERDUE_MSG = "您的账号已过期，请联系系统管理员！";
	
	public static final String ERROR_REGISTER_MSG = "该用户名已注册！";
	
	public static final String ERROR_MODIFYPASSWORD_MSG = "原密码错误，请重新输入！";
	
	public static final String ERROR_FORGETPASSWORD_MSG = "该用户名不存在，请确认后重新输入。";
	
	public static final String ERROR_FORGETPASSWORD_MOBILE_MSG = "手机号码与用户名不匹配，请确认后重新输入。";
	
	public static final String ERROR_FORGETPASSWORD_MOBILECODE_MSG = "手机验证码错误，请确认后重新输入。";

	
	/**
	 * 异常
	 */
	public static final String EXCEPTION = "20001";
	public static final String EXCEPTION_DESC = "异常:";
	public static final String EXCEPTION_MSG = "抱歉,系统繁忙,请稍后重试.";

	/**
	 * 请求体为空
	 */
	public static final String ER_HTTP_REQBODY_NULL = "20002";
	public static final String ER_HTTP_REQBODY_NULL_DESC = "%s请求体为空";
	public static final String ER_HTTP_REQBODY_NULL_MSG = "抱歉,请求失败,请稍后重试.";

	/**
	 * 解密失败
	 */
	public static final String ER_CRYPTO = "20003";
	public static final String ER_CRYPTO_DESC = "%s解密失败";
	public static final String ER_CRYPTO_MSG = "抱歉,系统繁忙,请稍后重试.";

	/**
	 * 域名不合法
	 */
	public static final String ER_ORIGIN_ILLEGAL = "20004";
	public static final String ER_ORIGIN_ILLEGAL_DESC = "%s域名不合法";
	public static final String ER_ORIGIN_ILLEGAL_MSG = "抱歉,系统繁忙,请稍后重试.";

	/**
	 * IP不合法
	 */
	public static final String ER_IP_ILLEGAL = "20005";
	public static final String ER_IP_ILLEGAL_DESC = "%sIP不合法";
	public static final String ER_IP_ILLEGAL_MSG = "抱歉,系统繁忙,请稍后重试.";

	/**
	 * 没有操作权限
	 */
	public static final String ER_AUTHORITY_LACK = "20006";
	public static final String ER_AUTHORITY_LACK_DESC = "用户%s没有操作权限.";
	public static final String ER_AUTHORITY_LACK_MSG = "抱歉,您没有权限执行该操作.";

	/**
	 * 参数为空
	 */
	public static final String ER_PRAM_EMPTY = "21001";
	public static final String ER_PRAM_EMPTY_DESC = "必要参数%s为空";
	public static final String ER_PRAM_EMPTY_MSG = "必要参数为空";

	/**
	 * 参数不合法
	 */
	public static final String ER_PRAM_ILLEGAL = "21002";
	public static final String ER_PRAM_ILLEGAL_DESC = "参数%s不合法";
	public static final String ER_PRAM_ILLEGAL_MSG = "参数不合法";
	
	/**
     * token不合法
     */
    public static final String ER_PRAM_TOKEN_ILLEGAL = "21004";

    public static final String ER_PRAM_TOKEN_ILLEGAL_DESC = "token%s不合法";

    public static final String ER_PRAM_TOKEN_ILLEGAL_MSG = "参数不合法.";
    
    
	/**
	 * 鉴权标识不合法
	 */
	public static final String ER_PRAM_AUTHFLAG_ILLEGAL = "21003";
	public static final String ER_PRAM_AUTHFLAG_ILLEGAL_DESC = "鉴权标识%s不合法";
	public static final String ER_PRAM_AUTHFLAG_ILLEGAL_MSG = "参数不合法.";
	
	/**
	 * 安全检查不通过
	 */
	public static final String ER_SECURECHECK_NOPASS = "21004";
	public static final String ER_SECURECHECK_NOPASS_DESC = "%s访问%s频率超过限制.";
	public static final String ER_SECURECHECK_NOPASS_MSG = "抱歉,访问过于频繁,请稍后重试.";
	
}