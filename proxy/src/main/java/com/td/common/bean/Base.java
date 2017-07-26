package com.td.common.bean;

/** 
 * @description: 对象bean基类
 * @author: chenminghua 2013-12-11
 * @version: 1.0
 * @modify: 
 * @Copyright: 公司版权拥有
 */
public abstract class Base {
	/**
	 * 限制条件sql
	 */
	private String restrictionSql;

	public String getRestrictionSql() {
		return restrictionSql;
	}

	public void setRestrictionSql(String restrictionSql) {
		this.restrictionSql = restrictionSql;
	}
	
	/**
	 * 当前页,名字必须为page
	 */
	private int page;

	/**
	 * 每页大小,名字必须为rows
	 */
	private int rows;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
}
