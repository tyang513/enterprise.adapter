package com.td.admin.mapper;

import java.util.List;
import java.util.Map;

import com.td.admin.entity.SysOaInstance;

public interface SysOaInstanceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OA_OAINSTANCE
     *
     * @mbggenerated Tue Aug 27 09:24:25 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OA_OAINSTANCE
     *
     * @mbggenerated Tue Aug 27 09:24:25 CST 2013
     */
    int insert(SysOaInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OA_OAINSTANCE
     *
     * @mbggenerated Tue Aug 27 09:24:25 CST 2013
     */
    int insertSelective(SysOaInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OA_OAINSTANCE
     *
     * @mbggenerated Tue Aug 27 09:24:25 CST 2013
     */
    SysOaInstance selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OA_OAINSTANCE
     *
     * @mbggenerated Tue Aug 27 09:24:25 CST 2013
     */
    int updateByPrimaryKeySelective(SysOaInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OA_OAINSTANCE
     *
     * @mbggenerated Tue Aug 27 09:24:25 CST 2013
     */
    int updateByPrimaryKey(SysOaInstance record);

	List<SysOaInstance> find(SysOaInstance oaInstance);

	int findCount(SysOaInstance oaInstance);
	
	SysOaInstance getOainstance(Map candidate);
	List<SysOaInstance> queryOainstance(SysOaInstance oaInstance);
}