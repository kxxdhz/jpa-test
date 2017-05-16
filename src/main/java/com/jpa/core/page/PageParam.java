package com.jpa.core.page;

import java.io.Serializable;

/**
 * 分页参数
 * @author zrk  
 * @date 2016年4月1日 下午1:31:22
 */
public class PageParam implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer rows = 20;//页大小
	private Integer page = 1;//页
	private String sidx = "id";//排序字段
	private String sord = "desc";//排序方向
	
	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}


	public Integer getPage() {
		return page;
	}


	public void setPage(Integer page) {
		this.page = page;
	}

	
	public String getSidx() {
		return sidx;
	}


	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}
	
	
}
