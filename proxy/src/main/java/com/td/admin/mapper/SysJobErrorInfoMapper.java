package com.td.admin.mapper;

import java.util.List;
import java.util.Map;

import com.td.admin.entity.SysJobErrorInfo;

public interface SysJobErrorInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_JOB_ERRORINFO
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_JOB_ERRORINFO
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    int insert(SysJobErrorInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_JOB_ERRORINFO
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    int insertSelective(SysJobErrorInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_JOB_ERRORINFO
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    SysJobErrorInfo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_JOB_ERRORINFO
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    int updateByPrimaryKeySelective(SysJobErrorInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_JOB_ERRORINFO
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    int updateByPrimaryKeyWithBLOBs(SysJobErrorInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_JOB_ERRORINFO
     *
     * @mbggenerated Fri Aug 09 14:44:19 CST 2013
     */
    int updateByPrimaryKey(SysJobErrorInfo record);
    
    
    Long getPagingCount(SysJobErrorInfo jobErrorInfo);
    
    List<SysJobErrorInfo> queryJobError(SysJobErrorInfo record);

    List<SysJobErrorInfo> getPagingList(Map<String, Object> condMap);
    
    int selectCountqueryJobError(SysJobErrorInfo record);
}