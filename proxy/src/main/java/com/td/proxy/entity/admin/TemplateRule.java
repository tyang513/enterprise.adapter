package com.td.proxy.entity.admin;

import java.util.Date;
import java.util.List;

import com.talkingdata.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>TemplateRuleEntity<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public class TemplateRule extends BaseEntity {
	
	private Integer id;
	private Integer tempId;
	private Integer ruleDefinitionId;
	private Date ctime;
	private Date mtime;
	
	private String description;
	
	private String name; //结算模板定义规则名称
	private String businessType; // 规则业务类型
	private String ruleClassName; //规则实现类
	private List<RuleConfigDefinition> ruleConfigDefinition;
	private List<RuleConfig> ruleConfigList;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getRuleClassName() {
		return ruleClassName;
	}

	public void setRuleClassName(String ruleClassName) {
		this.ruleClassName = ruleClassName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<RuleConfigDefinition> getRuleConfigDefinition() {
		return ruleConfigDefinition;
	}

	public void setRuleConfigDefinition(List<RuleConfigDefinition> ruleConfigDefinition) {
		this.ruleConfigDefinition = ruleConfigDefinition;
	}
	
	public List<RuleConfig> getRuleConfigList() {
		return ruleConfigList;
	}

	public void setRuleConfigList(List<RuleConfig> ruleConfigList) {
		this.ruleConfigList = ruleConfigList;
	}	
	
	
}

