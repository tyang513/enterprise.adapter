package com.td.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.dao.ApprovalTemplateManageDao;
import com.td.admin.entity.SysOaTemplate;
import com.td.admin.entity.SysOaTemplateTask;
import com.td.admin.mapper.SysOaTemplateMapper;
import com.td.admin.mapper.SysOaTemplateTaskMapper;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;



@Service("approvalTemplateManageService")
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ApprovalTemplateManageService {
	
	
	private static final Logger logger = Logger.getLogger(ApprovalTemplateManageService.class);
	
	@Autowired
	private ApprovalTemplateManageDao approvalTemplateManageDao;
	
	@Autowired
	private SysOaTemplateMapper oaOatemplateMapper;
	
	@Autowired
	private SysOaTemplateTaskMapper oaTemplatetaskMapper;
		
	
	public PageBean queryApprovalTemplate(SysOaTemplate record){
		PageBean pageBean = null;
		pageBean = approvalTemplateManageDao.queryApprovalTemplate(record);
		return pageBean;
	}
	
	
	public List<SysOaTemplateTask> queryApprovalChain(SysOaTemplateTask record){
		return approvalTemplateManageDao.queryApprovalChain(record);
	}
	
	
	public Body saveApprovalTemplate(SysOaTemplate record)throws Exception{
		Body body = null;
		body = approvalTemplateManageDao.saveApprovalTemplate(record);
		return body;
	}
	
	

	public Body updateApprovalTemplate(SysOaTemplate record)throws Exception{
		Body body = null;
		body = approvalTemplateManageDao.updateApprovalTemplate(record);
		return body;
	}
	
	public Body deleteApprovalTemplate(SysOaTemplate record)throws Exception{
		Body body = null;
		body = approvalTemplateManageDao.deleteApprovalTemplate(record);
		return body;
	}
	
	public SysOaTemplate findByCode(String code) {
		SysOaTemplate oatemplate = approvalTemplateManageDao.selectBycode(code);
		return oatemplate;
	}
	
	/**
	 * 审批链
	 * @param userUmId
	 * @param templateCode
	 * @return
	 */
	public Map<String, Object> getApproveChain(String userUmId, String templateCode) {
		Map<String, Object> chainMap = new HashMap<String, Object>();
		
		List<SysOaTemplateTask> oaTemplateTaskList = new ArrayList<SysOaTemplateTask>();
		//获取审批模板
		SysOaTemplate oaTemplate = oaOatemplateMapper.selectMasterSlaveByCode(templateCode);
		
		if(oaTemplate != null && oaTemplate.getOaTemplatetasks() != null) {
			oaTemplateTaskList = oaTemplate.getOaTemplatetasks();
		}
		//从销售自己的审批模版中获取
		
		chainMap.put(templateCode, oaTemplateTaskList);
		return chainMap;
	}
}
