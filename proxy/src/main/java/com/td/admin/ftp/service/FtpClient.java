package com.td.admin.ftp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.td.admin.constant.CacheConstant;
import com.td.admin.constant.FTPConstant;
import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysFtpServerConfig;
import com.td.admin.entity.SysParam;
import com.td.admin.ftp.util.FileProgressMonitor;
import com.td.admin.ftp.util.SFTPChannel;
import com.td.common.exception.CommonException;

/**
 * @description: 提供SFTP的常用命令 注意：在调用完以后，必须执行disconnect方法来断开连接
 * @author: cmh 2013-6-25
 * @version: 1.0
 * @modify:
 * @Copyright: 公司版权拥有
 */
public class FtpClient {
	private final static Logger logger = Logger.getLogger(FtpClient.class);

	private static int timeout = 6000000;

	private ChannelSftp channelSftp = null;

	private SysFtpServerConfig ftpServerConfig;

	private SFTPChannel sftpChannel = null;

	public FileProgressMonitor fileProgressMonitor = null;

	public FtpClient(SysFtpServerConfig ftpServerConfig) {
		this.ftpServerConfig = ftpServerConfig;
	}

	/**
	 * 初始化SFTP，并创建连接SFTP服务器
	 * 
	 * @return ChannelSftp
	 * @throws CommonException
	 */
	public ChannelSftp init() throws CommonException {
		return connect();
	}

	/**
	 * 创建
	 * 
	 * @throws CommonException
	 */
	public ChannelSftp connect() throws CommonException {
		if (channelSftp == null) {
			logger.info("连接sftp server " + ftpServerConfig.getServername() + "，ip地址  " + ftpServerConfig.getHost());
			sftpChannel = new SFTPChannel();
			try {
				SysParam timeoutParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, FTPConstant.SFTP_TIMEOUT);
				if (timeoutParam != null) {
					timeout = Integer.valueOf(timeoutParam.getParamvalue());
				}
				channelSftp = sftpChannel.getChannel(ftpServerConfig, timeout);
			} catch (JSchException e) {
				logger.error("连接sftp服务器失败", e);
				throw new CommonException("sftp.connect.fail", e, new Object[] { ftpServerConfig.getServername(), ftpServerConfig.getHost(),
						ftpServerConfig.getPort() });
			}
		}

		return channelSftp;
	}

	/**
	 * 断开连接
	 * 
	 * @throws CommonException
	 */
	public void disconnect() throws CommonException {
		logger.info("断开连接");
		if (channelSftp != null) {
			channelSftp.quit();
			channelSftp = null;
		}

		try {
			if (sftpChannel != null) {
				sftpChannel.closeChannel();
				sftpChannel = null;
			}
		} catch (CommonException e) {
			logger.error("sftp服务器断开连接异常", e);
			throw new CommonException("sftp.disconnect.fail", e, new Object[] { ftpServerConfig.getServername(), ftpServerConfig.getHost(),
					ftpServerConfig.getPort() });
		}

	}

	/**
	 * 验证目录是否存在
	 * 
	 * @param dir
	 *            目录
	 * @return 存在返回true
	 * @throws CommonException
	 */
	public boolean testDir(String dir) throws CommonException {
		try {
			channelSftp.ls(dir);
		} catch (Exception e) {
			logger.error("", e);
			return false;
//			throw new CommonException("sftp.list.path.files.fail", e, new Object[] { ftpServerConfig.getServername() + "服务器下" + dir + "" });
			}
		return true;
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param path
	 *            目录
	 * @return List
	 * @throws CommonException
	 */
	public List<String> list(String path) throws CommonException {
		logger.debug("列出目录" + path + "下的文件");
		List<String> fileList = new ArrayList<String>();

		Vector<?> files = null;
		if (channelSftp == null) {
			connect();
		}

		try {
			files = channelSftp.ls(path);
			for (Object obj : files) {
				if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
					SftpATTRS arr = ((com.jcraft.jsch.ChannelSftp.LsEntry) obj).getAttrs();
					String fileName = ((com.jcraft.jsch.ChannelSftp.LsEntry) obj).getFilename();
					if (!fileName.equals("..") && !fileName.equals(".")) { // 文件名称
																			// .
																			// 和
																			// ..
																			// 不做处理
						if (!arr.isDir()) { // 不是目录的处理
							fileList.add(fileName);
						}
					}
					arr = null;
				}
				obj = null;
			}
		} catch (SftpException e) {
			logger.error("列出sftp服务器路径[" + path + "]目录下的文件失败", e);
			throw new CommonException("sftp.list.path.files.fail", e, new Object[] { ftpServerConfig.getServername() + "服务器下" + path + "" });
		}

		return fileList;
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param path
	 *            目录
	 * @return Map<String, Long> key:文件名,value:最后更新时间(毫秒)
	 * @throws CommonException
	 */
	public Map<String, Long> list2Map(String path) throws CommonException {
		logger.debug("列出目录" + path + "下的文件");
		Map<String, Long> fileMap = new HashMap<String, Long>();

		Vector<?> files = null;
		if (channelSftp == null) {
			connect();
		}

		try {
			files = channelSftp.ls(path);
			for (Object obj : files) {
				if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
					SftpATTRS arr = ((com.jcraft.jsch.ChannelSftp.LsEntry) obj).getAttrs();
					String fileName = ((com.jcraft.jsch.ChannelSftp.LsEntry) obj).getFilename();
					if (!fileName.equals("..") && !fileName.equals(".")) { // 文件名称
																			// .
																			// 和
																			// ..
																			// 不做处理
						if (!arr.isDir()) { // 不是目录的处理
							fileMap.put(fileName, (long) arr.getMTime() * 1000);
						}
					}
					arr = null;
				}
				obj = null;
			}
		} catch (SftpException e) {
			logger.error("列出sftp服务器路径[" + path + "]目录下的文件失败", e);
			throw new CommonException("sftp.list.path.files.fail", e, new Object[] { ftpServerConfig.getServername() + "服务器下" + path + "" });
		}
		return fileMap;
	}

	/**
	 * 创建目录
	 * 
	 * @param path
	 *            目录
	 * @throws CommonException
	 */
	public void mkdir(String path) throws CommonException {
		if (channelSftp == null) {
			connect();
		}
		mkdir4Put(path);
	}

	/**
	 * 通过sftp上传文件
	 * 
	 * @param src
	 *            本地文件名,如"D:\\sftp\\test1001.rar"
	 * @param dstDir
	 *            目标文件路径,如"/home/sftp/"
	 * @param dstFileName
	 *            目标文件名,如"test1001.rar"
	 * @throws CommonException
	 */
	public int put(String src, String dstDir, String dstFileName) throws CommonException {
		logger.info("开始上传文件......");
		int workStatus = FTPConstant.FILE_STATU_NO_FINISH;

		File srcFile = new File(src);
		long fileSize = srcFile.length();
		String dst = dstDir + dstFileName;
		if (!dstDir.endsWith(FTPConstant.SERVER_PATH_SEPARATOR)) {
			dst = dstDir + FTPConstant.SERVER_PATH_SEPARATOR + dstFileName;
		}
		OutputStream out = null;
		
		if(testDir(dstDir) == false){
			mkdir4Put(dstDir);
		}
	
		fileProgressMonitor = new FileProgressMonitor(fileSize);
		InputStream is = null;
		try {
			// 使用OVERWRITE模式
			out = channelSftp.put(dst, fileProgressMonitor, ChannelSftp.OVERWRITE);
			byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
			int read;
			if (out != null) {
				logger.info("Start to read input stream");
				is = new FileInputStream(srcFile);
				do {
					read = is.read(buff, 0, buff.length);
					if (read > 0) {
						out.write(buff, 0, read);
					}
					out.flush();
				} while (read >= 0);
				logger.info("input stream read done.");
			}
			workStatus = FTPConstant.FILE_STATU_FINISH;

		} catch (SftpException e) {
			workStatus = FTPConstant.FILE_STATU_EXCEPTION;
			logger.error("上传文件失败！原文件[" + src + "],目标文件[" + dst + "]", e);
			throw new CommonException("sftp.upload.files.fail", e, new Object[] { src, dst });
		} catch (FileNotFoundException e) {
			workStatus = FTPConstant.FILE_STATU_EXCEPTION;
			logger.error("上传文件失败![" + src + "],目标文件[" + dst + "]", e);
			throw new CommonException("sftp.upload.files.fail", e, new Object[] { src, dst });
		} catch (IOException e) {
			workStatus = FTPConstant.FILE_STATU_EXCEPTION;
			logger.error("上传文件失败![" + src + "],目标文件[" + dst + "]", e);
			throw new CommonException("sftp.upload.files.fail", e, new Object[] { src, dst });
		} finally {
			fileProgressMonitor.stop();
			try {
				if (is != null) {
					is.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("关闭文件流失败!", e);
				throw new CommonException("sftp.upload.files.fail", e, new Object[] { src, dst });
			}
		}
		return workStatus;
	}

	/**
	 * 为上传时目标路径不存在，创建路径
	 * 
	 * @param dstDir
	 *            目标路径
	 * @throws CommonException
	 */
	private void mkdir4Put(String dstDir) throws CommonException {
		String tempDstDir = "";
		String[] dstSplit = dstDir.split(FTPConstant.SERVER_PATH_SEPARATOR);

		boolean isValid = false;

		for (int i = 0; i < dstSplit.length; i++) {
			tempDstDir += dstSplit[i] + FTPConstant.SERVER_PATH_SEPARATOR;

			if (isValid) {
				try {
					channelSftp.mkdir(tempDstDir);
				} catch (SftpException e1) {
					logger.error("创建目录失败[目标路径[" + dstDir + "]", e1);
					throw new CommonException("sftp.mkdir.files.fail", e1, new Object[] { dstDir });
				}
				continue;
			}
			try {
				channelSftp.ls(tempDstDir); // 首先查看下目录，如果不存在，系统会被错，捕获这个错，生成新的目录。
			} catch (Exception e) {
				isValid = true;
				try {
					channelSftp.mkdir(tempDstDir);
				} catch (SftpException e1) {
					logger.error("创建目录失败[目标路径[" + dstDir + "]", e);
					throw new CommonException("sftp.mkdir.fail", e1, new Object[] { dstDir });
				}
			}
		}
	}

	/**
	 * 进入目录
	 * 
	 * @param path
	 *            目录
	 * @throws CommonException
	 */
	public void cd(String path) throws CommonException {
		if (channelSftp == null) {
			connect();
		}

		try {
			channelSftp.cd(path);
		} catch (SftpException e) {
			logger.error("进入[" + path + "]目录失败！", e);
			throw new CommonException("sftp.cd.fail", e, new Object[] { path });
		}
	}

	public void rm(String pathFile) throws CommonException {
		logger.debug("删除文件[" + pathFile + "]");
		// InputStream in = null;
		if (channelSftp == null) {
			connect();
		}
		try {
			// in = channelSftp.get(pathFile);
			channelSftp.rm(pathFile);
		} catch (SftpException e) {
			if (ChannelSftp.SSH_FX_NO_SUCH_FILE == e.id) {
				logger.warn("删除文件[" + pathFile + "]失败！此文件不存在", e);
			} else {
				logger.error("删除文件[" + pathFile + "]失败！", e);
				throw new CommonException("sftp.rm.fail", e, new Object[] { pathFile });
			}
		}

	}

	/**
	 * 使用sftp 下载文件
	 * 
	 * @param source
	 *            ftp服务器文件，如"/home/omc/ylong/sftp/INTPahcfg.tar.gz"
	 * @param dst
	 *            客户端存放下载文件路径，如"D:\\INTPahcfg.tar.gz"
	 * @throws CommonException
	 */
	public int get(String source, String dst) throws CommonException {
		int workStatus = FTPConstant.FILE_STATU_NO_FINISH;
		logger.info("下载文件");
		if (channelSftp == null) {
			connect();
		}

		SftpATTRS attr;
		// OutputStream out;

		try {
			attr = channelSftp.stat(source);
			// out = new FileOutputStream(dst);
			long fileSize = attr.getSize();
			fileProgressMonitor = new FileProgressMonitor(fileSize);
			channelSftp.get(source, dst, fileProgressMonitor); //
			workStatus = FTPConstant.FILE_STATU_FINISH;
			// chSftp.get(filename, out, new FileProgressMonitor(fileSize)); //
		} catch (Exception e) {
			logger.error("下载文件[" + source + "]失败！", e);
			workStatus = FTPConstant.FILE_STATU_EXCEPTION;
			throw new CommonException("sftp.get.fail", e, new Object[] { source });
		} finally {
			if (fileProgressMonitor != null) {
				fileProgressMonitor.stop();
			}
		}
		return workStatus;
	}

	// public void setFtpServerConfig(FtpServerConfig ftpServerConfig) {
	// this.ftpServerConfig = ftpServerConfig;
	// }

	/**
	 * sftp doesn't support the commands like 'bin' or 'ascii' that ftp
	 * supports. sftp不支持'bin' 或者 'ascii'的传输模式
	 */
	public void setMode() {

	}

//	public static void main(String[] args) {
//		Date now = new Date();
//		//今天读取昨天的
//		now.setDate(now.getDate()-1);
//		// 获取FTP
//		SysFtpServerConfig ftpServerConfig = (SysFtpServerConfig) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_FTP_SERVER_CONFIG,
//				Constant.TASK_FTP_SERVER_CODE);
//		// 获取目录信息
//		SysParam param = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, "SEND_LIST_OUTPUT_PATH");
//		SmsDailyfee dailyfee = null;
//		// 开启Ftp连接
//		FtpClient client = new FtpClient(ftpServerConfig);
//		client.init();
//		
//		SmsDailyfee desc = client.smsDailyfeeDao.findDailyfeeDateDesc();
//		String descDate =  DateUtil.format(desc.getDate(), "yyyyMMdd");
//		
//		String dir = DateUtil.format(now, "yyyyMMdd");
//		String dirPath = param.getParamvalue() + dir;
//		if(client.testDir(dirPath)){
//			List<String> list = client.list(dirPath);
//			String targetPath = "";
//			Map fileData = new HashMap();
//			try {
//				for (String objFile : list) {
//					//如果当天上传过
//					if(descDate.equals(objFile.substring(0, objFile.indexOf(".")))){
//						logger.debug("文件已经生成 "+descDate);
//						return;
//					}else{
//						String suffix = SmsYfeeJob.getFileSuffix(objFile);
//						targetPath = CommonUtil.getAttachmentFilePath() + FTPConstant.SERVER_PATH_SEPARATOR + CommonUtil.getUUID() + SmsYfeeJob.getFileSuffix(objFile);
//						fileData.put(suffix, targetPath);
//						fileData.put(suffix+"Name", objFile);
//					}
//				}
//				
//				if(!fileData.containsKey(".fin")){
//					logger.debug("没有发现FIN文件，停止上传目录文件 "+dir);
//					return;
//				}
//				//发现.stat文件后
//				if(fileData.containsKey(".stat")){
//					dailyfee = new SmsDailyfee();
//					int istatus = client.get(dirPath + FTPConstant.SERVER_PATH_SEPARATOR + fileData.get(".statName"),fileData.get(".stat").toString());
//					if (istatus == 1) {
//						List<String> dataLine = SmsYfeeJob.readerFile(fileData.get(".stat").toString());
//						if (dataLine != null && dataLine.size() > 0) {
//							for (String string : dataLine) {
//								dailyfee = SmsYfeeJob.getDailyfeeData(string.split("\t"));
//								dailyfee.setDate(DateUtil.parseDate(DateUtil.format(now), "yyyy-MM-dd"));
//								client.smsDailyfeeDao.add(dailyfee);
//							}
//						}else{
//							logger.debug("读取文件数九为空  "+dir);
//							return;
//						}
//					}else{
//						logger.debug("文件下载失败  "+dir);
//						return;
//					}
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				client.disconnect();
//			}
//		}else{
//			logger.debug("没有发现目录："+dir);
//		}
//	}
//	public SmsDailyfeeDao smsDailyfeeDao = (SmsDailyfeeDao) ApplicationContextManager.getBean("smsDailyfeeDao");
//
//	public static void main(String[] args) {
//		// 获取FTP
//		SysFtpServerConfig ftpServerConfig = (SysFtpServerConfig) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_FTP_SERVER_CONFIG,
//				Constant.TASK_FTP_SERVER_CODE);
//		// 获取目录信息
//		SysParam param = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, "STATISTICAL_SEND_LOG_PATH");
//		// 开启Ftp连接
//		FtpClient client = new FtpClient(ftpServerConfig);
//		client.init();
//
//		SmsDailyfee dailyfee = null;
//		String descDate = "";
//		SmsDailyfee desc = client.smsDailyfeeDao.findDailyfeeDateDesc();
//		if (desc != null) {
//			desc.getDate().setDate(desc.getDate().getDate() + 1);
//
//			descDate = DateUtil.format(desc.getDate(), "yyyy-MM-dd");
//		}
//		List<String> list = client.list(param.getParamvalue());
//		String targetPath = "";
//
//		List<String> isFindDateFile = new ArrayList<String>();
//		List<String> isFindDateFileName = new ArrayList<String>();
//		try {
//			for (String object : list) {
//				targetPath = CommonUtil.getAttachmentFilePath() + FTPConstant.SERVER_PATH_SEPARATOR + CommonUtil.getUUID() + client.getFileSuffix(object);
//				// 如果找到符合的时间,拉取一个文件 ,否则拉取全部文件
//				String fileDate = object.substring(0, object.indexOf("."));
//				if (descDate != null && !"".equals(descDate)) {
//					if (descDate.equals(fileDate)) {
//						isFindDateFile.clear();
//						isFindDateFileName.clear();
//
//						isFindDateFile.add(targetPath);
//						isFindDateFileName.add(object);
//						break;
//					} else {
//						Date dDate = DateUtil.parseDate(descDate, "yyyy-MM-dd");
//						Date fDate = DateUtil.parseDate(fileDate, "yyyy-MM-dd");
//						if (dDate.getTime() < fDate.getTime()) {
//							isFindDateFile.add(targetPath);
//							isFindDateFileName.add(object);
//						}
//					}
//				} else {
//					isFindDateFile.add(targetPath);
//					isFindDateFileName.add(object);
//				}
//			}
//
//			for (int i = 0; i < isFindDateFile.size(); i++) {
//				dailyfee = new SmsDailyfee();
//				int istatus = client.get(param.getParamvalue() + FTPConstant.SERVER_PATH_SEPARATOR + isFindDateFileName.get(i), isFindDateFile.get(i));
//				if (istatus == 1) {
//					String[] dataLine = client.readerFile(isFindDateFile.get(i));
//					if (dataLine != null && dataLine.length > 0) {
//						dailyfee = client.getDailyfeeData(dataLine);
//						client.smsDailyfeeDao.add(dailyfee);
//					}
//				}
//
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			client.disconnect();
//		}
//	}
//
//	public String getFileSuffix(String fileName) {
//		if (fileName.lastIndexOf(".") != -1) {
//			return fileName.substring(fileName.lastIndexOf("."), fileName.length());
//		} else {
//			return ".txt";
//		}
//	}
//
//	public SmsDailyfee getDailyfeeData(String[] dataLine) {
//		SmsDailyfee dailyfee = new SmsDailyfee();
//		dailyfee.setActivityId(Integer.valueOf(dataLine[0]));
//		dailyfee.setActivityName(dataLine[1]);
//		dailyfee.setSystemId(Integer.valueOf(dataLine[2]));
//		dailyfee.setSystemName(dataLine[3]);
//		dailyfee.setDepartmentId(Integer.valueOf(dataLine[4]));
//		dailyfee.setDepartmentName(dataLine[5]);
//		dailyfee.setSendRound(Integer.valueOf(dataLine[6]));
//		dailyfee.setAmount(BigDecimal.valueOf(Long.valueOf(dataLine[7])));
//		dailyfee.setTotalCount(Integer.valueOf(dataLine[8]));
//		dailyfee.setReceiptCount(Integer.valueOf(dataLine[9]));
//		dailyfee.setSuccCount(Integer.valueOf(dataLine[10]));
//		dailyfee.setDate(DateUtil.parseDate(dataLine[11], "yyyy-MM-dd"));
//		System.out.println(dailyfee);
//		return dailyfee;
//	}
//
//	public String[] readerFile(String targetPath) {
//		boolean bl = false;
//		// Reader Stream
//		InputStreamReader isr = null;
//		BufferedReader br = null;
//
//		String[] dataLine = null;
//		try {
//			// 解析文件
//			isr = new InputStreamReader(new FileInputStream(targetPath), "gbk");
//
//			br = new BufferedReader(isr);
//			dataLine = br.readLine().split(",");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			// 关闭读取流和FTP
//			try {
//				br.close();
//				isr.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
//		return dataLine;
//	}
}
