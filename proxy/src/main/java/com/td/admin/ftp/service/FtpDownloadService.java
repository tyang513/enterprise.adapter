package com.td.admin.ftp.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.constant.CacheConstant;
import com.td.admin.constant.FTPConstant;
import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysFtpServerConfig;
import com.td.admin.mapper.SysFtpServerConfigMapper;
import com.td.common.exception.CommonException;


/** 
 * @description: 
 * @author: LiAnJie  2014-02-21
 * @version: 1.0
 * @modify: 
 * @Copyright: 公司版权拥有
 */
@Service(value = "ftpDownloadService")
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS, rollbackFor = Throwable.class)
public class FtpDownloadService {
    
    private final static Logger logger = Logger.getLogger(FtpDownloadService.class);
    
    @Autowired
    private SysFtpServerConfigMapper ftpServerConfigMapper;
    
    public void downloadFile(String jobClassName) {
        FtpClient ftpClient = null;
        Map<String, Object> condMap = new HashMap<String, Object>();
        
        //查询sftp server 服务器配置
        //需要搜索的ftp服务器code名称
        String[] ftpDownloadSvrNames = null;
        if(SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, FTPConstant.FTPDOWNLOAD_SEARCH_SERVERS_KEY) != null) {
            ftpDownloadSvrNames = SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, FTPConstant.FTPDOWNLOAD_SEARCH_SERVERS_KEY).toString().split(",");
        }
        
        if(ftpDownloadSvrNames == null) {
            logger.warn("系统参数为配置" + FTPConstant.FTPDOWNLOAD_SEARCH_SERVERS_KEY + "值，FtpDownloadJob无法查找指定的ftp服务器！");
            return;
        }
        
        List<String> ftpServerCodes = new ArrayList<String>();
        
        for (int i = 0; i < ftpDownloadSvrNames.length; i++) {
            ftpServerCodes.add(ftpDownloadSvrNames[i]);
        }
        condMap.put("ftpServerCodes", ftpServerCodes);
        
        List<SysFtpServerConfig> ftpServerConfigList = ftpServerConfigMapper.getFtpServerListConfigByMap(condMap);

        //初始化变量
        String finFlag = FTPConstant.DEFAULT_FILE_FIN_FLAG;
        
        String ftpServerConfigRootPath = "";//ftp服务器端配置的路径
        
        String ftpDownloadSvrSubDir = SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, FTPConstant.FTPDOWNLOAD_FTP_SERVER_SUB_DIR_KEY).toString();//服务器子路径
        String ftpDownloadClientDir = SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, FTPConstant.CLIENT_INPUT_DIR_KEY).toString();//客户端路径
        String ftpDownloadPath = "";//ftp服务器路径
            
        String ftpClientConfigCode = "";//客户端配置的code

        String filePrefix = "";//文件后缀
        String downloadFileName = "";//download的文件名
        
        //查找所有的ftp服务器配置，依次连接不同的服务器
        for (SysFtpServerConfig ftpServerConfig : ftpServerConfigList) {
            try {
                //根据systemparam配置的server对应client找到对应的client端
                ftpClientConfigCode = SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, ftpServerConfig.getCode()).toString();
                ftpServerConfigRootPath = ftpServerConfig.getRootpath();
                //组装成完整路径
                ftpDownloadPath = ftpServerConfigRootPath + ftpDownloadSvrSubDir;
                if (StringUtils.isNotBlank(ftpClientConfigCode)) {
                    //连接ftp服务器
                    ftpClient = new FtpClient(ftpServerConfig);
                    ftpClient.connect();
                    //判断服务器目录
                    if (!ftpClient.testDir(ftpDownloadPath)) {
                        logger.warn("ftp服务器[" + ftpServerConfig.getServername() + "]的目录[" + ftpDownloadPath + "]不存在！");
                        ftpClient.disconnect();
                        continue;
                    }
                    List<String> fileList = ftpClient.list(ftpDownloadPath);
                    
                    for (String fileName : fileList) {
                        try {
                        	//得到文件的后缀名
                            filePrefix = fileName.substring(fileName.lastIndexOf(".") + 1);
                            if (filePrefix.equalsIgnoreCase(finFlag)) {
                            	//去掉完成标识的后缀名得到已经完成的文件名
                                downloadFileName = fileName.substring(0, fileName.length() - filePrefix.length() - 1);
                                //服务器对应文件路径
                                String ftpSevPath = ftpDownloadPath + downloadFileName;
                                //客户端对应文件路径
                                String clientPath = ftpDownloadClientDir + downloadFileName;
                                //开始下载
                                int status = ftpClient.get(ftpSevPath, clientPath);
                                //下载成功
                                if(status == FTPConstant.FILE_STATU_FINISH) {
                                	//删除服务器文件,
                                    ftpClient.rm(ftpSevPath);
                                    ftpClient.rm(ftpSevPath + "." + finFlag);
                                    //添加下载成功标识文件
                                    File filename = new File(clientPath + "." + finFlag);
                                    if (!filename.exists()) {
                                        try {
                                            filename.createNewFile();
                                        }
                                        catch (IOException e) {
                                            logger.error("FtpDownload job 异常", e);
                                        }
                                    }
                                }
                            }
                        } catch (CommonException e) {
                            logger.error("FtpDownload 文件下载异常", e);
                        } catch (Exception e) {
                            logger.error("FtpDownload 文件下载异常", e);
                        }
                    }
                }
                else {
                    logger.warn("根据Ftp服务器的配置信息的code，在缓存中未找到对应的客户端的配置code，无法给下载的文件指定客户端的路径！");
                }
            }
            catch (CommonException e) {
                logger.error("FtpDownload 文件下载异常", e);
            } catch (Exception e) {
                logger.error("FtpDownload 文件下载异常", e);
            }finally {
                if(ftpClient != null) {
                    try {
                        ftpClient.disconnect();
                    }
                    catch (CommonException e) {
                        logger.error("FtpDownload 关闭连接异常", e);
                    }
                }
            }
        }
    }
}
