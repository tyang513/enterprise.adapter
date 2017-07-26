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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.casclient.User;
import com.td.admin.constant.AuditConstant;
import com.td.admin.entity.SysAuditLog;
import com.td.admin.entity.SysEmailReceiveJobInput;
import com.td.admin.entity.SysEmailSendJobInput;
import com.td.admin.entity.SysEmailServerConfig;
import com.td.admin.entity.SysEmailTemplate;
import com.td.admin.service.AuditLogService;
import com.td.admin.service.BusinessSystemService;
import com.td.admin.service.MailManageService;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;
import com.td.common.constant.CommonConstant;
import com.td.common.util.UIUtil;


@Controller
@RequestMapping("mail")
public class MailManageController {
	

	private static final Logger logger = LoggerFactory.getLogger(MailManageController.class);
	
	@Autowired
	private MailManageService mailManageService; 
	@Autowired
	private BusinessSystemService businessSystemService;
	
	@Autowired
	private AuditLogService auditLogService;
	
	@RequestMapping(value="/queryMailServers.do")
	@ResponseBody
	public String queryMailServers(HttpServletRequest request,HttpServletResponse response){
		String jsonData = "";
		Long page = new Long(request.getParameter("page"));
		Long rows = new Long(request.getParameter("rows"));
		PageBean pageBean = mailManageService.queryMailServers(page, rows);
		List<SysEmailServerConfig> list = (List<SysEmailServerConfig>)pageBean.getExtend().get("list");
		String json = UIUtil.getJSONFromList(list);
		jsonData = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),json);
		return jsonData;
	}
	
	@RequestMapping(value="/updateMailServer.do")
	@ResponseBody
	public Map updateMailServer(SysEmailServerConfig record,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap= new HashMap<String,Object>();
		Body body = null;
		String newPassword = request.getParameter("newPassword");
		/**
         * 审计信息
         */
        HttpSession session =request.getSession(); 
        User user =(User)session.getAttribute("user");
        SysAuditLog auditLog = new SysAuditLog();
        auditLog.setActorumid(user.getLoginName());
		auditLog.setActorname(user.getName());
		auditLog.setCreatetime(new Date());
		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_UPDATE_EMAIL_CONFIG);
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_EMAIL);
		auditLog.setTargetid(record.getId().toString());
		try{
			body = mailManageService.updateMailServer(record,newPassword);
			if(body.getStatusCode().equals("200")){
				resultMap.put("message", "OK");
			}else{
				resultMap.put("message", body.getMessage());
			}
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("email邮件服务器配置修改成功");
		}catch(Exception e){
			String message = "message : " + e.getMessage();
			body = new Body();
			body.error();
			body.setMessage(message);
			logger.error(message);
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
   			auditLog.setDescription("email邮件服务器配置修改失败");
   			
			resultMap.put("message", body.getMessage());

		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		return resultMap;
	}
	
	@RequestMapping(value="/queryMailTemplate.do")
	@ResponseBody
	public String queryMailTemplate(SysEmailTemplate emailTemp,HttpServletRequest request,HttpServletResponse response){
		String jsonData = "";
		Long page = new Long(request.getParameter("page"));
		Long rows = new Long(request.getParameter("rows"));
		PageBean pageBean = mailManageService.queryMailTemplate(emailTemp,page, rows);
		List<SysEmailTemplate> list = (List<SysEmailTemplate>)pageBean.getExtend().get("list");
		String json = UIUtil.getJSONFromList(list);
		jsonData = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),json);
		jsonData = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),json);
		return jsonData;
	}
	
	@RequestMapping(value="/addMailTemplate.do",method=RequestMethod.POST)
	@ResponseBody
	public Object addMailTemplate(SysEmailTemplate record,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try{
			SysEmailTemplate emailTemplate = mailManageService.getMailTemplateByCode(record.getCode());
			if(emailTemplate!=null){
				returnMap.put("message", "邮件模板已存在！");
				return returnMap;
			}
			int result = mailManageService.saveMailTemplate(record);
			if(result>0){
				returnMap.put("message", "OK");
			}else{
				returnMap.put("message", "邮件模板新增失败！");
			}
		}catch(Exception e){
			String message = "message : " + e.getMessage();
			logger.error(message);
			returnMap.put("message", "邮件模板新增失败！");
		}
		return returnMap;
	}
	@RequestMapping(value="/updateMailTemplate.do",method=RequestMethod.POST)
	@ResponseBody
	public Object updateMailTemplate(SysEmailTemplate record,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> returnMap = new HashMap<String, Object>();

		/**
         * 审计信息
         */
        HttpSession session =request.getSession(); 
        User user =(User)session.getAttribute("user");
        SysAuditLog auditLog = new SysAuditLog();
        auditLog.setActorumid(user.getLoginName());
		auditLog.setActorname(user.getName());
		auditLog.setCreatetime(new Date());
		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_UPDATE_MOD);
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_EMAIL);
		
		auditLog.setTargetid(record.getId().toString());
		try{
			
			int result = mailManageService.updateMailTemplate(record);
			if(result>0){
				returnMap.put("message", "邮件模板修改成功！");
			}else{
				returnMap.put("message", "邮件模板修改失败！");
			}
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("email模板修改成功！");

		}catch(Exception e){
			String message = "message : " + e.getMessage();
		
			logger.error(message);
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
   			auditLog.setDescription("email模板修改失败！");
			returnMap.put("message", "邮件模板修改失败！");

		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		return returnMap;
	}
	
//	@RequestMapping(value="/updateMailTemplate.do",method=RequestMethod.POST)
//	@ResponseBody
//	public Body updateMailTemplate(FinEmailTemplate record,HttpServletRequest request,HttpServletResponse response){
//		Body body = null;
//        /**
//         * 审计信息
//         */
//        HttpSession session =request.getSession(); 
//        User user =(User)session.getAttribute("user");
//        FinAuditLog auditLog = new FinAuditLog();
//        auditLog.setActorumid(user.getLoginName());
//		auditLog.setActorname(user.getName());
//		auditLog.setCreatetime(new Date());
//		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
//		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_UPDATE_MOD);
//		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_EMAIL);
//		auditLog.setTargetid(record.getId().toString());
//		try{
//			
//			body = mailManageService.updateMailTemplate(record);
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
//			auditLog.setDescription("email模板修改成功！");
//		}catch(Exception e){
//			String message = "message : " + e.getMessage();
//			body = new Body();
//			body.error();
//			body.setMessage(message);
//			logger.error(message);
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
//   			auditLog.setDescription("email模板修改失败！");
//		}finally{
//			auditLogService.addAuditLog(auditLog);
//		}
//		return body;
//	}
//	
	@RequestMapping(value="/deleteMailTemp.do")
    @ResponseBody
    public Map<String, Object> deleteMailTemp(SysEmailTemplate record,HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//0失败 1成功
		resultMap.put("status", 0);
		/**
         * 审计信息
         */
        HttpSession session =request.getSession(); 
        User user =(User)session.getAttribute("user");
        SysAuditLog auditLog = new SysAuditLog();
        auditLog.setActorumid(user.getLoginName());
		auditLog.setActorname(user.getName());
		auditLog.setCreatetime(new Date());
		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_DELETE_MOD);
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_EMAIL);
		auditLog.setTargetid(record.getId().toString());
        try {
        	if(mailManageService.deleteMailTemp(record.getId())>0){
        		resultMap.put("status", 1);
        		resultMap.put("msg", "删除成功");
        		auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
    			auditLog.setDescription("email模板删除成功！");
            }else{
            	resultMap.put("msg", "删除失败,无对应数据库记录!");
            }
		} catch (Exception e) {
            logger.error("删除邮件模版异常",e);
            resultMap.put("msg", "删除失败,数据库链接异常!");
            auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
   			auditLog.setDescription("email模板删除失败！");
		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		return resultMap;
    }
	@RequestMapping(value = "/getMailTemplates.do")
	@ResponseBody
	public List<SysEmailTemplate> getMailTemplates(HttpServletRequest request, HttpServletResponse response) {
		String codeOrTitle = request.getParameter("codeOrTitle");
//		String q = request.getParameter("q");
//		logger.info(" codeOrTitle == " + codeOrTitle + " q == " + q);
//		if (codeOrTitle == null){
//			codeOrTitle = q;
//		}
		//List<Map<String, Object>> list = dcdsUserAndRoleService.getUsersByUmidOrUsername(codeOrTitle);
		List<SysEmailTemplate> list = mailManageService.getMailTemplatesByCodeOrTitle(codeOrTitle);
		return list;
	}
	
	@RequestMapping(value="/addReceiveJobInput.action")
	@ResponseBody
	public Map<String, Object> addReceiveJobInput(SysEmailReceiveJobInput receiveJobInput){
	    Map<String, Object> returnMap = new HashMap<String, Object>();
	    mailManageService.addReceiveJobInput(receiveJobInput);
		returnMap.put("success", "添加发送记录成功!");
		return returnMap;
	}
	
	@RequestMapping(value="/updateSendJobInput.action")
	@ResponseBody
	public Map<String, Object> updateSendJobInput(SysEmailSendJobInput sendJobInput){
	    Map<String, Object> returnMap = new HashMap<String, Object>();
	    mailManageService.updateSendJobInput(sendJobInput);
		returnMap.put("success", "添加发送记录成功!");
		return returnMap;
	}
}
