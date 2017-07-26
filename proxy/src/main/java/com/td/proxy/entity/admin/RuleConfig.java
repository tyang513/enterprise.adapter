package com.td.proxy.entity.admin;

import java.util.Date;

import com.talkingdata.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>RuleConfigEntity<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public class RuleConfig extends BaseEntity {
	
	private Integer id;
	private Integer templateRuleId;
	private String code;
	private String content;
//	private String convertClassName;
	private Date ctime;
	private Date mtime;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTemplateRuleId() {
		return this.templateRuleId;
	}

	public void setTemplateRuleId(Integer templateRuleId) {
		this.templateRuleId = templateRuleId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	public String getConvertClassName() {
//		return convertClassName;
//	}
//
//	public void setConvertClassName(String convertClassName) {
//		this.convertClassName = convertClassName;
//	}

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
}

