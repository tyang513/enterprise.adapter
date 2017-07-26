package com.td.common.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.common.dao.SysUserApproveChainStepDao;

/**
 * 
 * <br>
 * <b>功能：</b>SysUserApproveChainStepService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013, <br>
 */
@Service("sysUserApproveChainStepService")
public class SysUserApproveChainStepService<T> extends BaseService<T> {
	private final static Logger logger = Logger.getLogger(SysUserApproveChainStepService.class);
	
	@Autowired
	private SysUserApproveChainStepDao<T> dao;

	public SysUserApproveChainStepDao<T> getDao() {
		return dao;
	}
}
