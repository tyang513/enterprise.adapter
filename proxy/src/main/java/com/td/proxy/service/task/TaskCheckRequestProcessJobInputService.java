package com.td.proxy.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.admin.constant.Constant;
import com.td.proxy.dao.task.TaskCheckRequestProcessJobInputDao;
import com.td.proxy.entity.admin.Partner;
import com.td.proxy.entity.task.TaskCheckRequestProcessJobInput;

/**
 * 
 * <br>
 * <b>功能：</b>TaskCheckRequestProcessJobInputService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("taskCheckRequestProcessJobInputService")
public class TaskCheckRequestProcessJobInputService extends BaseService<TaskCheckRequestProcessJobInput> {

	public final static Logger logger = Logger.getLogger(TaskCheckRequestProcessJobInputService.class);

	@Autowired
	private TaskCheckRequestProcessJobInputDao<TaskCheckRequestProcessJobInput> dao;

	public TaskCheckRequestProcessJobInputDao<TaskCheckRequestProcessJobInput> getDao() {
		return dao;
	}

	/**
	 * 获取所有的检查请求文件处理jobinput
	 * @return
	 */
	public List<TaskCheckRequestProcessJobInput> findAll() {
		return dao.queryAll();
	}

	/**
	 * @param jobInputList
	 * @return
	 */
	public Map<Integer, TaskCheckRequestProcessJobInput> jobInputList2Map(List<TaskCheckRequestProcessJobInput> jobInputList) {
		if (jobInputList == null || jobInputList.size() == 0){
			return new HashMap<Integer, TaskCheckRequestProcessJobInput>();
		}
		Map<Integer, TaskCheckRequestProcessJobInput> returnMap = new HashMap<Integer, TaskCheckRequestProcessJobInput>();
		for (TaskCheckRequestProcessJobInput jobInput : jobInputList){
			returnMap.put(jobInput.getPartnerId(), jobInput);
		}
		return returnMap;
	}
	
	/**
	 * 根据合作伙伴创建新的jobInput
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public Integer createTaskCheckRequestFileJobInput(Partner partner) throws Exception{
		TaskCheckRequestProcessJobInput jobInput = new TaskCheckRequestProcessJobInput();
		jobInput.setPartnerId(partner.getId());
		jobInput.setPartnerFullName(partner.getPartnerFullName());
		jobInput.setStatus(Constant.ENABLE);
		return insert( jobInput);
	}

	/**
	 * 根据id更新checkRequestFileJobInput的状态
	 * @param id
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Integer updateCheckRequestFileJobInputStatus(Integer id, Integer status) throws Exception{
		TaskCheckRequestProcessJobInput jobInput = new TaskCheckRequestProcessJobInput();
		jobInput.setId(id);
		jobInput.setStatus(status);
		return updateByPrimaryKey(jobInput);
	}
	
}
