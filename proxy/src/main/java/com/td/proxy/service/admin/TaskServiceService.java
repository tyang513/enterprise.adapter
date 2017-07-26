package com.td.proxy.service.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.proxy.dao.admin.TaskServiceDao;
import com.td.proxy.entity.admin.TaskService;

/**
 * 
 * <br>
 * <b>功能：</b>TaskServiceService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("taskServiceService")
public class TaskServiceService extends BaseService<TaskService> {
	
	public final static Logger logger = Logger.getLogger(TaskServiceService.class);
	
	@Autowired
	private TaskServiceDao dao;

	public TaskServiceDao getDao() {
		return dao;
	}

	/**
	 * @param serviceCode
	 * @return
	 */
	public Object findaskServiceByCode(String serviceCode) {
		return dao.findaskServiceByCode(serviceCode);
	}
}
