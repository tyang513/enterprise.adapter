package com.td.admin.ehcache;
import org.apache.log4j.Logger;

import com.td.admin.constant.CacheConstant;
import com.td.admin.service.DictService;
import com.td.admin.service.FtpClientConfigService;
import com.td.admin.service.FtpServerConfigService;
import com.td.admin.service.ParamService;
import com.td.common.util.ApplicationContextManager;
import com.td.proxy.service.admin.CloudUserService;
import com.td.proxy.service.admin.TaskServiceService;

/**
 * 缓存对外操作类
 * @author lianjie 2013-09-09
 *
 */
public class SysCacheWrapper {
	
	private static final Logger logger = Logger.getLogger(SysCacheWrapper.class);
	
//	private static SecurityService securityService = UmRmiServiceFactory.getSecurityService();
	
    /**
     * 获取缓存值
     * @param cacheName 缓存名
     * @param elementKey 节点KEY
     * @return
     */
	public static Object getValue(String cacheName,String key){
		Object result = SysCache.getValue(cacheName,key);
		if(result==null){
			logger.debug("缓存:"+cacheName+"中无对应参数:"+key+",开始查询数据库.");
			result = searchCacheValue(cacheName,key);
			if(result!=null){
				SysCache.setValue(cacheName,key,result);
				logger.debug("数据库中查找到对应参数:key="+key+",value="+result+".");
			}else{
				logger.debug("缓存与数据库中没有对应参数:key="+key+".");
			}
		}else{
			//logger.debug("缓存:"+cacheName+"中查询对应参数:key="+key+",value="+result+".");
			logger.debug("缓存:"+cacheName+"中查询对应参数:key="+key);
		}
	    return result;
	}
	
	/**
	 * 从数据库查找缓存对应值
	 * @param cacheName
	 * @param key
	 * @return
	 */
	private static Object searchCacheValue(String cacheName, String key) {
		Object result = null;
		//查询对应数据库数据
		if(CacheConstant.CACHE_NAME_DIC_ITEM.equals(cacheName)){
			//数据字典
			DictService dictService =(DictService) ApplicationContextManager.getBean("dictService");
			//查询数据字典并组成树形结构
			result = dictService.findDicItemForTree(key);
		}else if(CacheConstant.CACHE_NAME_SYSTEM_PARAM.equals(cacheName)){
			//系统参数
			ParamService paramService =(ParamService) ApplicationContextManager.getBean("paramService");
			result = paramService.getParamByKey(key);
		}else if(CacheConstant.CACHE_NAME_ROLE.equals(cacheName)){
			//角色
//			result = securityService.getRoleByRolename(key);
		}else if(CacheConstant.CACHE_NAME_USER.equals(cacheName)){
			//用户
//			result = securityService.getUserByUmid(key);
		}else if(CacheConstant.CACHE_NAME_ROLE_OF_USER.equals(cacheName)){
			//角色对应用户
//			result = securityService.getUsersByRolename(key);
		}else if(CacheConstant.CACHE_NAME_FTP_SERVER_CONFIG.equals(cacheName)){
			//FTP客户端配置
			FtpServerConfigService ftpServerConfigService =(FtpServerConfigService) ApplicationContextManager.getBean("ftpServerConfigService");
			result = ftpServerConfigService.getFtpServerConfigByCode(key);
		}else if(CacheConstant.CACHE_NAME_FTP_CLIENT_CONFIG.equals(cacheName)){
			//FTP服务器配置
			FtpClientConfigService ftpClientConfigService =(FtpClientConfigService) ApplicationContextManager.getBean("ftpClientConfigService");
			result = ftpClientConfigService.getFtpClientConfigByCode(key);
		}
		else if (CacheConstant.CACHE_NAME_CLOUD_USER.equals(cacheName)){
			CloudUserService cloudUserService = ApplicationContextManager.getBean(CloudUserService.class);
			result = cloudUserService.getCloudUserByCode(key);
		}
		else if (CacheConstant.CACHE_NAME_TASK_SERVICE.equals(cacheName)){
			TaskServiceService taskServiceService = ApplicationContextManager.getBean(TaskServiceService.class);
			result = taskServiceService.findaskServiceByCode(key);
		}
		return result;
	}
	
	/**
	 * 更新缓存内容
	 * @param cachename 
	 */
	public static boolean refreshCache(String cacheName) {
		logger.error("清除缓存:"+cacheName);
		try{
			SysCache.removeElementAll(cacheName);
		}catch (Exception e) {
			logger.error("清除缓存内容失败",e);
			return false;
		}
		return true;
	}
	
	/**
	 * 更新所有缓存内容
	 * @param cachename 
	 */
	public static boolean refreshCacheAll() {
		logger.error("清除所有缓存");
		try{
			SysCache.clearAll();
		}catch (Exception e) {
			logger.error("清除缓存内容失败",e);
			return false;
		}
		return true;
	}
	
}
