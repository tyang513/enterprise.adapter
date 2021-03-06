package com.td.admin.mapper;

import java.util.List;
import java.util.Map;

import com.td.admin.entity.SysProcessInstance;


public interface SysProcessInstanceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_INTEGRAL_MARKET_PROCESSINSTANCE
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_INTEGRAL_MARKET_PROCESSINSTANCE
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    int insert(SysProcessInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_INTEGRAL_MARKET_PROCESSINSTANCE
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    int insertSelective(SysProcessInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_INTEGRAL_MARKET_PROCESSINSTANCE
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    SysProcessInstance selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_INTEGRAL_MARKET_PROCESSINSTANCE
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    int updateByPrimaryKeySelective(SysProcessInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_INTEGRAL_MARKET_PROCESSINSTANCE
     *
     * @mbggenerated Fri Aug 09 11:00:05 CST 2013
     */
    int updateByPrimaryKey(SysProcessInstance record);


	List<SysProcessInstance> queryProcessInstances(SysProcessInstance record);
    
    Long queryProcessInstancesTotalCount(SysProcessInstance record);
    
    void updatePI(SysProcessInstance record);
    
    List<SysProcessInstance> getFinIntegralMarketProcessinstance(String processcode);
    
    public List<SysProcessInstance> selectByMap(Map<String, Object> sheetId);
    
    /**
     * 查询订单对应的所有运行中流程
     * @param condition		参数包括sheetId、sheetType
     * @return
     */
    List<SysProcessInstance> findProcessInstances(Map condition);
    
}