package com.td.proxy.controller.admin;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.base.web.BaseController;
import com.td.proxy.entity.admin.CloudUser;
import com.td.proxy.page.admin.CloudUserPage;
import com.td.proxy.service.admin.CloudUserService;
 
/**
 * 
 * <br>
 * <b>功能：</b>CloudUserController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */ 
@Controller
@RequestMapping("/cloudUser") 
public class CloudUserController extends BaseController{
	
	public final static Logger logger = Logger.getLogger(CloudUserController.class);
	
	@Autowired
	private CloudUserService cloudUserService; 
		
	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(CloudUserPage page) throws Exception {
		List<CloudUser> rows = cloudUserService.queryByList(page);
		return getGridData(page.getPager().getRowCount(), rows);
	}
	
	/**
	 * 新建或修改
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(CloudUser entity) throws Exception {
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			cloudUserService.insert(entity);
			return getSuccessMessage("云用户添加成功!");
		} else {
			cloudUserService.updateByPrimaryKey(entity);
			return getSuccessMessage("云用户修改成功!");
		}
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
		CloudUser entity = (CloudUser) JSONObject.toBean(jsonForm.getJSONObject("entity"), CloudUser.class);

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			cloudUserService.insert(entity);
			return getSuccessMessage("合作伙伴添加成功!");
		} else {
			cloudUserService.updateByPrimaryKey(entity);
			return getSuccessMessage("合作伙伴修改成功!");
		}
	}
	
	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		CloudUser entity = cloudUserService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}
	
	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id) throws Exception {
		cloudUserService.deleteByPrimaryKey(id);
		return getSuccessMessage("云用户删除成功!");
	}
}
