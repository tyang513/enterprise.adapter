package com.td.proxy.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.td.admin.constant.Constant;
import com.td.common.exception.ExceptionUtil;
import com.td.common.util.ApplicationContextManager;
import com.td.proxy.ProxyConstant;
import com.td.proxy.entity.admin.RuleDefinition;
import com.td.proxy.entity.admin.Template;
import com.td.proxy.entity.task.TaskInfo;
import com.td.proxy.entity.task.TaskRequestFileProcessJobInput;
import com.td.proxy.process.ITaskProcess;
import com.td.proxy.service.admin.RuleDefinitionService;
import com.td.proxy.service.admin.TemplateService;
import com.td.proxy.service.task.TaskInfoService;
import com.td.proxy.service.task.TaskRequestFileProcessJobInputService;

/**
 * 请求文件处理Task
 * @author yangtao
 */
public class TaskRequestFileProcessJob implements Job, StatefulJob {

	/**
	 * 日志
	 */
	public Logger logger = LoggerFactory.getLogger(TaskRequestFileProcessJob.class);
	
	/*
	 * (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		long startTime = System.currentTimeMillis();
		logger.info("请求文件处理开始");
		TaskRequestFileProcessJobInputService jobInputService = ApplicationContextManager.getBean(TaskRequestFileProcessJobInputService.class);
		List<TaskRequestFileProcessJobInput> jobInputList = jobInputService.findReady2RunJobInput();
		
		TaskInfoService taskInfoService = ApplicationContextManager.getBean(TaskInfoService.class);
		TemplateService templateService = ApplicationContextManager.getBean(TemplateService.class);
		RuleDefinitionService ruleDefinitionService = ApplicationContextManager.getBean(RuleDefinitionService.class);
		TaskInfo taskInfo = null;
		for (TaskRequestFileProcessJobInput requestJobInput : jobInputList){
			try {
				requestJobInput = jobInputService.selectByPrimaryKey(requestJobInput.getId());
				// 如果正在运行或者处理完成，不进行处理
				if (Constant.JOB_INPUT_RUNNING.equals(requestJobInput.getStatus()) || Constant.JOB_INPUT_PROCESSED.equals(requestJobInput.getStatus())){
					continue;
				}
				// 更新JobInput 正在处理
				jobInputService.updateRspFileProcJobInputStatusById(requestJobInput.getId(), Constant.JOB_INPUT_RUNNING);
				Map<String, Object> taskContext = new HashMap<String, Object>();
				taskInfo = taskInfoService.selectByTaskCode(requestJobInput.getTaskCode());
				if (taskInfo != null){
					taskInfoService.updateTaskInfoStatus(taskInfo.getId(), ProxyConstant.TASK_INFO_RUNNING); // 正在处理
				}
				Template template = templateService.findTemplateByPartnerId(taskInfo.getPartnerId());
				RuleDefinition ruleDefinition = ruleDefinitionService.queryByBusinessTypeTemplate(Constant.REQUEST_FILE_PROCESS, template.getId(), taskInfo.getType());
				ITaskProcess process = (ITaskProcess) Class.forName(ruleDefinition.getRuleClassName()).newInstance();
				taskContext.put(ProxyConstant.TASK_CONTEXT_KEY_JOB_INPUT, requestJobInput);
				taskContext.put(ProxyConstant.TASK_CONTEXT_KEY_TEMPLATE, template);
				taskContext.put(ProxyConstant.TASK_CONTEXT_KEY_RULE_DEFINITION, ruleDefinition);
				taskContext.put(ProxyConstant.TASK_CONTEXT_KEY_TASK_INFO, taskInfo);
				process.execute(taskContext);
				// 更新JobInput处理完成
				jobInputService.updateRspFileProcJobInputStatusById(requestJobInput.getId(), Constant.JOB_INPUT_PROCESSED);
			} catch (Exception e) {
				logger.error("请求文件处理出错", e);
				if (taskInfo != null){
					taskInfoService.updateTaskInfoStatus(taskInfo.getId(), ProxyConstant.TASK_INFO_EXCEPTION); // 处理异常
				}
				ExceptionUtil.createJobErrorInfo(this.getClass().getName(), "请求文件处理异常" + ExceptionUtil.getStackTrace(e), requestJobInput.getId());
				jobInputService.updateRspFileProcJobInputStatusAndExcptionById(requestJobInput.getId(), Constant.JOB_INPUT_EXCEPTION, e);
			}
		}
		long endTime = System.currentTimeMillis();
		logger.info("请求文件处理完成,供耗时" + ((endTime - startTime) / 1000) + "秒");
	}

}
