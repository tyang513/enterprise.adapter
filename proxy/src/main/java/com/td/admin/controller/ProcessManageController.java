package com.td.admin.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.casclient.User;
import com.td.admin.constant.CacheConstant;
import com.td.admin.ehcache.SysCacheWrapper;
import com.td.admin.entity.SysAuditLog;
import com.td.admin.entity.SysDicItem;
import com.td.admin.entity.SysOaInstance;
import com.td.admin.entity.SysOaTask;
import com.td.admin.entity.SysProcessInstance;
import com.td.admin.entity.SysProcessTask;
import com.td.admin.service.AuditLogService;
import com.td.admin.service.OaInstanceService;
import com.td.admin.service.ProcessManageService;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;
import com.td.common.constant.CommonConstant;
import com.td.common.util.UIUtil;
import com.td.common.util.WorkFlowManageUtil;

@Controller
@RequestMapping("workflow")
public class ProcessManageController {

	private static final Logger logger = LoggerFactory.getLogger(ProcessManageController.class);

	@Autowired
	private ProcessManageService processManageService;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private OaInstanceService oaInstanceService;

	@RequestMapping(value = "/queryProcessInstance.do")
	@ResponseBody
	public String queryProcessInstance(SysProcessInstance record, HttpServletRequest request, HttpServletResponse response) {
		String gridJson = "";
		if (record.getStatus() != null && record.getStatus() == 0) {
			record.setStatus(null);
		}
		Long rows = new Long(request.getParameter("rows"));
		Long page = new Long(request.getParameter("page"));
		record.setNumPerPage(rows);
		record.setPageNum(page);
		try {
			PageBean pageBean = processManageService.queryPI(record);
			String json = UIUtil.getJSONFromList(pageBean.getExtend().get("list"));
			gridJson = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(), json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gridJson;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/stopProcessInstance.do")
	@ResponseBody
	public Object stopProcessInstance(SysProcessInstance record, HttpServletRequest request, HttpServletResponse response) {
		Body body = new Body();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		boolean status = false;
		HashMap<String, String> mapObj = null;
		if ("".equals(request.getParameter("id")) || request.getParameter("id") == null) {
			logger.info("停止流程失败:流程不存在！");
			resultMap.put("message", "停止流程失败:流程不存在！");
			return resultMap;
		}
		record.setId(Long.parseLong(request.getParameter("id")));

		try {
			String json = WorkFlowManageUtil.stopProcessInstance(record.getId());
			// 解析返回数据
			mapObj = (HashMap<String, String>) JSONObject.toBean(JSONObject.fromObject(json), HashMap.class);
			if ("200".equals(mapObj.get("status"))) {
				status = true;
			}
		} catch (Exception e) {
			status = false;
			body.error();
			String message = "流程结束异常　：" + e.getMessage();
			body.setMessage(message);
			logger.info(message);
		}
		// 添加审计日志
		SysAuditLog auditLog = new SysAuditLog();
		User user = (User) request.getSession().getAttribute("user");
		auditLog.setActorumid(user.getLoginName());
		auditLog.setActorname(user.getName());
		auditLog.setCreatetime(new Date());
		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
		auditLog.setOperationtype(CommonConstant.AUDIT_LOG_OPERATION_TYPE_STOP_PROCESS);
		auditLog.setTargettype(CommonConstant.AUDIT_LOG_TARGET_TYPE_PROCESS);
		auditLog.setTargetid(record.getId().toString());
		if (status) {
			body.success();
			body.setMessage("流程停止操作成功.");
			resultMap.put("message", "OK");
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("流程ID:" + record.getId() + ",流程停止操作成功.");
		} else {
			body.error();
			body.setMessage(mapObj.get("message") != null ? mapObj.get("message") : body.getMessage());
			String message = mapObj.get("message") != null ? mapObj.get("message") : body.getMessage();

			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription(mapObj.get("message") != null ? "流程ID:" + record.getId() + "," + mapObj.get("message") : "流程ID:" + record.getId() + ","
					+ body.getMessage());
			resultMap.put("message", "停止流程失败！");

		}
		if (auditLog.getDescription().length() > 1023) {
			auditLog.setDescription(auditLog.getDescription().substring(0, 1022));
		}
		auditLogService.addAuditLog(auditLog);
		return resultMap;
	}

	@RequestMapping(value = "/queryTask.do")
	@ResponseBody
	public String queryTask(SysProcessTask record, HttpServletRequest request, HttpServletResponse response) {
		String gridJson = "";
		if (record.getStatus() != null && record.getStatus() == 0) {
			record.setStatus(null);
		}
		Long rows = new Long(request.getParameter("rows"));
		Long page = new Long(request.getParameter("page"));
		record.setNumPerPage(rows);
		record.setPageNum(page);
		record.setBeginIndex(rows * page - rows);
		// 只查功能任务
		record.setApprove(2);
		try {
			PageBean pageBean = processManageService.queryTask(record);
			String json = UIUtil.getJSONFromList(pageBean.getExtend().get("list"));
			gridJson = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(), json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gridJson;
	}

	@RequestMapping(value = "/queryUser.do")
	@ResponseBody
	public String queryUser(HttpServletRequest request, HttpServletResponse response) {
		String jsonUsers = "";
		logger.debug("查询用户");
		/*
		 * 查询用户需要，从FinUser中查找，且筛选不再taskOver中targerUser,或已超出时间范围的
		 */
		// List<FinUser> list = null;
		// jsonUsers = UIUtil.getJSONFromList(list);
		return jsonUsers;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/reAssignee.do")
	@ResponseBody
	public Object reAssignee(SysProcessTask record, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Body body = new Body();
		boolean status = false;
		HashMap<String, String> mapObj = null;
		try {
			String json = WorkFlowManageUtil.reassgin(record.getId() + "", record.getAssignerumid());
			status = true;
			// 解析返回数据
			mapObj = (HashMap<String, String>) JSONObject.toBean(JSONObject.fromObject(json), HashMap.class);
			if ("200".equals(mapObj.get("status"))) {
				status = true;
			}
		} catch (Exception e) {
			String message = "重新分配任务异常 ：" + e.getMessage();
			body.error();
			body.setMessage(message);
			logger.error(message);
		}
		// 添加审计日志
		SysAuditLog auditLog = new SysAuditLog();
		User user = (User) request.getSession().getAttribute("user");
		auditLog.setActorumid(user.getLoginName());
		auditLog.setActorname(user.getName());
		auditLog.setCreatetime(new Date());
		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
		auditLog.setOperationtype(CommonConstant.AUDIT_LOG_OPERATION_TYPE_RE_ASSIGNEE);
		auditLog.setTargettype(CommonConstant.AUDIT_LOG_TARGET_TYPE_TASK);
		auditLog.setTargetid(record.getId().toString());
		if (status) {
			body.success();
			body.setMessage("重新分配任务成功");
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("任务编号:" + record.getId() + ",重新分配任务成功.");
			resultMap.put("message", "OK");
		} else {
			body.error();
			body.setMessage(mapObj.get("message") != null ? mapObj.get("message") : body.getMessage());
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription(mapObj.get("message") != null ? "任务编号:" + record.getId() + "," + mapObj.get("message") : "任务编号:" + record.getId() + ","
					+ body.getMessage());
			resultMap.put("message", "重新分配任务失败！");
		}
		auditLogService.addAuditLog(auditLog);
		// return body;
		return resultMap;

	}

	/**
	 * 查询订单对应的流程
	 * 
	 * @param sheetId
	 *            xiaopy
	 * @return
	 */
	@RequestMapping(value = "/queryBysheetId.do", method = RequestMethod.GET)
	@ResponseBody
	public List<SysProcessInstance> queryBySheetid(@RequestParam("sheetId") String sheetId) {
		logger.debug("查询订处理历史=sheetId=" + sheetId);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Map<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("sheetId", sheetId);
		List<SysProcessInstance> processList = processManageService.selectByMap(condMap);

		return processList;
	}

	/**
	 * 查询流程对应的任务
	 * 
	 * @param sheetId
	 *            xiaopy
	 * @return
	 */
	@RequestMapping(value = "/queryByprocessId.do", method = RequestMethod.GET)
	@ResponseBody
	public List<SysProcessTask> queryByprocessId(SysProcessTask record, HttpServletRequest request, HttpServletResponse response) {
		// FinTask ft = new FinTask();
		// ft.setProcessinstanceid(Long.valueOf(processinstanceid));
		List<SysProcessTask> taskList = processManageService.selectTask(record);
		if (taskList != null && taskList.size() > 0) {
			for (SysProcessTask ft : taskList) {
				String taskName = ft.getTaskname() == null ? "" : ft.getTaskname();
				if (taskName.indexOf("—") > -1) {
					ft.setTaskname(taskName.substring(0, taskName.indexOf("—")));
				}
				if (ft.getApprove() != null && ft.getType() != null) {
					if (ft.getApprove().intValue() == 3 && ft.getType().intValue() == 2) {
						ft.setStatusName("转发处理");
					}
				}

			}
			// 原列表是降序排列，页面显示要求升序
			// Sortable sortable = new Sortable();
			// Collections.sort(taskList, sortable.sort);
		}
		return taskList;
	}

	/**
	 * @param xiaopy
	 * @return
	 */
	@RequestMapping(value = "/orderHistoryTraceTask.do", method = RequestMethod.GET)
	public String orderHistoryTraceTask(HttpServletRequest request, HttpServletResponse response) {
		return "view/points/orderHistoryTraceTask";
	}

	/**
	 * 给定订单号查询对应的任务
	 * 
	 * @param sheetId
	 *            xiaopy
	 * @return
	 */
	@RequestMapping(value = "/getBysheetId.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<Long, List<SysProcessTask>> getBySheetid(@RequestParam("sheetId") String sheetId) {
		logger.debug("查询订单信息=sheetId=" + sheetId);
		// 原列表是降序排列，考虑到有的任务是并发同时进行，starttime一模一样，
		// 因此，还需增加endtime的判断，如果endtime不为null，说明执行完了，否则，说明任务还在进行中

		Map<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("sheetId", sheetId);
		List<SysProcessInstance> processList = processManageService.selectByMap(condMap);// 通过订单号得到对应的所有流程
		Map<Long, List<SysProcessTask>> target = new HashMap<Long, List<SysProcessTask>>();
		if (processList != null && processList.size() > 0) {
			for (SysProcessInstance fpi : processList) {
				long processId = fpi.getId();
//				if (fpi.getStatus() == 1) {// 只需显示运行中状态的流程对应的任务
					SysProcessTask ft = new SysProcessTask();
					ft.setProcessinstanceid(Long.valueOf(processId));
					List<SysProcessTask> ftList = processManageService.selectTask(ft);
					if (ftList != null && ftList.size() > 0) {
						for (SysProcessTask task : ftList) {
							String taskName = task.getTaskname() == null ? "" : task.getTaskname();
							task.setTaskname(taskName.substring(0, taskName.indexOf("—")));
							if (task.getApprove() != null && task.getType() != null) {
								if (task.getApprove().intValue() == 3 && task.getType().intValue() == 2) {
									task.setStatusName("转发处理");
								}
							}
						}
						List<SysProcessTask> temp = new ArrayList<SysProcessTask>();
						for (int j = 0; j < ftList.size(); j++) {
							temp.add(ftList.get(j));
						}
						target.put(Long.valueOf(processId), temp);
					}
//				}
			}
		}
		return target;
	}

	/**
	 * 给定流程号查询对应的任务2
	 * 
	 * @param sheetId
	 *            xiaopy
	 * @return
	 */
	@RequestMapping(value = "/getTaskByProcessId.do", method = RequestMethod.GET)
	@ResponseBody
	public List<SysProcessTask> getTaskByProcessId(@RequestParam("processId") String processId) {
		logger.debug("查询流程号的任务信息=processId=" + processId);
		SysProcessTask task = new SysProcessTask();
		task.setProcessinstanceid(Long.valueOf(processId));
		List<SysProcessTask> taskList = processManageService.selectTask(task);
		List<SysProcessTask> target;
		if (taskList != null && taskList.size() > 0) {
			for (SysProcessTask ft : taskList) {
				String taskName = ft.getTaskname() == null ? "" : ft.getTaskname();
				ft.setTaskname(taskName.substring(0, taskName.indexOf("—")));
			}
			if (taskList.size() > 2) {// 以下排序方式就是为了页面显示效果而已
				target = new ArrayList<SysProcessTask>();
				target.add(taskList.get(0));
				int j = taskList.size() - 1;
				while (j > 0) {
					target.add(taskList.get(j));
					j--;
				}
				return target;
			}
		}
		return taskList;
	}

	/**
	 * 给定订单号及 流程号 查询对应的任务
	 * 
	 * @param sheetId
	 *            xiaopy
	 * @return
	 */
	@RequestMapping(value = "/getBysheetIdAndProcessId.do", method = RequestMethod.GET)
	@ResponseBody
	public List<SysProcessTask> getBysheetIdAndProcessId(@RequestParam("processId") String processId, @RequestParam("processCode") String processCode,
			@RequestParam("orderId") String orderId) {
		logger.debug("查询流程号信息" + processId + ",流程编码" + processCode + ",订单号" + orderId);

		SysProcessTask ft = new SysProcessTask();
		ft.setProcessinstanceid(Long.valueOf(processId));
		List<SysProcessTask> ftList = processManageService.selectTask(ft);
		if (ftList != null && ftList.size() > 0) {
			for (SysProcessTask task : ftList) {
				String taskName = task.getTaskname() == null ? "" : task.getTaskname();
				if (taskName.contains("—")) {
					task.setTaskname(taskName.substring(0, taskName.indexOf("—")));
				} else {
					task.setTaskname(taskName);
				}

				if (task.getApprove() != null && task.getType() != null) {
					if (task.getApprove().intValue() == 3 && task.getType().intValue() == 2) {
						task.setStatusName("转发处理");
					}
				}

			}
		}
		// 获取审批链
		logger.debug("查询processCode" + processCode);
		String processTemplate = "";
		List<SysDicItem> result = (List<SysDicItem>) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_DIC_ITEM, "PROCESS_TEMPLATE_MAPPING");
		if (result != null) {// 从缓存中获取模板processTemplate
			for (SysDicItem fdi : result) {
				if (fdi.getDicitemkey().trim().equals(processCode)) {
					processTemplate = fdi.getDicitemvalue().trim();
					break;
				}
			}
		}
		/*
		 * List<FinSalesOrderapprovechain> chainList =
		 * finSalesOrderapprovechainService .selectByOrderId(orderId);
		 * List<FinSalesOrderapprovechain> target = null; if (chainList != null)
		 * {// 通过orderId查询出来的审批，过滤出满足流程模板的审批，主要是这个类没提过orderId,template两个参数的dao,
		 * target = new ArrayList<FinSalesOrderapprovechain>(); for
		 * (FinSalesOrderapprovechain chain : chainList) { if
		 * (chain.getTemplatecode().trim() .equals(processTemplate.trim())) {
		 * target.add(chain); } } }
		 */
		// fintask里存在几个审批的人，在ftList插入type=1,剩下的target里的审批的人，状态一直为 未审批
		/*
		 * if (target != null && target.size() > 0) { if (ftList != null &&
		 * ftList.size() > 0) { int targetIndex = 0; for(FinTask temp : ftList){
		 * if (temp.getType() == 1) {
		 * if(temp.getAssignername().trim().equals(target
		 * .get(targetIndex).getApprovername())){ targetIndex++; } } } while
		 * (targetIndex < target.size()) { FinTask finTask = new FinTask();
		 * finTask.setType(1); finTask.setApprove(8);// 8就说明未审批，为了前台好显示
		 * finTask.setAssignername(target.get(targetIndex) .getApprovername());
		 * ftList.add(finTask); targetIndex++; } } else { ftList = new
		 * ArrayList<FinTask>(); for (FinSalesOrderapprovechain chain : target)
		 * { FinTask finTask = new FinTask(); finTask.setType(1);
		 * finTask.setApprove(8);// 8就说明未审批，为了前台好显示
		 * finTask.setAssignername(chain.getApprovername());
		 * ftList.add(finTask); } } }
		 */
		SysOaInstance oaInstance = new SysOaInstance();
		oaInstance.setFatherprocessinstnaceid(Long.valueOf(processId));
		oaInstance.setTemplatecode(processTemplate);
		List<SysOaInstance> oaInstanceList = oaInstanceService.queryOainstance(oaInstance);
		if (oaInstanceList != null && oaInstanceList.size() > 0) {
			SysOaInstance oaInstancetemp = oaInstanceList.get(0);
			SysOaTask task = new SysOaTask();
			task.setInstanceid(oaInstancetemp.getId());
			List<SysOaTask> tasks = oaInstanceService.queryOaTask(task);
			if (tasks != null && tasks.size() > 0) {
				for (SysOaTask oatask : tasks) {
					if (oatask.getTaskid() == null) {
						SysProcessTask finTask = new SysProcessTask();
						finTask.setType(1);
						finTask.setApprove(8);// 8就说明未审批，为了前台好显示
						finTask.setAssignername(oatask.getAssignername());
						ftList.add(finTask);
					}
				}
			}
		}
		return ftList;
	}

}

class Sortable {
	Comparator<SysProcessTask> sort = new Comparator<SysProcessTask>() {
		@Override
		public int compare(SysProcessTask o1, SysProcessTask o2) {
			if (o1.getStarttime().getTime() > o2.getStarttime().getTime()) {
				return 1;
			} else if (o1.getStarttime().getTime() == o2.getStarttime().getTime()) {
				if (o1.getEndtime() != null && o2.getEndtime() == null) {
					return -1;
				} else if (o1.getEndtime() == null && o2.getEndtime() != null) {
					return 1;
				} else if (o1.getEndtime() == null && o2.getEndtime() == null) {
					return 0;
				} else {
					if (o1.getEndtime().getTime() > o2.getEndtime().getTime()) {
						return 1;
					} else if (o1.getEndtime().getTime() == o2.getEndtime().getTime()) {
						return 0;
					} else {
						return -1;
					}
				}

			} else
				return -1;
		}

	};
}
