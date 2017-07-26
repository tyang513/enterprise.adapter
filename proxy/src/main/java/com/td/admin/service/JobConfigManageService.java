package com.td.admin.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.dao.JobConfigManageDao;
import com.td.admin.entity.SysJobConfig;
import com.td.admin.entity.SysJobErrorInfo;
import com.td.admin.entity.SysJobTempJobInput;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;

@Service("JobConfigService")
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class JobConfigManageService {
	
	private static final Logger logger = Logger.getLogger(JobConfigManageService.class);
	
	@Autowired
	private JobConfigManageDao jobConfigManageDao;
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PageBean queryJobConfig(SysJobConfig record){
		PageBean pageBean = null;
		Long totalCount = jobConfigManageDao.queryJobConfigTotalCount(record);
		pageBean = new PageBean(record.getPageNum(),record.getNumPerPage(),totalCount);
		record.setBeginIndex(pageBean.getFirstResult());
		List<SysJobConfig> list = jobConfigManageDao.queryJobConfig(record);
		Map extend = pageBean.getExtend();
		extend.put("list",list);
		return pageBean;
	}
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PageBean queryJobError(SysJobErrorInfo record){
		PageBean pageBean = null;
		Long totalCount = jobConfigManageDao.queryJobErrorInfoTotalCount(record);
		pageBean = new PageBean(record.getPageNum(),record.getNumPerPage(),totalCount);
		record.setBeginIndex(pageBean.getFirstResult());
		List<SysJobErrorInfo> list = jobConfigManageDao.queryJobErrorInfo(record);
		Map extend = pageBean.getExtend();
		extend.put("list",list);
		return pageBean;
	}
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PageBean queryJobTemp(SysJobTempJobInput record){
		PageBean pageBean = null;
		Long totalCount = jobConfigManageDao.queryJobTempInfoTotalCount(record);
		pageBean = new PageBean(record.getPageNum(),record.getNumPerPage(),totalCount);
		record.setBeginIndex(pageBean.getFirstResult());
		List<SysJobTempJobInput> list = jobConfigManageDao.queryJobTempInfo(record);
		Map extend = pageBean.getExtend();
		extend.put("list",list);
		return pageBean;
	}
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Body updateJobConfig(SysJobConfig record){
		Body body = null;
		body = jobConfigManageDao.updateJobConfig(record);
		return body;
	}
	
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Body updateJobConfigTemp(Long id){
		Body body = null;
		//Long a=Long.parseLong(id);
		logger.debug("JobConfigManageService"+id);
		body = jobConfigManageDao.updateJobConfigTemp(id);
		return body;
	}
	
	
	
}
