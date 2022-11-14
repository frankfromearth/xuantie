package com.xuantie.qidaren.service;


import com.xuantie.qidaren.model.entity.UserEntity;
import com.xuantie.qidaren.model.vo.UserVo;



public interface IUserService {  
    
	public UserVo selectByUserName(String username);

	public int addUser(UserEntity userEntityTemp);

}  
