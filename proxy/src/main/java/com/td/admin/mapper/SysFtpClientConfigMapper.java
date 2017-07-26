package com.td.admin.mapper;

import java.util.List;
import java.util.Map;

import com.td.admin.entity.SysFtpClientConfig;

public interface SysFtpClientConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FtpClientConfig
     *
     * @mbggenerated Thu Jun 20 17:32:28 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FtpClientConfig
     *
     * @mbggenerated Thu Jun 20 17:32:28 CST 2013
     */
    int insert(SysFtpClientConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FtpClientConfig
     *
     * @mbggenerated Thu Jun 20 17:32:28 CST 2013
     */
    int insertSelective(SysFtpClientConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FtpClientConfig
     *
     * @mbggenerated Thu Jun 20 17:32:28 CST 2013
     */
    SysFtpClientConfig selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FtpClientConfig
     *
     * @mbggenerated Thu Jun 20 17:32:28 CST 2013
     */
    int updateByPrimaryKeySelective(SysFtpClientConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FtpClientConfig
     *
     * @mbggenerated Thu Jun 20 17:32:28 CST 2013
     */
    int updateByPrimaryKey(SysFtpClientConfig record);
    
    /**
     * 查询所有的ftp客户端配置
     * @param record
     * @return
     */
    List<SysFtpClientConfig> getPagingList(SysFtpClientConfig ftpClientConfig);
    
    /**
     * 总数
     * @param record
     * @return
     */
    Long getPagingCount(SysFtpClientConfig record);

    List<SysFtpClientConfig> findByInCode(Map<String, Object> condMap);
    
    /**
     * 根据code查找指定ftp客户端配置
     * @param code ftp客户端配置code
     * @return FtpClientConfig
     */
    SysFtpClientConfig getFtpClientConfigByCode(String code);
}