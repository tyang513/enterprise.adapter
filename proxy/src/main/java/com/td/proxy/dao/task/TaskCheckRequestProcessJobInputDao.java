package com.td.proxy.dao.task;

import java.util.List;

import com.talkingdata.base.dao.BaseDao;
import com.td.proxy.entity.task.TaskCheckRequestProcessJobInput;

/**
 * 
 * <br>
 * <b>功能：</b>TaskCheckRequestProcessJobInputDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface TaskCheckRequestProcessJobInputDao<T> extends BaseDao<T> {
	
	/**
	 * 查询所有jobInput
	 * @return
	 */
	public List<TaskCheckRequestProcessJobInput> queryAll();
	
}
