package com.td.proxy.dao.task;

import java.util.List;

import com.talkingdata.base.dao.BaseDao;
import com.td.proxy.entity.task.TaskResponseFileProcessJobInput;

/**
 * 
 * <br>
 * <b>功能：</b>TaskResponseFileProcessJobInputDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface TaskResponseFileProcessJobInputDao extends BaseDao<TaskResponseFileProcessJobInput> {

	List<TaskResponseFileProcessJobInput> findReady2RunJobInput(String triggerType);
	
	int updateStatusByPrimaryKeySelective(TaskResponseFileProcessJobInput input);

	TaskResponseFileProcessJobInput selectByTaskCode(String taskCode);
}
