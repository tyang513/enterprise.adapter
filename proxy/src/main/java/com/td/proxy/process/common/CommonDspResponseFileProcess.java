package com.td.proxy.process.common;

import java.io.IOException;
import java.util.Map;

import com.td.admin.entity.SysFtpServerConfig;
import com.td.proxy.entity.admin.ExtendedAttri;
import com.td.proxy.entity.admin.RuleConfig;
import com.td.proxy.entity.admin.Template;
import com.td.proxy.entity.task.TaskInfo;

/**
 * @author yangtao
 *
 */
public class CommonDspResponseFileProcess extends CommonResponseFileProcess {

	/* (non-Javadoc)
	 * @see com.td.proxy.process.common.CommonResponseFileProcess#downloadFileFromCloud(com.td.proxy.entity.task.TaskInfo, java.lang.String, java.util.Map)
	 */
	public void downloadFileFromCloud(TaskInfo taskInfo, String downloadPath, Map<String, RuleConfig> ruleConfigMap) throws Exception {
		super.downloadFileFromCloud(taskInfo, downloadPath, ruleConfigMap);
	}
	
	/* (non-Javadoc)
	 * @see com.td.proxy.process.AbstractTaskResponseFileProcess#uploadFile2Ftp(java.lang.String, java.lang.String, com.td.admin.entity.SysFtpServerConfig, java.util.Map)
	 */
	public void uploadFile2Ftp(String encryptFilePath, String uploadFtpFilePath, SysFtpServerConfig ftpServerConfig,
			Map<String, ExtendedAttri> extendedAttriMap) throws IOException {
		super.uploadFile2Ftp(encryptFilePath, uploadFtpFilePath, ftpServerConfig, extendedAttriMap);
	}

	/* (non-Javadoc)
	 * @see com.td.proxy.process.AbstractTaskResponseFileProcess#encryptFile(java.lang.String, java.util.Map, com.td.proxy.entity.admin.Template, java.lang.String)
	 */
	public void encryptFile(String srcfilePath, Map<String, RuleConfig> ruleConfigMap, Template template, String encryptFilePath,
			Map<String, ExtendedAttri> extendedAttriMap) throws Exception {
		super.encryptFile(srcfilePath, ruleConfigMap, template, encryptFilePath, extendedAttriMap);
	}

}
