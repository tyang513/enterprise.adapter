package com.td.proxy.dao.admin;

import java.util.List;

import com.talkingdata.base.dao.BaseDao;
import com.td.proxy.entity.admin.Partner;

/**
 * 
 * <br>
 * <b>功能：</b>PartnerDao<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */
public interface PartnerDao<T> extends BaseDao<T> {

	/**
	 * 查询所有合作伙伴
	 * @return
	 */
	public List<Partner> findAllList();
}
