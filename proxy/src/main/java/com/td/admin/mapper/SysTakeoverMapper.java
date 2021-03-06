package com.td.admin.mapper;

import java.util.List;
import java.util.Map;

import com.td.admin.entity.SysTakeover;
import com.td.admin.entity.SysTakeoverExtends;

public interface SysTakeoverMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_SALES_TAKEOVERSETTING
     *
     * @mbggenerated Tue Aug 13 14:35:10 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_SALES_TAKEOVERSETTING
     *
     * @mbggenerated Tue Aug 13 14:35:10 CST 2013
     */
    int insert(SysTakeover record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_SALES_TAKEOVERSETTING
     *
     * @mbggenerated Tue Aug 13 14:35:10 CST 2013
     */
    int insertSelective(SysTakeover record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_SALES_TAKEOVERSETTING
     *
     * @mbggenerated Tue Aug 13 14:35:10 CST 2013
     */
    SysTakeover selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_SALES_TAKEOVERSETTING
     *
     * @mbggenerated Tue Aug 13 14:35:10 CST 2013
     */
    int updateByPrimaryKeySelective(SysTakeover record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_SALES_TAKEOVERSETTING
     *
     * @mbggenerated Tue Aug 13 14:35:10 CST 2013
     */
    int updateByPrimaryKey(SysTakeover record);
    
    //******** extends ***********************//
    
    /**
     * 查询某用户对应的记录
     * @param umid
     * @return
     */
    List<SysTakeoverExtends> selectByUmid(String umid);
    
    /**
     * 根据条件查询记录
     * @param params	只有userumid/targetuserumid为查询参数
     * @return
     */
    List<SysTakeoverExtends> selectByUmidOrTargetUmid(SysTakeover params);
    
    /**
     * 根据业务Key获取记录ID
     * @param vo
     * @return
     */
    SysTakeoverExtends selectByBusinessKey(SysTakeover vo);
    
    /**
     * 按条件分页查询（结果集）
     * @param params 包括userumid、targetuserumid、starttime、endtime、limitStart、limitEnd
     * @return
     */
    List<SysTakeoverExtends> selectByConditionsPagely(Map<String, Object> params);
    /**
     * 按条件分页查询（记录总数）
     * @param params 包括userumid、targetuserumid、starttime、endtime、limitStart、limitEnd
     * @return
     */
    int selectTotalCountByConditions(Map<String, Object> params);
    
    /**
     * 查询用户的接管者
     * @param params 包括userumid、currentTime、limitStart、pageRows
     * @return
     */
    List<SysTakeoverExtends> getTakeoverUsers(Map<String, Object> params);    

	int selectUserByTime(SysTakeover record);
    
	int selectTakeOverUserByTime(SysTakeover record);

	List<SysTakeover> findAccreditUserByUmid(String umid);
}