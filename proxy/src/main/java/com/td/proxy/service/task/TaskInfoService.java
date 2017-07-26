package com.td.proxy.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.admin.constant.CacheConstant;
import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysParam;
import com.td.common.constant.CommonConstant;
import com.td.common.util.ProxyFileUtil;
import com.td.common.util.SequenceUtil;
import com.td.proxy.ProxyConstant;
import com.td.proxy.dao.task.TaskInfoDao;
import com.td.proxy.entity.admin.Partner;
import com.td.proxy.entity.task.TaskCheckRequestProcessJobInput;
import com.td.proxy.entity.task.TaskFile;
import com.td.proxy.entity.task.TaskInfo;
import com.td.proxy.entity.task.TaskRequestFileProcessJobInput;
import com.td.proxy.entity.task.TaskResponseFileProcessJobInput;
import com.td.proxy.service.admin.PartnerService;

/**
 * 
 * <br>
 * <b>功能：</b>TaskInfoService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("taskInfoService")
public class TaskInfoService extends BaseService<TaskInfo> {
	
	public final static Logger logger = Logger.getLogger(TaskInfoService.class);
	
	@Autowired
	private TaskInfoDao<TaskInfo> dao;
	
	@Autowired
	private TaskRequestFileProcessJobInputService taskRequestFileProcessJobInputService;
	
	@Autowired
	private TaskResponseFileProcessJobInputService taskResponseFileProcessJobInputService;
	
	@Autowired
	private TaskLogService taskLogService;
	
	@Autowired
	private PartnerService partnerService;
	
	public TaskInfoDao<TaskInfo> getDao() {
		return dao;
	}
	
	public TaskInfo selectByTaskCode(String taskCode){
		return dao.selectByTaskCode(taskCode);
	}
	
	public void createTaskInfoAndRequestFileJobInput(Partner partner, TaskFile taskFile, TaskCheckRequestProcessJobInput checkJobInput) throws Exception{
		if (partner == null || taskFile == null){
			logger.info("参数不正确,无法创建任务信息或请求文件处理");
			return ;
		}
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setPartnerFullName(partner.getPartnerFullName());
		taskInfo.setPartnerId(partner.getId());
		taskInfo.setFileName(taskFile.getFileName());
		taskInfo.setFilePath(taskFile.getFilePath());
		taskInfo.setRefDmpId(ProxyFileUtil.getFileName(taskFile.getFileName()));
		taskInfo.setType(taskFile.getType());
		taskInfo.setTaskName(taskInfo.getType() + " ：" + taskFile.getFileName());
		String taskCode = SequenceUtil.getSequenceCode(ProxyConstant.PREFIX_TASK_CODE);
		taskInfo.setTaskCode(taskCode);
		taskInfo.setStatus(ProxyConstant.TASK_INFO_UNTREATED);
		
		if (ProxyConstant.TASK_INFO_TYPE_DSP.equals(taskFile.getType())) { // dsp文件名称进行特殊处理
			String fileName = ProxyFileUtil.getFileName(taskFile.getFileName());
			String[] s = fileName.split("_");
			taskInfo.setAttr2("dspcode");
			taskInfo.setAttr3(s[0]);
			taskInfo.setAttr4(s[1]);
			taskInfo.setRefDmpId(s[2]);
		}
		
		this.insert(taskInfo);
		TaskRequestFileProcessJobInput input = new TaskRequestFileProcessJobInput();
		input.setJobInputName("请求文件处理");
		input.setFileName(taskFile.getFileName());
		input.setFilePath(taskFile.getFilePath());
		input.setTaskCode(taskInfo.getTaskCode());
		input.setStatus(Integer.valueOf(com.td.admin.constant.Constant.JOB_INPUT_UNTREATED));
		SysParam sysParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, CommonConstant.SYSTEM_PARAM_JOB_MAX_RETRY);
		input.setRetry(0);
		input.setMaxRetry(sysParam == null ? 3 : Integer.valueOf(sysParam.getParamvalue()));
		taskRequestFileProcessJobInputService.insert(input);
		
		taskLogService.checkReqFileLog(partner, taskInfo, checkJobInput);// 创建日志
	}

	/**
	 * @param partner
	 * @param fileName
	 * @param filePath
	 * @return List<TaskInfo>
	 */
	public List<TaskInfo> findTaskInfoByFile(Partner partner, String fileName, String filePath) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("partnerId", partner.getId());
		paramMap.put("fileName", fileName);
		paramMap.put("filePath", filePath);
		List<TaskInfo> taskInfoList = dao.findTaskInfoByMap(paramMap);
		return taskInfoList;
	}

	/**
	 * 根据任务号创建请求文件响应的处理
	 * @param taskCode
	 * @throws Exception 
	 */
	public void createResponseFileProcess(String taskCode) throws Exception {
		TaskInfo taskInfo = dao.selectByTaskCode(taskCode);
		Partner partner = partnerService.selectByPrimaryKey(taskInfo.getPartnerId());
		
		TaskResponseFileProcessJobInput input = new TaskResponseFileProcessJobInput();
		input.setJobInputName("结果文件处理");
		input.setTaskCode(taskCode);
		input.setStatus(Integer.valueOf(com.td.admin.constant.Constant.JOB_INPUT_UNTREATED));
		SysParam sysParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, CommonConstant.SYSTEM_PARAM_JOB_MAX_RETRY);
		input.setRetry(0);
		input.setMaxRetry(sysParam == null ? 3 : Integer.valueOf(sysParam.getParamvalue()));
		taskResponseFileProcessJobInputService.insert(input);
		
		
		String msg = "接收到云端的回调信息，回调参数TaskCode为" + taskCode + "，创建响应(结果)文件处理任务。";
		taskLogService.responseFileLog(partner, taskInfo, input, msg, ProxyConstant.TASK_LOG_TYPE_CALLBACK);// 创建日志
	}
	
	public void updateTaskInfoRefCloudId(Integer id, String refCloudId){
		TaskInfo updateTaskInfo = new TaskInfo();
		updateTaskInfo.setId(id);
		updateTaskInfo.setRefCloudId(refCloudId);
		dao.updateByPrimaryKeySelective(updateTaskInfo);
	}
	
	/**
	 * 更新状态
	 * @param id
	 * @param status
	 */
	public int updateTaskInfoStatus(Integer id, Integer status){
		TaskInfo updateTaskInfo = new TaskInfo();
		updateTaskInfo.setId(id);
		updateTaskInfo.setStatus(status);
		return dao.updateByPrimaryKeySelective(updateTaskInfo);
	}
	
}
