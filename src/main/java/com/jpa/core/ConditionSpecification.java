package com.jpa.core;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * 向 jpa 条件查询接口 传入自定义查询条件类  
 * @author zrk  
 * @date 2015年12月14日 下午8:36:42
 */
public class ConditionSpecification<T> implements Specification<T> {

	private Condition condition;
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return Condition.parseCondition(root, query, cb, condition);
	}
	
	public ConditionSpecification(Condition condition){
		this.condition = condition;
	}

}
