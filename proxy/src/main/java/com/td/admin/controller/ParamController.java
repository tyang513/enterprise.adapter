package com.td.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.casclient.User;
import com.td.admin.constant.AuditConstant;
import com.td.admin.entity.SysAuditLog;
import com.td.admin.entity.SysParam;
import com.td.admin.service.AuditLogService;
import com.td.admin.service.BusinessSystemService;
import com.td.admin.service.ParamService;
import com.td.common.bean.ExecuteInfo;
import com.td.common.constant.CommonConstant;

/**
 * 系统参数控制器
 * @author zhouguoping
 */
@Controller
@RequestMapping("param")
public class ParamController {
	private static Logger logger = Logger.getLogger(ParamController.class);
	
	@Autowired
	private ParamService paramService;
	@Autowired
	private BusinessSystemService businessSystemService;
	
	@Autowired
	private AuditLogService auditLogService;
	
	@RequestMapping(value="/showParam.do")
	public String showParam(Model model) {
	    logger.debug("showParam===");
	    model.addAttribute("subSystemList", businessSystemService.findAll()); 
	    return "view/config/param/param";
	}
	/**
	 * 获取所有系统参数
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getParams.do")
	@ResponseBody
	public Map<String,Object> getParams(SysParam param,@RequestParam Long rows,@RequestParam Long page){
		param.setRows(rows);
		param.setPage(page);
		return paramService.getParams(param);
//		String gridJson = paramService.getParams(param);
//		return gridJson;
	}	
	
	/**
	 * 修改系统参数
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/saveParam.do")
	@ResponseBody
	public Object saveParam(SysParam finSystemParam,@RequestParam String paramvalueOld,HttpServletResponse response,HttpServletRequest request){
		try {
			finSystemParam.setMtime(new Date());
			if("Y".equals(finSystemParam.getPw()) || "1".equals(finSystemParam.getPw())){
				// 判断原始值
				SysParam old = paramService.getParamById(finSystemParam.getId());
				if(!old.getParamvalue().equals(paramvalueOld)){
					throw new RuntimeException("原始参数值输入不正确。");
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			new ExecuteInfo().error().info("保存失败:\n"+e.getMessage()).writeJsonStrToServletOutputstream(response);
			return null;
		}
		try {
			// 判断paramkey是否已存在
			SysParam old = paramService.getParamByKey(finSystemParam.getParamkey());
			if(old!=null && !old.getId().equals(finSystemParam.getId())){
				throw new RuntimeException("参数英文名已存在。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ExecuteInfo().error().info("保存失败：\n"+e.getMessage()).writeJsonStrToServletOutputstream(response);
			return null;
		}
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
		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_UPDATE_PARAM);
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_SYSTEM_PARAM);
		auditLog.setTargetid(finSystemParam.getId().toString());
		try {
			// 保存		
			paramService.updateParam(finSystemParam);
			
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("系统参数编辑成功！");
		} catch (Exception e) {
			e.printStackTrace();
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
   			auditLog.setDescription("系统参数编辑失败");
			new ExecuteInfo().error().info("保存失败：请联系系统运维人员或稍后再试。").writeJsonStrToServletOutputstream(response);
			return null;
		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		new ExecuteInfo().success().writeJsonStrToServletOutputstream(response);
		return null;
	}
	private Map<String,Object> saveParams(SysParam finSystemParam,
			String paramvalueOld, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
	
		finSystemParam.setMtime(new Date());
		if("Y".equals(finSystemParam.getPw()) || "1".equals(finSystemParam.getPw())){
			// 判断原始值
			SysParam old = paramService.getParamById(finSystemParam.getId());
			if(!old.getParamvalue().equals(paramvalueOld)){
				//throw new RuntimeException("原始参数值输入不正确。");
				returnMap.put("message", "原始参数值输入不正确!");
				return returnMap;
			}
		}			
		// 判断paramkey是否已存在
		SysParam old = paramService.getParamByKey(finSystemParam.getParamkey());
		if(old!=null && !old.getId().equals(finSystemParam.getId())){
			//throw new RuntimeException("参数英文名已存在。");
			returnMap.put("message", "参数英文名已存在!");
			return returnMap;
		}
		
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
		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_UPDATE_PARAM);
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_SYSTEM_PARAM);
		auditLog.setTargetid(finSystemParam.getId().toString());
		try {
			// 保存		
			int result = paramService.updateParam(finSystemParam);
			
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("系统参数编辑成功！");
			returnMap.put("message", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
   			auditLog.setDescription("系统参数编辑失败");
			new ExecuteInfo().error().info("保存失败：请联系系统运维人员或稍后再试。").writeJsonStrToServletOutputstream(response);
			returnMap.put("message", "系统参数编辑失败!");

		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		return returnMap;

		//new ExecuteInfo().success().writeJsonStrToServletOutputstream(response);
		//return null;
	}
	/**
	 * 校验输入信息，更新数据库
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/updatesysParam.do")
	@ResponseBody
	public Object validateStockout(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> returnMap = new HashMap<String, Object>();

		String paramvalueOld = request.getParameter("paramvalueOld");
		SysParam finSystemParam = new SysParam();
		finSystemParam.setId(Long.parseLong(request.getParameter("sysParamId")));
		finSystemParam.setParamkey(request.getParameter("paramkey"));
		finSystemParam.setParamvalue(request.getParameter("paramvalue"));
		if("是".equals(request.getParameter("pw"))){
			finSystemParam.setPw("Y");
		}else{
			finSystemParam.setPw("N");
		}

		finSystemParam.setSystemcode(request.getParameter("systemcode"));
		finSystemParam.setDescription( request.getParameter("description"));
		returnMap = saveParams(finSystemParam, paramvalueOld, response, request);
		return returnMap;

	}
}
