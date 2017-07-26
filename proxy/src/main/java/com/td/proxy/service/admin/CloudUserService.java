package com.td.proxy.service.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.proxy.dao.admin.CloudUserDao;
import com.td.proxy.entity.admin.CloudUser;

/**
 * 
 * <br>
 * <b>功能：</b>CloudUserService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("cloudUserService")
public class CloudUserService extends BaseService<CloudUser> {
	
	public final static Logger logger = Logger.getLogger(CloudUserService.class);
	
	@Autowired
	private CloudUserDao dao;

	public CloudUserDao getDao() {
		return dao;
	}

	public CloudUser getCloudUserByCode(String key) {
		return dao.getCloudUserByCode(key);
	}
}
