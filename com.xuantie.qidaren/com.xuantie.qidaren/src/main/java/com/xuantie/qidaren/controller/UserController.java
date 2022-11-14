/**
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/26
 * @version  : v1.0
 */
package com.xuantie.qidaren.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.xuantie.qidaren.common.exception.BizException;
import com.xuantie.qidaren.model.base.RequestParameter;
import com.xuantie.qidaren.model.base.ResponseParameter;
import com.xuantie.qidaren.model.base.ResultConstant;
import com.xuantie.qidaren.model.dto.UserDto;
import com.xuantie.qidaren.model.entity.UserEntity;
import com.xuantie.qidaren.model.vo.UserVo;
import com.xuantie.qidaren.service.IUserService;  
  
  
@RestController  
@RequestMapping("/user")
public class UserController extends  BaseController{  
  
    @Autowired  
    private IUserService userService;  
       
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    private RedisTemplate redisTemplate;

    
    /**
	 * 用户注册
	 * @param requestParam
	 * @return ResponseParameter
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseParameter register(@RequestBody RequestParameter<UserEntity> requestParam) {
        try {
        	//获取参数
        	UserEntity userEntity = requestParam.getReqparam();
    		String username = userEntity.getUsername(); //用户名
    		String password = userEntity.getPassword(); //密码
    		String name = userEntity.getName(); //联系人姓名
    		//参数验证
    		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
    			throw new BizException(ResultConstant.ER_PRAM_EMPTY,ResultConstant.ER_PRAM_EMPTY_MSG,
    					String.format(ResultConstant.ER_PRAM_EMPTY_DESC, "{username:"+ username +",password:"+ password +"}")); //输出提示
    		}
    		
    		UserVo uservo = userService.selectByUserName(username); //根据用户名查询用户
    		//判断用户名已注册
    		if(uservo != null){
    			return ErrorJson(ResultConstant.ERROR, ResultConstant.ERROR_REGISTER_MSG, ResultConstant.ERROR_DESC); //输出错误提示
    		}
    		//创建用户
    		UserDto userdto = new UserDto(); //创建用户对象
    		userdto.setUsername(username); //用户名
    		userdto.setPassword(password); //密码
    		int size = userService.addUser(userEntity); //创建用户
    		//判断创建用户成功
    		if(size > 0){
    			setRespBase(true, ResultConstant.SUCCESS, ResultConstant.SUCCESS_DESC); //基础返回参数，成功
    		}else{
    			setRespBase(false, ResultConstant.ERROR, ResultConstant.ERROR_DESC); //基础返回参数，失败
    		}
    		return getResponseObj(); //输出参数
        } catch (BizException e) {
        	return ErrorJson(e.getErrorCode(), e.getErrorMsg(), e.getErrorDesc()); //输出错误提示
        } catch (Exception e) {
        	return ErrorJson(ResultConstant.EXCEPTION, ResultConstant.EXCEPTION_MSG, ResultConstant.EXCEPTION_DESC, e); //输出异常提示
        }
	}
	
	/**
	 * test
	 * @param
	 * @return ResponseParameter
	 */
	@RequestMapping(value = "/testredis", method = RequestMethod.POST)
	public void testredis() {
        try {
        	 stringRedisTemplate.opsForValue().set("aaa", "111");
        	 System.out.println("value:"+stringRedisTemplate.opsForValue().get("aaa"));
        }catch (Exception e) {
        	System.out.println("**********异常**********");
        }
	}
	
}  