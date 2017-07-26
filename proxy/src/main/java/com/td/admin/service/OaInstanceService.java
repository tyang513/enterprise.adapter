package com.td.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.entity.SysOaInstance;
import com.td.admin.entity.SysOaTask;
import com.td.admin.mapper.SysOaInstanceMapper;
import com.td.admin.mapper.SysOaTaskMapper;

/**
 * OA流程监控Controller
 * @author lianjie
 */
@Service
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class OaInstanceService {
	
	@Autowired
	private SysOaInstanceMapper oaOainstanceMapper;
	@Autowired
	private SysOaTaskMapper oaTaskMapper;
	
	public List<SysOaInstance> find(SysOaInstance oaInstance) {
		return oaOainstanceMapper.find(oaInstance);
	}

	public int findCount(SysOaInstance oaInstance) {
		return oaOainstanceMapper.findCount(oaInstance);
	}
	public List<SysOaInstance> queryOainstance(SysOaInstance oaInstance){
		return oaOainstanceMapper.queryOainstance(oaInstance);
	}
	public List<SysOaTask> queryOaTask(SysOaTask task){
		return oaTaskMapper.getOaTaskByInstanceId(task);
		//return null;
	}
}
