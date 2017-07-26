package com.td.proxy.process.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.td.admin.constant.CacheConstant;
import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysFtpServerConfig;
import com.td.admin.entity.SysParam;
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
import com.td.proxy.process.AbstractTaskRequestFileProcess;

public class CommonRequestFileProcess extends AbstractTaskRequestFileProcess {

	/* (non-Javadoc)
	 * @see com.td.proxy.process.AbstractTaskRequestFileProcess#decryptFile(java.lang.String)
	 */
	public void decryptFile(String encryptFilePath,  Map<String, RuleConfig> ruleConfigMap, Template template, String decryptFilePath,Map<String, ExtendedAttri> extendedAttriMap) throws Exception {
		ExtendedAttri transformationExtendedAttri = extendedAttriMap.get(DESedeUtil.DESede_Transformation);
		ExtendedAttri keyExtendedAttri = extendedAttriMap.get(DESedeUtil.DESede_Key);
		ExtendedAttri algorithmExtendedAttri = extendedAttriMap.get(DESedeUtil.DESede_Algorithm);
		ExtendedAttri ivsExtendedAttri = extendedAttriMap.get(DESedeUtil.DESede_Ivs);
		
		String transformation = transformationExtendedAttri.getExtendValue();
		String keys = keyExtendedAttri.getExtendValue();
		String algorithm = algorithmExtendedAttri.getExtendValue();
		String ivs = ivsExtendedAttri.getExtendValue();
		DESedeUtil.decryptBase64ByLine(transformation, keys, algorithm, ivs, encryptFilePath, decryptFilePath);
	}

	/* (non-Javadoc)
	 * @see com.td.proxy.process.AbstractTaskRequestFileProcess#uploadFile2Cloud(java.lang.String)
	 */
	public void uploadFile2Cloud(String decryptFilePath, Map<String, RuleConfig> ruleConfigMap) throws Exception {
		CloudUser cloudUser = (CloudUser) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_CLOUD_USER,
				CommonConstant.CLOUD_USER_TALKINGDATA_CLOUD_USER);
		SysParam callBackUrlParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM,
				CommonConstant.SYSTEM_PARAM_CALLBACK_URL);
		
		RuleConfig ruleConfig = ruleConfigMap.get(ProxyConstant.RULE_CONFIG_UPLOAD_SERVICE_CODE);
		
		TaskService taskService = (TaskService) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_TASK_SERVICE,
				ruleConfig.getContent());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String url = taskService.getService(); 
		String token = cloudUser.getToken();
		String appkey = cloudUser.getAppkey();
		String callBackUrl = callBackUrlParam.getParamvalue().replace("${taskCode}", getTaskInfo().getTaskCode());
		paramMap.put("file", new File(decryptFilePath));
		paramMap.put("token", token);
		paramMap.put("appkey", appkey);
		paramMap.put("callBackUrl", callBackUrl);
		paramMap.put("type", ProxyConstant.API_TALKINGDATA_IDUPLPAD_TYPE);
		paramMap.put("inputIdType", ProxyConstant.API_TALKINGDATA_IDUPLPAD_INPUT_ID_TYPE);
		paramMap.put("outputIdType", ProxyConstant.API_TALKINGDATA_IDUPLPAD_OUTPUT_ID_TYPE);
		Map<String, Object> returnParamMap = HttpUtil.doUploadFile(url, paramMap, taskService.getType());
		String taskid = String.valueOf(returnParamMap.get("taskid"));
		getTaskInfoService().updateTaskInfoRefCloudId(getTaskInfo().getId(), taskid);
	}

	/* (non-Javadoc)
	 * @see com.td.proxy.process.AbstractTaskRequestFileProcess#downloadFile(com.td.admin.entity.SysFtpServerConfig, com.td.proxy.entity.task.TaskInfo)
	 */
	public String downloadFile(SysFtpServerConfig ftpServerConfig, TaskInfo taskInfo) throws IOException {
		ProxyFileUtil.createFolderByFilePath(taskInfo.getFilePath());
		return taskInfo.getFilePath();
	}
	
}
