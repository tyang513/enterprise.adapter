package com.td.admin.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * 缓存管理累
 * @author lianjie 2013-09-09
 */
class SysCacheManager {
	
	/**
	 * 获取缓存对象
	 * @param cacheName 缓存名称
	 * @return 缓存对象
	 */
	public static Cache getCache(String cacheName){
		return CacheManager.getInstance().getCache(cacheName);
	}
	/**
	 * 清空所有缓存
	 */
	public static void clearAll(){
		CacheManager.getInstance().clearAll();
	}
	/**
	 * 关闭所有缓存
	 */
	public static void close(){
		CacheManager.getInstance().shutdown();
	}
}
