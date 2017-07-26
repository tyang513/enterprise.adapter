package com.td.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.casclient.User;
import com.td.admin.constant.AuditConstant;
import com.td.admin.entity.SysAuditLog;
import com.td.admin.entity.SysBusinessSystem;
import com.td.admin.service.AuditLogService;
import com.td.admin.service.BusinessSystemService;
import com.td.common.constant.CommonConstant;

/**
 * 子系统参数Controller
 * 
 * @author lianjie
 */
@Controller
@RequestMapping("businessSystem")
public class BusinessSystemController {

	private static final Logger logger = LoggerFactory.getLogger(BusinessSystemController.class);
	@Autowired
	private BusinessSystemService businessSystemService;
	@Autowired
	private AuditLogService auditLogService;

	/**
	 * 查询所有子系统参数
	 * @return Map
	 */
	@RequestMapping(value = "findAll.do")
	@ResponseBody
	public Map<String, Object> findAll() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<SysBusinessSystem> list = businessSystemService.findAll();
			resultMap.put("rows", list);
		} catch (Exception e) {
			logger.error("查询子系统参数异常!", e);
		}
		return resultMap;
	}
	
	/**
	 * 查询所有子系统参数
	 * 
	 * @return Map
	 */
	@RequestMapping(value = "/getAll.do")
	@ResponseBody
	public List<SysBusinessSystem> getAll() {
		List<SysBusinessSystem> list = new ArrayList<SysBusinessSystem>();
		try {
			list = businessSystemService.findAll();
		} catch (Exception e) {
			logger.error("查询子系统参数异常!", e);
		}
		return list;		
	}

	@RequestMapping(value = "/update.do" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(SysBusinessSystem businessSystem, Model model, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 0失败 1成功
		//resultMap.put("status", 0);
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
		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_UPDATE);
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_SYSTEM_PARAM);
		auditLog.setTargetid(businessSystem.getId().toString());

		try {
			if (businessSystemService.update(businessSystem) > 0) {
				//resultMap.put("status", 1);
				resultMap.put("sucMessage", "修改成功");
				auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
				auditLog.setDescription("子系统修改成功!");
			} else {
				resultMap.put("failMessage", "修改失败,无对应数据库记录!");
			}
		} catch (Exception e) {
			logger.error("修改子系统参数异常", e);
			resultMap.put("failMessage", "修改失败,数据库链接异常!");
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription("子系统修改失败!");
		} finally {
			auditLogService.addAuditLog(auditLog);
		}
		return resultMap;
	}

	@RequestMapping(value = "/findSubSystem.do")
	@ResponseBody
	public List<SysBusinessSystem> findSubSystem(SysBusinessSystem businessSystem, Model model) {
		List<SysBusinessSystem> list = businessSystemService.findAll();
		return list;
	}
}
