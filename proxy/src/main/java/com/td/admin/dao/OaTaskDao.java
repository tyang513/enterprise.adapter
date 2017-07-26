package com.td.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.admin.entity.SysOaTask;
import com.td.admin.mapper.SysOaTaskMapper;
import com.td.common.bean.PageBean;


@Service
public class OaTaskDao {
	
	private static final Logger logger = LoggerFactory.getLogger(OaTaskDao.class);
	
	@Autowired
    private SysOaTaskMapper oaTaskMapper;
//
//	public  Body saveFinUser(FinUser record) {
//		Body body  = new Body();
//		logger.info("执行添加用户操作");
//		finUserMapper.insertSelective(record);
//		finUserMapper.addUserRole(record);
//		body.setMessage("添加成功");
//		body.success();
//		return body;
//	}
//
//	public Body deleteFinUser(FinUser record) {
//		Body body  = new Body();
//		logger.info("执行删除用户操作");
//		finUserMapper.deleteByPrimaryKey(record.getId());
//		finUserMapper.deleteUserRole(record);
//		body.setMessage("删除成功");
//		body.success();
//		return body;
//	}
//
//	public Body updateFinUser(FinUser record) {
//		Body body  = new Body();
//		logger.info("执行更新用户操作");
//		finUserMapper.updateByPrimaryKeySelective(record);
//		finUserMapper.updateUserRole(record);
//		body.setMessage("更新成功");
//		body.success();
//		return body;
//	}

	public PageBean queryOaTask(SysOaTask record) {
		logger.debug("查询审批任务=====");
    	Long total = oaTaskMapper.queryTotalCount(record);
    	PageBean pageBean = new PageBean(record.getPageNum(), record.getNumPerPage(), total);
    	record.setBeginIndex(pageBean.getFirstResult());
    	logger.debug("起始查询位置===="+record.getBeginIndex()+",要查询的记录数"+record.getNumPerPage());
    	List<SysOaTask> list = oaTaskMapper.queryOaTask(record);
    	pageBean.getExtend().put("list", list);
    	return pageBean;
	}
    
	public SysOaTask getOaTask(Long finTaskId){
		logger.info("查询Oatask finTaskId = " +finTaskId);
		Map<String,Object> condidate = new HashMap<String,Object>();
		condidate.put("taskId",finTaskId);
		return oaTaskMapper.getOaTask(condidate);
	}
	/**
	 * 查询审批历史
	 */
	public List<SysOaTask> approveHistory(Long instanceId){
		List<SysOaTask> list = null;
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("instanceId",instanceId);
		list = oaTaskMapper.approveHistory(condition);
		return list;
	}
}
