package com.td.um.dao;

import java.util.List;
import java.util.Map;

import com.talkingdata.base.dao.BaseDao;
import com.td.um.entity.Resource;

/**
 * 
 * <br>
 * <b>功能：</b>ResourceDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014, <br>
 */
public interface ResourceDao extends BaseDao<Resource> {

	List<Resource> findList(Resource page);
	
	List<Resource> getResourceByIds(List<Integer> resourIds);
	
	List<Resource> getResourceByArray(Map<String, Object> map);

	List<Resource> getAuthMenu(Map<String, Object> map);

	List<Resource> getSystemMenu();

	List<Resource> getExtResourceByType(Map<String, Object> map);
	
	List<Resource> findByAppRidResourceTypeIdResourceCode(Map<String, Object> param);
	
	/**
	 * 根据应用系统id ，资源类型 和 parent_resource_rid =0 查找 root 的资源id
	 * @param re
	 * @return
	 */
	List<Resource> findByAppRidResourceTypeIdParentResourceRid(Resource re);

	List<Resource> getAdminMenu(Map<String,Object> map);

	Resource getResourcebyCodeAndAppCode(Map<String, Object> paramMap);

	List<Resource> getResourceByRole(Map<String,Object> map);
	
	List<Resource> findAllResource(Map<String, Object> map);
}
