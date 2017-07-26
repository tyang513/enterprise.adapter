package com.td.proxy.service.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.proxy.dao.admin.RuleConfigDefinitionDao;

/**
 * 
 * <br>
 * <b>功能：</b>RuleConfigDefinitionService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("ruleConfigDefinitionService")
public class RuleConfigDefinitionService<T> extends BaseService<T> {
	private final static Logger logger = Logger.getLogger(RuleConfigDefinitionService.class);
	
	@Autowired
	private RuleConfigDefinitionDao<T> dao;

	public RuleConfigDefinitionDao<T> getDao() {
		return dao;
	}

	public boolean findRuleConfigDefinition(String code, int ruleDefId) {
		int i = dao.findRuleConfigDefinition(code, ruleDefId);
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}
}
