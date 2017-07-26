package com.td.proxy.process.common;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.td.proxy.ProxyConstant;
import com.td.proxy.entity.admin.ExtendedAttri;
import com.td.proxy.entity.admin.Partner;
import com.td.proxy.entity.admin.RuleConfig;
import com.td.proxy.entity.task.TaskFile;
import com.td.proxy.entity.task.TaskInfo;
import com.td.proxy.process.AbstractTaskCheckRequestFileProcess;

public class CommonCheckRequestProcess extends AbstractTaskCheckRequestFileProcess {

	private Logger logger = LoggerFactory.getLogger(CommonCheckRequestProcess.class);


	/* (non-Javadoc)
	 * @see com.td.proxy.process.AbstractTaskCheckRequestFileProcess#checkReqFile(com.td.proxy.entity.admin.RuleConfig)
	 */
	public List<TaskFile> checkReqFile(RuleConfig ruleConfig, Map<String, ExtendedAttri> extendedAttriMap2) {
		String checkFolderStr = ruleConfig.getContent();
		logger.info("检查文件路径地址" + checkFolderStr);
		String[] checkFolders = checkFolderStr.split(";");
		List<TaskFile> returnList = new ArrayList<TaskFile>();
		ExtendedAttri successFileIdExtendedAttri = extendedAttriMap2.get(ProxyConstant.SUCCESS_FILE_ID);
		String successFileId = successFileIdExtendedAttri.getExtendValue();
		for (String folderPath : checkFolders) {
			logger.info("检查文件目录 : " + folderPath);
			File folder = new File(folderPath);
			File[] fileList = folder.listFiles();
			if (fileList == null){
				continue;
			}
			for (File file : fileList) {
				List<TaskFile> taskFileList = isExists(file, ruleConfig, successFileId);
				returnList.addAll(taskFileList);
			}
		}
		return returnList;
	}

	/**
	 * 检查文件是否存在
	 * @param parentFile
	 * @param successFileId
	 * @return
	 */
	public List<TaskFile> isExists(File parentFile, RuleConfig ruleConfig, final String successFileId) {
		File[] fileList = parentFile.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return !successFileId.equals(pathname.getName());
			}
		});
		List<TaskFile> returnList = new ArrayList<TaskFile>();
		TaskFile taskFile = null;
		Partner partner = getPartner();
		for (File file : fileList) {
			String fileName = file.getName();
			String filePath = file.getPath();
			List<TaskInfo> taskInfoList = taskInfoService.findTaskInfoByFile(partner, fileName, filePath);
			if (taskInfoList == null || taskInfoList.size() == 0) {
				taskFile = new TaskFile();
				taskFile.setFileName(fileName);
				taskFile.setFilePath(filePath);
				taskFile.setType(ruleConfig.getCode());
				returnList.add(taskFile);
			}
		}
		return returnList;
	}
}
