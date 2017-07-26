package com.td.proxy.dao.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.talkingdata.base.dao.BaseDao;
import com.td.proxy.entity.admin.ExtendedAttri;

/**
 * 
 * <br>
 * <b>功能：</b>ExtendedAttriDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface ExtendedAttriDao extends BaseDao<ExtendedAttri> {
	public ExtendedAttri selectBytemplateId(@Param(value="templateId")Integer templateId, @Param(value="extendCode")String extendCode);
	
	public List<ExtendedAttri> queryBytemplateId(Integer templateId);

	public void deleteByTemplateId(Integer templateId);
}
