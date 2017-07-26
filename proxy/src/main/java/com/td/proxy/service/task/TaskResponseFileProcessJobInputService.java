package com.td.proxy.service.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.common.exception.ExceptionUtil;
import com.td.proxy.dao.task.TaskResponseFileProcessJobInputDao;
import com.td.proxy.entity.task.TaskResponseFileProcessJobInput;

/**
 * 
 * <br>
 * <b>功能：</b>TaskResponseFileProcessJobInputService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("taskResponseFileProcessJobInputService")
public class TaskResponseFileProcessJobInputService extends BaseService<TaskResponseFileProcessJobInput> {
	
	protected final static Logger logger = Logger.getLogger(TaskResponseFileProcessJobInputService.class);
	
	@Autowired
	private TaskResponseFileProcessJobInputDao dao;

	public TaskResponseFileProcessJobInputDao getDao() {
		return dao;
	}
	
	/**
	 * 根据id,更新input的状态
	 * @param id 主键
	 * @param status 状态
	 * @param e 异常
	 * @return Integer
	 */
	public Integer updateRspFileProcJobInputStatusAndExcptionById(Integer id, String status, Exception e){
		TaskResponseFileProcessJobInput input = new TaskResponseFileProcessJobInput();
		input.setId(id);
		input.setStatus(Integer.valueOf(status));
		if (e != null){
			input.setErrorInfo(ExceptionUtil.getStackTrace(e));
		}
		else {
			input.setErrorInfo("");
		}
		return dao.updateStatusByPrimaryKeySelective(input);
	}
	
	/**
	 * 根据id更新状态
	 * @param id 主键
	 * @param status 状态
	 * @return Integer
	 */
	public Integer updateRspFileProcJobInputStatusById(Integer id, String status){
		return updateRspFileProcJobInputStatusAndExcptionById(id, status, null);
	}

	
	public List<TaskResponseFileProcessJobInput> findReady2RunJobInput(String triggerType) {
		return dao.findReady2RunJobInput(triggerType);
	}

	public TaskResponseFileProcessJobInput selectByTaskCode(String taskCode) {
		return dao.selectByTaskCode(taskCode);
	}
}
