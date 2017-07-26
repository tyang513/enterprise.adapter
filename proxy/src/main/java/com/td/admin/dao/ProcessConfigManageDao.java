package com.td.admin.dao;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.admin.entity.SysProcessConfig;
import com.td.admin.mapper.SysProcessConfigMapper;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;


@Service
public class ProcessConfigManageDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessConfigManageDao.class);
	
	@Autowired
	private SysProcessConfigMapper finProcessConfigMapper; 

    public PageBean queryProcessConfig(SysProcessConfig record){
    	logger.debug("查询流程设置=====");
    	Long total = finProcessConfigMapper.queryTotalCount(record);
    	PageBean pageBean = new PageBean(record.getPageNum(), record.getNumPerPage(), total);
    	record.setBeginIndex(pageBean.getFirstResult());
    	logger.debug("起始查询位置===="+record.getBeginIndex()+"要查询的记录数"+record.getNumPerPage());
    	List<SysProcessConfig> list = finProcessConfigMapper.queryProcessConfig(record);
    	pageBean.getExtend().put("list", list);
    	return pageBean;
    	
    }

	public Body saveProcessConfig(SysProcessConfig record) {
		Body body  = new Body();
		logger.info("执行添加流程设置操作");
		record.setCtime(new Date());
		finProcessConfigMapper.insertSelective(record);
		body.setMessage("添加成功");
		body.success();
		return body;
	}
	public Body deleteProcessConfig(SysProcessConfig record) {
		Body body  = new Body();
		logger.info("执行删除流程设置操作");
		finProcessConfigMapper.deleteByPrimaryKey(record.getId());
		body.setMessage("删除成功");
		body.success();
		return body;
	}
	
	public Body updateProcessConfig(SysProcessConfig record) {
		Body body  = new Body();
		logger.info("执行更新流程设置操作");
		record.setMtime(new Date());
		finProcessConfigMapper.updateByPrimaryKeySelective(record);
		body.setMessage("更新成功");
		body.success();
		return body;
	}

	public List<SysProcessConfig> queryAllProcessConfig() {
		SysProcessConfig record = new SysProcessConfig();
		record.setBeginIndex((long)0);
		record.setNumPerPage((long)50);
		return  finProcessConfigMapper.queryProcessConfig(record);
	}

	public SysProcessConfig findByCode(String code) {
		return  finProcessConfigMapper.findByCode(code);
	}
}
