package com.td.proxy.process;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.td.common.util.ApplicationContextManager;
import com.td.proxy.ProxyConstant;
import com.td.proxy.entity.admin.ExtendedAttri;
import com.td.proxy.entity.admin.Partner;
import com.td.proxy.entity.admin.RuleConfig;
import com.td.proxy.entity.admin.RuleDefinition;
import com.td.proxy.entity.admin.Template;
import com.td.proxy.entity.task.TaskCheckRequestProcessJobInput;
import com.td.proxy.entity.task.TaskFile;
import com.td.proxy.service.admin.ExtendedAttriService;
import com.td.proxy.service.admin.PartnerService;
import com.td.proxy.service.admin.RuleConfigService;
import com.td.proxy.service.task.TaskInfoService;
import com.td.proxy.service.task.TaskLogService;

/**
 * 检查请求文件是处理抽象类
 * 
 * @author yangtao
 */
public abstract class AbstractTaskCheckRequestFileProcess implements ITaskCheckRequestFileProcess {

	public final static Logger logger = Logger.getLogger(AbstractTaskCheckRequestFileProcess.class);

	/**
	 * 规则配置服务
	 */
	protected RuleConfigService ruleConfigService;

	/**
	 * 合作伙伴服务
	 */
	protected PartnerService partnerService;

	/**
	 * 任务服务
	 */
	protected TaskInfoService taskInfoService;

	/**
	 * 任务日志
	 */
	protected TaskLogService taskLogService;

	/**
	 * 模板
	 */
	private Template template;

	/**
	 * 合作伙伴
	 */
	private Partner partner;

	/**
	 * 规则定义
	 */
	private RuleDefinition ruleDefinition;

	/**
	 * 规则配置
	 */
	private List<RuleConfig> ruleConfigList;

	/**
	 * 检查请求处理输入
	 */
	private TaskCheckRequestProcessJobInput jobInput;

	/**
	 * 模板扩展属性服务
	 */
	private ExtendedAttriService extendedAttriService;

	/**
	 * 模板扩展属性
	 */
	private Map<String, ExtendedAttri> extendedAttriMap;

	/**
	 * 
	 */
	public AbstractTaskCheckRequestFileProcess() {
		ruleConfigService = ApplicationContextManager.getBean(RuleConfigService.class);
		partnerService = ApplicationContextManager.getBean(PartnerService.class);
		taskLogService = ApplicationContextManager.getBean(TaskLogService.class);
		taskInfoService = ApplicationContextManager.getBean(TaskInfoService.class);
		extendedAttriService = ApplicationContextManager.getBean(ExtendedAttriService.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.td.proxy.process.ITaskCheckRequestFileProcess#execute(java.util.Map)
	 */
	public void execute(Map<String, Object> context) throws Exception {
		template = (Template) context.get(ProxyConstant.TASK_CONTEXT_KEY_TEMPLATE);
		extendedAttriMap = extendedAttriService.findExtendedAttriMapByTemplateId(template.getId());
		partner = partnerService.selectByPrimaryKey(template.getPartnerId());
		jobInput = (TaskCheckRequestProcessJobInput) context.get(ProxyConstant.TASK_CONTEXT_KEY_JOB_INPUT);
		ruleDefinition = (RuleDefinition) context.get(ProxyConstant.TASK_CONTEXT_KEY_RULE_DEFINITION);
		ruleConfigList = ruleConfigService.findByRuleDefinitionIdAndTemplateId(ruleDefinition.getId(), template.getId());
		for (RuleConfig ruleConfig : ruleConfigList) {
			List<TaskFile> taskFileList = checkReqFile(ruleConfig, extendedAttriMap);
			if (taskFileList != null && taskFileList.size() > 0) {
				for (TaskFile taskFile : taskFileList) {
					logger.info(partner.getPartnerFullName() + "新上传了请求文件,文件名：" + taskFile.getFileName() + " 文件路径：" + taskFile.getFilePath());
					taskInfoService.createTaskInfoAndRequestFileJobInput(partner, taskFile, jobInput);
				}
				continue;
			}
			logger.info("未找到新上传的文件");
		}
	}

	public abstract List<TaskFile> checkReqFile(RuleConfig ruleConfig, Map<String, ExtendedAttri> extendedAttriMap2);

	/**
	 * @return the ruleConfigService
	 */
	public RuleConfigService getRuleConfigService() {
		return ruleConfigService;
	}

	/**
	 * @return the partnerService
	 */
	public PartnerService getPartnerService() {
		return partnerService;
	}

	/**
	 * @return the taskInfoService
	 */
	public TaskInfoService getTaskInfoService() {
		return taskInfoService;
	}

	/**
	 * @return the taskLogService
	 */
	public TaskLogService getTaskLogService() {
		return taskLogService;
	}

	/**
	 * @return the template
	 */
	public Template getTemplate() {
		return template;
	}

	/**
	 * @return the partner
	 */
	public Partner getPartner() {
		return partner;
	}

	/**
	 * @return the ruleDefinition
	 */
	public RuleDefinition getRuleDefinition() {
		return ruleDefinition;
	}

	/**
	 * @return the ruleConfigList
	 */
	public List<RuleConfig> getRuleConfigList() {
		return ruleConfigList;
	}

	/**
	 * @return the jobInput
	 */
	public TaskCheckRequestProcessJobInput getJobInput() {
		return jobInput;
	}

	/**
	 * @return the extendedAttriService
	 */
	public ExtendedAttriService getExtendedAttriService() {
		return extendedAttriService;
	}

	/**
	 * @return the extendedAttriMap
	 */
	public Map<String, ExtendedAttri> getExtendedAttriMap() {
		return extendedAttriMap;
	}

}
