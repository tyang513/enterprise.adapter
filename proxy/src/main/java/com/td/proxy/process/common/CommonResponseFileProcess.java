package com.td.proxy.process.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.td.admin.constant.CacheConstant;
import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysFtpServerConfig;
import com.td.common.constant.CommonConstant;
import com.td.common.util.DESedeUtil;
import com.td.common.util.HttpUtil;
import com.td.common.util.ProxyFileUtil;
import com.td.proxy.ProxyConstant;
import com.td.proxy.entity.admin.CloudUser;
import com.td.proxy.entity.admin.ExtendedAttri;
import com.td.proxy.entity.admin.RuleConfig;
import com.td.proxy.entity.admin.TaskService;
import com.td.proxy.entity.admin.Template;
import com.td.proxy.entity.task.TaskInfo;
import com.td.proxy.process.AbstractTaskResponseFileProcess;

/**
 * @author yangtao
 */
public class CommonResponseFileProcess extends AbstractTaskResponseFileProcess {

	/* (non-Javadoc)
	 * @see com.td.proxy.process.AbstractTaskResponseFileProcess#downloadFileFromCloud(com.td.proxy.entity.task.TaskInfo, java.lang.String, java.util.Map)
	 */
	public void downloadFileFromCloud(TaskInfo taskInfo, String downloadPath, Map<String, RuleConfig> ruleConfigMap) throws Exception {
		CloudUser cloudUser = (CloudUser) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_CLOUD_USER,
				CommonConstant.CLOUD_USER_TALKINGDATA_CLOUD_USER);
		
		RuleConfig ruleConfig = ruleConfigMap.get(ProxyConstant.RULE_CONFIG_UPLOAD_SERVICE_CODE);
		
		TaskService taskService = (TaskService) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_TASK_SERVICE,
				ruleConfig.getContent());
		
		String url = taskService.getService();
		String token = cloudUser.getToken();
		String appkey = cloudUser.getAppkey();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("token", token);
		paramMap.put("appkey", appkey);
		paramMap.put("taskid", taskInfo.getRefCloudId());
		HttpUtil.doDownloadFile(url, paramMap, downloadPath, taskService.getType());
	}

	/* (non-Javadoc)
	 * @see com.td.proxy.process.AbstractTaskResponseFileProcess#encryptFile(java.lang.String, java.util.Map, com.td.proxy.entity.admin.Template, java.lang.String)
	 */
	public void encryptFile(String srcfilePath, Map<String, RuleConfig> ruleConfigMap, Template template, String encryptFilePath,
			Map<String, ExtendedAttri> extendedAttriMap) throws Exception {
		ExtendedAttri transformationExtendedAttri = extendedAttriMap.get(DESedeUtil.DESede_Transformation);
		ExtendedAttri keyExtendedAttri = extendedAttriMap.get(DESedeUtil.DESede_Key);
		ExtendedAttri algorithmExtendedAttri = extendedAttriMap.get(DESedeUtil.DESede_Algorithm);
		ExtendedAttri ivsExtendedAttri = extendedAttriMap.get(DESedeUtil.DESede_Ivs);

		String transformation = transformationExtendedAttri.getExtendValue();
		String keys = keyExtendedAttri.getExtendValue();
		String algorithm = algorithmExtendedAttri.getExtendValue();
		String ivs = ivsExtendedAttri.getExtendValue();
		DESedeUtil.encryptBase64ByLine(transformation, keys, algorithm, ivs, srcfilePath, encryptFilePath);
	}

	/* (non-Javadoc)
	 * @see com.td.proxy.process.AbstractTaskResponseFileProcess#uploadFile2Ftp(java.lang.String, java.lang.String, com.td.admin.entity.SysFtpServerConfig, java.util.Map)
	 */
	public void uploadFile2Ftp(String encryptFilePath, String uploadFtpFilePath, SysFtpServerConfig ftpServerConfig,
			Map<String, ExtendedAttri> extendedAttriMap) throws IOException {
		ExtendedAttri extendedAttri = extendedAttriMap.get(ProxyConstant.SUCCESS_FILE_ID);
		File file = new File(ProxyFileUtil.getParentPath(uploadFtpFilePath) + File.separatorChar + extendedAttri.getExtendValue());
		file.createNewFile();
	}

}
