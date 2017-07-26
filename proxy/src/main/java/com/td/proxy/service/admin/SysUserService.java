package com.td.proxy.service.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.proxy.dao.admin.SysUserDao;
import com.td.proxy.entity.admin.SysUser;

/**
 * 
 * <br>
 * <b>功能：</b>SysUserService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("sysUserService")
public class SysUserService extends BaseService<SysUser> {
	private final static Logger logger = Logger.getLogger(SysUserService.class);
	
	@Autowired
	private SysUserDao dao;

	public SysUserDao getDao() {
		return dao;
	}

	public SysUser findUserByUserIdAndPasswod(String userId, String password) {
		SysUser user = new SysUser();
		user.setUserId(userId);
		user.setPassword(password);
		return dao.findUserByUserIdAndPasswod(user);
	}
}
