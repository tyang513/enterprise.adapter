package com.td.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.entity.SysFtpClientConfig;
import com.td.admin.mapper.SysFtpClientConfigMapper;
import com.td.common.bean.PageBean;
import com.td.common.constant.CommonConstant;


/** 
 * @description: ftp客户端配置 服务类
 * @author: cmh  2013-6-24
 * @version: 1.0
 * @modify: 
 * @Copyright: 公司版权拥有
 */
@Service(value = "ftpClientConfigService")
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class FtpClientConfigService {
    private final static Logger logger = Logger.getLogger(FtpClientConfigService.class);
    
    @Autowired
    private SysFtpClientConfigMapper sysFtpClientConfigMapper;
    
    public Map<String, Object> queryFtpClientConfigList(SysFtpClientConfig ftpClientConfig, PageBean page){
    	logger.debug("queryFtpClientConfigList!");
        Map<String, Object> retureList = new HashMap<String, Object>();
        //总记录数
        Long totalCount = sysFtpClientConfigMapper.getPagingCount(ftpClientConfig);
        ftpClientConfig.setBeginIndex((page.getPage() - 1) * page.getRows());
        ftpClientConfig.setNumPerPage(page.getRows());
        
        List<SysFtpClientConfig> list = sysFtpClientConfigMapper.getPagingList(ftpClientConfig);
        
        retureList.put(CommonConstant.PAGE_LIST_DATA_TOTAL, totalCount);
        retureList.put(CommonConstant.PAGE_LIST_DATA_ROWS, list);
        
        return retureList;
    }
    
    /**
     * 根据id得到FTP客户端配置
     * @param id
     * @return
     */
    public SysFtpClientConfig getftpClientConfigById(Long id) {
        return sysFtpClientConfigMapper.selectByPrimaryKey(id);
    }
    
    /**
     * 根据id更新到FTP客户端配置
     * @param id
     * @return
     */
    public int updateFtpClientConfigById(SysFtpClientConfig ftpClientConfig) {
        return sysFtpClientConfigMapper.updateByPrimaryKeySelective(ftpClientConfig);
    }
    
    /**
     * 根据id得到FTP客户端配置
     * @param id
     * @return
     */
    public SysFtpClientConfig getFtpClientConfigByCode(String code) {
        return sysFtpClientConfigMapper.getFtpClientConfigByCode(code);
    }
}
