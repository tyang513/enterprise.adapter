package com.td.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.casclient.User;
import com.td.admin.constant.AuditConstant;
import com.td.admin.entity.SysAuditLog;
import com.td.admin.entity.SysJobConfig;
import com.td.admin.entity.SysJobErrorInfo;
import com.td.admin.entity.SysJobTempJobInput;
import com.td.admin.service.AuditLogService;
import com.td.admin.service.JobConfigManageService;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;
import com.td.common.constant.CommonConstant;
import com.td.common.util.DateConvertEditor;
import com.td.common.util.UIUtil;

@Controller
@RequestMapping("JobConfig")
public class JobConfigController {
	
	
	public static final Logger logger = LoggerFactory.getLogger(JobConfigController.class);
	
	@Autowired
	private JobConfigManageService jobConfigManageService; 
		
	@Autowired
	private AuditLogService auditLogService; 
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}
	
	@RequestMapping(value="/queryJobConfigList.do")
	@ResponseBody
	public String queryProcessInstance(SysJobConfig record,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String gridJson = "";
		System.out.println(record.getStatus()+"===============================");
		if(record.getStatus()!=null&&record.getStatus().equals(0)){
			record.setStatus(null);
		}
		
		Long rows = new Long(request.getParameter("rows"));
		Long page = new Long(request.getParameter("page"));

		record.setNumPerPage(rows);
		record.setPageNum(page);
		record.setBeginIndex(rows*page-rows);
		PageBean pageBean = jobConfigManageService.queryJobConfig(record);
		String messageJson = UIUtil.getJSONFromList(pageBean.getExtend().get("list"));
		gridJson = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),messageJson);
		return gridJson;
	}
	
	
	@RequestMapping(value="/queryJobConfigErrorList.do")
	@ResponseBody
	public String queryJobError(SysJobErrorInfo record,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String gridJson = "";

		Long rows = new Long(request.getParameter("rows"));
		Long page = new Long(request.getParameter("page"));
		record.setNumPerPage(rows);
		record.setPageNum(page);
		record.setBeginIndex(rows*page-rows);
		PageBean pageBean = jobConfigManageService.queryJobError(record);
		String messageJson = UIUtil.getJSONFromList(pageBean.getExtend().get("list"));
		gridJson = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),messageJson);
		return gridJson;
	}
	
	@RequestMapping(value="/queryJobConfigTempList.do")
	@ResponseBody
	public String queryJobTemp(SysJobTempJobInput record,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String gridJson = "";
//		if(record.getStatus()!=null&&record.getStatus()==0){
//			record.setStatus(null);
//		}
		
		Long rows = new Long(request.getParameter("rows"));
		Long page = new Long(request.getParameter("page"));
        	
        record.setNumPerPage(rows);
		record.setPageNum(page);
		record.setBeginIndex(rows*page-rows);
		PageBean pageBean = jobConfigManageService.queryJobTemp(record);
		String messageJson = UIUtil.getJSONFromList(pageBean.getExtend().get("list"));
		gridJson = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),messageJson);
		return gridJson;
	}
	
	@RequestMapping(value="/updateJobConfig.do")
	@ResponseBody
	public Body updateJobConfig(SysJobConfig record,HttpServletRequest request,HttpServletResponse response){
		Body body = null;
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
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_TASK);
		auditLog.setTargetid(record.getId().toString());
		//String newPassword = request.getParameter("newPassword");
	    try{
	    
		body = jobConfigManageService.updateJobConfig(record);
		auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
		auditLog.setDescription("定时任务配置修改成功!");
	    }catch(Exception e){
	    	auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription("定时任务配置修改失败!"+e.toString());
	    }finally{
	    	auditLogService.addAuditLog(auditLog);
	    }
		return body;
	}
	
	
	
	@RequestMapping(value="/updateJobConfigTemp.do")
	@ResponseBody
	public Map<String, Object> updateJobConfigTemp(@RequestParam Long id,HttpServletRequest request,HttpServletResponse response){
		Body body = null;
		//String newPassword = request.getParameter("newPassword");
		logger.debug("updateJobConfigTemp"+id);
		HttpSession session =request.getSession(); 
	    User user =(User)session.getAttribute("user");
	    Map<String, Object> returnMap = new HashMap<String, Object>();
	    /**
		 * 审计日志
		 */
		SysAuditLog auditLog = new SysAuditLog();
		auditLog.setActorumid(user.getLoginName());
		auditLog.setActorname(user.getName());
		auditLog.setCreatetime(new Date());
		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_UPDATE);
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_TASK);
		auditLog.setTargetid(id.toString());
		try{
		body = jobConfigManageService.updateJobConfigTemp(id);
		auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
		auditLog.setDescription("临时任务配置修改成功!");
		returnMap.put("success", "临时任务配置修改成功!");
		}catch(Exception e){
		auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
		auditLog.setDescription("临时任务配置修改失败!"+e.toString());
		returnMap.put("error", "临时任务配置修改失败!");
		}finally{
			
			auditLogService.addAuditLog(auditLog);
		}
		//return body.toString();
		return returnMap;
	}
}
