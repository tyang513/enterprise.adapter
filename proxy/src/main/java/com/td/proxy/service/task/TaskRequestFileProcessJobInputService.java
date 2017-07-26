package com.td.proxy.service.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.common.exception.ExceptionUtil;
import com.td.proxy.dao.task.TaskRequestFileProcessJobInputDao;
import com.td.proxy.entity.task.TaskRequestFileProcessJobInput;

/**
 * 
 * <br>
 * <b>功能：</b>TaskRequestFileProcessJobInputService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("taskRequestFileProcessJobInputService")
public class TaskRequestFileProcessJobInputService extends BaseService<TaskRequestFileProcessJobInput> {
	
	protected final static Logger logger = Logger.getLogger(TaskRequestFileProcessJobInputService.class);
	
	@Autowired
	private TaskRequestFileProcessJobInputDao dao;

	public TaskRequestFileProcessJobInputDao getDao() {
		return dao;
	}

	public List<TaskRequestFileProcessJobInput> findReady2RunJobInput() {
		return dao.findReady2RunJobInput();
	}

	/**
	 * 根据id更新状态
	 * @param id 主键
	 * @param status 状态
	 * @return Integer
	 */
	public Integer updateRspFileProcJobInputStatusById(Integer id, String status) {
		return updateRspFileProcJobInputStatusAndExcptionById(id, status, null);
	}

	/**
	 * 根据id,更新input的状态
	 * @param id 主键
	 * @param status 状态
	 * @param e 异常
	 * @return Integer
	 */
	public Integer updateRspFileProcJobInputStatusAndExcptionById(Integer id, String status, Exception e) {
		TaskRequestFileProcessJobInput input = new TaskRequestFileProcessJobInput();
		input.setId(id);
		input.setStatus(Integer.valueOf(status));
		if (e != null){
			input.setErrorInfo(ExceptionUtil.getStackTrace(e));
		}
		else{
			input.setErrorInfo("");
		}
		return dao.updateStatusByPrimaryKeySelective(input);
	}
	
	public TaskRequestFileProcessJobInput selectByTaskCode(String taskCode){
		return dao.selectByTaskCode(taskCode);
	}
}
