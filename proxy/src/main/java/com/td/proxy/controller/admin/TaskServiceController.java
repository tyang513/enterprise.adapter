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
import com.td.proxy.entity.admin.TaskService;
import com.td.proxy.page.admin.TaskServicePage;
import com.td.proxy.service.admin.TaskServiceService;
 
/**
 * 
 * <br>
 * <b>功能：</b>TaskServiceController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */ 
@Controller
@RequestMapping("/taskService") 
public class TaskServiceController extends BaseController{
	
	public final static Logger logger = Logger.getLogger(TaskServiceController.class);
	
	@Autowired
	private TaskServiceService taskServiceService; 
		
	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(TaskServicePage page) throws Exception {
		List<TaskService> rows = taskServiceService.queryByList(page);
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
	public Map<String, Object> save(TaskService entity) throws Exception {
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			taskServiceService.insert(entity);
			return getSuccessMessage("服务注册添加成功!");
		} else {
			taskServiceService.updateByPrimaryKey(entity);
			return getSuccessMessage("服务注册修改成功!");
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
		TaskService entity = (TaskService) JSONObject.toBean(jsonForm.getJSONObject("entity"), TaskService.class);

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			taskServiceService.insert(entity);
			return getSuccessMessage("合作伙伴添加成功!");
		} else {
			taskServiceService.updateByPrimaryKey(entity);
			return getSuccessMessage("合作伙伴修改成功!");
		}
	}
	
	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		TaskService entity = taskServiceService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}
	
	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id) throws Exception {
		taskServiceService.deleteByPrimaryKey(id);
		return getSuccessMessage("服务注册删除成功!");
	}
}
