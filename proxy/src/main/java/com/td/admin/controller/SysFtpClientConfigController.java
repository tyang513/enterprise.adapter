package com.td.admin.controller;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.base.web.BaseController;
import com.td.admin.entity.SysFtpClientConfig;
import com.td.admin.service.FtpClientConfigService;
import com.td.common.bean.PageBean;
 
/**
 * 
 * <br>
 * <b>功能：</b>SysFtpClientConfigController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014, <br>
 */ 
@Controller
@RequestMapping("/sysFtpClientConfig") 
public class SysFtpClientConfigController extends BaseController{
	
	@Autowired
	private FtpClientConfigService ftpClientConfigService; 
		
	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(SysFtpClientConfig ftpClientConfig,PageBean page) throws Exception {
		
	    Map<String, Object> list = ftpClientConfigService.queryFtpClientConfigList(ftpClientConfig, page);
	    
		return list;
	}
	
	/**
	 * 新建或修改
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(SysFtpClientConfig entity) throws Exception {
		if (entity.getId() != null && StringUtils.isNotBlank(entity.getId().toString())) {
			ftpClientConfigService.updateFtpClientConfigById(entity);
			return getSuccessMessage("FTP客户端配置修改成功!");
		}
		return getFailureMessage("FTP客户端配置修改失败");
	}
	
	/**
	 * 新建或修改，用于复杂表单
	 * @param formData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveData.do", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Map<String, Object> saveData(@RequestBody Map<String, Object> formData) throws Exception {
		JSONObject jsonForm = JSONObject.fromObject(formData);
		SysFtpClientConfig entity = (SysFtpClientConfig) JSONObject.toBean(jsonForm.getJSONObject("entity"), SysFtpClientConfig.class);
	
		if (entity.getId() != null && StringUtils.isNotBlank(entity.getId().toString())) {
			ftpClientConfigService.updateFtpClientConfigById(entity);
			return getSuccessMessage("FTP客户端配置修改成功!");
		}
		return getFailureMessage("FTP客户端配置修改失败!");
	}
	
	@RequestMapping("/getId.do")
	@ResponseBody
	public Map<String, Object> getId(String id) throws Exception {
		SysFtpClientConfig entity = ftpClientConfigService.getftpClientConfigById(Long.parseLong(id));
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}
}