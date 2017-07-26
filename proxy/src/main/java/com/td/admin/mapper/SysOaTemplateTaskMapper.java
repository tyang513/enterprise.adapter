package com.td.admin.mapper;

import java.util.List;

import com.td.admin.entity.SysOaTemplateTask;

public interface SysOaTemplateTaskMapper {
	   /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oa_templatetask
     *
     * @mbggenerated Sun Aug 11 16:44:43 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oa_templatetask
     *
     * @mbggenerated Sun Aug 11 16:44:43 CST 2013
     */
    int insert(SysOaTemplateTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oa_templatetask
     *
     * @mbggenerated Sun Aug 11 16:44:43 CST 2013
     */
    int insertSelective(SysOaTemplateTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oa_templatetask
     *
     * @mbggenerated Sun Aug 11 16:44:43 CST 2013
     */
    SysOaTemplateTask selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oa_templatetask
     *
     * @mbggenerated Sun Aug 11 16:44:43 CST 2013
     */
    int updateByPrimaryKeySelective(SysOaTemplateTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oa_templatetask
     *
     * @mbggenerated Sun Aug 11 16:44:43 CST 2013
     */
    int updateByPrimaryKey(SysOaTemplateTask record);
    
    
    List<SysOaTemplateTask> queryApprovalChain(SysOaTemplateTask record);
    
    void insertBatch(List<SysOaTemplateTask> approvalChain);
    
    void deleteTemplate(Long templateid);
    
    public List<SysOaTemplateTask> selectByTemplateId(Long templateId);
    
    //List<FinSalesSalesapprovechainstep> queryChainChangeSalesTemplateStep(OaTemplatetask task);
}