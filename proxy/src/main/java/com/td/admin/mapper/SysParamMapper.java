package com.td.admin.mapper;

import java.util.List;
import java.util.Map;

import com.td.admin.entity.SysParam;

public interface SysParamMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_SYSTEM_PARAM
     *
     * @mbggenerated Fri Aug 09 10:22:34 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_SYSTEM_PARAM
     *
     * @mbggenerated Fri Aug 09 10:22:34 CST 2013
     */
    int insert(SysParam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_SYSTEM_PARAM
     *
     * @mbggenerated Fri Aug 09 10:22:34 CST 2013
     */
    int insertSelective(SysParam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_SYSTEM_PARAM
     *
     * @mbggenerated Fri Aug 09 10:22:34 CST 2013
     */
    SysParam selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_SYSTEM_PARAM
     *
     * @mbggenerated Fri Aug 09 10:22:34 CST 2013
     */
    int updateByPrimaryKeySelective(SysParam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_SYSTEM_PARAM
     *
     * @mbggenerated Fri Aug 09 10:22:34 CST 2013
     */
    int updateByPrimaryKey(SysParam record);
    
    //******** extends ***********************//
    
    List<SysParam> selectAllParamsWithPW();
    
    SysParam selectByParamKey(String key);

	Long findTotal(SysParam param);

	List<SysParam> findParams(SysParam param);
	
    int updateParamByMap(Map<String,Object> params);

    List<SysParam> selectExcludePW(SysParam param);
	
}