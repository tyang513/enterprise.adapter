package com.td.admin.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.admin.entity.SysCacheInfo;
import com.td.admin.mapper.SysCacheInfoMapper;


@Service
public class CacheDao {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheDao.class);
	
	
	
	@Autowired
	private SysCacheInfoMapper sysCacheinfoMapper; 
	
	
	
	/**
	 * @description 查询缓存信息
	 * @return
	 */
	public List<SysCacheInfo> queryCache(SysCacheInfo record){
		logger.info("查询缓存");
		//List<FinJobConfig> result=finJobConfigMapper。;
		List<SysCacheInfo> result=sysCacheinfoMapper.queryCache(record);
		return result;
	}
	
	/**
	 * @description 查询缓存记录数
	 * @param record
	 * @return
	 */
	public Long queryCacheTotalCount(SysCacheInfo record){
		logger.info("查询缓存记录数");	
		Long result = sysCacheinfoMapper.getPagingCount(record);
		return result;
	}
	
}
