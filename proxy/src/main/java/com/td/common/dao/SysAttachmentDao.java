package com.td.common.dao;

import com.talkingdata.base.dao.BaseDao;
import com.td.common.entity.SysAttachment;

/**
 * 
 * <br>
 * <b>功能：</b>SysAttachmentDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014, <br>
 */
public interface SysAttachmentDao<T> extends BaseDao<T> {
	int insertSelective(SysAttachment attachment);
}
