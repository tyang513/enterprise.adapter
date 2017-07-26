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
import com.td.admin.entity.SysFtpServerConfig;
import com.td.admin.service.FtpServerConfigService;
import com.td.common.bean.PageBean;
 
/**
 * 
 * <br>
 * <b>功能：</b>SysFtpServerConfigController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014, <br>
 */ 
@Controller
@RequestMapping("/sysFtpServerConfig") 
public class SysFtpServerConfigController extends BaseController{
	
	@Autowired
	private FtpServerConfigService ftpServerConfigService; 
		
	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(SysFtpServerConfig ftpServerConfig,PageBean page) throws Exception {
		
	    Map<String, Object> list = ftpServerConfigService.queryFtpServerConfigList(ftpServerConfig, page);
	    
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
	public Map<String, Object> save(SysFtpServerConfig entity) throws Exception {
		if (entity.getId() != null && StringUtils.isNotBlank(entity.getId().toString())) {
			ftpServerConfigService.updateFtpServerConfigById(entity);
			return getSuccessMessage("FTP服务器配置修改成功!");
		}
		return getFailureMessage("FTP服务器配置修改失败");
	}
	
	/**
	 * 新建或修改，用于复杂表单
	 * 
	 * @param formData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveData.do", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Map<String, Object> saveData(@RequestBody Map<String, Object> formData) throws Exception {
		JSONObject jsonForm = JSONObject.fromObject(formData);
		SysFtpServerConfig entity = (SysFtpServerConfig) JSONObject.toBean(jsonForm.getJSONObject("entity"), SysFtpServerConfig.class);

		if (entity.getId() != null && StringUtils.isNotBlank(entity.getId().toString())) {
			ftpServerConfigService.updateFtpServerConfigById(entity);
			return getSuccessMessage("FTP服务器配置修改成功!");
		}
		return getFailureMessage("FTP服务器配置修改失败");
	}
	
	@RequestMapping("/getId.do")
	@ResponseBody
	public Map<String, Object> getId(String id) throws Exception {
		SysFtpServerConfig entity = ftpServerConfigService.getFtpServerConfigById(Long.parseLong(id));
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}
}