package com.td.proxy.dao.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.talkingdata.base.dao.BaseDao;
import com.td.proxy.entity.admin.TemplateRule;

/**
 * 
 * <br>
 * <b>功能：</b>TemplateRuleDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface TemplateRuleDao<T> extends BaseDao<T> {
	
	List<TemplateRule> selectByTemplateId(Integer tempId);
	
	TemplateRule findByTemplateIdAndbusinessType(@Param(value="tempId")Integer tempId,@Param(value="businessType")String businessType);
	
	List<TemplateRule> findByBusinessType(String businessType);
	
	void deleteByTemplateId(Integer tempId);
}
