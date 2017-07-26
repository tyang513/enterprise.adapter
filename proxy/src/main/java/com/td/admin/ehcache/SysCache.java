package com.td.admin.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

/**
 * @author lianjie 2013-09-09
 *
 */
public class SysCache {
	
	private static final Logger logger = Logger.getLogger(SysCache.class);
	
	/**
	 * 查询缓存值
	 * @param cacheName 缓存名
	 * @param key 缓存KEY
	 * @return 缓存值
	 */
	public static Object getValue(String cacheName,String key){
		Cache cache = SysCacheManager.getCache(cacheName);
		Object result = null;
		if(cache!=null){
			Element element = cache.get(key);
			if(element!=null){
				result = element.getValue();
				//logger.debug("cacheName:"+cacheName+",getValue:key="+key+",value="+result);
			}
		}else
			logger.debug("无对应缓存:"+cacheName);
		
		return result;
	}

	/**
	 * 设置缓存值
	 * @param cacheName 缓存名
	 * @param key 缓存键
	 * @param value 缓存值
	 */
	public static void setValue(String cacheName, String key, Object value) {
		Cache cache = SysCacheManager.getCache(cacheName);
		if(cache!=null)
			cache.put(new Element(key, value));
		else
			logger.debug("无对应缓存:"+cacheName);
		logger.debug("cacheName:"+cacheName+",setValue="+key+",value="+value);
	}
	
	/**
	 * 清除单个缓存
	 * @param cacheName 缓存名
	 */
	public static void removeElementAll(String cacheName) {
		Cache cache = SysCacheManager.getCache(cacheName);
		if(cache==null){
			logger.debug("无对应缓存cacheName:"+cacheName+"");
			return;
		}
		cache.removeAll();
		logger.debug("cacheName:"+cacheName+",removeElementAll");
	}

	/**
	 * 清除所有缓存
	 */
	public static void clearAll() {
		SysCacheManager.clearAll();
		logger.debug("refreshAll");
	}
}
