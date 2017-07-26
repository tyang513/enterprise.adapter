package com.td.admin.ehcache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.td.common.util.ApplicationContextManager;

/**
 * 刷新缓存Controller
 * @author lianjie 2013-09-02
 */
@Controller
@RequestMapping("finRefreshCache")
public class FinRefreshCacheController {
	
	/**
	 * 刷新缓存
	 * @return Map
	 */
	@RequestMapping(value="/refreshCache.do")
	@ResponseBody
	public Map<String, Object> refreshCache(String cacheName){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			SysCacheWrapper.refreshCache(cacheName);
			resultMap.put("success", "刷新缓存"+cacheName+"成功!");
		}catch (Exception e) {
			resultMap.put("error", "刷新缓存"+cacheName+"失败!");
		}
		return resultMap;
	}
	
	/**
	 * 刷新所有缓存
	 * @return Map
	 */
	@RequestMapping(value="/refreshCacheAll.do")
	@ResponseBody
	public Map<String, Object> refreshCacheAll(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			SysCacheWrapper.refreshCacheAll();
			resultMap.put("success", "刷新所有缓存成功!");
		}catch (Exception e) {
			resultMap.put("error", "刷新所有缓存失败!");
		}
		return resultMap;
	}
	//test
	public static void main(String[] args) {
		FinRefreshCacheController cacheController = (FinRefreshCacheController) ApplicationContextManager.getBean("finRefreshCacheController");
		SysCacheWrapper.getValue("dicItemCache", "Bank");
		SysCacheWrapper.getValue("dicItemCache", "Bank");
		cacheController.refreshCacheAll();
		SysCacheWrapper.getValue("dicItemCache", "Bank");
	}
}
