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
import com.td.proxy.entity.task.TaskResponseFileProcessJobInput;
import com.td.proxy.process.ITaskProcess;
import com.td.proxy.service.admin.RuleDefinitionService;
import com.td.proxy.service.admin.TemplateService;
import com.td.proxy.service.task.TaskInfoService;
import com.td.proxy.service.task.TaskResponseFileProcessJobInputService;

/**
 * 响应每天运行一次
 * @author yangtao
 */
public class TaskResponseFileProcessCronTriggerJob implements Job, StatefulJob{

	/**
	 * 日志
	 */
	public Logger logger = LoggerFactory.getLogger(TaskResponseFileProcessCronTriggerJob.class);
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		long startTime = System.currentTimeMillis();
		logger.info("响应文件处理开始");
		TaskResponseFileProcessJobInputService jobInputService = ApplicationContextManager
				.getBean(TaskResponseFileProcessJobInputService.class);
		List<TaskResponseFileProcessJobInput> jobInputList = jobInputService.findReady2RunJobInput(ProxyConstant.JOB_TRIGGER_TYPE_CRON);
		TaskInfoService taskInfoService = ApplicationContextManager.getBean(TaskInfoService.class);
		TemplateService templateService = ApplicationContextManager.getBean(TemplateService.class);
		RuleDefinitionService ruleDefinitionService = ApplicationContextManager.getBean(RuleDefinitionService.class);
		TaskInfo taskInfo = null;
		for (TaskResponseFileProcessJobInput responseJobInput : jobInputList){
			try {
//				responseJobInput = jobInputService.selectByPrimaryKey(responseJobInput.getId());
//				if (Constant.JOB_INPUT_RUNNING.equals(responseJobInput.getStatus()) || Constant.JOB_INPUT_PROCESSED.equals(responseJobInput.getStatus())){
//					continue;
//				}
				// 更新JobInput正在处理
//				jobInputService.updateRspFileProcJobInputStatusById(responseJobInput.getId(), Constant.JOB_INPUT_RUNNING);
				Map<String, Object> taskContext = new HashMap<String, Object>();
				taskInfo = taskInfoService.selectByTaskCode(responseJobInput.getTaskCode());
				if (taskInfo != null){
					taskInfoService.updateTaskInfoStatus(taskInfo.getId(), ProxyConstant.TASK_INFO_RUNNING); // 正在处理
				}
				Template template = templateService.findTemplateByPartnerId(taskInfo.getPartnerId());
				RuleDefinition ruleDefinition = ruleDefinitionService.queryByBusinessTypeTemplate(Constant.RESPONSE_FILE_PROCESS, template.getId(), taskInfo.getType());
				ITaskProcess process = (ITaskProcess) Class.forName(ruleDefinition.getRuleClassName()).newInstance();
				taskContext.put(ProxyConstant.TASK_CONTEXT_KEY_JOB_INPUT, responseJobInput);
				taskContext.put(ProxyConstant.TASK_CONTEXT_KEY_TEMPLATE, template);
				taskContext.put(ProxyConstant.TASK_CONTEXT_KEY_RULE_DEFINITION, ruleDefinition);
				taskContext.put(ProxyConstant.TASK_CONTEXT_KEY_TASK_INFO, taskInfo);
				process.execute(taskContext);
				// 更新JobInput处理完成
				jobInputService.updateRspFileProcJobInputStatusById(responseJobInput.getId(), Constant.JOB_INPUT_UNTREATED);
				if (taskInfo != null){
					taskInfoService.updateTaskInfoStatus(taskInfo.getId(), ProxyConstant.TASK_INFO_PROCESSED); // 处理完成
				}
			} catch (Exception e) {
				logger.error("响应文件处理出错", e);
				// 更新JobInput处理出现异常
				ExceptionUtil.createJobErrorInfo(this.getClass().getName(), "请求文件处理异常" + ExceptionUtil.getStackTrace(e), responseJobInput.getId());
				jobInputService.updateRspFileProcJobInputStatusAndExcptionById(responseJobInput.getId(), Constant.JOB_INPUT_EXCEPTION, e);
				if (taskInfo != null){
					taskInfoService.updateTaskInfoStatus(taskInfo.getId(), ProxyConstant.TASK_INFO_EXCEPTION); // 异常
				}
			}
		}
		long endTime = System.currentTimeMillis();
		logger.info("响应文件处理完成,供耗时" + ((endTime - startTime) / 1000) + "秒");
	}

}
