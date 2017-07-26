package com.td.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.td.admin.constant.CacheConstant;
import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysCacheInfo;
import com.td.admin.entity.SysDicItem;
import com.td.admin.service.CacheService;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;
import com.td.common.util.UIUtil;


@Controller
@RequestMapping("cache")
public class CacheController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(CacheController.class);
	
	@Autowired
	private CacheService cacheService; 
		

	@RequestMapping(value="/cachePage.do")
	public String cachePage(SysCacheInfo record,HttpServletRequest request,HttpServletResponse response){
		return "view/config/cache/systemCache";
	}
	
	
	@RequestMapping(value="/queryCache.do")
	@ResponseBody
	public String queryCache(SysCacheInfo record,HttpServletRequest request,HttpServletResponse response){
		String gridJson = "";
		
		Long rows = new Long(request.getParameter("rows"));
		Long page = new Long(request.getParameter("page"));

		record.setNumPerPage(rows);
		record.setPageNum(page);
		record.setBeginIndex(rows*page-rows);
		PageBean pageBean = cacheService.queryCache(record);
		/**
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(pageBean.getExtend().get("list"));
		**/
		String messeageInfo = UIUtil.getJSONFromList(pageBean.getExtend().get("list"));
		//gridJson = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),messeageInfo);
		gridJson = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),messeageInfo);
		//gridJson = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),jsonArray.toString());
		return gridJson;
	}
	
	@RequestMapping(value="/removeCache.do")
	@ResponseBody
	public Body removeCache(@RequestParam String cachename){
		Body body = new Body();
		boolean flag = SysCacheWrapper.refreshCache(cachename);
		if(flag){
			body.success();
			body.setMessage("刷新缓存成功");
		}
		return body;
	}
	
	/**
	 * 从缓存中查询多个数据字典Map集合
	 * @param dicNames 字典条目名 多个用","逗号隔开
	 * @return Map结果
	 */
	@RequestMapping(value="/findDicItemForMap.do")
	@ResponseBody
	public Map<String, Object> findDicItemForMap(@RequestParam String dicNames){
		logger.debug("findDicItem:dicNames="+dicNames);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] dicNameArray = dicNames.split(",");
		for (int i = 0; i < dicNameArray.length; i++) {
			//返回结果Map:key的命名规则为    字典项名称
			//返回结果Map:value为多级树形结构 FinDicItem.childrenList
			resultMap.put(dicNameArray[i], SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_DIC_ITEM, dicNameArray[i]));
		}
		return resultMap;
	}
	
	/**
	 * 从缓存中查询数据字典集合
	 * @param dicName 字典条目名
	 * @param parentId 父条目ID
	 * @param isCheckAll 是否添加全部选项
	 * @return list结果
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/findDicItem.do")
	@ResponseBody
	public Object findDicItem(@RequestParam String dicName,@RequestParam(value="parentId",required = false) Long parentId,@RequestParam Boolean isCheckAll){
		logger.debug("findDicItem:dicName="+dicName);
		List<SysDicItem> result = new ArrayList<SysDicItem>();
		//添加全部选项
		if(isCheckAll){
			SysDicItem dicItem = new SysDicItem();
			dicItem.setDicitemkey("");
			dicItem.setDicitemvalue("全部");
			result.add(dicItem);
		}
		List<SysDicItem> dicItemList = (List<SysDicItem>)SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_DIC_ITEM, dicName);
		//查询缓存结果
		if(parentId!=null && parentId>0){
			for (int i = 0; dicItemList!=null && i < dicItemList.size(); i++) {
				SysDicItem dicItem = dicItemList.get(i);
				if(parentId.equals(dicItem.getParentid())){
					result.add(dicItem);
					logger.debug("dicItemValue:"+dicItem.getDicitemvalue());
				}
			}
		}else{
			for (int i = 0; dicItemList!=null && i < dicItemList.size(); i++) {
				SysDicItem dicItem = dicItemList.get(i);
				if(dicItem.getParentid()==null||dicItem.getParentid()==0){
					result.add(dicItem);
					logger.debug("dicItemValue:"+dicItem.getDicitemvalue());
				}
			}
		}
		return result;
	}
}
