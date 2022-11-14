package com.xuantie.qidaren.dao;



import com.xuantie.qidaren.model.entity.UserEntity;
import com.xuantie.qidaren.model.vo.UserVo;
   
public interface UserDao {  
	/**
     * 查询用户
     * @param username
     * @return
     */
    public UserVo selectByUserName(String username);
    
    /**
     * 新增用户
     * @param userEntityTemp
     * @return
     */
    public int addUser(UserEntity userEntityTemp);
} 
