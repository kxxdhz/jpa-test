package com.jpa.service;

import java.util.List;

import com.jpa.core.Condition;
import com.jpa.core.page.PageData;
import com.jpa.core.page.PageParam;

public interface BaseService<T> {
	
	T save(T entity);
	
	List<T> save(List<T> entities);
	
	T findOne(Long id);
	
	void remove(Long id);

	List<T> findAll();
	
	List<T> findAll(Condition condition);
	
	PageData<T> findAll(PageParam pageParam);
	
	PageData<T> findAll(Condition condition,PageParam pageParam);
	
}
