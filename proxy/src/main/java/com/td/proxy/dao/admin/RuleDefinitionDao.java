package com.td.proxy.dao.admin;

import java.util.List;
import java.util.Map;

import com.talkingdata.base.dao.BaseDao;
import com.td.proxy.entity.admin.RuleDefinition;

/**
 * 
 * <br>
 * <b>功能：</b>RuleDefinitionDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface RuleDefinitionDao<T> extends BaseDao<T> {
	public List<RuleDefinition> queryByBusinessType(String businessType);
	
	public List<RuleDefinition> queryByBusinessTypeAndId(Map<String,Object> map);
	
	public RuleDefinition queryByBusinessTypeTemplate(Map<String,Object> map);
}
