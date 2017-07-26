package com.td.proxy.dao.admin;

import java.util.List;
import java.util.Map;

import com.talkingdata.base.dao.BaseDao;
import com.td.proxy.entity.admin.Template;
import com.td.proxy.page.admin.TemplatePage;

/**
 * 
 * <br>
 * <b>功能：</b>SettleTemplateDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface TemplateDao<T> extends BaseDao<T> {
	
	public List<Template> queryValidByList(TemplatePage page);
	
	public List<Template> findTemplateSubjectumIdAndStatus(Map param);
	
	public Template queryBySubjectumIdAndDate(Map param);
	
	/**
	 * 判断指定时间是否存在记录
	 * @param entity
	 * @return
	 */
	public int queryByEffectDateExpiryDateCount(Template entity);

	public int queryTemplateByCount(TemplatePage page);

	public List<Template> findTemplateByftpServerConfigId(Integer mediaSource);

	/**
	 * @param templateCode
	 * @return
	 */
	public Template findTemplateByTemplateCode(String templateCode);

	public List<Template> findTemplateAll();

	public Template findTemplateByPartnerId(Integer partnerId);

}
