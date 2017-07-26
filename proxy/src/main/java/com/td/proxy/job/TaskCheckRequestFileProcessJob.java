package com.td.proxy.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;

import com.td.admin.constant.Constant;
import com.td.common.exception.ExceptionUtil;
import com.td.common.util.ApplicationContextManager;
import com.td.proxy.ProxyConstant;
import com.td.proxy.entity.admin.RuleDefinition;
import com.td.proxy.entity.admin.Template;
import com.td.proxy.entity.task.TaskCheckRequestProcessJobInput;
import com.td.proxy.process.ITaskCheckRequestFileProcess;
import com.td.proxy.service.admin.RuleDefinitionService;
import com.td.proxy.service.admin.TemplateService;
import com.td.proxy.service.task.TaskCheckRequestProcessJobInputService;

/**
 * 检查合作伙伴目录是否有新上传的文件
 * @author yangtao
 */
public class TaskCheckRequestFileProcessJob implements Job, StatefulJob {

	/**
	 * 日志
	 */
	public Logger logger = org.slf4j.LoggerFactory.getLogger(TaskCheckRequestFileProcessJob.class);
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		long startTime = System.currentTimeMillis();
		logger.info("检查是否有新上传的文件开始");
		try{
			TaskCheckRequestProcessJobInputService checkRequestProcessJobInputService = ApplicationContextManager
					.getBean(TaskCheckRequestProcessJobInputService.class);
			TemplateService templateService = ApplicationContextManager.getBean(TemplateService.class);
			RuleDefinitionService ruleDefinitionService = ApplicationContextManager.getBean(RuleDefinitionService.class);
			List<TaskCheckRequestProcessJobInput> checkRequestProcessJobInputList = checkRequestProcessJobInputService.findAll();
			for (TaskCheckRequestProcessJobInput jobInput : checkRequestProcessJobInputList) {
				try {
					// 获取合作伙伴的文件检查处理任务
					Map<String, Object> taskContext = new HashMap<String, Object>();
					taskContext.put(ProxyConstant.TASK_CONTEXT_KEY_JOB_INPUT, jobInput);
					Template template = templateService.findTemplateByPartnerId(jobInput.getPartnerId());
					RuleDefinition ruleDefinition = ruleDefinitionService.queryByBusinessTypeTemplate(Constant.CHECK_REQUEST_FILE_PROCESS, template.getId());
					ITaskCheckRequestFileProcess process = (ITaskCheckRequestFileProcess) Class.forName(ruleDefinition.getRuleClassName()).newInstance();
					taskContext.put(ProxyConstant.TASK_CONTEXT_KEY_TEMPLATE, template);
					taskContext.put(ProxyConstant.TASK_CONTEXT_KEY_RULE_DEFINITION, ruleDefinition);
					process.execute(taskContext);
				} catch (Exception e) {
					logger.error("jobInput请求文件出错", e);
					ExceptionUtil.createJobErrorInfo(this.getClass().getName(), "检查合作伙伴目录是否有新上传的文件异常" + ExceptionUtil.getStackTrace(e),
							jobInput.getId());
				}
			}
		}
		catch(Exception e){
			logger.error("检查请求文件出错", e);
			ExceptionUtil.createJobErrorInfo(this.getClass().getName(), "检查合作伙伴目录是否有新上传的文件异常" + ExceptionUtil.getStackTrace(e), -1);
		}
		long endTime = System.currentTimeMillis();
		logger.info("检查请求文件处理完成,供耗时" + ((endTime - startTime) / 1000) + "秒");
	}

}
