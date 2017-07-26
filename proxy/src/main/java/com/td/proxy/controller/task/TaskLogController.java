package com.td.proxy.controller.task;

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
import com.td.proxy.entity.task.TaskLog;
import com.td.proxy.page.task.TaskLogPage;
import com.td.proxy.service.task.TaskLogService;
 
/**
 * 
 * <br>
 * <b>功能：</b>TaskLogController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */ 
@Controller
@RequestMapping("/taskLog") 
public class TaskLogController extends BaseController{
	
	public final static Logger logger = Logger.getLogger(TaskLogController.class);
	
	@Autowired
	private TaskLogService taskLogService; 
		
	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(TaskLogPage page) throws Exception {
		List<TaskLog> rows = taskLogService.queryByList(page);
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
	public Map<String, Object> save(TaskLog entity) throws Exception {
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			taskLogService.insert(entity);
			return getSuccessMessage("任务日志添加成功!");
		} else {
			taskLogService.updateByPrimaryKey(entity);
			return getSuccessMessage("任务日志修改成功!");
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
		TaskLog entity = (TaskLog) JSONObject.toBean(jsonForm.getJSONObject("entity"), TaskLog.class);

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			taskLogService.insert(entity);
			return getSuccessMessage("合作伙伴添加成功!");
		} else {
			taskLogService.updateByPrimaryKey(entity);
			return getSuccessMessage("合作伙伴修改成功!");
		}
	}
	
	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		TaskLog entity = taskLogService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}
	
	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id) throws Exception {
		taskLogService.deleteByPrimaryKey(id);
		return getSuccessMessage("任务日志删除成功!");
	}
}
