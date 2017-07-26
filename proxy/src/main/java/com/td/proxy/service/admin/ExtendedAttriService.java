package com.td.proxy.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.proxy.dao.admin.ExtendedAttriDao;
import com.td.proxy.entity.admin.ExtendedAttri;

/**
 * 
 * <br>
 * <b>功能：</b>ExtendedAttriService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("extendedAttriService")
public class ExtendedAttriService extends BaseService<ExtendedAttri> {
	
	public final static Logger logger = Logger.getLogger(ExtendedAttriService.class);

	@Autowired
	private ExtendedAttriDao dao;

	public ExtendedAttriDao getDao() {
		return dao;
	}

	public ExtendedAttri findByTemplateId(Integer templateId, String extendCode){
		return dao.selectBytemplateId(templateId,extendCode);
	}
	
	public List<ExtendedAttri> queryByTemplateId(Integer templateId){
		return dao.queryBytemplateId(templateId);
	}

	/**
	 * 
	 * @param templateId
	 * @return
	 */
	public Map<String, ExtendedAttri> findExtendedAttriMapByTemplateId(Integer templateId){
		List<ExtendedAttri> queryResultList =  (List<ExtendedAttri>) dao.queryBytemplateId(templateId);
		Map<String, ExtendedAttri> returnMap = new HashMap<String, ExtendedAttri>();
		for (ExtendedAttri attr : queryResultList){
			returnMap.put(attr.getExtendCode(), attr);
		}
		return returnMap;
	}
}
