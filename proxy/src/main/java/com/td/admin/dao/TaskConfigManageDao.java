package com.td.admin.dao;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.admin.entity.SysProcessConfig;
import com.td.admin.entity.SysProcessTaskConfig;
import com.td.admin.mapper.SysProcessTaskConfigMapper;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;


@Service
public class TaskConfigManageDao {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskConfigManageDao.class);
	
	@Autowired
	private SysProcessTaskConfigMapper finTaskConfigMapper; 

    public PageBean queryTaskConfig(SysProcessTaskConfig record){
    	logger.debug("查询任务设置=====");
    	Long total = finTaskConfigMapper.queryTotalCount(record);
    	PageBean pageBean = new PageBean(record.getPageNum(), record.getNumPerPage(), total);
    	record.setBeginIndex(pageBean.getFirstResult());
    	logger.debug("起始查询位置===="+record.getBeginIndex()+"要查询的记录数"+record.getNumPerPage());
    	List<SysProcessConfig> list = finTaskConfigMapper.queryTaskConfig(record);
    	pageBean.getExtend().put("list", list);
    	return pageBean;
    	
    }

	public Body saveTaskConfig(SysProcessTaskConfig record) {
	   Body body  = new Body();
	   logger.info("添加任务设置操作");
	   record.setCtime(new Date());
	   finTaskConfigMapper.insertSelective(record);
	   body.setMessage("添加成功");
	   body.success();
	   return body;
	}

	public Body deleteTaskConfig(SysProcessTaskConfig record) {
		Body body = new Body();
		logger.info("删除任务设置操作");
		finTaskConfigMapper.deleteByPrimaryKey(record.getId());
		body.setMessage("删除成功");
		body.success();
		return body;
	}

	public Body updateTaskConfig(SysProcessTaskConfig record) {
		Body body = new Body();
		logger.info("更新任务设置操作");
		record.setMtime(new Date());
//		System.out.println(record.getProcessconfigid() + record.getSystemcode());
		finTaskConfigMapper.updateByPrimaryKeySelective(record);
		body.setMessage("更新成功");
		body.success();
		return body;
	}
	
}
