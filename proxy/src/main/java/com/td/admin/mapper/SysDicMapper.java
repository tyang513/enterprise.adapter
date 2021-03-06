package com.td.admin.mapper;

import java.util.List;
import java.util.Map;

import com.td.admin.entity.SysDic;

public interface SysDicMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_DIC
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_DIC
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    int insert(SysDic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_DIC
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    int insertSelective(SysDic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_DIC
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    SysDic selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_DIC
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    int updateByPrimaryKeySelective(SysDic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FIN_DIC
     *
     * @mbggenerated Fri Aug 09 10:15:02 CST 2013
     */
    int updateByPrimaryKey(SysDic record);
    
    //******** extends ***********************//
    
    List<SysDic> selectAllDicts(Map<String, Object> params);
    
    SysDic selectByDicKey(Long id);
    
    SysDic selectByName(String name);

	Long selectTotal(Map<String, Object> params);

}