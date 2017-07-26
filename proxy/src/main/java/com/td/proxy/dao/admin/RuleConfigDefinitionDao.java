package com.td.proxy.dao.admin;

import org.apache.ibatis.annotations.Param;

import com.talkingdata.base.dao.BaseDao;

/**
 * 
 * <br>
 * <b>功能：</b>RuleConfigDefinitionDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface RuleConfigDefinitionDao<T> extends BaseDao<T> {

	int findRuleConfigDefinition(@Param("code")String code, @Param("ruleDefId")int ruleDefId);
	
}
