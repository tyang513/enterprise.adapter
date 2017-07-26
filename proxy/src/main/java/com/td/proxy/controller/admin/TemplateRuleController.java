package com.td.proxy.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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
import com.td.common.util.Constants;
import com.td.proxy.entity.admin.RuleConfig;
import com.td.proxy.entity.admin.Template;
import com.td.proxy.entity.admin.TemplateRule;
import com.td.proxy.page.admin.TemplateRulePage;
import com.td.proxy.service.admin.RuleConfigService;
import com.td.proxy.service.admin.TemplateRuleService;
import com.td.proxy.service.admin.TemplateService;

/**
 * <br>
 * <b>功能：</b>TemplateRuleController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014br>
 */
@Controller
@RequestMapping("/templateRule")
public class TemplateRuleController extends BaseController {

	public final static Logger logger = Logger.getLogger(TemplateRuleController.class);

	@Autowired
	private TemplateRuleService<TemplateRule> templateRuleService;

	@Autowired
	private RuleConfigService ruleConfigService;

	@Autowired
	private TemplateService settleTemplateService;

	@RequestMapping("/list.do")
	@ResponseBody
	public Map<String, Object> list(TemplateRulePage page) throws Exception {
		page.setStatus(Constants.BASIS_STATUS_ENABLE);
		List<TemplateRule> rows = templateRuleService.queryByList(page);
		return getGridData(page.getPager().getRowCount(), rows);
	}

	@RequestMapping("/queryRuleConfigList.do")
	@ResponseBody
	public List<TemplateRule> queryTemplateRuleList(TemplateRulePage page) throws Exception {
		page.getPager().setPageEnabled(false);
		return templateRuleService.queryByList(page);
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
	public Map<String, Object> save(TemplateRule entity) throws Exception {

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			templateRuleService.insert(entity);
			return getSuccessMessage("模板计算规则添加成功!");
		} else {
			templateRuleService.updateByPrimaryKeySelective(entity);
			return getSuccessMessage("模板计算规则修改成功!");
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
		TemplateRule entity = (TemplateRule) JSONObject.toBean(jsonForm.getJSONObject("entity"), TemplateRule.class);
		RuleConfig[] ruleConfigs = (RuleConfig[]) JSONArray.toArray(jsonForm.getJSONArray("ruleConfigs"), RuleConfig.class);
		Date d = new Date();

		// 验证模板计算规则不能重复!
		if (checkExist(entity.getId(), entity.getTempId(), entity.getRuleDefinitionId())) {
			return getFailureMessage("模板计算规则不能重复!");
		}

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {

			templateRuleService.insert(entity);
			for (RuleConfig ruleConfig : ruleConfigs) {
				ruleConfig.setTemplateRuleId(entity.getId());
				ruleConfig.setCTime(d);
			}
			ruleConfigService.insertRuleConfigs(ruleConfigs);
			return getSuccessMessage("模板计算规则添加成功!");
		} else {
			templateRuleService.updateByPrimaryKeySelective(entity);
			ruleConfigService.updateRuleConfigs(ruleConfigs);
			return getSuccessMessage("模板计算规则修改成功!");
		}
	}

	@RequestMapping("/saveAllData.do")
	@ResponseBody
	public Map<String, Object> saveAllData(HttpServletRequest request, HttpServletResponse response, TemplateRule entity) throws Exception {
		// 验证模板计算规则不能重复!
		if (checkExist(entity.getId(), entity.getTempId(), entity.getRuleDefinitionId())) {
			return getFailureMessage("模板计算规则不能重复!");
		}

		String approvalChainJsonData = request.getParameter("approvalChainJsonData");
		JSONArray jsonArray = JSONArray.fromObject(approvalChainJsonData);
		RuleConfig[] rcList = (RuleConfig[]) JSONArray.toArray(jsonArray, RuleConfig.class);

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			templateRuleService.insert(entity);

			for (RuleConfig rc : rcList) {
				rc.setId(null);
				rc.setTemplateRuleId(entity.getId());
				rc.setCTime(new Date());
			}

			ruleConfigService.insertRuleConfigs(rcList);

			return getSuccessMessage("模板计算规则添加成功!");
		} else {
			templateRuleService.updateByPrimaryKeySelective(entity);

			for (RuleConfig rc : rcList) {
				rc.setTemplateRuleId(entity.getId());
				rc.setMTime(new Date());
			}

			ruleConfigService.updateRuleConfigs(rcList);

			return getSuccessMessage("模板计算规则修改成功!");
		}

	}

	/**
	 * 验证模板计算规则不能重复!
	 * 
	 * @param id
	 * @param tempId
	 * @param ruleDefinitionId
	 * @return
	 * @throws Exception
	 */
	private boolean checkExist(Integer id, Integer tempId, Integer ruleDefinitionId) throws Exception {
		if (id == null) {
			TemplateRulePage t = new TemplateRulePage();
			t.setTempId(tempId);
			t.setRuleDefinitionId(ruleDefinitionId);
			t.setStatus("1");
			int count = templateRuleService.queryByCount(t);
			if (count > 0) {
				return true;
			}
		} else {
			TemplateRule self = (TemplateRule) templateRuleService.selectByPrimaryKey(id);
			if (self.getRuleDefinitionId() == ruleDefinitionId) {

			} else {
				TemplateRulePage t = new TemplateRulePage();
				t.setTempId(tempId);
				t.setRuleDefinitionId(ruleDefinitionId);
				int count = templateRuleService.queryByCount(t);
				if (count > 0) {
					return true;
				}
			}
		}
		return false;
	}

	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		TemplateRule entity = templateRuleService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}

	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id) throws Exception {
		TemplateRule rule = templateRuleService.selectByPrimaryKey(id[0]);
		Integer tempId = rule.getTempId();
		Template settleTemplate = settleTemplateService.selectByPrimaryKey(tempId);
		// Integer umid = settleTemplate.getSubjectumId();
		// Subjectum subjectum = subjectumService.selectByPrimaryKey(umid);
		// if (Integer.parseInt(subjectum.getStatus()) > 1) {
		// return getFailureMessage("已使用的不能删除");
		// }

		templateRuleService.deleteByPrimaryKey(id);
		return getSuccessMessage("模板与规则关联关系删除成功!");
	}

	@RequestMapping("/findRuleByTempId.do")
	@ResponseBody
	public List<TemplateRule> findRuleByTempId(Integer tempId) {
		return templateRuleService.selectByTemplateId(tempId);
	}
}
