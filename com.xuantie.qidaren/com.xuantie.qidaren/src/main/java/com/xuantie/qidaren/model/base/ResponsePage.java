package com.xuantie.qidaren.model.base;

import java.io.Serializable;

/**
 * 分页响应参数
 * 
 * @description:
 * @author add by xuhh
 * @date 2018/4/26
 * @备注：添加字段时,需要同步修改clean()方法
 *
 */
public class ResponsePage implements Serializable {

	private static final long serialVersionUID = 3559085346904615128L;

	// ****备注：添加字段时,需要同步修改clean()方法****

	/**
	 * 数据总记录数
	 */
	private long total;

	/**
	 * 分页索引，和调用方传参保持一致
	 */
	private int page;

	/**
	 * 每页数据量，和调用方传参保持一致
	 */
	private int size;

	// ----------------------get set--------------------------

	/**  
	 * 数据总记录数   
	 */
	public long getTotal() {
		return total;
	}

	/**  
	 * 数据总记录数    
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**  
	 * 分页索引，和调用方传参保持一致   
	 */
	public int getPage() {
		return page;
	}

	/**  
	 * 分页索引，和调用方传参保持一致    
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**  
	 * 每页数据量，和调用方传参保持一致   
	 */
	public int getSize() {
		return size;
	}

	/**  
	 * 每页数据量，和调用方传参保持一致    
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 重置对象属性值
	 * @author pansq
	 * @date 2017年2月17日 下午3:50:29
	 */
	public void clean() {
		this.total = 0;
		this.page = 0;
		this.size = 0;
	}

}
