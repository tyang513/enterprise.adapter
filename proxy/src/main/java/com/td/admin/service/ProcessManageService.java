package com.td.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.dao.OaTaskDao;
import com.td.admin.dao.ProcessManageDao;
import com.td.admin.entity.SysOaInstance;
import com.td.admin.entity.SysOaTask;
import com.td.admin.entity.SysProcessConfig;
import com.td.admin.entity.SysProcessInstance;
import com.td.admin.entity.SysProcessTask;
import com.td.admin.entity.SysProcessTaskConfig;
import com.td.admin.mapper.SysOaInstanceMapper;
import com.td.admin.mapper.SysProcessConfigMapper;
import com.td.admin.mapper.SysProcessTaskConfigMapper;
import com.td.common.bean.PageBean;

@Service("processManageService")
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ProcessManageService {
	
	private static final Logger logger = Logger.getLogger(ProcessManageService.class);
	
	@Autowired
	private ProcessManageDao processManageDao;
	
	@Autowired
	private OaTaskDao oaTaskDao;
	@Autowired
	private SysOaInstanceMapper oaOainstanceMapper; 
	
	@Autowired
	private SysProcessTaskConfigMapper finTaskConfigMapper;
	
	@Autowired
	private SysProcessConfigMapper finProcessConfigMapper;
	
//	@Autowired
//	private FinSalesCancelSheetMapper finSalesCancelSheetMapper;	
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PageBean queryPI(SysProcessInstance record){
		PageBean pageBean = null;
		Long totalCount = processManageDao.queryProcessInstancesTotalCount(record);
		pageBean = new PageBean(record.getPageNum(),record.getNumPerPage(),totalCount);
		record.setBeginIndex(pageBean.getFirstResult());
		List<SysProcessInstance> list = processManageDao.queryProcessInstances(record);
		Map extend = pageBean.getExtend();
		extend.put("list",list);
		return pageBean;
	}
	
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public PageBean queryTask(SysProcessTask record){
		PageBean pageBean = null;
		Long totalCount = processManageDao.queryTaskTotalCount(record);
		pageBean = new PageBean(record.getPageNum(),record.getNumPerPage(),totalCount);
		record.setBeginIndex(pageBean.getFirstResult());
		List<SysProcessTask> list = processManageDao.queryTasks(record);
		Map extend = pageBean.getExtend();
		extend.put("list",list);
		return pageBean;
	}
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Map queryMyFinTask(SysProcessTask finTask,Long page,Long rows){
		Map<String,Object> result = new HashMap<String,Object>();
		Long total = processManageDao.queryMyFinTaskCount(finTask);
		Long index = (page-1)*rows;
		finTask.setBeginIndex(index);
		finTask.setNumPerPage(rows);
		List<Map<String,Object>> list = processManageDao.queryMyFinTask(finTask);
		result.put("total",total);
		result.put("rows",list);
		
		return result;
	}
	
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Map<String,Object> queryMyCompleteFinTask(String userId,Long page,Long rows, Map<String, Object> condition){
		Map<String,Object> result = new HashMap<String,Object>();
		Long total = processManageDao.queryMyCompleteFinTaskCount(userId, condition);
		Long index = (page-1)*rows;
		List<Map<String,Object>> list = processManageDao.queryMyCompleteFinTask(userId,index,rows, condition);
		result.put("total",total);
		result.put("rows",list);
		return result;
	}
	
	
	public SysProcessTask getFinTask(Long finTaskId){
		return processManageDao.getFinTask(finTaskId);
	}
	
	public SysProcessInstance getFinProcessInstance(Long finProcessInstanceId){
		return processManageDao.getFinProcessInstance(finProcessInstanceId);
	}
	
	public SysOaTask findOaTaskByFinTaskId(Long finTaskId){
	    return oaTaskDao.getOaTask(finTaskId);
	}
	
	public List<SysOaTask> approveHistory(Long finTaskId){
		SysOaTask oatask = oaTaskDao.getOaTask(finTaskId);
		return oaTaskDao.approveHistory(oatask.getInstanceid());
	}
	
//	public List<FinSalesOrderapprovechain> queryFinSalesOrderapprovechain(String sheetId,String sheetType){
//		return oaTaskDao.queryFinSalesOrderapprovechain(sheetId,sheetType);
//	}
	
	public SysOaInstance getOainstances(Long fatherFPI){
		Map<Object,Object> candidate = new HashMap<Object,Object>();
		candidate.put("fid",fatherFPI);
		return oaOainstanceMapper.getOainstance(candidate);
	}
	
//	public List<FinSalesOrderapprovechain> queryBaseApproveHistory(List<OaTask> list,String sheetId,String sheetType){
//		logger.info("查询基础历史审批链 sheetId = " + sheetId + " , sheetType = " + sheetType);
//		List<FinSalesOrderapprovechain> result = new ArrayList<FinSalesOrderapprovechain>();
//		if(list!=null&&list.size()>0){
//			Map<Object,Object> candidate = new HashMap<Object,Object>();
//			candidate.put("sheetId",sheetId);
//			candidate.put("sheetType",sheetType);
//			candidate.put("list",list);
//			result = finSalesOrderapprovechainMapper.queryBaseApproveHistory(candidate);
//		}
//		return result;
//	}
	
//	public List<FinSalesOrderapprovechain> selectOrderApproveChainByOrderId(String sheetId){
//	    return finSalesOrderapprovechainMapper.selectByOrderId(sheetId);
//	}
	
	public SysProcessConfig getFinProcessConfig(String processCode){
		logger.info("查询FinProcessConfig processCode = " + processCode);
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("processCode",processCode);
		return finProcessConfigMapper.getFinProcessConfig(condition);
	}
	
	public SysProcessTaskConfig getFinTaskConfig(Long processConfigId,String taskCode){
		logger.info("查询FinTaskConfig, processConfigId = " + processConfigId+",taskCode = " + taskCode);
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("processConfigId",processConfigId);
		condition.put("taskCode",taskCode);
		return finTaskConfigMapper.getFinTaskConfig(condition);
	}
	/**
	 * 查询订单对应的流程
	 * @param sheetId xiaopy
	 * @return
	 */
	public List<SysProcessInstance> selectByMap(Map<String, Object> sheetId) {
		SysProcessInstance fpi = new SysProcessInstance();
		fpi.setSheetid(sheetId.get("sheetId").toString());
		List<SysProcessInstance> list = processManageDao.queryProcessInstances(fpi);
		return list;
	}
	/**
	 * 给定流程号查询对应的任务 
	 * @param processid sheetId xiaopy
	 * @return
	 */
	public List<SysProcessTask> selectTask(SysProcessTask record) {
		List<SysProcessTask> list = processManageDao.selectTasks(record);
		
		return list;
	}


	public List<SysProcessConfig> queryProcessConf() {
		return finProcessConfigMapper.queryFinProcessConfig();
	}
	public List<SysProcessConfig> queryProcessConf(String name) {
		SysProcessConfig config = new SysProcessConfig();
		config.setName(name);
		return finProcessConfigMapper.queryFinProcessConfigByName(config);
	}
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Map<String, Object> queryMyValidateFinTask(SysProcessTask finTask,Long page,Long rows){
		Map<String,Object> result = new HashMap<String,Object>();
		Long total = processManageDao.queryMyVerifyingTaskCount(finTask);
		Long index = (page-1)*rows;
		finTask.setBeginIndex(index);
		finTask.setNumPerPage(rows);
		List<Map<String,Object>> list = processManageDao.queryMyVerifyingTask(finTask);
		result.put("total",total);
		result.put("rows",list);
		return result;
	}
	
}
