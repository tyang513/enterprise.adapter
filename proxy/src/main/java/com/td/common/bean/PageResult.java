package com.td.common.bean;

import java.util.List;

/**
 * 返回给前端页面的分页数据
 * @author zhouguoping
 */
public class PageResult<E> {
	
	private int total ;
	
	private List<E> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}
	
}
