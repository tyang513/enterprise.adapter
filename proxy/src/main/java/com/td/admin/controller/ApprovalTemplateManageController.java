package com.td.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.casclient.User;
import com.td.admin.constant.AuditConstant;
import com.td.admin.entity.OaSystemregistry;
import com.td.admin.entity.SysAuditLog;
import com.td.admin.entity.SysOaTemplate;
import com.td.admin.entity.SysOaTemplateTask;
import com.td.admin.service.ApprovalTemplateManageService;
import com.td.admin.service.AuditLogService;
import com.td.common.bean.Body;
import com.td.common.bean.PageBean;
import com.td.common.constant.CommonConstant;
import com.td.common.util.UIUtil;

@Controller
@RequestMapping("approvalTemplate")
public class ApprovalTemplateManageController {

	public static final Logger logger = LoggerFactory.getLogger(ApprovalTemplateManageController.class);

	@Autowired
	private ApprovalTemplateManageService approvalTemplateManageService;
	
	@Autowired
	private AuditLogService auditLogService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryApprovalTemplate.do")
	@ResponseBody
	public String queryApprovalTemplate(HttpServletRequest request, HttpServletResponse response, SysOaTemplate record) {
		String jsonData = "";
		Long page = new Long(request.getParameter("page"));
		Long rows = new Long(request.getParameter("rows"));
		record.setBeginIndex((page - 1) * rows);
		record.setPageNum(page);
		record.setNumPerPage(rows);
		PageBean pageBean = approvalTemplateManageService.queryApprovalTemplate(record);
		List<OaSystemregistry> list = (List<OaSystemregistry>) pageBean.getExtend().get("list");
		jsonData = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(), UIUtil.getJSONFromList(list));
		return jsonData;
	}

	@RequestMapping("/queryApprovalChain.do")
	@ResponseBody
	public String queryApprovalChain(HttpServletRequest request, HttpServletResponse response, SysOaTemplateTask record) {
		String jsonData = "";
		System.out.println("--------------------" + record.getTemplateid());
		if (record.getTemplateid() != null) {
			List<SysOaTemplateTask> list = approvalTemplateManageService.queryApprovalChain(record);
			for (SysOaTemplateTask oaTemplatetask : list) {
				oaTemplatetask.setMtime(null);
				oaTemplatetask.setCtime(null);
			}
			jsonData = UIUtil.getEasyUiGridJson(new Long(list.size()), UIUtil.getJSONFromList(list));
		} else {
			jsonData = "{\"total\":0,\"rows\":[]}";
		}
		return jsonData;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/saveApprovalTemplate.do")
	@ResponseBody
	public Body saveApprovalTemplate(SysOaTemplate record) throws Exception {
		Body body = new Body();
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = UIUtil.getCollectionType(mapper, ArrayList.class, SysOaTemplateTask.class);
		List<SysOaTemplateTask> list = (List<SysOaTemplateTask>) mapper
				.readValue(record.getApprovalChainJsonData(), javaType);
		record.setOaTemplatetasks(list);
		try {
			body = approvalTemplateManageService.saveApprovalTemplate(record);
		} catch (Exception e) {
			e.printStackTrace();
			body.error();
			body.setMessage(e.getMessage());
		}
		return body;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/updateApprovalTemplate.do")
	@ResponseBody
	public Body updateApprovalTemplate(HttpServletRequest request, HttpServletResponse response, SysOaTemplate record)
			throws Exception {
		Body body = null;
		if (record.getApprovalChainJsonData() != null && !record.getApprovalChainJsonData().equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			JavaType javaType = UIUtil.getCollectionType(mapper, ArrayList.class, SysOaTemplateTask.class);
			List<SysOaTemplateTask> list = (List<SysOaTemplateTask>) mapper.readValue(record.getApprovalChainJsonData(),
					javaType);
			record.setOaTemplatetasks(list);
		}

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		/**
		 * 审计日志
		 */
		SysAuditLog auditLog = new SysAuditLog();
		auditLog.setActorumid(user.getLoginName());
		auditLog.setActorname(user.getName());
		auditLog.setCreatetime(new Date());
		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_UPDATE_MOD);
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_OA_APPROVE);
		auditLog.setTargetid(record.getId().toString());
		try {
			body = approvalTemplateManageService.updateApprovalTemplate(record);
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("OA审批模板修改成功.");
		} catch (Exception e) {
			body.error();
			body.setMessage(e.getMessage());
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
   			auditLog.setDescription("OA审批模板修改失败.");
		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		return body;
	}

	@RequestMapping("/deleteApprovalTemplate.do")
	@ResponseBody
	public Body deleteApprovalTemplate(HttpServletRequest request, HttpServletResponse response, SysOaTemplate record)
			throws Exception {
		Body body = null;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		/**
		 * 审计日志
		 */
		SysAuditLog auditLog = new SysAuditLog();
		auditLog.setActorumid(user.getLoginName());
		auditLog.setActorname(user.getName());
		auditLog.setCreatetime(new Date());
		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_DELETE_MOD);
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_OA_APPROVE);
		auditLog.setTargetid(record.getId().toString());
		try {
			body = approvalTemplateManageService.deleteApprovalTemplate(record);
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("OA审批模板删除成功.");
		} catch (Exception e) {
			body.error();
			body.setMessage(e.getMessage());
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription("OA审批模板删除失败.");
		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		return body;
	}
}
