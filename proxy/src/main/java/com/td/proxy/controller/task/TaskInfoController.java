package com.td.proxy.controller.task;

import java.util.ArrayList;
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
import com.td.proxy.entity.task.TaskInfo;
import com.td.proxy.entity.task.TaskRequestFileProcessJobInput;
import com.td.proxy.entity.task.TaskResponseFileProcessJobInput;
import com.td.proxy.page.task.TaskInfoPage;
import com.td.proxy.service.task.TaskInfoService;
import com.td.proxy.service.task.TaskRequestFileProcessJobInputService;
import com.td.proxy.service.task.TaskResponseFileProcessJobInputService;
 
/**
 * 
 * <br>
 * <b>功能：</b>TaskInfoController<br>
 * <b>作者：</b>code generator<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2014<br>
 */ 
@Controller
@RequestMapping("/taskInfo") 
public class TaskInfoController extends BaseController{
	
	protected final static Logger logger = Logger.getLogger(TaskInfoController.class);
	
	@Autowired
	private TaskInfoService taskInfoService;
	
	@Autowired
	private TaskRequestFileProcessJobInputService taskRequestFileProcessJobInputService;
	
	@Autowired
	private TaskResponseFileProcessJobInputService taskResponseFileProcessJobInputService;
		
	@RequestMapping("/list.do") 
	@ResponseBody
	public Map<String, Object> list(TaskInfoPage page) throws Exception {
		page.setStatusArray(page.getStatus().split(","));
		page.setStatus(null);
		List<TaskInfo> rows = taskInfoService.queryByList(page);
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
	public Map<String, Object> save(TaskInfo entity) throws Exception {
		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			taskInfoService.insert(entity);
			return getSuccessMessage("任务信息添加成功!");
		} else {
			taskInfoService.updateByPrimaryKey(entity);
			return getSuccessMessage("任务信息修改成功!");
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
		TaskInfo entity = (TaskInfo) JSONObject.toBean(jsonForm.getJSONObject("entity"), TaskInfo.class);

		if (entity.getId() == null || StringUtils.isBlank(entity.getId().toString())) {
			taskInfoService.insert(entity);
			return getSuccessMessage("合作伙伴添加成功!");
		} else {
			taskInfoService.updateByPrimaryKey(entity);
			return getSuccessMessage("合作伙伴修改成功!");
		}
	}
	
	@RequestMapping("/findById.do")
	@ResponseBody
	public Map<String, Object> findById(String id) throws Exception {
		TaskInfo entity = taskInfoService.selectByPrimaryKey(id);
		if (entity == null) {
			return getFailureMessage("没有找到对应的记录!");
		} else {
			return getSuccessData(entity);
		}
	}
	
	@RequestMapping("/deleteById.do")
	@ResponseBody
	public Map<String, Object> delete(String[] id) throws Exception {
		taskInfoService.deleteByPrimaryKey(id);
		return getSuccessMessage("任务信息删除成功!");
	}
	
	@RequestMapping("/taskProcessList.do")
	@ResponseBody
	public Map<String, Object> taskProcessList(String taskCode) throws Exception {
		TaskRequestFileProcessJobInput requestJobInput = taskRequestFileProcessJobInputService.selectByTaskCode(taskCode);
		TaskResponseFileProcessJobInput responseJobInput = taskResponseFileProcessJobInputService.selectByTaskCode(taskCode);
		List<Object> rows = new ArrayList<Object>();
		if (responseJobInput != null){
			rows.add(responseJobInput);
		}
		rows.add(requestJobInput);
		return getGridData(rows.size(), rows);
	}
}
