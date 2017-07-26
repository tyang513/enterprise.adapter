package com.td.admin.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.dao.OaTaskDao;
import com.td.admin.entity.SysOaTask;
import com.td.common.bean.PageBean;

@Service("oaTaskService")
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class OaTaskService {
	
	private static final Logger logger = Logger.getLogger(OaTaskService.class);

	@Autowired
	private OaTaskDao oaTaskDao ;


	public PageBean queryOaTask(SysOaTask record) {
		PageBean pageBean = null ; 
		logger.info("OaTask查询");
		pageBean = oaTaskDao.queryOaTask(record);
		return pageBean;
	}
	

	
	
	
}
