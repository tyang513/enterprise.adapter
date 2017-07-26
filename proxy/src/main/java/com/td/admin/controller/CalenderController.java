package com.td.admin.controller;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.casclient.User;
import com.td.admin.constant.AuditConstant;
import com.td.admin.entity.FinCalender;
import com.td.admin.entity.SysAuditLog;
import com.td.admin.service.AuditLogService;
import com.td.admin.service.CalenderService;
import com.td.common.constant.CommonConstant;

/**
 * 日历管理Controller
 * 
 * @author lianjie
 */
@Controller
@RequestMapping("calender")
public class CalenderController {

	private static final Logger logger = LoggerFactory.getLogger(CalenderController.class);
	@Autowired
	private CalenderService calenderService;

	@Autowired
	private AuditLogService auditLogService;
	/**
	 * 查询日历
	 * 
	 * @return Map
	 */
	@RequestMapping(value = "/find.do")
	@ResponseBody
	public Map<String, Object> find(@RequestParam String date) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<FinCalender> list = calenderService.find(date);
			resultMap.put("rows", list);
			
		} catch (Exception e) {
			logger.error("查询日历异常!", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "/edit.do")
	@ResponseBody
	public Map<String, Object> edit(FinCalender calender, Model model, HttpServletRequest request) {
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
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_CALENDAR);
		if(calender.getId()!=null){
			auditLog.setTargetid(calender.getId().toString());
		}
		
		try {
			if (calender.getId() != null && calender.getId() > 0) {
				if (calenderService.update(calender) > 0) {
					//resultMap.put("status", 1);
					resultMap.put("sucMessage", "修改成功");
					auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
					auditLog.setDescription("日历修改成功!");
				} else {
					resultMap.put("failMessage", "修改失败,无对应数据库记录!");
				}
			} else {
				FinCalender tempCalendar = new FinCalender();
				tempCalendar.setDate(calender.getDate());
				List<FinCalender> list = calenderService.queryCalendar(tempCalendar);
				if(list != null && list.size()>0){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");    
					resultMap.put("failMessage", "添加失败,日期"+sdf.format(calender.getDate())+"已存在.");
					auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_ADD);
					auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
					auditLog.setDescription("添加失败,日期"+sdf.format(calender.getDate())+"已存在.");
				}else{
					if (calenderService.add(calender) > 0) {
						//resultMap.put("status", 1);
						resultMap.put("sucMessage", "添加成功");
						auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_ADD);
						auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
						auditLog.setDescription("日历添加成功!");
					} else {
						resultMap.put("failMessage", "添加失败,SQL异常!");
						auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_ADD);
						auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
						auditLog.setDescription("日历添加失败!");
					}
				}
				
			}
		} catch (Exception e) {
			logger.error("编辑日历异常", e);
			resultMap.put("failMessage", "编辑失败,数据库链接异常!");
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription("日历修改失败!");
		} finally {
			auditLogService.addAuditLog(auditLog);
		}
		return resultMap;
	}

	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public Map<String, Object> delete(FinCalender calender, Model model, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 0失败 1成功
		resultMap.put("status", 0);
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
		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_DELETE);
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_CALENDAR);
		auditLog.setTargetid(calender.getId().toString());
		try {
			if (calenderService.delete(calender.getId()) > 0) {
				//resultMap.put("status", 1);
				resultMap.put("sucMessage", "删除成功");
				auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
				auditLog.setDescription("日历删除成功!");
			} else {
				resultMap.put("failMessage", "删除失败,无对应数据库记录!");
			}
		} catch (Exception e) {
			logger.error("删除日历异常", e);
			resultMap.put("failMessage", "删除失败,数据库链接异常!");
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription("日历删除失败!");
		} finally {
			auditLogService.addAuditLog(auditLog);
		}
		return resultMap;
	}
	
	/**
	 * 查询日历
	 * 
	 * @return Map
	 */
	@RequestMapping(value = "/queryCalender.do" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCalender(FinCalender calender, Model model, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<FinCalender> list = calenderService.queryCalendar(calender);
			resultMap.put("rows", list);
			if(list != null ){
				resultMap.put("total",list.size());
			}else{
				resultMap.put("total",0);
			}
		} catch (Exception e) {
			logger.error("查询日历异常!", e);
		}
		return resultMap;
	}
}
