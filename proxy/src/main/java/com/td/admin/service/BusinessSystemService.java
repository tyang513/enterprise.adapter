package com.td.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.dao.BusinessSystemDao;
import com.td.admin.entity.SysBusinessSystem;

/**
 * 子系统参数Controller
 * @author lianjie
 */
@Service
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class BusinessSystemService {
	
	@Autowired
	private BusinessSystemDao businessSystemDao;

	public List<SysBusinessSystem> findAll() {
		return businessSystemDao.findAll();
	}

	public int update(SysBusinessSystem businessSystem) {
		return businessSystemDao.update(businessSystem);
	}
}
