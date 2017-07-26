package com.td.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("TaskConfig")
public class TaskConfigController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(TaskConfigController.class);
//	
//	@Autowired
//	private TaskConfigManageService taskConfigManageService; 
//	
//	@Autowired
//	private AuditLogService auditLogService; 
//	
//	private static SecurityService securityService = UmRmiServiceFactory.getSecurityService();
//
//	@RequestMapping(value="/getAllTaskConfig.do")
//	@ResponseBody
//	public String getAllProcessConfig(SysProcessTaskConfig  record,HttpServletRequest request,HttpServletResponse response){
//		Long rows = new Long(request.getParameter("rows"));
//		Long page = new Long(request.getParameter("page"));
//		record.setPageNum(page);
//		record.setNumPerPage(rows);
//		String gridJson = "" ;
//		PageBean pageBean = taskConfigManageService.queryTaskConfig(record);
//		String messageJson = UIUtil.getJSONFromList(pageBean.getExtend().get("list"));
//		gridJson = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),messageJson);
//		return gridJson;
//	}
//	
//	@RequestMapping(value="/saveTaskConfig.do")
//	@ResponseBody
//	public Object saveTaskConfig(SysProcessTaskConfig record,HttpServletRequest request,HttpServletResponse response){
//		Body body = null ;
//		Map<String,Object> resultMap = new HashMap<String,Object>();
//		try{
//			body = taskConfigManageService.saveTaskConfig(record);
//			resultMap.put("message", "OK");
//		}catch(Exception e){
//			body = new Body();
//			String message = "message : " + e.getMessage();
//			body.error();
//			body.setMessage(message);
//			logger.debug(message);
//			resultMap.put("message", "任务设置新增失败！");
//
//		}
//		//return body;
//		return resultMap;
//	}
//	
//	@RequestMapping(value="/deleteTaskConfig.do")
//	@ResponseBody
//	public Body deleteTaskConfig(SysProcessTaskConfig record,HttpServletRequest request,HttpServletResponse response){
//		Body body = null ;
//		HttpSession session =request.getSession(); 
//	    User user =(User)session.getAttribute("user");
//	    /**
//		 * 审计日志
//		 */
//		SysAuditLog auditLog = new SysAuditLog();
//		auditLog.setActorumid(user.getLoginName());
//		auditLog.setActorname(user.getName());
//		auditLog.setCreatetime(new Date());
//		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
//		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_DELETE);
//		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_TASK);
//		auditLog.setTargetid(record.getId().toString());
//		try{
//			body = taskConfigManageService.deleteTaskConfig(record);
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
//			auditLog.setDescription("任务设置删除成功");
//		}catch(Exception e){
//			body = new Body();
//			String message = "message : " + e.getMessage();
//			body.error();
//			body.setMessage(message);
//			logger.debug(message);
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
//			auditLog.setDescription("任务设置删除失败");
//		}finally{
//			auditLogService.addAuditLog(auditLog);
//		}
//		return body;
//	}
//	
//	@RequestMapping(value="/updateTaskConfig.do")
//	@ResponseBody
//	public Object updateTaskConfig(SysProcessTaskConfig record,HttpServletRequest request,HttpServletResponse response){
//		Map<String,Object> resultMap = new HashMap<String,Object>();
//
//		Body body = null ;
//		HttpSession session =request.getSession(); 
//	    User user =(User)session.getAttribute("user");
//	    /**
//		 * 审计日志
//		 */
//		SysAuditLog auditLog = new SysAuditLog();
//		auditLog.setActorumid(user.getLoginName());
//		auditLog.setActorname(user.getName());
//		auditLog.setCreatetime(new Date());
//		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
//		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_UPDATE);
//		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_TASK);
//		auditLog.setTargetid(record.getId().toString());
//		try{
////			System.out.println(record.getSystemcode() + record.getProcessconfigid() + "======================");
//			body = taskConfigManageService.updateTaskConfig(record);
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
//			auditLog.setDescription("任务设置修改成功");
//			resultMap.put("message", "OK");
//
//		}catch(Exception e){
//			body = new Body();
//			String message = "message : " + e.getMessage();
//			body.error();
//			body.setMessage(message);
//			logger.debug(message);
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
//			auditLog.setDescription("任务设置修改失败");
//			resultMap.put("message", "任务设置修改失败！");
//
//		}finally{
//			auditLogService.addAuditLog(auditLog);
//		}
//	 	return resultMap;
//	}
//	
//	
//	@RequestMapping(value="/queryFinRoleToSelect.do")
//	@ResponseBody
//	public List<Role> queryFinRoleToSelect(HttpServletRequest request, HttpServletResponse response){
//	       List<Role> roles = securityService.getRolesByRolename(CommonConstant.DCDS_ROLE_PREFIX);
//	       return roles;
//	}
	 
	

}
