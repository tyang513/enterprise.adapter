package com.td.admin.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.admin.entity.SysBusinessSystem;
import com.td.admin.mapper.SysBusinessSystemMapper;


@Service
public class BusinessSystemDao {
	
	@Autowired
	private SysBusinessSystemMapper sysBusinessSystemMapper;

	public List<SysBusinessSystem> findAll() {
		return sysBusinessSystemMapper.findAll();
	}

	public int update(SysBusinessSystem businessSystem) {
		return sysBusinessSystemMapper.updateByPrimaryKeySelective(businessSystem);
	}
}
