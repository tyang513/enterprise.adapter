package com.td.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.entity.SysFtpServerConfig;
import com.td.admin.mapper.SysFtpServerConfigMapper;
import com.td.common.bean.PageBean;
import com.td.common.constant.CommonConstant;


/** 
 * @description: FTP服务器配置 服务类
 * @author: cmh  2013-6-25
 * @version: 1.0
 * @modify: 
 * @Copyright: 公司版权拥有
 */
@Service(value = "ftpServerConfigService")
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class FtpServerConfigService {
    private final static Logger logger = Logger.getLogger(FtpServerConfigService.class);
    
    @Autowired
    private SysFtpServerConfigMapper sysFtpServerConfigMapper;
    
    /**
     * FTP服务器配置查询
     * @param ftpServerConfig FTP服务器配置对象
     * @param page 翻页
     * @return
     */
    public Map<String, Object> queryFtpServerConfigList(SysFtpServerConfig ftpServerConfig, PageBean page){
    	logger.debug("queryFtpClientConfigList!");
        Map<String, Object> retureList = new HashMap<String, Object>();
        //总记录数
        Long totalCount = sysFtpServerConfigMapper.getPagingCount(ftpServerConfig);
        ftpServerConfig.setBeginIndex((page.getPage() - 1) * page.getRows());
        ftpServerConfig.setNumPerPage(page.getRows());
        
        List<SysFtpServerConfig> list = sysFtpServerConfigMapper.getPagingList(ftpServerConfig);
        
        retureList.put(CommonConstant.PAGE_LIST_DATA_TOTAL, totalCount);
        retureList.put(CommonConstant.PAGE_LIST_DATA_ROWS, list);
        
        return retureList;
    }
    
    /**
     * 根据服务器名称得到FTP服务器配置
     * @param servername
     * @return
     */
    public SysFtpServerConfig getFtpServerConfig(String servername) {
        return sysFtpServerConfigMapper.getFtpServerConfig(servername);
    }
    
    /**
     * 根据id得到FTP服务器配置
     * @param id
     * @return
     */
    public SysFtpServerConfig getFtpServerConfigById(Long id) {
        return sysFtpServerConfigMapper.selectByPrimaryKey(id);
    }
    
    public SysFtpServerConfig getFtpServerConfigByCode(String code) {
        Map<String, Object> condMap = new HashMap<String, Object>();
        condMap.put("code", code);
        return sysFtpServerConfigMapper.getFtpServerConfigByMap(condMap);
    }
    
    /**
     * 根据id更新到FTP服务器配置
     * @param id
     * @return
     */
    public int updateFtpServerConfigById(SysFtpServerConfig ftpServerConfig) {
        return sysFtpServerConfigMapper.updateByPrimaryKeySelective(ftpServerConfig);
    }
    
    /**
     * 根据id更新到FTP服务器配置
     * @param id
     * @return
     */
    public int getPasswordByPassword(SysFtpServerConfig ftpServerConfig) {
        Map<String, Object> condMap = new HashMap<String, Object>();
        
        condMap.put("password", ftpServerConfig.getOldPassword());
        condMap.put("id", ftpServerConfig.getId());
        return sysFtpServerConfigMapper.getFtpServerPassword(condMap);
    }
}
