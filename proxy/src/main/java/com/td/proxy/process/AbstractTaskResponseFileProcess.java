package com.td.proxy.process;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.td.admin.constant.CacheConstant;
import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysFtpServerConfig;
import com.td.admin.entity.SysParam;
import com.td.admin.service.FtpServerConfigService;
import com.td.common.constant.CommonConstant;
import com.td.common.util.ApplicationContextManager;
import com.td.common.util.GZipUtil;
import com.td.common.util.ProxyFileUtil;
import com.td.proxy.ProxyConstant;
import com.td.proxy.entity.admin.ExtendedAttri;
import com.td.proxy.entity.admin.Partner;
import com.td.proxy.entity.admin.RuleConfig;
import com.td.proxy.entity.admin.RuleDefinition;
import com.td.proxy.entity.admin.Template;
import com.td.proxy.entity.task.TaskInfo;
import com.td.proxy.entity.task.TaskResponseFileProcessJobInput;
import com.td.proxy.service.admin.ExtendedAttriService;
import com.td.proxy.service.admin.PartnerService;
import com.td.proxy.service.admin.RuleConfigService;
import com.td.proxy.service.task.TaskLogService;

public abstract class AbstractTaskResponseFileProcess implements ITaskProcess {

	public org.slf4j.Logger logger = LoggerFactory.getLogger(AbstractTaskResponseFileProcess.class);

	/**
	 * 模板
	 */
	private Template template;

	/**
	 * 规则定义
	 */
	private RuleDefinition ruleDefinition;

	/**
	 * 任务信息
	 */
	private TaskInfo taskInfo;

	/**
	 * 规则集合
	 */
	private Map<String, RuleConfig> ruleConfigMap;

	/**
	 * 合作伙伴
	 */
	private Partner partner;

	/**
	 * 模板扩展属性
	 */
	private Map<String, ExtendedAttri> extendedAttriMap;

	/**
	 * ftp配置服务
	 */
	private FtpServerConfigService ftpServerConfigService;

	private RuleConfigService ruleConfigService;

	private PartnerService partnerService;

	private TaskResponseFileProcessJobInput jobInput;

	private TaskLogService taskLogService;

	private ExtendedAttriService extendedAttriService;

	public AbstractTaskResponseFileProcess() {
		ftpServerConfigService = ApplicationContextManager.getBean(FtpServerConfigService.class);
		ruleConfigService = ApplicationContextManager.getBean(RuleConfigService.class);
		partnerService = ApplicationContextManager.getBean(PartnerService.class);
		taskLogService = ApplicationContextManager.getBean(TaskLogService.class);
		extendedAttriService = ApplicationContextManager.getBean(ExtendedAttriService.class);
	}

	public void execute(Map<String, Object> context) throws Exception {
		template = (Template) context.get(ProxyConstant.TASK_CONTEXT_KEY_TEMPLATE);
		ruleDefinition = (RuleDefinition) context.get(ProxyConstant.TASK_CONTEXT_KEY_RULE_DEFINITION);
		taskInfo = (TaskInfo) context.get(ProxyConstant.TASK_CONTEXT_KEY_TASK_INFO);
		partner = partnerService.selectByPrimaryKey(template.getPartnerId());
		jobInput = (TaskResponseFileProcessJobInput) context.get(ProxyConstant.TASK_CONTEXT_KEY_JOB_INPUT);
		ruleConfigMap = ruleConfigService.findByDefinitionIdAndTempalteIdMap(ruleDefinition.getId(), template.getId());
		extendedAttriMap = extendedAttriService.findExtendedAttriMapByTemplateId(template.getId());

		String refCloudId = taskInfo.getRefCloudId();
		SysParam sysParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, CommonConstant.SYSTEM_PARAM_ATTACHMENT_PATH);

		String basePath = sysParam.getParamvalue() + File.separatorChar + taskInfo.getTaskCode() + File.separatorChar + refCloudId
				+ File.separatorChar;
		ProxyFileUtil.createFolderByPath(basePath);
		logger.info("响应文件处理基础路径：" + basePath);

		// 下载路径
		String downlaodFileName = refCloudId + ".gz";
		String downloadPath = basePath + File.separatorChar + downlaodFileName;
		String msg = "获取响应(结果)文件" + downlaodFileName + "，存储路径为：" + downloadPath;
		Integer logId = taskLogService.responseFileLog(partner, taskInfo, jobInput, msg, ProxyConstant.TASK_LOG_TYPE_DOWNLOAD_FILE_FROM_CLOUD);
		// 获取下载文件路径
		downloadFileFromCloud(taskInfo, downloadPath, ruleConfigMap);
		taskLogService.updateFinishTimeById(logId, null);
		// 解压文件
		String ungzipPath = basePath + File.separatorChar + refCloudId + ".txt";
		msg = "解压结果文件" + downloadPath + ",解压路径为" + ungzipPath + "。";
		logId = taskLogService.responseFileLog(partner, taskInfo, jobInput, msg, ProxyConstant.TASK_LOG_TYPE_UNGZIP_FILE);
		GZipUtil.ungzip(downloadPath, ungzipPath);
		taskLogService.updateFinishTimeById(logId, null);

		// 对文件进行加密
		String encryptFilePath = createEncryptFilePath(taskInfo, ruleConfigMap, template);
		ProxyFileUtil.createFolderByFilePath(encryptFilePath);
		msg = "文件加密，加密后文件存储路径为：" + encryptFilePath + "。";
		logId = taskLogService.responseFileLog(partner, taskInfo, jobInput, msg, ProxyConstant.TASK_LOG_TYPE_ENCRYPT_FILE);
		encryptFile(ungzipPath, ruleConfigMap, template, encryptFilePath, extendedAttriMap);
		taskLogService.updateFinishTimeById(logId, null);

		// 创建成功文件
		String uploadFtpFilePath = ruleConfigMap.get(taskInfo.getType()).getContent() + File.separatorChar
				+ ProxyFileUtil.getFilePathLast2Paragraphs(taskInfo.getFilePath());
		msg = "上传响应(结果)文件到ftp，文件路径为：" + uploadFtpFilePath;
		logId = taskLogService.responseFileLog(partner, taskInfo, jobInput, msg, ProxyConstant.TASK_LOG_TYPE_UPLOAD_FILE_TO_FTP);
		SysFtpServerConfig ftpServerConfig = ftpServerConfigService.getFtpServerConfigById(Long.valueOf(template.getFtpServerConfigId()));
		uploadFile2Ftp(encryptFilePath, uploadFtpFilePath, ftpServerConfig, extendedAttriMap);
		taskLogService.updateFinishTimeById(logId, null);
	}

	/**
	 * 上传加密到的文件到ftp
	 * 
	 * @param encryptFilePath
	 *            加密后的文件存储路径
	 * @param uploadFtpFilePath
	 *            ftp上的文件路径
	 * @param ftpServerConfig
	 *            ftp配置文件信息
	 * @throws IOException
	 */
	public abstract void uploadFile2Ftp(String encryptFilePath, String uploadFtpFilePath, SysFtpServerConfig ftpServerConfig,
			Map<String, ExtendedAttri> extendedAttriMap) throws IOException;

	/**
	 * 创建文件加密存储路径
	 * 
	 * @return String 文件加密存储路径
	 */
	private String createEncryptFilePath(TaskInfo taskInfo, Map<String, RuleConfig> ruleConfigMap, Template template) {
		RuleConfig uploadRuleConfig = ruleConfigMap.get(taskInfo.getType());
		String path = uploadRuleConfig.getContent() + File.separatorChar + ProxyFileUtil.getFilePathLast2Paragraphs(taskInfo.getFilePath());
		return path;
	}

	/**
	 * 从云端下载文件
	 * @param taskInfo
	 * @param downloadPath
	 * @return
	 * @throws Exception
	 */
	public abstract void downloadFileFromCloud(TaskInfo taskInfo, String downloadPath, Map<String, RuleConfig> ruleConfigMap) throws Exception ;

	/**
	 * @param extendedAttriMap2 
	 * @param decryptFilePath
	 *            待解密文件路径 Map<String, RuleConfig> ruleConfigMap
	 * @return String decryptFilePath 解密之后的问题存储路径
	 * @throws Exception
	 */
	public abstract void encryptFile(String srcfilePath, Map<String, RuleConfig> ruleConfigMap, Template template, String encryptFilePath, Map<String, ExtendedAttri> extendedAttriMap)
			throws Exception;

	/**
	 * @return the template
	 */
	public Template getTemplate() {
		return template;
	}

	/**
	 * @return the ruleDefinition
	 */
	public RuleDefinition getRuleDefinition() {
		return ruleDefinition;
	}

	/**
	 * @return the taskInfo
	 */
	public TaskInfo getTaskInfo() {
		return taskInfo;
	}

	/**
	 * @return the ruleConfigMap
	 */
	public Map<String, RuleConfig> getRuleConfigMap() {
		return ruleConfigMap;
	}

	/**
	 * @return the partner
	 */
	public Partner getPartner() {
		return partner;
	}

	/**
	 * @return the ftpServerConfigService
	 */
	public FtpServerConfigService getFtpServerConfigService() {
		return ftpServerConfigService;
	}

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
	 * @return the jobInput
	 */
	public TaskResponseFileProcessJobInput getJobInput() {
		return jobInput;
	}

}
