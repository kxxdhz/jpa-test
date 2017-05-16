package com.jpa.core.page;

import java.util.List;
import org.springframework.data.domain.Page;
/**
 * 分页封装
 * @author zrk  
 * @date 2015年9月29日 下午3:38:40
 */
public class PageData<T> {
	
	private List<T> content ;
	private Integer number;
	private Integer size;
	private Boolean last;
	private Long totalElements;
	private Integer totalPages;
//	private String sort;
//	private String[] order;

	public PageData(){
	}
	public PageData(Page<T> page){
		this.content = page.getContent();
		this.number = page.getNumber();
		this.size = page.getSize();
		this.last = page.isLast();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Boolean getLast() {
		return last;
	}

	public void setLast(Boolean last) {
		this.last = last;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
	
	
}
