package com.td.proxy.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.proxy.dao.admin.RuleConfigDao;
import com.td.proxy.entity.admin.RuleConfig;

/**
 * 
 * <br>
 * <b>功能：</b>RuleConfigService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("ruleConfigService")
public class RuleConfigService extends BaseService<RuleConfig> {

	protected final static Logger logger = Logger.getLogger(RuleConfigService.class);

	@Autowired
	private RuleConfigDao<RuleConfig> dao;

	public RuleConfigDao<RuleConfig> getDao() {
		return dao;
	}

	public void insertRuleConfigs(RuleConfig[] ruleConfigs) {
		for (RuleConfig t : ruleConfigs) {
			dao.insert(t);
		}
	}

	public void updateRuleConfigs(RuleConfig[] ruleConfigs) {
		for (RuleConfig t : ruleConfigs) {
			dao.updateByPrimaryKeySelective(t);
		}
	}

	public List<RuleConfig> selectByTemplateRuleIds(Map<String, Object> map) {
		return dao.selectByTemplateRuleIds(map);
	}

	public List<RuleConfig> findByRuleDefinitionIdAndTemplateId(Integer ruleDefinitionId, Integer templateId) {
		return dao.findByRuleDefinitionIdAndTemplateId(String.valueOf(ruleDefinitionId), String.valueOf(templateId));
	}
	
	public Map<String, RuleConfig> findByDefinitionIdAndTempalteIdMap(Integer ruleDefinitionId, Integer templateId){
		List<RuleConfig> ruleConfigList = this.findByRuleDefinitionIdAndTemplateId(ruleDefinitionId, templateId);
		Map<String, RuleConfig> returnMap = new HashMap<String, RuleConfig>();
		for (RuleConfig config : ruleConfigList){
			returnMap.put(config.getCode(), config);
		}
		return returnMap;
	}
	
}
