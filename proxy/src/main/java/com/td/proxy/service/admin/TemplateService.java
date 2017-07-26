package com.td.proxy.service.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.admin.service.DictService;
import com.td.common.util.Constants;
import com.td.common.util.SequenceUtil;
import com.td.proxy.dao.admin.TemplateDao;
import com.td.proxy.entity.admin.Template;
import com.td.proxy.page.admin.TemplatePage;

/**
 * 
 * <br>
 * <b>功能：</b>SettleTemplateService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("templateService")
public class TemplateService extends BaseService<Template> {

	public final static Logger logger = Logger.getLogger(TemplateService.class);

	@Autowired
	private TemplateDao<Template> dao;

	@Autowired
	private DictService dictService;

	public TemplateDao<Template> getDao() {
		return dao;
	}

	public List<Template> queryValidByList(TemplatePage page) {
		// page.setStatus(Constants.SETTLE_BASIS_STATUS_DISABLE);
		// page.setOrder("DESC");
		// page.setSort("version");
		return dao.queryValidByList(page);
	}

	public void insertTemplate(Template template) {
		Date d = new Date(System.currentTimeMillis());
		String code = SequenceUtil.getSequenceCode(Constants.TEMPLATE_NUMBER);
		Integer version = SequenceUtil.getSubSequenceNum(code);
		template.setTemplateCode(code);
		template.setVersion(version);
		template.setCtime(d);
		dao.insert(template);
	}

	/**
	 * 判断指定时间是否存在记录
	 * @param entity
	 * @return
	 */
	public int queryByEffectDateExpiryDateCount(Template entity) {
		return dao.queryByEffectDateExpiryDateCount(entity);
	}

	public int queryTemplateByCount(TemplatePage page) throws Exception {
		return getDao().queryTemplateByCount(page);
	}

	public Template findTemplateByPartnerId(Integer partnerId){
		return getDao().findTemplateByPartnerId(partnerId);
	}
	
	public List<Template> findTemplateByftpServerConfigId(Integer ftpServerConfigId) {
		return dao.findTemplateByftpServerConfigId(ftpServerConfigId);
	}

	public Template findTemplateByTemplateCode(String templateCode) {
		return dao.findTemplateByTemplateCode(templateCode);
	}

	public List<Template> findTemplateAll() {
		return dao.findTemplateAll();
	}

	/**
	 * 将模板转换成合作伙伴id与模板的对应关系
	 * 
	 * @param templateList
	 * @return
	 */
	public Map<Integer, Template> templateAll4Map(List<Template> templateList) {
		if (templateList == null || templateList.size() == 0) {
			return new HashMap<Integer, Template>();
		}
		Map<Integer, Template> returnMap = new HashMap<Integer, Template>();
		for (Template template : templateList) {
			returnMap.put(template.getPartnerId(), template);
		}
		return returnMap;
	}

}
