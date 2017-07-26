package com.td.proxy.service.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.proxy.dao.admin.RuleConfigDao;
import com.td.proxy.dao.admin.TemplateRuleDao;
import com.td.proxy.entity.admin.RuleConfig;
import com.td.proxy.entity.admin.TemplateRule;

/**
 * 
 * <br>
 * <b>功能：</b>TemplateRuleService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("templateRuleService")
public class TemplateRuleService<T> extends BaseService<T> {
	private final static Logger logger = Logger.getLogger(TemplateRuleService.class);
	
	@Autowired
	private TemplateRuleDao<T> dao;
	
	@Autowired
	private RuleConfigDao<RuleConfig> ruleConfigDao;

	public TemplateRuleDao<T> getDao() {
		return dao;
	}
	
	public List<TemplateRule> selectByTemplateId(Integer tempId){
		return dao.selectByTemplateId(tempId);
	}
	
	public TemplateRule findByTemplateId(Integer tempId,String businessType){
		return dao.findByTemplateIdAndbusinessType(tempId,businessType);
	}
	
	public List<TemplateRule> findByBusinessType(String businessType){
		return dao.findByBusinessType(businessType);
	}
}
