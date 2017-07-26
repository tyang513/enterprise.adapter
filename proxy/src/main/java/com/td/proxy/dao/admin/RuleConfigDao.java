package com.td.proxy.dao.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.talkingdata.base.dao.BaseDao;
import com.td.proxy.entity.admin.RuleConfig;

/**
 * 
 * <br>
 * <b>功能：</b>RuleConfigDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface RuleConfigDao<T> extends BaseDao<T> {
	
	List<RuleConfig> selectByTemplateRuleId(Integer templateRuleId);
	
	void deleteByTemplateRuleId(Integer templateRuleId);
	
	List<RuleConfig> selectByTemplateRuleIds(Map<String,Object> map);
	
	List<RuleConfig> findByRuleDefinitionIdAndTemplateId(@Param(value="ruleDefinitionId")String ruleDefinitionId,@Param(value="templateId")String templateId);
}
