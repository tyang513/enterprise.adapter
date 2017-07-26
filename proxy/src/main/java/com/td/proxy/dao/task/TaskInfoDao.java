package com.td.proxy.dao.task;

import java.util.List;
import java.util.Map;

import com.talkingdata.base.dao.BaseDao;
import com.td.proxy.entity.task.TaskInfo;

/**
 * 
 * <br>
 * <b>功能：</b>TaskInfoDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface TaskInfoDao<T> extends BaseDao<T> {

	TaskInfo selectByTaskCode(String taskCode);

	List<TaskInfo> findTaskInfoByMap(Map<String, Object> paramMap);
	
}
