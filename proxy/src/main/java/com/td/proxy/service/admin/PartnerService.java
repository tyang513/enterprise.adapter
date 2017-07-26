package com.td.proxy.service.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.proxy.dao.admin.PartnerDao;
import com.td.proxy.entity.admin.Partner;

/**
 * 
 * <br>
 * <b>功能：</b>PartnerService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("partnerService")
public class PartnerService extends BaseService<Partner> {
	
	public  final static Logger logger = Logger.getLogger(PartnerService.class);
	
	@Autowired
	private PartnerDao<Partner> dao;

	public PartnerDao<Partner> getDao() {
		return dao;
	}
	
	public List<Partner> findAllList() {
		return dao.findAllList();
	}
	
}
