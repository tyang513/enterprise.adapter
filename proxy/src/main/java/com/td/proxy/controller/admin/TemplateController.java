package com.td.proxy.controller.admin;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.td.common.util.Constants;
import com.td.common.util.SequenceUtil;
import com.td.proxy.entity.admin.Template;
import com.td.proxy.page.admin.TemplatePage;
import com.td.proxy.service.admin.TemplateService;

/**
 * 
 * <br>
 * <b>功能：</b>SettleTemplateController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
@Controller
@RequestMapping("/template")
public class TemplateController extends BaseController {

	public final static Logger logger = Logger.getLogger(TemplateController.class);

	@Autowired
	private TemplateService templateService;

	@RequestMapping("/list.do")
	@ResponseBody
	public Map<String, Object> list(TemplatePage page) throws Exception {
		List<Template> rows = new ArrayList<Template>();
		page.getPager().setPageEnabled(false); // 设置不分页
		// if(!page.isCheckedId()) {
		// List<SettleTemplate> templateList =
		// settleTemplateService.queryValidByList(page);
		// if(templateList != null && templateList.size() > 0) {
		// rows.add(templateList.get(0));
		// }
		// } else {
		page.setSort("version");
		page.setOrder("desc");
		rows = templateService.queryByList(page);
		// }
		return getGridData(rows.size(), rows);
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
	public Map<String, Object> save(Template entity) throws Exception {
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			templateService.insertTemplate(entity);
			return getSuccessData(entity);
		} else {
			templateService.updateByPrimaryKeySelective(entity);
			return getSuccessData(entity);
		}

	}

	@RequestMapping(value = "/saveTemplate.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTemplate(Template entity, Integer categoryId) throws Exception {
		templateService.insertTemplate(entity);
		// TemplateCategoryRel templateCategoryRel =new TemplateCategoryRel();
		// templateCategoryRel.setCategoryId(categoryId);
		// templateCategoryRel.setTemplateCode(entity.getTemplateCode());
		// templateCategoryRelService.insert(templateCategoryRel);
		return getSuccessData(entity);
	}

	/*
	 * @RequestMapping("/queryMaxVersionTemplate.do")
	 * 
	 * @ResponseBody public Map<String, Object>
	 * queryMaxVersionSettleTemplate(Integer subjectumId) throws Exception {
	 * List<Template> rows = new ArrayList<Template>(); TemplatePage page = new
	 * TemplatePage(); page.setSort("version"); page.setOrder("desc"); rows =
	 * templateService.queryByList(page); if(rows != null) { return
	 * getSuccessData(rows.get(0)); } else { return
	 * getFailureMessage("不存在可用于升版的模板！"); } }
	 */

	/**
	 * 新建或修改
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEnableDisableTemplate.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEnableDisableSettleTemplate(Template entity) throws Exception {

		templateService.updateByPrimaryKeySelective(entity);
		return getSuccessData(entity);
	}

	/**
	 * 升级版本
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upgradeTemplate.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upgradeSettleTemplate(String id) throws Exception {
		if (id != null && !"".equals(id)) {
			Template template = templateService.selectByPrimaryKey(id);
			/*
			 * 新增一条模板记录
			 */
			Date effectDate = template.getExpiryDate();

			Calendar cal = Calendar.getInstance();
			cal.setTime(template.getExpiryDate());
			cal.add(Calendar.YEAR, 1);
			Date expireDate = cal.getTime();
			template.setId(null);
			Integer version = SequenceUtil.getSubSequenceNum(template.getTemplateCode());
			template.setCtime(new Date());
			template.setVersion(version);
			template.setEffectDate(effectDate);
			template.setExpiryDate(expireDate);
			template.setStatus(Constants.BASIS_STATUS_UNENABLE);
			templateService.insert(template);
			return getSuccessMessage("模板升级成功!");
		} else {
			return getFailureMessage("模板升级失败!");
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
		Template entity = (Template) JSONObject.toBean(jsonForm.getJSONObject("entity"), Template.class);
		Date d = new Date(System.currentTimeMillis());
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			entity.setCtime(d);
			String code = SequenceUtil.getSequenceCode(Constants.TEMPLATE_NUMBER);
			entity.setTemplateCode(code);
			entity.setVersion(SequenceUtil.getSubSequenceNum(code));
			templateService.insert(entity);
			return getSuccessData(entity);
		} else {
			templateService.updateByPrimaryKey(entity);
			return getSuccessData(entity);
		}
	}

	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		Template entity = templateService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}

	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id, String templateCode) throws Exception {
		templateService.deleteByPrimaryKey(id);
		return getSuccessMessage("模板删除成功!");
	}
}
