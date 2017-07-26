package com.td.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.casclient.User;
import com.td.admin.entity.SysProcessConfig;
import com.td.admin.entity.SysProcessInstance;
import com.td.admin.entity.SysProcessTask;
import com.td.admin.entity.SysProcessTaskConfig;
import com.td.admin.service.ProcessManageService;
import com.td.common.util.WorkFlowManageUtil;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("myTaskManage")
public class MyTaskController {

	private static final Logger logger = Logger.getLogger(MyTaskController.class);

	@Autowired
	private ProcessManageService processManageService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "queryMyTask.do")
	@ResponseBody
	public Map queryMyFinTask(HttpServletRequest request, HttpServletResponse response, SysProcessTask finTask) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Long page = new Long(request.getParameter("page"));
		Long rows = new Long(request.getParameter("rows"));
		User user = (User) request.getSession().getAttribute("user");
		String userId = user.getLoginName();
		finTask.setAssignerumid(userId);
		result = processManageService.queryMyFinTask(finTask, page, rows);
		return result;
	}

	@RequestMapping(value = "queryMyCompleteTask.do")
	@ResponseBody
	public Map queryMyCompleteFinTask(HttpServletRequest request, HttpServletResponse response) {
		Map result = new HashMap();
		User user = (User) request.getSession().getAttribute("user");
		Long page = new Long(request.getParameter("page"));
		Long rows = new Long(request.getParameter("rows"));
		String processName = request.getParameter("processName");
		String taskName = request.getParameter("taskName");
		String sheetId = request.getParameter("sheetId");
		//String candidateUser = request.getParameter("candidateUser");
		String starterName = request.getParameter("starterName");
		String startTime = request.getParameter("taskCompleteStartTime");
		String endTime = request.getParameter("taskCompleteEndTime");
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("processName", processName);
		condition.put("taskName", taskName);
		condition.put("sheetId", sheetId);
		//condition.put("candidateUser", candidateUser);
		condition.put("starterName", starterName);
		condition.put("startTime", startTime);
		condition.put("endTime", endTime);
		result = processManageService.queryMyCompleteFinTask(user.getLoginName(), page, rows, condition);
		return result;
	}

	@RequestMapping(value = "claimTask.do")
	@ResponseBody
	public Map claimFinTask(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		Long finTaskId = new Long(request.getParameter("finTaskId"));
		String userId = user.getLoginName();
		String info = "";
		Map map = null;
		try {
			info = WorkFlowManageUtil.claimFinTask(finTaskId, userId);
			ObjectMapper om = new ObjectMapper();
			map = om.readValue(info, Map.class);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "completeApprove.do")
	@ResponseBody
	public Map completeApprove(HttpServletRequest request, HttpServletResponse response) {
		String jsonData = "";
		String finTaskId = request.getParameter("finTaskId");
		String approve = request.getParameter("approve");
		String memo = request.getParameter("memo");
		String approveIndex = request.getParameter("approveIndex");
		String isSuggests = request.getParameter("isSuggests");
		Map map = null;
		try {
			SysProcessTask finTask = processManageService.getFinTask(new Long(finTaskId));
			boolean isSuggest = finTask.getTaskname().indexOf("征询") >= 0; // 如果是征询
			if (approve.equals("0")) {
				SysProcessInstance fpi = processManageService.getFinProcessInstance(finTask.getProcessinstanceid());
				String backUserId = request.getParameter("backUserId");
				if (isSuggest || Boolean.valueOf(isSuggests)) {
					logger.info("征求人不同意,直接结束流程");
					jsonData = WorkFlowManageUtil.completeApprove(finTaskId, approve, memo);
				} else if (!isSuggest && backUserId.equals(fpi.getStarterumid()) && approveIndex.equals("-1")) {
					//退回发起人直接结束任务
					jsonData = WorkFlowManageUtil.completeApprove(finTaskId, approve, memo);
				} else {
					User user = (User) request.getSession().getAttribute("user");
					memo = "由" + user.getLoginName() + "退回:" + memo;
					jsonData = WorkFlowManageUtil.rollbackApprove(finTaskId, backUserId, memo, approveIndex);
				}
			} else {
				//批准
				jsonData = WorkFlowManageUtil.completeApprove(finTaskId, approve, memo);
			}
			ObjectMapper om = new ObjectMapper();
			map = om.readValue(jsonData, Map.class);
			if (!"200".equals(map.get("status"))) {
				//			    FinAuditLog auditLog = new FinAuditLog();
				//		        User user = (User) request.getSession().getAttribute("user");
				//		        auditLog.setActorumid(user.getLoginName());
				//		        auditLog.setActorname(user.getName());
				//		        auditLog.setCreatetime(new Date());
				//		        auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
				//		        auditLog.setOperationtype(CommonConstant.AUDIT_LOG_OPERATION_TYPE_RE_ASSIGNEE);
				//		        auditLog.setTargettype(CommonConstant.AUDIT_LOG_TARGET_TYPE_PROCESS);
				//                auditLog.setTargetid(finTaskId);
				//		        auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
				String message = map.get("message") != null ? "任务ID:" + finTaskId + "," + map.get("message") : "流程ID:" + finTaskId;
				logger.error(message);
				//                auditLog.setDescription();
				map.put("message", "审批任务出现异常");
				//		        auditLogService.addAuditLog(auditLog);
			}
		} catch (Exception e) {
			logger.error("审批异常", e);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "completeTask.do")
	@ResponseBody
	public Map complateFinTask(HttpServletRequest request, HttpServletResponse response) {
		String info = "";
		String finTaskId = request.getParameter("finTaskId");
		String memo = request.getParameter("memo");
		Map map = null;
		try {
			info = WorkFlowManageUtil.completeFinTask(finTaskId, memo, null);
			ObjectMapper om = new ObjectMapper();
			map = om.readValue(info, Map.class);
			if (!"200".equals(map.get("status"))) {
				String message = map.get("message") != null ? "任务ID:" + finTaskId + "," + map.get("message") : "流程ID:" + finTaskId;
				logger.error(message);
				map.put("message", "结束任务出现异常");
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "forwardOaTask.do", method = RequestMethod.POST)
	@ResponseBody
	public Map forwardOaTask(HttpServletRequest request, HttpServletResponse response) {
		Map<Object, Object> map = null;
		String info = null;
		try {
			String finTaskId = request.getParameter("finTaskId");
			String userId = request.getParameter("userId");
			String memo = request.getParameter("memo");
			info = WorkFlowManageUtil.forwardOaTask(finTaskId, userId, memo);
			ObjectMapper om = new ObjectMapper();
			map = om.readValue(info, Map.class);
			if (!"200".equals(map.get("status"))) {
				String message = map.get("message") != null ? "任务ID:" + finTaskId + "," + map.get("message") : "流程ID:" + finTaskId;
				logger.error(message);
				map.put("message", "转发任务出现异常");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "askOther.do", method = RequestMethod.POST)
	@ResponseBody
	public Map askOther(HttpServletRequest request, HttpServletResponse response) {
		Map<Object, Object> map = null;
		String info = "";
		try {
			String finTaskId = request.getParameter("finTaskId");
			String userId = request.getParameter("userId");
			String memo = request.getParameter("memo");
			info = WorkFlowManageUtil.askOther(finTaskId, userId, memo);
			ObjectMapper om = new ObjectMapper();
			map = om.readValue(info, Map.class);
			if (!"200".equals(map.get("status"))) {
				String message = map.get("message") != null ? "任务ID:" + finTaskId + "," + map.get("message") : "流程ID:" + finTaskId;
				logger.error(message);
				map.put("message", "任务征询出现异常");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return map;
	}

	@RequestMapping(value = "getNodifyFinTask.do")
	@ResponseBody
	public SysProcessTask getNodifyFinTask(HttpServletRequest request, HttpServletResponse response) {
		SysProcessTask task = null;
		String finTaskIdStr = request.getParameter("finTaskId");
		User user = (User) request.getSession().getAttribute("user");
		String userId = user.getLoginName();
		Long finTaskId = new Long(finTaskIdStr);
		task = processManageService.getFinTask(finTaskId);
		if (task.getStatus() != 1 || !userId.equals(task.getAssignerumid())) {
			task = null;
		}
		return task;
	}

	@RequestMapping(value = "initTaskHead.do")
	@ResponseBody
	public Map<String, Object> initTashHead(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Long finTaskId = new Long(request.getParameter("finTaskId"));
		SysProcessTask finTask = processManageService.getFinTask(finTaskId);
		SysProcessInstance fpi = processManageService.getFinProcessInstance(finTask.getProcessinstanceid());
		SysProcessConfig fpc = processManageService.getFinProcessConfig(fpi.getProcesscode());
		String[] taskCode = finTask.getTaskcode().split("-");
		SysProcessTaskConfig ftc = processManageService.getFinTaskConfig(fpc.getId(), taskCode[0]);
		map.put("finTaskId", finTaskId);
		map.put("processName", fpi.getProcessname());
		map.put("processInstanceId", fpi.getId());
		map.put("finTaskName", finTask.getTaskname());
		map.put("finTaskDescription", ftc.getTaskdescription());
//		map.put("finTaskIconUrl", "");
		map.put("sheetId", fpi.getSheetid());
		map.put("sheetType", fpi.getSheettype());
		map.put("applierName", fpi.getStartername());
		map.put("applyTime", fpi.getStarttime());
		if (finTask.getAssignerumid() != null && !finTask.getAssignerumid().equals("")) {
			map.put("operate", "handle");
		} else {
			map.put("operate", "show");
		}
		return map;
	}

	@RequestMapping(value = "initTaskApprove")
	@ResponseBody
	public Map<String, Object> initTaskApprove(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	@RequestMapping(value = "queryProcessConf.do")
	@ResponseBody
	public Object queryProcessConf(HttpServletRequest request, HttpServletResponse response) {
		//		result.put("value", processManageService.queryProcessConf());
		String q = request.getParameter("q");
		String name = "";
		logger.info(" q == " + q);
		if (q != null && !("".equals(q))) {
			name = q.trim();
		}
		return processManageService.queryProcessConf(name);
	}

	@RequestMapping(value = "queryMyVerifyingTask.do")
	@ResponseBody
	public Map queryMyValidateFinTask(HttpServletRequest request, HttpServletResponse response, SysProcessTask task) {
		Map<String, Object> result = new HashMap<String, Object>();
		Long page = new Long(request.getParameter("page"));
		Long rows = new Long(request.getParameter("rows"));
		User user = (User) request.getSession().getAttribute("user");
		String userId = user.getLoginName();
		task.setAssignerumid(userId);
		result = processManageService.queryMyValidateFinTask(task, page, rows);
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "queryMyTaskById.do")
	@ResponseBody
	public Map queryMyFinTaskById(HttpServletRequest request, HttpServletResponse response, SysProcessTask task) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Long page = new Long(1);
		Long rows = new Long(2);
		User user = (User) request.getSession().getAttribute("user");
		String userId = user.getLoginName();
		task.setAssignerumid(userId);
		result = processManageService.queryMyFinTask(task, page, rows);
		return result;
	}

	@RequestMapping(value = "querySuggestBackUser")
	@ResponseBody
	public Map<String, Object> querySuggestBackUser(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

}
