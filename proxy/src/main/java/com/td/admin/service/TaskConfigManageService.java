package com.td.admin.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.dao.TaskConfigManageDao;
import com.td.admin.entity.SysProcessTaskConfig;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;

@Service("taskConfigManageService")
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class TaskConfigManageService {
	
	private static final Logger logger = Logger.getLogger(TaskConfigManageService.class);
	
	@Autowired
	private TaskConfigManageDao taskConfigManageDao;
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PageBean queryTaskConfig(SysProcessTaskConfig record){
		PageBean pageBean = null;
        pageBean = taskConfigManageDao.queryTaskConfig(record); 
		return pageBean;
	}

	public Body saveTaskConfig(SysProcessTaskConfig record) {
		Body body = null ;
		body = taskConfigManageDao.saveTaskConfig(record);
		return body;
	}

	public Body deleteTaskConfig(SysProcessTaskConfig record) {
		Body body = null ;
		body = taskConfigManageDao.deleteTaskConfig(record);
		return body;
	}

	public Body updateTaskConfig(SysProcessTaskConfig record) {
		Body body = null ;
		body = taskConfigManageDao.updateTaskConfig(record);
		return body;
	}
	

	
	
	
}
