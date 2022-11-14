package com.xuantie.qidaren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuantie.qidaren.dao.UserDao;
import com.xuantie.qidaren.model.entity.UserEntity;
import com.xuantie.qidaren.model.vo.UserVo;
import com.xuantie.qidaren.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired  
    private UserDao userDao;

	@Override
	public UserVo selectByUserName(String username) {
		// TODO Auto-generated method stub
		return userDao.selectByUserName(username);
	}

	@Override
	public int addUser(UserEntity userEntityTemp) {
		// TODO Auto-generated method stub
		return userDao.addUser(userEntityTemp);
	}  

}
