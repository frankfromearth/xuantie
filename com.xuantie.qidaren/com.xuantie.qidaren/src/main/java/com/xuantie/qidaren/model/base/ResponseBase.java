package com.xuantie.qidaren.model.base;

import java.io.Serializable;

/**
 * 基础响应参数
 * 
 * @description:
 * @author add by xuhh
 * @date 2018/4/26
 * @备注：添加字段时,需要同步修改clean()方法
 */
public class ResponseBase implements Serializable {

	private static final long serialVersionUID = 5737407722919586758L;

	// ****备注：添加字段时,需要同步修改clean()方法****

	/**
	 * 是否成功
	 */
	private boolean status;

	/**
	 * 结果返回码
	 */
	private String returncode;

	/**
	 * 返回结果提示
	 */
	private String returnmsg;

	// ----------------------get set--------------------------

	/**  
	 * 是否成功   
	 */
	public boolean getStatus() {
		return status;
	}

	/**  
	 * 是否成功    
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**  
	 * 结果返回码   
	 */
	public String getReturncode() {
		return returncode;
	}

	/**  
	 * 结果返回码    
	 */
	public void setReturncode(String returncode) {
		this.returncode = returncode;
	}

	/**  
	 * 返回结果提示
	 */
	public String getReturnmsg() {
		return returnmsg;
	}

	/**  
	 * 返回结果提示
	 */
	public void setReturnmsg(String returnmsg) {
		this.returnmsg = returnmsg;
	}

	/**
	 * 重置对象属性值
	 * @author pansq
	 * @date 2017年2月17日 下午3:47:02
	 */
	public void clean() {
		this.status = false;
		this.returncode = null;
		this.returnmsg = null;
	}
}
