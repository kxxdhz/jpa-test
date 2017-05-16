package com.jpa.core;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 查询条件组成基础类
 * @author zrk  
 * @date 2015年12月14日 下午6:20:43
 */
public class Condition {

	private String fieldName; // 属性名
	private Object value; // 对应值
	private String operator; // 计算符
	
	private String groupOp ; //and 、 or  描述 conditions 中各个条件的关系
	
	List<Condition> conditions = new ArrayList<Condition>();
	
	public Condition() {
		super();
	}
	public Condition(String groupOp) {
		super();
		this.groupOp = groupOp;
	}

	private Condition(String operator,String fieldName, Object... value) {
		super();
		this.operator = operator;
		this.fieldName = fieldName;
		this.value = value;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Predicate parseCondition(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder, Condition condition) {
		if(condition==null)
			return null;
		if("and".equalsIgnoreCase(condition.getGroupOp())){
			List<Predicate> predicates = new ArrayList<Predicate>();  
			for (Condition con : condition.getConditions()) {
				predicates.add(parseCondition(root, query, builder, con));
			}
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		}else if("or".equalsIgnoreCase(condition.getGroupOp())){
			List<Predicate> predicates = new ArrayList<Predicate>();  
			for (Condition con : condition.getConditions()) {
				predicates.add(parseCondition(root, query, builder, con));
			}
			return builder.or(predicates.toArray(new Predicate[predicates.size()]));
		}else if(!"".equalsIgnoreCase(condition.getFieldName())){
			Path expression = null;
			String fieldName = condition.getFieldName();
			Object value = ((Object[])condition.getValue())[0];
			if (fieldName.contains(".")) {
				String[] names = fieldName.split("\\.");
				expression = root.get(names[0]);
				for (int i = 1; i < names.length; i++) {
					expression = expression.get(names[i]);
				}
			} else {
				expression = root.get(fieldName);
			}
			String operator = condition.getOperator();
			switch (operator) {
			case "equal":
				return builder.equal(expression, value);
			case "ne":
				return builder.notEqual(expression, value);
			case "like":
				return builder.like(expression, "%" + value + "%");
			case "lt":
				return builder.lessThan(expression, (Comparable) value);
			case "gt":
				return builder.greaterThan(expression, (Comparable) value);
			case "lte":
				return builder.lessThanOrEqualTo(expression, (Comparable) value);
			case "gte":
				return builder.greaterThanOrEqualTo(expression, (Comparable) value);
			case "isNull": 
				return builder.isNull(expression);
			case "isNotNull": 
				return builder.isNotNull(expression);
			case "in":
				return in(expression,builder,(Object[])condition.getValue());
			case "notIn":
				return notIn(expression,builder,(Object[])condition.getValue());
			case "between": 
				return between(expression, builder,(Object[])condition.getValue());
			default:
				return null;
			}
		}
		return null;
	}
	 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Predicate in(Path expression ,CriteriaBuilder builder, Object... value) {
		if ((value == null) || (value.length == 0)) 
			return null;
		In in = builder.in(expression);
		for (Object object : value) {
			in.value(object);
		}
		return in;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Predicate notIn(Path expression ,CriteriaBuilder builder, Object... value) {
        if ((value == null) || (value.length == 0)) 
            return null;
        In in = builder.in(expression);
        for (Object object : value) {
        	in.value(object);
		}
        return builder.not(in);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Predicate between(Path expression, CriteriaBuilder builder, Object... value) {
		if ((value == null) || (value.length == 0))
			return null;
		Object value1 ;
		Object value2 ;
		if(value.length == 1){
			value1 = ((Object[])value)[0];
			return builder.greaterThan(expression, (Comparable) value1);
		}
		if (value.length >=2 ) {
			value1 = ((Object[])value)[0];
			value2 = ((Object[])value)[1];
			return builder.between(expression,(Comparable)value1,(Comparable)value2);
		}
		return null;
	}

	
	//包含
	public static Condition like(String fieldName,Object... value){
		return new Condition("like",fieldName,value);
	}
	//等于
	public static Condition equal(String fieldName,Object... value){
		return new Condition("equal",fieldName,value);
	}
	//不等
	public static Condition ne(String fieldName,Object... value){
		return new Condition("ne",fieldName,value);
	}
	//大于
	public static Condition gt(String fieldName,Object... value){
		return new Condition("gt",fieldName,value);
	}
	//大于等于
	public static Condition gte(String fieldName,Object... value){
		return new Condition("gte",fieldName,value);
	}
	//小于
	public static Condition lt(String fieldName,Object... value){
		return new Condition("lt",fieldName,value);
	}
	//小于等于
	public static Condition lte(String fieldName,Object... value){
		return new Condition("lte",fieldName,value);
	}
	//in
	public static Condition in(String fieldName,Object... value){
		return new Condition("in",fieldName,value);
	}
	//not in
	public static Condition notIn(String fieldName,Object... value){
		return new Condition("notIn",fieldName,value);
	}
	//between
	public static Condition between(String fieldName,Object... value){
		return new Condition("between",fieldName,value);
	}
	//isNull
	public static Condition isNull(String fieldName){
		return new Condition("isNull",fieldName,new Object());
	}
	//isNull
	public static Condition isNotNull(String fieldName){
		return new Condition("isNotNull",fieldName,new Object());
	}
	
	
	//and
	public static Condition and(Condition... condition){
		Condition handler = new Condition("and");
		for (Condition c : condition) {
			handler.getConditions().add(c);
		}
		return handler;
	}
	//or
	public static Condition or(Condition... condition){
		Condition handler = new Condition("or");
		for (Condition c : condition) {
			handler.getConditions().add(c);
		}
		return handler;
	}
	

	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}


	
	
}
