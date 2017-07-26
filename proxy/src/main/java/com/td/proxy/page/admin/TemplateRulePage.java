package com.td.proxy.page.admin;

import java.util.Date;

import com.talkingdata.base.page.BasePage;

/**
 * 
 * <br>
 * <b>功能：</b>TemplateRulePage<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public class TemplateRulePage extends BasePage {

	private Integer id;
	private Integer tempId;
	private Integer ruleDefinitionId;
	private Date ctime;
	private Date mtime;
	
	private String status; //规则定义状态

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTempId() {
		return this.tempId;
	}

	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}

	public Integer getRuleDefinitionId() {
		return this.ruleDefinitionId;
	}

	public void setRuleDefinitionId(Integer ruleDefinitionId) {
		this.ruleDefinitionId = ruleDefinitionId;
	}

	public Date getCTime() {
		return this.ctime;
	}

	public void setCTime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getMTime() {
		return this.mtime;
	}

	public void setMTime(Date mtime) {
		this.mtime = mtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
