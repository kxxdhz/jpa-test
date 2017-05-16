package com.jpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.dao.BaseRepository;
import com.jpa.dao.UserRepository;
import com.jpa.entity.User;
import com.jpa.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

	@Autowired
	private UserRepository repository;
	
	@Override
	protected BaseRepository<User> getRepository() {
		return this.repository;
	}

}
