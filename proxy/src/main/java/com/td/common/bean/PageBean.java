package com.td.common.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 分页处理类
 * @author: cmh
 * @version: 1.0
 * @modify: yuhui 2013-9-17
 * @Copyright: 
 */
public class PageBean implements java.io.Serializable {
	private static final long serialVersionUID = 7232798260610351342L;
	/**
	 * 页数
	 */
	private Long pageNum;

	/**
	 * 显示条数
	 */
	private Long numPerPage;

	/**
	 * 总页数
	 */
	private Long pageCount;

	/**
	 * 总条数
	 */
	private Long totalCount;

	/**
	 * 扩展Map
	 */
	private Map extend = new HashMap();

	/**
	 * 从多少条到多少条
	 */
	private Long firstResult;

	/**
	 * 到多少条
	 */
	private Long maxResult;

	/***************************************************************/
	/**
	 * 当前页,名字必须为page
	 */
	private int page;

	/**
	 * 每页大小,名字必须为rows
	 */
	private int rows;

	/**
	 * 排序字段
	 */
	private String sort;

	/**
	 * 排序规则
	 */
	private String order;

	/**
	 * 开始行数
	 */
	private int beginIndex;

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
		beginIndex = (page - 1) * rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	/***************************************************************/
	public Long getPageNum() {
		return pageNum;
	}

	public void setPageNum(Long pageNum) {
		this.pageNum = pageNum;
	}

	public Long getPageCount() {
		return pageCount;
	}

	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public Map getExtend() {
		return extend;
	}

	public void setExtend(Map extend) {
		this.extend = extend;
	}

	public Long getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Long firstResult) {
		this.firstResult = firstResult;
	}

	public Long getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(Long maxResult) {
		this.maxResult = maxResult;
	}

	public void setTotalCount(Long totalCount) {
		// 计算当前总页数
		if (totalCount > 1) {
			if (numPerPage > totalCount) {
				pageCount = 1l;
				this.totalCount = totalCount;
			} else {
				pageCount = totalCount / numPerPage;
				if (pageCount * numPerPage < totalCount) {
					pageCount = pageCount + 1;
				}
				this.totalCount = totalCount;
			}
		} else {
			pageCount = 0l;
			this.totalCount = totalCount;
		}
		if (pageNum > pageCount) {
			pageNum = pageCount;
		} else if (pageNum < 1) {
			pageNum = 1l;
		}
	}

	public PageBean() {
		super();
	}

	public PageBean(Long pageNum, Long numPerPage, Long totalCount) {
		setPageNum(pageNum);
		setNumPerPage(numPerPage);
		setTotalCount(totalCount);
		maxResult = pageNum * numPerPage;
		firstResult = maxResult - numPerPage;
	}

	public Long getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Long numPerPage) {
		this.numPerPage = numPerPage;
	}

	@Override
	public String toString() {
		return "PageBean [pageNum=" + pageNum + ", numPerPage=" + numPerPage + ", pageCount=" + pageCount
				+ ", totalCount=" + totalCount + ", extend=" + extend + ", firstResult=" + firstResult + ", maxResult="
				+ maxResult + ", page=" + page + ", rows=" + rows + ", sort=" + sort + ", order=" + order + "]";
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
}
