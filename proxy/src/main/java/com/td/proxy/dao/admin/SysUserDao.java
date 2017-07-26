package com.td.proxy.dao.admin;

import com.talkingdata.base.dao.BaseDao;
import com.td.proxy.entity.admin.SysUser;

/**
 * 
 * <br>
 * <b>功能：</b>SysUserDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface SysUserDao extends BaseDao<SysUser> {

	SysUser findUserByUserIdAndPasswod(SysUser user);
	
}
