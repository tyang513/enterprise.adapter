package com.td.proxy.entity.admin;

import java.util.Date;

import com.talkingdata.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>RuleConfigDefinitionEntity<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public class RuleConfigDefinition extends BaseEntity {
	
	private Integer id;
	private String code;
	private Integer ruleDefId;
	private String content;
//	private String converterClassName;
	private String description;
	private Date ctime;
	private Date mtime;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getRuleDefId() {
		return this.ruleDefId;
	}

	public void setRuleDefId(Integer ruleDefId) {
		this.ruleDefId = ruleDefId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	public String getConverterClassName() {
//		return converterClassName;
//	}
//
//	public void setConverterClassName(String converterClassName) {
//		this.converterClassName = converterClassName;
//	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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
}

