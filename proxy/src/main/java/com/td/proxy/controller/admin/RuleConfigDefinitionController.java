package com.td.proxy.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.base.web.BaseController;
import com.td.proxy.entity.admin.RuleConfigDefinition;
import com.td.proxy.page.admin.RuleConfigDefinitionPage;
import com.td.proxy.service.admin.RuleConfigDefinitionService;

/**
 * 
 * <br>
 * <b>功能：</b>RuleConfigDefinitionController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014,<br>
 */ 
@Controller
@RequestMapping("/ruleConfigDefinition") 
public class RuleConfigDefinitionController extends BaseController{

	public final static Logger logger = Logger.getLogger(RuleConfigDefinitionController.class);

	@Autowired
	private RuleConfigDefinitionService<RuleConfigDefinition> ruleConfigDefinitionService; 

	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(RuleConfigDefinitionPage page) throws Exception {
		List<RuleConfigDefinition> rows = ruleConfigDefinitionService.queryByList(page);
		return getGridData(page.getPager().getRowCount(), rows);
	}
	
	@RequestMapping("/queryRuleConfigDefinitionList.do") 
	@ResponseBody
	public Map<String, Object> queryRuleConfigDefinitionList(RuleConfigDefinitionPage page) throws Exception {
		page.getPager().setPageEnabled(false);
		List<RuleConfigDefinition> rows = ruleConfigDefinitionService.queryByList(page);
		return getGridData(page.getPager().getRowCount(), rows);
	}

	/**
	 * 新建或修改
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(RuleConfigDefinition entity) throws Exception {
		
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			//同一规则下配置代码是否重复
			String code = entity.getCode();
			int ruleDefId = entity.getRuleDefId();
			boolean isExist = ruleConfigDefinitionService.findRuleConfigDefinition(code, ruleDefId);
			if (isExist) {
				return this.getFailureMessage("同一规则下配置代码重复");
			} else {
				ruleConfigDefinitionService.insert(entity);
				return getSuccessMessage("规则配置定义添加成功!");
			}
		} else {
			entity.setMTime(new Date());
			ruleConfigDefinitionService.updateByPrimaryKeySelective(entity);
			return getSuccessMessage("规则配置定义修改成功!");
		}
	}

	/**
	 * 新建或修改，用于复杂表单
	 * 
	 * @param formData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveData.do", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Map<String, Object> saveData(@RequestBody Map<String, Object> formData) throws Exception {
		JSONObject jsonForm = JSONObject.fromObject(formData);
		RuleConfigDefinition entity = (RuleConfigDefinition) JSONObject.toBean(jsonForm.getJSONObject("entity"), RuleConfigDefinition.class);
		String code = entity.getCode();
		int ruleDefId = entity.getRuleDefId();
		boolean isExist = ruleConfigDefinitionService.findRuleConfigDefinition(code, ruleDefId);
		if (isExist) {
			return this.getFailureMessage("同一规则下的配置代码不能重复");
		} else {

			if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
				ruleConfigDefinitionService.insert(entity);
				return getSuccessMessage("规则配置定义添加成功!");
			} else {
				ruleConfigDefinitionService.updateByPrimaryKey(entity);
				return getSuccessMessage("规则配置定义修改成功!");
			}
		}
	}

	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		RuleConfigDefinition entity = ruleConfigDefinitionService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}

	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id) throws Exception {
		ruleConfigDefinitionService.deleteByPrimaryKey(id);
		return getSuccessMessage("规则配置定义删除成功!");
	}
}
