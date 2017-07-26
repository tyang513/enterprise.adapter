package com.td.admin.ftp.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.constant.FTPConstant;
import com.td.admin.entity.SysFtpServerConfig;
import com.td.admin.mapper.SysFtpServerConfigMapper;
import com.td.common.exception.CommonException;

@Service(value = "ftpUploadService")
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS, rollbackFor = Throwable.class)
public class FtpUploadService {
	private final static Logger logger = Logger.getLogger(FtpUploadService.class);

	@Autowired
	private SysFtpServerConfigMapper ftpServerConfigMapper;

	public void uploadFile(String filePath) {
		logger.info("定时上传文件=============");
		FtpClient ftpClient = null;
		
		String finFlag = FTPConstant.DEFAULT_FILE_FIN_FLAG;
		Map<String, Object> condMap = new HashMap<String, Object>();
		try {
			List<SysFtpServerConfig> ftpServerConfigList = ftpServerConfigMapper.getFtpServerListConfigByMap(condMap);
			SysFtpServerConfig ftpServerConfig = ftpServerConfigList.get(0);

			ftpClient = new FtpClient(ftpServerConfig);
			ftpClient.connect();
			//服务器路径
			String dstDir = ftpServerConfig.getRootpath();
			//文件名
			String dstFileName = filePath.substring(filePath.lastIndexOf(FTPConstant.SERVER_PATH_SEPARATOR) + 1);

			logger.debug("dstDir==" + dstDir);
			logger.debug("dstFileName==" + dstFileName);
			//上传文件
			int status = ftpClient.put(filePath, dstDir, dstFileName);

			if (status == FTPConstant.FILE_STATU_FINISH) {
				//上传标识文件
				File filename = new File(filePath + "." + finFlag);
				if (!filename.exists()) {
					filename.createNewFile();
				}
				ftpClient.put(filePath + "." + finFlag, dstDir, dstFileName + "."+ finFlag);
				//删除本地文件
				File delFile = new File(filePath);
				File delFinFile = new File(filePath + "." + finFlag);
				FileUtils.forceDelete(delFile);
				FileUtils.forceDelete(delFinFile);
				
				logger.debug("文件=[" + delFile + "," + delFinFile + "]");
			}
			ftpClient.disconnect();
		} catch (Exception e) {
			logger.error("FtpUpload job 上传文件异常", e);
		} finally {
			if (ftpClient != null) {
				try {
					ftpClient.disconnect();
				} catch (CommonException e) {
					logger.error("FtpUpload 上传文件，关闭ftp链接失败", e);
				}
			}
		}
	}
}
