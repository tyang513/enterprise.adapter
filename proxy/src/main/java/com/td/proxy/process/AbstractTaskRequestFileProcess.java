package com.td.proxy.process;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
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
import com.td.proxy.entity.task.TaskRequestFileProcessJobInput;
import com.td.proxy.service.admin.ExtendedAttriService;
import com.td.proxy.service.admin.PartnerService;
import com.td.proxy.service.admin.RuleConfigService;
import com.td.proxy.service.task.TaskInfoService;
import com.td.proxy.service.task.TaskLogService;

/**
 * 请求文件处理抽象类
 * 
 * @author yangtao
 */
public abstract class AbstractTaskRequestFileProcess implements ITaskProcess {

	public static Logger logger = LoggerFactory.getLogger(AbstractTaskRequestFileProcess.class);

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
	 * 模板扩展属性
	 */
	private Map<String, ExtendedAttri> extendedAttriMap;
	
	/**
	 * 合作伙伴
	 */
	private Partner partner;

	/**
	 * ftp配置服务
	 */
	private FtpServerConfigService ftpServerConfigService;

	private RuleConfigService ruleConfigService;

	private PartnerService partnerService;

	private TaskLogService taskLogService;

	private TaskRequestFileProcessJobInput jobInput;

	private TaskInfoService taskInfoService;
	
	private ExtendedAttriService extendedAttriService;

	public AbstractTaskRequestFileProcess() {
		ftpServerConfigService = ApplicationContextManager.getBean(FtpServerConfigService.class);
		ruleConfigService = ApplicationContextManager.getBean(RuleConfigService.class);
		partnerService = ApplicationContextManager.getBean(PartnerService.class);
		taskLogService = ApplicationContextManager.getBean(TaskLogService.class);
		taskInfoService = ApplicationContextManager.getBean(TaskInfoService.class);
		extendedAttriService = ApplicationContextManager.getBean(ExtendedAttriService.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.td.proxy.process.ITaskProcess#execute(java.util.Map)
	 */
	public void execute(Map<String, Object> context) throws Exception {
		template = (Template) context.get(ProxyConstant.TASK_CONTEXT_KEY_TEMPLATE);
		ruleDefinition = (RuleDefinition) context.get(ProxyConstant.TASK_CONTEXT_KEY_RULE_DEFINITION);
		taskInfo = (TaskInfo) context.get(ProxyConstant.TASK_CONTEXT_KEY_TASK_INFO);
		partner = partnerService.selectByPrimaryKey(template.getPartnerId());
		jobInput = (TaskRequestFileProcessJobInput) context.get(ProxyConstant.TASK_CONTEXT_KEY_JOB_INPUT);
		SysFtpServerConfig ftpServerConfig = ftpServerConfigService.getFtpServerConfigById(Long.valueOf(template.getFtpServerConfigId()));
		Map<String, RuleConfig> ruleConfigMap = ruleConfigService.findByDefinitionIdAndTempalteIdMap(ruleDefinition.getId(), template.getId());
		extendedAttriMap = extendedAttriService.findExtendedAttriMapByTemplateId(template.getId());
		
		// 1.从ftp下载文件
		String msg = "下载文件" + taskInfo.getFileName() + ",文件路径为：" + taskInfo.getFilePath();
		logger.info(" 下载文件 " + msg);
		Integer logId = taskLogService.requestFileLog(partner, taskInfo, jobInput, msg, ProxyConstant.TASK_LOG_TYPE_DOWNLOAD_FILE);
		String downloadFilePath = downloadFile(ftpServerConfig, taskInfo);
		msg += ",文件本地存储路径为：" + downloadFilePath;
		logger.info(msg);
		taskLogService.updateFinishTimeById(logId, msg);

		// 2.文件解密,解密后的文件存储路径为本系统的附件目录+taskCode+refDmpId+fileName
		SysParam sysParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, CommonConstant.SYSTEM_PARAM_ATTACHMENT_PATH);
		String decryptFilePath = sysParam.getParamvalue() + File.separatorChar + taskInfo.getTaskCode() + File.separatorChar
				+ ProxyFileUtil.getFilePathLast2Paragraphs(taskInfo.getFilePath());
		String parentPath = ProxyFileUtil.getParentPath(decryptFilePath);
		ProxyFileUtil.createFolderByPath(parentPath);

		msg = "解密文件" + downloadFilePath + "，解密后的文件存储路径为：" + decryptFilePath + "。";
		logger.info(" 解密文件  " + msg);
		logId = taskLogService.requestFileLog(partner, taskInfo, jobInput, msg, ProxyConstant.TASK_LOG_TYPE_DECRYPT_FILE);
		decryptFile(downloadFilePath, ruleConfigMap, template, decryptFilePath, extendedAttriMap);
		taskLogService.updateFinishTimeById(logId, null);

		// 3.压缩文件
		String gzFileName = ProxyFileUtil.getFileName(taskInfo.getFileName()) + ".gz";
		String gzFilePath = parentPath + File.separatorChar + gzFileName;
		msg = "压缩文件，压缩后的文件存储路径为。" + gzFilePath;
		logger.info(msg);
		logId = taskLogService.requestFileLog(partner, taskInfo, jobInput, msg, ProxyConstant.TASK_LOG_TYPE_GZIP_FILE);
		GZipUtil.gzip(decryptFilePath, parentPath, gzFileName);
		taskLogService.updateFinishTimeById(logId, null);

		// 4.文件上传到云端
		File gzFile = new File(gzFilePath);
		msg = "上传文件" + gzFile.getName() + ",文件路径" + gzFile.getAbsolutePath() + "到云端。";
		logger.info(msg);
		logId = taskLogService.requestFileLog(partner, taskInfo, jobInput, msg, ProxyConstant.TASK_LOG_TYPE_UPDATE_FILE_TO_CLOUD);
		uploadFile2Cloud(gzFilePath, ruleConfigMap);
		taskLogService.updateFinishTimeById(logId, null);
	}

	/**
	 * @param ftpServerConfig
	 *            ftp配置信息
	 * @param taskInfo
	 *            任务信息
	 * @return 下载文件保存路径
	 * @throws IOException
	 */
	public abstract String downloadFile(SysFtpServerConfig ftpServerConfig, TaskInfo taskInfo) throws IOException;

	/**
	 * @param decryptFilePath
	 * @throws Exception
	 */
	public abstract void uploadFile2Cloud(String decryptFilePath, Map<String, RuleConfig> ruleConfigMap) throws Exception ;//{
//		CloudUser cloudUser = (CloudUser) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_CLOUD_USER,
//				CommonConstant.CLOUD_USER_TALKINGDATA_CLOUD_USER);
//		SysParam callBackUrlParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM,
//				CommonConstant.SYSTEM_PARAM_CALLBACK_URL);
//		
//		RuleConfig ruleConfig = ruleConfigMap.get(ProxyConstant.RULE_CONFIG_UPLOAD_SERVICE_CODE);
//		
//		TaskService taskService = (TaskService) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_TASK_SERVICE,
//				ruleConfig.getContent());
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		String url = taskService.getService(); 
//		String token = cloudUser.getToken();
//		String appkey = cloudUser.getAppkey();
//		String callBackUrl = callBackUrlParam.getParamvalue().replace("${taskCode}", getTaskInfo().getTaskCode());
//		paramMap.put("file", new File(decryptFilePath));
//		paramMap.put("token", token);
//		paramMap.put("appkey", appkey);
//		paramMap.put("callBackUrl", callBackUrl);
//		paramMap.put("type", ProxyConstant.API_TALKINGDATA_IDUPLPAD_TYPE);
//		paramMap.put("inputIdType", ProxyConstant.API_TALKINGDATA_IDUPLPAD_INPUT_ID_TYPE);
//		paramMap.put("outputIdType", ProxyConstant.API_TALKINGDATA_IDUPLPAD_INPUT_ID_TYPE);
//		Map<String, Object> returnParamMap = HttpUtil.doUploadFile(url, paramMap, taskService.getType());
//		String taskid = (String) returnParamMap.get("taskid");
//		taskInfoService.updateTaskInfoRefCloudId(getTaskInfo().getId(), taskid);
//	}

	/**
	 * @param decryptFilePath
	 *            待解密文件路径 Map<String, RuleConfig> ruleConfigMap
	 * @param extendedAttriMap2 
	 * @return String decryptFilePath 解密之后的问题存储路径
	 * @throws Exception
	 */
	public abstract void decryptFile(String srcfilePath, Map<String, RuleConfig> ruleConfigMap, Template template, String decryptFilePath, Map<String, ExtendedAttri> extendedAttriMap)
			throws Exception;

	public Template getTemplate() {
		return template;
	}

	public RuleDefinition getRuleDefinition() {
		return ruleDefinition;
	}

	public TaskInfo getTaskInfo() {
		return taskInfo;
	}

	public Map<String, RuleConfig> getRuleConfigMap() {
		return ruleConfigMap;
	}

	public Partner getPartner() {
		return partner;
	}

	public FtpServerConfigService getFtpServerConfigService() {
		return ftpServerConfigService;
	}

	public RuleConfigService getRuleConfigService() {
		return ruleConfigService;
	}

	public PartnerService getPartnerService() {
		return partnerService;
	}

	/**
	 * @return the taskInfoService
	 */
	public TaskInfoService getTaskInfoService() {
		return taskInfoService;
	}
}
