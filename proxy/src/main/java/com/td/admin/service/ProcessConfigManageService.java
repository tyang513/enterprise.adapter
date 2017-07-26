package com.td.admin.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.dao.ProcessConfigManageDao;
import com.td.admin.entity.SysProcessConfig;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;

@Service("processConfigManageService")
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ProcessConfigManageService {
	
	private static final Logger logger = Logger.getLogger(ProcessConfigManageService.class);
	
	@Autowired
	private ProcessConfigManageDao processConfigManageDao;
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PageBean queryProcessConfig(SysProcessConfig record){
		PageBean pageBean = null;
        pageBean = processConfigManageDao.queryProcessConfig(record); 
		return pageBean;
	}

	public Body saveProcessConfig(SysProcessConfig record) {
		Body  body = null ;
		body = processConfigManageDao.saveProcessConfig(record);
		return body;
	}

	public Body deleteProcessConfig(SysProcessConfig record) {
		Body  body = null ;
		body = processConfigManageDao.deleteProcessConfig(record);
		return body;
	}

	public Body updateProcessConfig(SysProcessConfig record) {
		Body  body = null ;
		body = processConfigManageDao.updateProcessConfig(record);
		return body;
	}

	public List<SysProcessConfig> queryAllProcessConfig() {
		return processConfigManageDao.queryAllProcessConfig();
	}

	public SysProcessConfig findByCode(String code) {
		return processConfigManageDao.findByCode(code);
	}
}
