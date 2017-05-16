package com.jpa.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.jpa.core.Condition;
import com.jpa.core.ConditionSpecification;
import com.jpa.core.page.PageData;
import com.jpa.core.page.PageParam;
import com.jpa.dao.BaseRepository;
import com.jpa.entity.BaseEntity;
import com.jpa.service.BaseService;

/**
 * 基础实现
 * @author lvpeng
 * @date 2017年5月9日 下午2:50:09
 */
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T>{
	
	protected abstract BaseRepository<T> getRepository();

	@Override
	public T save(T entity) {
		return getRepository().save(entity);
	}

	@Override
	public List<T> save(List<T> entities) {
		return getRepository().save(entities);
	}
	
	public T findOne(Long id) {
		return getRepository().findOne(id);
	}
	
	public void remove(Long id) {
		getRepository().delete(id);;
	}

	@Override
	public List<T> findAll() {
		return getRepository().findAll();
	}
	
	public List<T> findAll(Condition condition){
		ConditionSpecification<T> specification = new ConditionSpecification<T>(condition);
		return getRepository().findAll(specification);
	}
	
	public PageData<T> findAll(PageParam pageParam) {
		Page<T> page = getRepository().findAll(converter(pageParam));
		return converter(page);
	}
	
	public PageData<T> findAll(Condition condition,PageParam pageParam){
		ConditionSpecification<T> specification = new ConditionSpecification<T>(condition);
		Page<T> page = getRepository().findAll(specification,converter(pageParam));
		return converter(page);
	}
	
	/**
	 * Page<实体>   转化为   PageData<实体>
	 * @date 2017年5月9日 下午2:47:42
	 * @param data
	 * @return
	 */
	public PageData<T> converter(Page<T> data) {
		PageData<T> pageData = new PageData<T>();
		pageData.setLast(data.isLast());
		pageData.setNumber(data.getNumber()+1);
		pageData.setSize(data.getSize());
		pageData.setTotalElements(data.getTotalElements());
		pageData.setTotalPages(data.getTotalPages());
		pageData.setContent(data.getContent());
		return pageData;
	}
	
	/**
	 * 把PageParam转换成PageRequest
	 * @param pageParam
	 * @return
	 */
	public PageRequest converter(PageParam pageParam) {
		if (!"desc".equalsIgnoreCase(pageParam.getSord()) && !"asc".equalsIgnoreCase(pageParam.getSord()))
			throw new IllegalArgumentException("'sidx' parameter must be 'desc' or 'asc'");
		Direction direction = "desc".equalsIgnoreCase(pageParam.getSord()) ? Direction.DESC : Direction.ASC;
		PageRequest pageRequest = new PageRequest(pageParam.getPage() - 1, pageParam.getRows(),direction,pageParam.getSidx());
		return pageRequest;
	}

}
