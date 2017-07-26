package com.td.proxy.job;

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
import com.td.proxy.entity.admin.Partner;
import com.td.proxy.entity.admin.Template;
import com.td.proxy.entity.task.TaskCheckRequestProcessJobInput;
import com.td.proxy.service.admin.PartnerService;
import com.td.proxy.service.admin.TemplateService;
import com.td.proxy.service.task.TaskCheckRequestProcessJobInputService;

/**
 * 创建合作伙伴检查请求文件处理任务
 * @author yangtao
 */
public class CreatePartnerTaskCheckRequestFileProcessJob implements Job, StatefulJob {

	public static Logger logger = LoggerFactory.getLogger(CreatePartnerTaskCheckRequestFileProcessJob.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			logger.info("启动：自动创建合作伙伴检查请求文件任务处理开始。");
			// 获取所有合作伙伴
			PartnerService partnerService = ApplicationContextManager.getBean(PartnerService.class);
			TaskCheckRequestProcessJobInputService checkRequestJobInputService = ApplicationContextManager
					.getBean(TaskCheckRequestProcessJobInputService.class);
			List<Partner> partnerList = partnerService.findAllList();
			List<TaskCheckRequestProcessJobInput> checkRequestProcessJobInputList = checkRequestJobInputService.findAll();
			Map<Integer, TaskCheckRequestProcessJobInput> partnerJobInputMap = checkRequestJobInputService
					.jobInputList2Map(checkRequestProcessJobInputList);
			TemplateService templateService = ApplicationContextManager.getBean(TemplateService.class);
			List<Template> templateList = templateService.findTemplateAll();
			Map<Integer, Template> templateMap = templateService.templateAll4Map(templateList);
			for (Partner partner : partnerList) {
				TaskCheckRequestProcessJobInput jobInput = partnerJobInputMap.get(partner.getId());
				if (jobInput == null) { // 如果jobInput为空，则创建检查job
					checkRequestJobInputService.createTaskCheckRequestFileJobInput(partner);
				} else { // 检查模板是否禁用，如果模板禁用则同时禁用checkRequestJobInput
					Template template = templateMap.get(partner.getId());
					if (Constant.FORBIDDEN.equals(template.getStatus()) && template.getStatus().equals(Constant.ENABLE)) {
						checkRequestJobInputService.updateCheckRequestFileJobInputStatus(jobInput.getId(), Constant.FORBIDDEN);
					} else if (Constant.ENABLE.equals(template.getStatus()) && template.getStatus().equals(Constant.FORBIDDEN)) {
						checkRequestJobInputService.updateCheckRequestFileJobInputStatus(jobInput.getId(), Constant.ENABLE);
					}
				}
			}
			logger.info("结束：自动创建合作伙伴检查请求文件任务处理完成。");
		} catch (Exception e) {
			logger.error("创建合作伙伴检查请求文件处理任务异常", e);
			ExceptionUtil.createJobErrorInfo(this.getClass().getName(), "自动创建合作伙伴检查请求文件任务处理开始异常" + ExceptionUtil.getStackTrace(e), -1);
		}
	}

}
