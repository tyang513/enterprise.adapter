package com.td.admin.mapper;

import java.util.List;

import com.td.admin.entity.SysAuditLog;

public interface SysAuditLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_AUDIT_LOG
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_AUDIT_LOG
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    int insert(SysAuditLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_AUDIT_LOG
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    int insertSelective(SysAuditLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_AUDIT_LOG
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    SysAuditLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_AUDIT_LOG
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    int updateByPrimaryKeySelective(SysAuditLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_AUDIT_LOG
     *
     * @mbggenerated Mon Sep 02 18:58:53 CST 2013
     */
    int updateByPrimaryKey(SysAuditLog record);

	List<SysAuditLog> find(SysAuditLog auditLog);

	int findCount(SysAuditLog auditLog);
}