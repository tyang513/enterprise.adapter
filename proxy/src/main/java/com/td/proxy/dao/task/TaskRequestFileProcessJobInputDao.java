package com.td.proxy.dao.task;

import java.util.List;

import com.talkingdata.base.dao.BaseDao;
import com.td.proxy.entity.task.TaskRequestFileProcessJobInput;

/**
 * <br>
 * <b>功能：</b>TaskRequestFileProcessJobInputDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface TaskRequestFileProcessJobInputDao extends BaseDao<TaskRequestFileProcessJobInput> {

	/**
	 * 查找准备运行的任务
	 * @return List<TaskRequestFileProcessJobInput>
	 */
	List<TaskRequestFileProcessJobInput> findReady2RunJobInput();
	
	int updateStatusByPrimaryKeySelective(TaskRequestFileProcessJobInput input);
	
	TaskRequestFileProcessJobInput selectByTaskCode(String taskCode);
	
}
