package com.td.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.casclient.User;
import com.td.admin.constant.AuditConstant;
import com.td.admin.entity.SysAuditLog;
import com.td.admin.entity.SysBusinessSystem;
import com.td.admin.entity.SysProcessConfig;
import com.td.admin.service.AuditLogService;
import com.td.admin.service.BusinessSystemService;
import com.td.admin.service.ProcessConfigManageService;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;
import com.td.common.constant.CommonConstant;
import com.td.common.util.UIUtil;

@Controller
@RequestMapping("ProcessConfig")
public class ProcessConfigController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessConfigController.class);
	
	@Autowired
	private ProcessConfigManageService processConfigManageService; 
	@Autowired
	private BusinessSystemService businessSystemService;
	
	@Autowired
	private AuditLogService auditLogService; 
	

	@RequestMapping(value="/getAllProcessConfig.do")
	@ResponseBody
	public String getAllProcessConfig(SysProcessConfig record,HttpServletRequest request,HttpServletResponse response){
		Long rows = new Long(request.getParameter("rows"));
		Long page = new Long(request.getParameter("page"));
		record.setPageNum(page);
		record.setNumPerPage(rows);
		String gridJson = "" ;
		PageBean pageBean = processConfigManageService.queryProcessConfig(record);
//		JSONArray jsonArray = new JSONArray();
//		jsonArray.put(pageBean.getExtend().get("list"));
//		gridJson = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),jsonArray.toString());
		String messageJson = UIUtil.getJSONFromList(pageBean.getExtend().get("list"));
		gridJson = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),messageJson);
		return gridJson;
		
	}
	
	
	@RequestMapping(value="/getAllProcessConfig2Select.do")
	@ResponseBody
	public String getAllProcessConfig2Select(HttpServletRequest request,HttpServletResponse response){
		String gridJson = "" ;
		List<SysProcessConfig> processList = processConfigManageService.queryAllProcessConfig();
//		JSONArray jsonArray = new JSONArray();
//	    jsonArray.put(processList);
//		gridJson = jsonArray.toString().substring(1,jsonArray.toString().length()-1);
		gridJson = UIUtil.getJSONFromList(processList);
		return gridJson;
	}
	
	@RequestMapping(value="/getAllBusinessSystem.do")
	@ResponseBody
	public String getAllBusinessSystem(SysProcessConfig record,HttpServletRequest request,HttpServletResponse response){
		String gridJson = "" ;
		List<SysBusinessSystem> businessSystemList = businessSystemService.findAll();
//		JSONArray jsonArray = new JSONArray();
//		jsonArray.put(businessSystemList);
//		gridJson = jsonArray.toString().substring(1,jsonArray.toString().length()-1);
		gridJson = UIUtil.getJSONFromList(businessSystemList);
		return gridJson;
	}
	
	@RequestMapping(value="/saveProcessConfig.do")
	@ResponseBody
	public Object saveProcessConfig(SysProcessConfig record,HttpServletRequest request,HttpServletResponse response){
		Body body = null ;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			body = processConfigManageService.saveProcessConfig(record);
			resultMap.put("message", "OK");
		}catch(Exception e){
			body = new Body();
			String message = "message : " + e.getMessage();
			body.error();
			body.setMessage(message);
			logger.debug(message);
			resultMap.put("message", "流程设置信息新增失败！");

		}
		return resultMap;
	}
	
	@RequestMapping(value="/deleteProcessConfig.do")
	@ResponseBody
	public Object deleteProcessConfig(SysProcessConfig record,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();

		Body body = null ;
		HttpSession session =request.getSession(); 
	    User user =(User)session.getAttribute("user");
	    /**
		 * 审计日志
		 */
		SysAuditLog auditLog = new SysAuditLog();
		auditLog.setActorumid(user.getLoginName());
		auditLog.setActorname(user.getName());
		auditLog.setCreatetime(new Date());
		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_DELETE);
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_PROCESS);
		auditLog.setTargetid(record.getId().toString());
		try{
			body = processConfigManageService.deleteProcessConfig(record);
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("删除流程设置成功");
			resultMap.put("message", "OK");

		}catch(Exception e){
			body = new Body();
			String message = "message : " + e.getMessage();
			body.error();
			body.setMessage(message);
			logger.debug(message);
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription("删除流程设置失败");
			resultMap.put("message", "删除流程设置失败！");

		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		return resultMap;
	}
	
	@RequestMapping(value="/updateProcessConfig.do")
	@ResponseBody
	public Object  updateProcessConfig(SysProcessConfig record,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Body body = null ;
		HttpSession session =request.getSession(); 
	    User user =(User)session.getAttribute("user");
	    /**
		 * 审计日志
		 */
		SysAuditLog auditLog = new SysAuditLog();
		auditLog.setActorumid(user.getLoginName());
		auditLog.setActorname(user.getName());
		auditLog.setCreatetime(new Date());
		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_UPDATE);
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_PROCESS);
		auditLog.setTargetid(record.getId().toString());
		try{
			body = processConfigManageService.updateProcessConfig(record);
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("修改流程设置成功");
			resultMap.put("message", "OK");

		}catch(Exception e){
			body = new Body();
			String message = "message : " + e.getMessage();
			body.error();
			body.setMessage(message);
			logger.debug(message);
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription("修改流程设置失败");
			resultMap.put("message", "修改流程设置失败！");

		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		return resultMap;
	}
	
}
