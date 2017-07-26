package com.td.proxy.service.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talkingdata.base.service.BaseService;
import com.td.common.util.Constants;
import com.td.common.util.JSONUtil;
import com.td.proxy.dao.admin.ExtendedAttriDao;
import com.td.proxy.dao.admin.ExtendedAttriDefinitionDao;
import com.td.proxy.entity.admin.ExtendedAttriDefinition;
import com.td.proxy.page.admin.ExtendedAttriDefinitionPage;
import com.td.proxy.page.admin.ExtendedAttriPage;

/**
 * 
 * <br>
 * <b>功能：</b>ExtendedAttriDefinitionService<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2014 <br>
 * <b>版权所有：<b>版权所有(C) 2013<br>
 */
@Service("extendedAttriDefinitionService")
public class ExtendedAttriDefinitionService<T> extends BaseService<T> {
	
	public final static Logger logger = Logger.getLogger(ExtendedAttriDefinitionService.class);
	
	@Autowired
	private ExtendedAttriDefinitionDao<T> dao;
	
	@Autowired
	private ExtendedAttriDao extendedAttriDao;

	public ExtendedAttriDefinitionDao<T> getDao() {
		return dao;
	}

	public boolean findExtendedAttriDefinitionByCode(String code) {
		int i = dao.findExtendedAttriDefinitionByCode(code);
		if (i != 0) {
			return true;
		} else {
		return false;
		}
	}
	
	
	/**
	 * 扩展属性定义数据导出，生成导出数据json
	 * @throws Exception 
	 */
	public Map<String,Object> createExtendedAttriDefData(Object[] id) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		/**
		 * id为空时，导出所有数据；否则导出选中数据
		 * 1.生成json数据
		 * 2.导出json文件
		 */
		List<ExtendedAttriDefinition> list = new ArrayList<ExtendedAttriDefinition>();
		if(id == null || id.length == 0) {
			ExtendedAttriDefinitionPage page = new ExtendedAttriDefinitionPage();
			page.getPager().setPageEnabled(false);
			list = (List<ExtendedAttriDefinition>) dao.queryByList(page);
			if(list == null || list.isEmpty()) {
				map.put(Constants.LOGIC_FAILURE, "无待导出的扩展属性定义信息数据！");
				return map;
			}
		} else {
			for(int i = 0; i < id.length; i++) {
				ExtendedAttriDefinition extendedAttriDef = (ExtendedAttriDefinition) this.dao.selectByPrimaryKey(id[i]);
				if(extendedAttriDef != null) {
					list.add(extendedAttriDef);
				}
			}
		}
		try {
//			String str = JSONUtil.objToJsonFormat(list);
			map.put(Constants.LOGIC_SUCCESS, list);
		} catch (Exception e) {
			map.put(Constants.LOGIC_FAILURE, "导出规则定义生成json文件异常！");
			return map;
		}
		return map;
	}
	
	/**
	 * 批处理导入数据
	 * @param str  json数据
	 * @param flag 是否强制覆盖(true 强制覆盖)
	 * @return
	 */
	public Map<String,String> extendedAttriDefData(String str, boolean flag) {
		
		Map<String,String> map = new HashMap<String, String>();
		List<ExtendedAttriDefinition> list = null;
		try {
			// 解析json字符串
			list = (List<ExtendedAttriDefinition>)JSONUtil.jsonToList(str, ExtendedAttriDefinition.class);
		} catch (Exception e) {
			map.put(Constants.LOGIC_FAILURE, "解析json文件出错！");
			return map;
		}
		
		if(list == null || list.isEmpty()) {
			map.put(Constants.LOGIC_FAILURE, "json文件无需要导入的数据！");
			return map;
		}
		
		map = this.insertExtendedAttriDef(map, list, flag);
		
		return map;
	}
	
	/**
	 * 数据导入数据库业务处理
	 * @param map
	 * @param list
	 * @param flag
	 * @return
	 */
	public Map<String, String> insertExtendedAttriDef(Map<String,String> map, List<ExtendedAttriDefinition> list, boolean flag) {
		Date d = new Date();
		for(ExtendedAttriDefinition extendedAttriDef : list) {
			ExtendedAttriDefinitionPage p = new ExtendedAttriDefinitionPage();
			p.setCode(extendedAttriDef.getCode());
			List<ExtendedAttriDefinition> extendedAttriDefList = (List<ExtendedAttriDefinition>) this.dao.queryByList(p);
			if(extendedAttriDefList != null && !extendedAttriDefList.isEmpty()) {
				if(extendedAttriDefList.size() > 1) {
					map.put(Constants.LOGIC_FAILURE, "数据库中存在重复的扩展属性定义【" + extendedAttriDefList.get(0).getCode() + "】");
					return map;
				}
				if(!flag)  {
					continue;
				}
				/**
				 *  强制覆盖前，清空原有数据
				 */
				
				map = this.clearExtendedAttriDef(extendedAttriDefList.get(0));
				if(map.containsKey(Constants.LOGIC_FAILURE)) {
					return map;
				}
			}
			
			extendedAttriDef.setId(null);
			extendedAttriDef.setCTime(d);
			this.dao.insert((T)extendedAttriDef);
		}
		
		return map;
	}
	
	/**
	 * 递归删除规则定义及下属表单信息
	 * json数据强制导入时，使用
	 * @param id
	 * @return
	 */
	private Map<String, String> clearExtendedAttriDef(ExtendedAttriDefinition extendedAttriDef) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		ExtendedAttriPage extendedAttriPage = new ExtendedAttriPage();
		extendedAttriPage.setExtendAttrId(extendedAttriDef.getId());
		int extendedAttriCount = extendedAttriDao.queryByCount(extendedAttriPage);
		if(extendedAttriCount > 0) {
			map.put(Constants.LOGIC_FAILURE, "规则定义【" + extendedAttriDef.getName() + "(" + extendedAttriDef.getCode() + ")" + "】已被计算规则使用！");
			return map;
		}
		
		this.dao.deleteByPrimaryKey(extendedAttriDef.getId());
		
		return map;
	}
}
