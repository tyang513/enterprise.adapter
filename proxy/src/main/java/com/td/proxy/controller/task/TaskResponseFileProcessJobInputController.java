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
import com.td.proxy.entity.task.TaskResponseFileProcessJobInput;
import com.td.proxy.page.task.TaskResponseFileProcessJobInputPage;
import com.td.proxy.service.task.TaskResponseFileProcessJobInputService;
 
/**
 * 
 * <br>
 * <b>功能：</b>TaskResponseFileProcessJobInputController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */ 
@Controller
@RequestMapping("/taskResponseFileProcessJobInput") 
public class TaskResponseFileProcessJobInputController extends BaseController{
	
	public final static Logger logger = Logger.getLogger(TaskResponseFileProcessJobInputController.class);
	
	@Autowired
	private TaskResponseFileProcessJobInputService taskResponseFileProcessJobInputService; 
		
	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(TaskResponseFileProcessJobInputPage page) throws Exception {
		List<TaskResponseFileProcessJobInput> rows = taskResponseFileProcessJobInputService.queryByList(page);
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
	public Map<String, Object> save(TaskResponseFileProcessJobInput entity) throws Exception {
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			taskResponseFileProcessJobInputService.insert(entity);
			return getSuccessMessage("响应文件处理任务添加成功!");
		} else {
			taskResponseFileProcessJobInputService.updateByPrimaryKey(entity);
			return getSuccessMessage("响应文件处理任务修改成功!");
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
		TaskResponseFileProcessJobInput entity = (TaskResponseFileProcessJobInput) JSONObject.toBean(jsonForm.getJSONObject("entity"), TaskResponseFileProcessJobInput.class);

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			taskResponseFileProcessJobInputService.insert(entity);
			return getSuccessMessage("合作伙伴添加成功!");
		} else {
			taskResponseFileProcessJobInputService.updateByPrimaryKey(entity);
			return getSuccessMessage("合作伙伴修改成功!");
		}
	}
	
	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		TaskResponseFileProcessJobInput entity = taskResponseFileProcessJobInputService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}
	
	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id) throws Exception {
		taskResponseFileProcessJobInputService.deleteByPrimaryKey(id);
		return getSuccessMessage("响应文件处理任务删除成功!");
	}
}
