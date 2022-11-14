package com.xuantie.qidaren.model.base;

import java.io.Serializable;

/**
 * 请求参数实体
 * 
 * @description:
 * @author add by xuhh
 * @date 2018/4/26
 * @param <T>
 *
 */
public class RequestParameter<T> implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2915011967601111130L;

	/**
	 * 请求基础参数
	 */
	private RequestBase reqbase;

	/**
	 * 请求分页参数
	 */
	private RequestPage reqpage;

	/**
	 * 请求业务参数
	 */
	private T reqparam;

	// ----------------------get set--------------------------

	/**  
	 * 请求基础参数   
	 */
	public RequestBase getReqbase() {
		return reqbase;
	}

	/**  
	 * 请求基础参数    
	 */
	public void setReqbase(RequestBase reqbase) {
		this.reqbase = reqbase;
	}

	/**  
	 * 请求分页参数   
	 */
	public RequestPage getReqpage() {
		return reqpage;
	}

	/**  
	 * 请求分页参数    
	 */
	public void setReqpage(RequestPage reqpage) {
		this.reqpage = reqpage;
	}

	/**  
	 * 请求业务参数   
	 */
	public T getReqparam() {
		return reqparam;
	}

	/**  
	 * 请求业务参数    
	 */
	public void setReqparam(T reqparam) {
		this.reqparam = reqparam;
	}

}
