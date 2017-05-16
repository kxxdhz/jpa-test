package com.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jpa.core.Condition;
import com.jpa.core.page.PageData;
import com.jpa.core.page.PageParam;
import com.jpa.entity.User;
import com.jpa.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/save")
	@ResponseBody
	public User save(){
		User user=new User();
		user.setUsername("lvpeng");
		user.setPassword("lvpeng");
		user.setSex(1);
		return userService.save(user);
	}
	
	@RequestMapping("/findOne")
	@ResponseBody
	public User findOne(){
		return userService.findOne(1l);
	}
	
	@RequestMapping("/remove")
	@ResponseBody
	public String remove(){
		userService.remove(2l);
		return "remove success";
	}
	

	@RequestMapping("/findAll")
	@ResponseBody
	public List<User> findAll(){
		return userService.findAll();
	}
	
	@RequestMapping("/findAllCondition")
	@ResponseBody
	public List<User> findAllCondition(){
		Condition condition=Condition.equal("sex", 1);
		return userService.findAll(condition);
	}
	
	@RequestMapping("/findAllPageParam")
	@ResponseBody
	public PageData<User> findAllPageParam(){
		PageParam pageParam=new PageParam();
		return userService.findAll(pageParam);
	}
	
	@RequestMapping("/findAllConditionAndPageParam")
	@ResponseBody
	public PageData<User> findAllConditionAndPageParam(){
		Condition condition=Condition.equal("sex", 1);
		PageParam pageParam=new PageParam();
		return userService.findAll(condition,pageParam);
	}
	
}
