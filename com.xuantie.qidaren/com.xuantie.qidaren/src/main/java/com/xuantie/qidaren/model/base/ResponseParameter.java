package com.xuantie.qidaren.model.base;

import java.io.Serializable;

/**
 * 业务响应参数
 * 
 * @description:
 * @author add by xuhh
 * @date 2018/4/26
 * @param <T> 业务返回参数实体
 * @备注：添加字段时,需要同步修改clean()方法
 *
 */
public class ResponseParameter implements Serializable {

	// ****备注：添加字段时,需要同步修改clean()方法****

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7254567415296805043L;

	/**
	 * 基础响应数据
	 */
	private ResponseBase respbase = new ResponseBase();

	/**
	 * 分页数据
	 */
	private ResponsePage resppage = new ResponsePage();

	/**
	 * 业务参数
	 */
	private Object respparam = null;

	// ----------------------get set--------------------------

	/**  
	 * 基础响应数据   
	 */
	public ResponseBase getRespbase() {
		return respbase;
	}

	/**  
	 * 基础响应数据    
	 */
	public void setRespbase(ResponseBase respbase) {
		this.respbase = respbase;
	}

	/**  
	 * 分页数据   
	 */
	public ResponsePage getResppage() {
		return resppage;
	}

	/**  
	 * 分页数据    
	 */
	public void setResppage(ResponsePage resppage) {
		this.resppage = resppage;
	}

	/**  
	 * 业务参数   
	 */
	public Object getRespparam() {
		return respparam;
	}

	/**  
	 * 业务参数    
	 */
	public void setRespparam(Object respparam) {
		this.respparam = respparam;
	}

	/**
	 * 清除对象已赋值
	 * @author pansq
	 * @date 2017年2月17日 下午3:41:59
	 */
	public void clean() {
		// 清除基础响应数据
		try {
			this.respbase.clean();
		} catch (Exception e) {
			this.respbase = new ResponseBase();
		}

		// 清除分页响应数据
		try {
			this.resppage.clean();
		} catch (Exception e) {
			this.resppage = new ResponsePage();
		}

		// 清除返回参数map
		this.respparam = null;
	}
}
