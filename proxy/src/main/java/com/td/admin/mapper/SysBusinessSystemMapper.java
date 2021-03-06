package com.td.admin.mapper;

import java.util.List;

import com.td.admin.entity.SysBusinessSystem;

public interface SysBusinessSystemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_BUSINESS_SYSTEM
     *
     * @mbggenerated Tue Aug 20 09:29:36 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_BUSINESS_SYSTEM
     *
     * @mbggenerated Tue Aug 20 09:29:36 CST 2013
     */
    int insert(SysBusinessSystem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_BUSINESS_SYSTEM
     *
     * @mbggenerated Tue Aug 20 09:29:36 CST 2013
     */
    int insertSelective(SysBusinessSystem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_BUSINESS_SYSTEM
     *
     * @mbggenerated Tue Aug 20 09:29:36 CST 2013
     */
    SysBusinessSystem selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_BUSINESS_SYSTEM
     *
     * @mbggenerated Tue Aug 20 09:29:36 CST 2013
     */
    int updateByPrimaryKeySelective(SysBusinessSystem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_BUSINESS_SYSTEM
     *
     * @mbggenerated Tue Aug 20 09:29:36 CST 2013
     */
    int updateByPrimaryKey(SysBusinessSystem record);

	List<SysBusinessSystem> findAll();
}