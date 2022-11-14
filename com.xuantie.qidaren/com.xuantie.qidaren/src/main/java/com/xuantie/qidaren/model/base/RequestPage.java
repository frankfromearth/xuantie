package com.xuantie.qidaren.model.base;

import java.io.Serializable;

/**
 * 请求分页参数
 * 
 * @description:
 * @author add by xuhh
 * @date 2018/4/26
 *
 */
public class RequestPage implements Serializable {

	/**
	 * 序列胡id
	 */
	private static final long serialVersionUID = -5973437056036915351L;

	/**
	 * 分页索引，默认为1
	 */
	private int page = 1;

	/**
	 * 每页数据量，默认值为10
	 */
	private int size = 10;

	/**
	 * 是否返回总条数,默认false
	 */
	private boolean count = false;

	/**
	 * 排序
	 */
	private String order = "";

	//----------------------get set------------------------

	/**  
	 * 分页索引，默认为1   
	 */
	public int getPage() {
		return page;
	}

	/**  
	 * 分页索引，默认为1    
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**  
	 * 每页数据量，默认值为10   
	 */
	public int getSize() {
		return size;
	}

	/**  
	 * 每页数据量，默认值为10    
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**  
	 * 是否返回总条数默认false   
	 */
	public boolean isCount() {
		return count;
	}

	/**  
	 * 是否返回总条数默认false    
	 */
	public void setCount(boolean count) {
		this.count = count;
	}

	/**  
	 * 排序   
	 */
	public String getOrder() {
		return order;
	}

	/**  
	 * 排序    
	 */
	public void setOrder(String order) {
		this.order = order;
	}

}
