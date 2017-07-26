package com.td.admin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.talkingdata.casclient.User;
import com.talkingdata.common.UserInfoUtil;
import com.td.admin.entity.SysAuditLog;
import com.td.admin.mapper.SysAuditLogMapper;
import com.td.common.constant.CommonConstant;

/**
 * 审计日志Controller
 * @author lianjie 2013-09-02
 */
@Service
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class AuditLogService {
	
	@Autowired
	private SysAuditLogMapper sysAuditLogMapper;

	public List<SysAuditLog> find(SysAuditLog auditLog) {
		return sysAuditLogMapper.find(auditLog);
	}

	public int findCount(SysAuditLog auditLog) {
		return sysAuditLogMapper.findCount(auditLog);
	}
	
	public int addAuditLog(SysAuditLog auditLog) {
		auditLog.setCreatetime(new Date());
		return sysAuditLogMapper.insertSelective(auditLog);
	}
	
	/**
	 * 新增审计信息
	 * @param systemcode 系统代码
	 * @param operationtype 操作类型
	 * @param targettype 目标类型
	 * @param targetId 目标id
	 * @param result 是否成功
	 * @param description 审计信息描述
	 * @return
	 */
	public int insertAuditLong(String systemcode, String operationtype, String targettype, String targetId, boolean result, String description) {
		User user = UserInfoUtil.getUser();
		SysAuditLog auditLog = new SysAuditLog();
		auditLog.setActorumid(user.getLoginName());
		auditLog.setActorname(user.getName());
		auditLog.setCreatetime(new Date());
		auditLog.setSystemcode(systemcode);
		auditLog.setOperationtype(operationtype);
		auditLog.setTargettype(targettype);
		auditLog.setTargetid(targetId);
		if(result) {
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
		} else {
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
		}
		
		auditLog.setDescription(description);
		
		return this.addAuditLog(auditLog);
	}
}
