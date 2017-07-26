package com.td.admin.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.dao.CacheDao;
import com.td.admin.entity.SysCacheInfo;
import com.td.common.bean.PageBean;

@Service("cacheService")
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class CacheService {
	
	private static final Logger logger = Logger.getLogger(CacheService.class);
	
	@Autowired
	private CacheDao cacheDao;
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PageBean queryCache(SysCacheInfo record){
		PageBean pageBean = null;
		Long totalCount = cacheDao.queryCacheTotalCount(record);
		pageBean = new PageBean(record.getPageNum(),record.getNumPerPage(),totalCount);
		record.setBeginIndex(pageBean.getFirstResult());
		List<SysCacheInfo> list = cacheDao.queryCache(record);
		Map extend = pageBean.getExtend();
		extend.put("list",list);
		return pageBean;
	}
}
