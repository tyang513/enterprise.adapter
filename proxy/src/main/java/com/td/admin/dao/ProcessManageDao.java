package com.td.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.td.admin.entity.SysProcessInstance;
import com.td.admin.entity.SysProcessTask;
import com.td.admin.mapper.SysProcessInstanceMapper;
import com.td.admin.mapper.SysProcessTaskMapper;

@Service
public class ProcessManageDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessManageDao.class);
	

	@Autowired
	private SysProcessInstanceMapper finProcessInstanceMapper; 
	
	@Autowired
	private SysProcessTaskMapper finTaskMapper;
	
	/**
	 * @description 查询流程实例（此实例记录在系统自己的表之中）
	 * @return
	 */
	public List<SysProcessInstance> queryProcessInstances(SysProcessInstance record){
		logger.info("查询流程实例");
		List<SysProcessInstance> result = finProcessInstanceMapper.queryProcessInstances(record);
		return result;
	}
	
	/**
	 * @description 查询流程记录数
	 * @param record
	 * @return
	 */
	public Long queryProcessInstancesTotalCount(SysProcessInstance record){
		logger.info("查询流程记录数");	
		Long result = finProcessInstanceMapper.queryProcessInstancesTotalCount(record);
		return result;
	}
	
	

	
	
	public Long queryTaskTotalCount(SysProcessTask record){
		logger.info("查询任务记录数");	
		Long result = finTaskMapper.queryTaskTotalCount(record);
		return result;
	}
	
	public List<SysProcessTask> queryTasks(SysProcessTask record){
		logger.info("查询任务实例");
		List<SysProcessTask> result = finTaskMapper.queryTasks(record);
		return result;
	}
	public List<SysProcessTask> selectTasks(SysProcessTask record){
		logger.info("查询任务实例");
		List<SysProcessTask> result = finTaskMapper.selectTasks(record);
		return result;
	}
	
	/**
	 * 查询我的任务
	 */
	public Long queryMyFinTaskCount(SysProcessTask finTask){
		Long total = null;
		total = finTaskMapper.queryMyFinTaskCount(finTask);
		return total;
	}
	
	/**
	 * @description 查询自己的任务
	 */
	public List<Map<String,Object>> queryMyFinTask(SysProcessTask finTask){
		logger.info("查询需要处理任务  userId = "+finTask.getAssignerumid());
		return finTaskMapper.queryMyFinTask(finTask);
	}
	
	/**
	 * 查询我以完成的任务记录
	 */
	public Long queryMyCompleteFinTaskCount(String userId, Map<String, Object> map){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("userId",userId.trim());
		if (map != null){
			condition.put("processName", map.get("processName"));
			condition.put("taskName", map.get("taskName"));
			condition.put("sheetId", map.get("sheetId"));
			condition.put("starterName", map.get("starterName"));
			condition.put("startTime", map.get("startTime"));
			condition.put("endTime", map.get("endTime"));
		}
		Long count = finTaskMapper.queryMyCompleteFinTaskCount(condition);
		return count;
	}
	
	/**
	 * 查询我以完成的任务
	 */
	public List<Map<String,Object>> queryMyCompleteFinTask(String userId,Long index,Long rows, Map<String, Object> map){
		List<Map<String,Object>> list = null;
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("userId",userId.trim());
		condition.put("beginIndex", index);
		condition.put("numPerPage",rows);
		if (map != null){
			condition.put("processName", map.get("processName"));
			condition.put("taskName", map.get("taskName"));
			condition.put("sheetId", map.get("sheetId"));
			condition.put("starterName", map.get("starterName"));
			condition.put("startTime", map.get("startTime"));
			condition.put("endTime", map.get("endTime"));
		}
		list = finTaskMapper.queryMyCompleteFinTask(condition);
		return list;
	}
	
	
	
	public SysProcessInstance getFinProcessInstance(Long id){
		logger.info("查询流程实例 id = " +id);
		return finProcessInstanceMapper.selectByPrimaryKey(id);
	}
	
	public SysProcessTask getFinTask(Long id){
		logger.info("查询任务实例 id = " +id);
		return finTaskMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 查询我的任务,校验中的任务
	 */
	public Long queryMyVerifyingTaskCount(SysProcessTask finTask){
		Long total = null;
		total = finTaskMapper.queryMyVerifyingTaskCount(finTask);
		return total;
	}
	
	/**
	 * @description 查询自己的任务,校验中的任务
	 */
	public List<Map<String,Object>> queryMyVerifyingTask(SysProcessTask finTask){
		logger.info("查询校验任务  userId = "+finTask.getAssignerumid());
		return finTaskMapper.queryMyVerifyingTask(finTask);
	}
}
