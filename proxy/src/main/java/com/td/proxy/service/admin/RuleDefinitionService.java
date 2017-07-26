package com.td.proxy.service.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.talkingdata.base.service.BaseService;
import com.talkingdata.casclient.User;
import com.td.common.entity.SysAttachment;
import com.td.common.page.SysAttachmentPage;
import com.td.common.service.SysAttachmentService;
import com.td.common.util.Constants;
import com.td.common.util.JSONUtil;
import com.td.common.util.SequenceUtil;
import com.td.proxy.dao.admin.RuleConfigDefinitionDao;
import com.td.proxy.dao.admin.RuleDefinitionDao;
import com.td.proxy.dao.admin.TemplateRuleDao;
import com.td.proxy.entity.admin.RuleConfigDefinition;
import com.td.proxy.entity.admin.RuleDefinition;
import com.td.proxy.entity.admin.TemplateRule;
import com.td.proxy.page.admin.RuleConfigDefinitionPage;
import com.td.proxy.page.admin.RuleDefinitionPage;
import com.td.proxy.page.admin.TemplateRulePage;

/**
 * 
 * <br>
 * <b>功能：</b>RuleDefinitionService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("ruleDefinitionService")
public class RuleDefinitionService extends BaseService<RuleDefinition> {
	
	public final static Logger logger = Logger.getLogger(RuleDefinitionService.class);

	@Autowired
	private RuleDefinitionDao<RuleDefinition> dao;

	@Autowired
	private SysAttachmentService<SysAttachment> sysAttachmentService;

	@Autowired
	private TemplateRuleDao<TemplateRule> templateRuleDao;

	@Autowired
	private RuleConfigDefinitionDao<RuleConfigDefinition> ruleConfigDefinitionDao;

//	@Autowired
//	private CategoryDao<Category> categoryDao;

	public RuleDefinitionDao<RuleDefinition> getDao() {
		return dao;
	}

	public int updateRuleStatusByPrimaryKey(RuleDefinition ruleDefinition) {

		return dao.updateByPrimaryKeySelective(ruleDefinition);

	}

	public List<RuleDefinition> queryByBusinessType(String businessType) {
		return dao.queryByBusinessType(businessType);
	}

	public Map<String, List<RuleDefinition>> buildRuleMapping(String businessType) {
		List<RuleDefinition> rules = queryByBusinessType(businessType);
		Map<String, List<RuleDefinition>> map = new HashMap<String, List<RuleDefinition>>();
		for (RuleDefinition rule : rules) {
			String attr1 = rule.getAttr1();
			if (!map.containsKey(attr1)) {
				map.put(attr1, new ArrayList<RuleDefinition>());
			}
			List<RuleDefinition> bizRules = map.get(attr1);
			bizRules.add(rule);
		}
		return map;
	}

//	public Map<String, List<RuleDefinition>> buildRuleMappingByRuleDefinition(List<RuleDefinition> ruleDefinitionList) {		
//		Map<String, List<RuleDefinition>> map = new HashMap<String, List<RuleDefinition>>();
//		for (RuleDefinition rule : ruleDefinitionList) {
//			String attr1 = rule.getAttr1();
//			if (!map.containsKey(attr1)) {
//				map.put(attr1, new ArrayList<RuleDefinition>());
//			}
//			List<RuleDefinition> bizRules = map.get(attr1);
//			bizRules.add(rule);
//		}
//		return map;
//	}

	public List<RuleDefinition> queryByBusinessTypeAndId(Map<String, Object> map) {
		return dao.queryByBusinessTypeAndId(map);
	}

	/**
	* 添加规则定义信息
	* @param entity
	* @param attachmentIds
	* @param dataFile
	* @param user
	* @return
	* 0、代表提交失败
	* 1、提交成功
	* 2、代表附件上传失败  不再进行入库操纵
	*/
	@SuppressWarnings("unchecked")
	public int saveRuleDefinition(RuleDefinition entity, String attachmentIds, MultipartFile dataFile, User user) {
		String code = SequenceUtil.getSequenceCode(Constants.SHEET_RULE_DEFINITION);
		entity.setCode(code);
		entity.setCTime(new Date());
		int result = 0;
		try {
			result = dao.insert( entity);
			//保存规则定义附件
			this.saveAttachment(code, dataFile, user);
		}
		catch (Exception e) {
			//2 代表附件上传失败  不再进行入库操纵
			result = 2;
			return result;
		}

		return result;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * 3、代表规则定义修改成功
	 */
	public int updateRuleDefinition(RuleDefinition entity, MultipartFile dataFile, User user) throws Exception {
		int result = 0;
		/** 此段代码为以前新增的规则定义未配置code的，生成code */
		if ("".equals(entity.getCode()) || entity.getCode() == null) {
			String code = SequenceUtil.getSequenceCode(Constants.SHEET_RULE_DEFINITION);
			entity.setCode(code);
		}
		if (dao.updateByPrimaryKeySelective(entity) > 0) {
			result = 3;
		}
		//保存规则定义附件
		saveAttachment(entity.getCode(), dataFile, user);
		return result;
	}

	/**
	 * 保存规则定义附件
	 * @param code
	 * @param dataFile
	 * @param user
	 */
	private void saveAttachment(String code, MultipartFile dataFile, User user) throws Exception {

		if (dataFile != null && dataFile.getSize() > 0) {
			// 附件信息
			SysAttachmentPage sp = new SysAttachmentPage();
			List<SysAttachment> attachmentList = sysAttachmentService.queryByList(sp);
			if (attachmentList != null && attachmentList.size() > 0) {//存在，先删除以前的，
				sysAttachmentService.deleteByPrimaryKey(attachmentList.get(0).getId());
			}

			SysAttachment attachment = new SysAttachment();
			attachment.setDescription(dataFile.getName());
			attachment.setDataFile(dataFile);
			attachment.setType(0);
			sysAttachmentService.uploadAttachment(user, attachment);
		}
	}

	/** 
	 * 删除规则定义 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public int deleteRuleDefinition(String[] id) throws Exception {
		for (int i = 0; i < id.length; i++) {
			RuleDefinition entity = (RuleDefinition) dao.selectByPrimaryKey(id[i]);
			if (entity == null) {
				// 没有找到要删除的记录
				return 1;
			}
			else {
				// 附件信息
				SysAttachmentPage sp = new SysAttachmentPage();
				List<SysAttachment> attachmentList = sysAttachmentService.queryByList(sp);
				if (attachmentList != null && attachmentList.size() > 0) { //存在，先删除关联的，
					sysAttachmentService.deleteByPrimaryKey(attachmentList.get(0).getId());
				}
			}
			dao.deleteByPrimaryKey(id[i]);
		}
		return 0;
	}

	/**
	 * 结算主体数据导出，生成导出数据json
	 * @throws Exception 
	 */
	public Map<String, Object> createRuleDefinitionData(Object[] id) {

		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * id为空时，导出所有数据；否则导出选中数据
		 * 1.生成json数据
		 * 2.导出json文件
		 */
		List<RuleDefinition> list = new ArrayList<RuleDefinition>();
		if (id == null || id.length == 0) {
			RuleDefinitionPage page = new RuleDefinitionPage();
			page.getPager().setPageEnabled(false);
			list = (List<RuleDefinition>) dao.queryByList(page);
			if (list == null || list.isEmpty()) {
				map.put(Constants.LOGIC_FAILURE, "无待导出的规则定义信息数据！");
				return map;
			}
		}
		else {
			for (int i = 0; i < id.length; i++) {
				RuleDefinition ruleDefinition = (RuleDefinition) this.dao.selectByPrimaryKey(id[i]);
				if (ruleDefinition != null) {
					list.add(ruleDefinition);
				}
			}
		}
		this.setListValue(list); // 重设list
		try {
//			String str = JSONUtil.objToJsonFormat(list);
			map.put(Constants.LOGIC_SUCCESS, list);
		}
		catch (Exception e) {
			map.put(Constants.LOGIC_FAILURE, "导出规则定义生成json文件异常！");
			return map;
		}
		return map;
	}

	/**
	 * 构建导出数据list
	 * @param list
	 * @return
	 */
	private void setListValue(List<RuleDefinition> list) {

		List<RuleConfigDefinition> rcDefinitionList = null;
		for (RuleDefinition ruleDefinition : list) {
			RuleConfigDefinitionPage rcDefinitionPage = new RuleConfigDefinitionPage();
			rcDefinitionPage.getPager().setPageEnabled(false);
			rcDefinitionPage.setRuleDefId(ruleDefinition.getId());
			rcDefinitionList = ruleConfigDefinitionDao.queryByList(rcDefinitionPage);
			if (rcDefinitionList != null && !rcDefinitionList.isEmpty()) {
				ruleDefinition.setRcDefinitionList(rcDefinitionList);
			}
		}
	}

	/**
	 * 批处理导入数据
	 * @param str  json数据
	 * @param flag 是否强制覆盖(true 强制覆盖)
	 * @return
	 */
	public Map<String, String> ruleDefinitionData(String str, boolean flag) {

		Map<String, String> map = new HashMap<String, String>();
		List<RuleDefinition> list = null;
		try {
			// 解析json字符串
			list = (List<RuleDefinition>) JSONUtil.jsonToList(str, RuleDefinition.class);
		}
		catch (Exception e) {
			map.put(Constants.LOGIC_FAILURE, "解析json文件出错！");
			return map;
		}

		if (list == null || list.isEmpty()) {
			map.put(Constants.LOGIC_FAILURE, "json文件无需要导入的数据！");
			return map;
		}

		map = this.insertRuleDefinition(map, list, flag);

		return map;
	}

	/**
	 * 数据导入数据库业务处理
	 * @param map
	 * @param list
	 * @param flag
	 * @return
	 */
	public Map<String, String> insertRuleDefinition(Map<String, String> map, List<RuleDefinition> list, boolean flag) {
		Date d = new Date();
		for (RuleDefinition ruleDefinition : list) {
			RuleDefinitionPage p = new RuleDefinitionPage();
			p.setName(ruleDefinition.getName());
			p.setRuleClassName(ruleDefinition.getRuleClassName());
			List<RuleDefinition> ruleDefinitionList = (List<RuleDefinition>) this.dao.queryByList(p);
			if (ruleDefinitionList != null && !ruleDefinitionList.isEmpty()) {
				if (ruleDefinitionList.size() > 1) {
					map.put(Constants.LOGIC_FAILURE, "数据库中存在重复的规则定义【" + ruleDefinitionList.get(0).getName() + "】");
					return map;
				}
				if (!flag) {
					continue;
				}
				/**
				 *  强制覆盖前，清空原有数据
				 */
				map = this.clearRuleDefinition(ruleDefinitionList.get(0));
				if (map.containsKey(Constants.LOGIC_FAILURE)) {
					return map;
				}
			}
			ruleDefinition.setId(null);
			ruleDefinition.setCTime(d);
			ruleDefinition.setCode(SequenceUtil.getSequenceCode(Constants.SHEET_RULE_DEFINITION));
			this.dao.insert(ruleDefinition);

			List<RuleConfigDefinition> rcDefinitionList = ruleDefinition.getRcDefinitionList();
			if (rcDefinitionList != null && !rcDefinitionList.isEmpty()) {
				for (RuleConfigDefinition rcDefinition : rcDefinitionList) {
					rcDefinition.setId(null);
					rcDefinition.setCTime(d);
					rcDefinition.setRuleDefId(ruleDefinition.getId());
					this.ruleConfigDefinitionDao.insert(rcDefinition);
				}
			}
		}

		return map;
	}

	/**
	 * 递归删除规则定义及下属表单信息
	 * json数据强制导入时，使用
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	private Map<String, String> clearRuleDefinition(RuleDefinition ruleDefinition) {

		Map<String, String> map = new HashMap<String, String>();

		TemplateRulePage templateRulePage = new TemplateRulePage();
		templateRulePage.setRuleDefinitionId(ruleDefinition.getId());
		templateRulePage.setStatus(Constants.BASIS_STATUS_ENABLE);
		int templateRuleCount = templateRuleDao.queryByCount(templateRulePage);
		if (templateRuleCount > 0) {
			map.put(Constants.LOGIC_FAILURE, "规则定义【" + ruleDefinition.getName() + "】已被计算规则使用！");
			return map;
		}

		List<RuleConfigDefinition> rcDefinitionList = ruleDefinition.getRcDefinitionList();
		if (rcDefinitionList != null && !rcDefinitionList.isEmpty()) {
			for (RuleConfigDefinition rcDefinition : rcDefinitionList) {
				this.ruleConfigDefinitionDao.deleteByPrimaryKey(rcDefinition.getId());
			}
		}

		// 删除附件
		SysAttachmentPage sp = new SysAttachmentPage();
		List<SysAttachment> attachmentList;
		try {
			attachmentList = sysAttachmentService.queryByList(sp);
			if (attachmentList != null && attachmentList.size() > 0) { //存在，先删除关联的，
				sysAttachmentService.deleteByPrimaryKey(attachmentList.get(0).getId());
			}
		}
		catch (Exception e) {
			map.put(Constants.LOGIC_FAILURE, "规则定义【" + ruleDefinition.getName() + "】关联的附件删除失败！");
			return map;
		}

		this.dao.deleteByPrimaryKey(ruleDefinition.getId());
		return map;
	}

	/**
	 * @param businessType
	 * @param templateId
	 * @param attr1 处理文件类型
	 * @return
	 */
	public RuleDefinition queryByBusinessTypeTemplate(String businessType, Integer templateId) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		queryParamMap.put("businessType", businessType);
		queryParamMap.put("temp_id", templateId);
		RuleDefinition ruleDefinition = dao.queryByBusinessTypeTemplate(queryParamMap);
		return ruleDefinition;
	}
	
	/**
	 * @param businessType
	 * @param templateId
	 * @param attr1 处理文件类型
	 * @return
	 */
	public RuleDefinition queryByBusinessTypeTemplate(String businessType, Integer templateId, String attr1) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		queryParamMap.put("businessType", businessType);
		queryParamMap.put("temp_id", templateId);
		queryParamMap.put("attr1", attr1);
		RuleDefinition ruleDefinition = dao.queryByBusinessTypeTemplate(queryParamMap);
		return ruleDefinition;
	}
	
}
