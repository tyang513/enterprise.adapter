package com.td.proxy.service.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.common.exception.ExceptionUtil;
import com.td.proxy.ProxyConstant;
import com.td.proxy.dao.task.TaskLogDao;
import com.td.proxy.entity.admin.Partner;
import com.td.proxy.entity.task.TaskCheckRequestProcessJobInput;
import com.td.proxy.entity.task.TaskInfo;
import com.td.proxy.entity.task.TaskLog;
import com.td.proxy.entity.task.TaskRequestFileProcessJobInput;
import com.td.proxy.entity.task.TaskResponseFileProcessJobInput;

/**
 * 日志服务
 * <br>
 * <b>功能：</b>TaskLogService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("taskLogService")
public class TaskLogService extends BaseService<TaskLog> {
	
	protected final static Logger logger = Logger.getLogger(TaskLogService.class);
	
	@Autowired
	private TaskLogDao dao;

	public TaskLogDao getDao() {
		return dao;
	}
	
	
	/**
	 * 创建检查请求日志
	 * @param partner
	 * @param taskInfo
	 * @param checkReqFileJobInput
	 * @return
	 */
	public Integer checkReqFileLog(Partner partner, TaskInfo taskInfo, TaskCheckRequestProcessJobInput checkReqFileJobInput) {
		TaskLog log = new TaskLog();
		log.setTaskCode(taskInfo.getTaskCode());
		log.setTaskJobInputId(checkReqFileJobInput.getId());
		log.setTaskJobInputName("检查合作伙伴文件上传");
		log.setDescription("扫描到合作伙伴" + partner.getPartnerShortName() + "上传的" + taskInfo.getFileName() + "文件,文件路径为：" + taskInfo.getFilePath()
				+ "，创建请求文件处理任务。");
		log.setStartTime(new Date());
		log.setType(ProxyConstant.TASK_LOG_TYPE_FILE_SCAN);
		dao.insert(log);
		return log.getId();
	}
	
	/**
	 * 请求文件处理
	 * @param partner
	 * @param taskInfo
	 * @param jobInput
	 * @param msg
	 * @return
	 */
	public Integer requestFileLog(Partner partner, TaskInfo taskInfo, TaskRequestFileProcessJobInput jobInput, String msg, Integer type){
		TaskLog log = new TaskLog();
		log.setTaskCode(taskInfo.getTaskCode());
		log.setTaskJobInputId(jobInput.getId());
		log.setTaskJobInputName("请求文件处理");
		log.setDescription(msg);
		log.setStartTime(new Date());
		log.setType(type);
		dao.insert(log);
		return log.getId();
	}
	
	/**
	 * 响应文件处理
	 * @param partner
	 * @param taskInfo
	 * @param jobInput
	 * @param msg
	 * @return
	 */
	public Integer responseFileLog(Partner partner, TaskInfo taskInfo, TaskResponseFileProcessJobInput jobInput, String msg, Integer type){
		TaskLog log = new TaskLog();
		log.setTaskCode(taskInfo.getTaskCode());
		log.setTaskJobInputId(jobInput.getId());
		log.setTaskJobInputName("响应文件处理");
		log.setDescription(msg);
		log.setStartTime(new Date());
		log.setType(type);
		dao.insert(log);
		return log.getId();
	}
	
	/**
	 * 更新错误日志
	 * @param id
	 * @param e
	 * @return
	 */
	public int updateErrorLog(Integer id, Exception e){
		TaskLog log = new TaskLog();
		log.setId(id);
		log.setFinishTime(new Date());
		log.setErrorInfo(ExceptionUtil.getStackTrace(e));
		return dao.updateByPrimaryKeySelective(log);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public Integer updateFinishTimeById(Integer id, String msg){
		TaskLog log = new TaskLog();
		log.setId(id);
		log.setFinishTime(new Date());
		if (msg != null && !log.equals("")){
			log.setDescription(msg);
		}
		return dao.updateByPrimaryKeySelective(log);
	}
	
	public static void main(String[] args) {
	}
	
}
