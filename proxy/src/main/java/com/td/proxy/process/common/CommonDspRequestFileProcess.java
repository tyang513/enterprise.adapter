package com.td.proxy.process.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.td.admin.constant.CacheConstant;
import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysFtpServerConfig;
import com.td.admin.entity.SysParam;
import com.td.common.constant.CommonConstant;
import com.td.common.util.DateUtil;
import com.td.common.util.HttpUtil;
import com.td.proxy.ProxyConstant;
import com.td.proxy.entity.admin.ExtendedAttri;
import com.td.proxy.entity.admin.RuleConfig;
import com.td.proxy.entity.admin.TaskService;
import com.td.proxy.entity.admin.Template;
import com.td.proxy.entity.task.TaskInfo;

public class CommonDspRequestFileProcess extends CommonRequestFileProcess {

	/**
	 * @param ftpServerConfig
	 * @param taskInfo
	 * @return
	 * @throws IOException
	 */
	public String downloadFile(SysFtpServerConfig ftpServerConfig, TaskInfo taskInfo) throws IOException {
		return super.downloadFile(ftpServerConfig, taskInfo);
	}

	/**
	 * @param decryptFilePath
	 * @param ruleConfigMap
	 * @throws Exception
	 */
	public void uploadFile2Cloud(String decryptFilePath, Map<String, RuleConfig> ruleConfigMap) throws Exception {
		SysParam sysParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, CommonConstant.SYSTEM_PARAM_ADVERTISER_ID);
		String advertiserid = sysParam.getParamvalue();
		// 第一步：新上传的受众文件,创建新的受众编号
		RuleConfig ruleConfig = ruleConfigMap.get(ProxyConstant.RULE_CONFIG_CREATE_SEGMENT_ID_SERVICE_CODE);
		TaskService service = (TaskService) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_TASK_SERVICE, ruleConfig.getContent());
		Map<String, Object> segmentIdMap = createSegmentId(service);
		Integer segmentid = (Integer) segmentIdMap.get("segmentid");
		String serialNumber = (String) segmentIdMap.get("snum");
		// 第二步：上传受众文件
		ruleConfig = ruleConfigMap.get(ProxyConstant.RULE_CONFIG_UPLOAD_SERVICE_CODE);
		service = (TaskService) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_TASK_SERVICE, ruleConfig.getContent());
		uploadFile(service, segmentid, advertiserid, decryptFilePath);
		
		TaskInfo updateTaskInfo = new TaskInfo();
		updateTaskInfo.setId(getTaskInfo().getId());
		updateTaskInfo.setRefCloudId(segmentid+"");
		updateTaskInfo.setAttr1(serialNumber);
		getTaskInfoService().updateByPrimaryKeySelective(updateTaskInfo);
		// 第三步：创建受众接口
		ruleConfig = ruleConfigMap.get(ProxyConstant.RULE_CONFIG_CREATE_SEGMENT_SERVICE_CODE);
		service = (TaskService) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_TASK_SERVICE, ruleConfig.getContent());
		createSegiment(service, segmentid);
		
		// 第四步：分发受众
		ruleConfig = ruleConfigMap.get(ProxyConstant.RULE_CONFIG_SWITCH_SEGMENT_SERVICE_CODE);
		service = (TaskService) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_TASK_SERVICE, ruleConfig.getContent());
		switchSegment(service, segmentid);
	}
	
	/**
	 * 创建的新的受众编号
	 * @throws Exception 
	 */
	private Map<String, Object> createSegmentId(TaskService taskService) throws Exception {
		SysParam sysParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, CommonConstant.SYSTEM_PARAM_ADVERTISER_ID);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("advertiserid", Integer.valueOf(sysParam.getParamvalue()));
		Map<String, Object> responseMap = HttpUtil.doPostJson(taskService.getService(), param, null, taskService.getType());
		return responseMap;
	}

	/**
	 * 上传文件
	 * @param service
	 * @param segmentid 受众id
	 * @param filePath 文件路径
	 * @param decryptFilePath 解密文件路径
	 * @throws Exception 
	 */
	private void uploadFile(TaskService service, Integer segmentid, String advertiserid, String filePath) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("segmentid", segmentid);
		File uploadFile = new File(filePath);
		paramMap.put("file", uploadFile);
//		paramMap.put("advertiserid", Integer.valueOf(advertiserid));
		String url = service.getService()+"?segmentid=" + segmentid + "&advertiserid="+Integer.valueOf(advertiserid);
		HttpUtil.doUploadFile(url, paramMap, service.getType());
	}
	
	/**
	 * 创建受众
	 * @param service 
	 * @param serialNumber
	 * @throws Exception 
	 */
	private void createSegiment(TaskService service, Integer segmentid) throws Exception {
		Map<String, Object> requestBodyMap = new HashMap<String, Object>();
		requestBodyMap.put("name", getTaskInfo().getTaskCode());
		requestBodyMap.put("id", segmentid);
		requestBodyMap.put("relation", 0); // 关系 0:none 1:and 2:or
		HttpUtil.doPostJson(service.getService(), null, requestBodyMap, service.getType());
	}
	
	/**
	 * 分发受众
	 * @param service 服务
	 * @param segmentid
	 * @throws Exception
	 */
	private void switchSegment(TaskService service, Integer segmentid) throws Exception {
		SysParam sysParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, CommonConstant.SYSTEM_PARAM_ADVERTISER_ID);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		TaskInfo taskInfo = getTaskInfo();
		paramMap.put("startdate", taskInfo.getAttr3());
		paramMap.put("enddate", taskInfo.getAttr4());
		Map<String, Object> requestBodyMap = new HashMap<String, Object>();
		requestBodyMap.put("advertiserID", Integer.valueOf(sysParam.getParamvalue()));
		requestBodyMap.put("segmentID", segmentid);
		
		requestBodyMap.put("adpID", String.valueOf(taskInfo.getAttr2())); // dsp广告主id   17:bigtree
		int duration = DateUtil.dateInterval(taskInfo.getAttr3(), taskInfo.getAttr4());
		requestBodyMap.put("duration", duration);
		HttpUtil.doPostJson(service.getService(), paramMap, requestBodyMap, service.getType());
	}
	
	/**
	 * @param srcfilePath 加密文件路径
	 * @param ruleConfigMap 规则配置集合
	 * @param template 模板
	 * @param decryptFilePath 解密之后文件存储路径
	 * @param extendedAttriMap 扩展属性集合
	 * @throws Exception
	 */
	public void decryptFile(String srcfilePath, Map<String, RuleConfig> ruleConfigMap, Template template, String decryptFilePath,
			Map<String, ExtendedAttri> extendedAttriMap) throws Exception {
		super.decryptFile(srcfilePath, ruleConfigMap, template, decryptFilePath, extendedAttriMap);
	}

}
