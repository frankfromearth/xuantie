package com.xuantie.qidaren.model.dto;

/**
 * 用户
 * @author xuhh
 * 2018/4/26
 */
public class UserDto {
	
	//用户id
	private Integer id;
	//用户名称
	private String username;
	//用户密码
	private String password;
	//昵称
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
