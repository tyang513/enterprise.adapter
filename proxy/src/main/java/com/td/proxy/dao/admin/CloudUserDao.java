package com.td.proxy.dao.admin;

import com.talkingdata.base.dao.BaseDao;
import com.td.proxy.entity.admin.CloudUser;

/**
 * 
 * <br>
 * <b>功能：</b>CloudUserDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface CloudUserDao extends BaseDao<CloudUser> {

	CloudUser getCloudUserByCode(String key);
	
}
