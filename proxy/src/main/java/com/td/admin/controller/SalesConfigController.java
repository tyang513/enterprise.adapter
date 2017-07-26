package com.td.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 销售管理（层级管理、临时接管设置等）控制器
 * 
 * @author zhouguoping
 */
@Controller
@RequestMapping("sales")
public class SalesConfigController {
	
	private static final Logger logger = LoggerFactory.getLogger(SalesConfigController.class);
//	
//	@Autowired
//	private DcdsUserAndRoleService dcdsUserAndRoleService;
//
//	@Autowired
//	private SalesConfigService salesConfigService;
//
//	@Autowired
//	private AuditLogService auditLogService;
//
//	@RequestMapping(value = "/getUsers.do")
//	@ResponseBody
//	public List<Map<String, Object>> getUsers(HttpServletRequest request, HttpServletResponse response) {
//		String umidOrUsername = request.getParameter("umidOrUsername");
//		String q = request.getParameter("q");
//		logger.info(" umidOrUsername == " + umidOrUsername + " q == " + q);
//		if (umidOrUsername == null){
//			umidOrUsername = q;
//		}
//		List<Map<String, Object>> list = dcdsUserAndRoleService.getUsersByUmidOrUsername(umidOrUsername);
//		return list;
//	}
//
//	@RequestMapping(value = "/getSalesTakeoverSettings.do")
//	@ResponseBody
//	public PageResult getSalesTakeoverSettings(HttpServletRequest request, HttpServletResponse response) {
//		String userUmId = request.getParameter("userUmId");
//		String userName = request.getParameter("userName");
//		String targetUserName = request.getParameter("targetUserName");
//		String startTime = request.getParameter("startTime");
//		String endTime = request.getParameter("endTime");
//		String page = request.getParameter("page");
//		String pageRows = request.getParameter("rows");
//		String flag = request.getParameter("flag");
//		String sort = request.getParameter("sort");
//		String order = request.getParameter("order");
//		List<SysTakeoverExtends> list = new ArrayList<SysTakeoverExtends>();
//		int total = 0;
//		PageResult pageResult = null;
//		if ("admin".equalsIgnoreCase(flag)) {
//			pageResult = salesConfigService.getSalesTakeoverSettings(userName, targetUserName, startTime, endTime,
//					pageRows == null ? 10 : Integer.valueOf(pageRows), page == null ? 1 : Integer.valueOf(page), sort,
//					order);
//		} else {
//			list = salesConfigService.getSalesTakeoverSettings(userUmId);
//			pageResult = new PageResult();
//			pageResult.setTotal(total);
//			pageResult.setRows(list);
//		}
//		return pageResult;
//	}
//
//	/**
//	 * 新增临时接管设置
//	 * 
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value = "/saveSalesTakeoverSetting.do" , method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> saveSalesTakeoverSetting(SysTakeover record, HttpServletRequest request, HttpServletResponse response) {
//		Map<String, Object> returnMap = new HashMap<String, Object>();
//		// 添加审计日志
//		SysAuditLog auditLog = new SysAuditLog();
//		User user = (User) request.getSession().getAttribute("user");
//		auditLog.setActorumid(user.getLoginName());
//		auditLog.setActorname(user.getName());
//		auditLog.setCreatetime(new Date());
//		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
//		auditLog.setOperationtype(CommonConstant.AUDIT_LOG_OPERATION_TYPE_TAKEOVER);
//		auditLog.setTargettype(CommonConstant.AUDIT_LOG_TARGET_TYPE_TAKEOVER);
//		try {
//			// 设置为系统授权
//			record.setType("2");
//			// 新增临时接管设置
//			if (salesConfigService.selectTakeOverUserByTime(record) > 0) {
//				auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
//				auditLog.setDescription("用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid()
//						+ "失败,在授权时间内,接管人已被其他用户授权或接管人已授权给其他人!");
//				auditLogService.addAuditLog(auditLog);
//				//return new ExecuteInfo().error().info("新增失败,在授权时间内,接管人已被其他用户授权或接管人已授权给其他人.重新选择.");
//				returnMap.put("failMessage","新增失败,在授权时间内,接管人已被其他用户授权或接管人已授权给其他人.重新选择.");
//				return returnMap;
//			} else if (salesConfigService.selectUserByTime(record) > 0) {
//				auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
//				auditLog.setDescription("用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid()
//						+ "失败,在授权时间内,用户已被其他用户授权或用户已授权给其他人!");
//				auditLogService.addAuditLog(auditLog);
//				//return new ExecuteInfo().error().info("新增失败,在授权时间内,用户已被其他用户授权或用户已授权给其他人.重新选择.");
//				returnMap.put("failMessage","新增失败,在授权时间内,用户已被其他用户授权或用户已授权给其他人.重新选择.");
//				return returnMap;
//			} else {
//				salesConfigService.saveSalesTakeoverSetting(record);
//				auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
//				auditLog.setDescription("用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid() + "成功!");
//				returnMap.put("sucMessage","用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid() + "成功!");
//			}
//		} catch (Exception e) {
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
//			auditLog.setDescription("用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid() + "失败,SQL异常:"
//					+ e.getMessage() + "!");
//			auditLogService.addAuditLog(auditLog);
//			e.printStackTrace();
//			//return new ExecuteInfo().error().info("新增失败,请联系系统运维人员或稍后再试.");
//			returnMap.put("failMessage","新增失败,请联系系统运维人员或稍后再试.");
//			return returnMap;
//		}
//		SysTakeoverExtends vo = null;
//		try {
//			vo = salesConfigService.getSalesTakeoversettingByBusinessKey(record);
//			auditLog.setTargetid(vo.getId().toString());
//			auditLogService.addAuditLog(auditLog);
//		} catch (Exception e) {
//			e.printStackTrace();
//			auditLogService.addAuditLog(auditLog);
//			//return new ExecuteInfo().error().info("新增成功,但获取记录ID失败,请刷新页面.");
//			returnMap.put("failMessage","新增成功,但获取记录ID失败,请刷新页面.");
//			return returnMap;
//		}
//		//return vo;
//		return returnMap;
//	}
//	/**
//	 * 新增临时接管设置
//	 * 
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value = "/saveSalesTakeoverSettingPerson.do")
//	@ResponseBody
//	public Map<String,Object> saveSalesTakeoverSettingPerson(SysTakeover record, HttpServletRequest request, HttpServletResponse response) {
//		// 添加审计日志
//		SysAuditLog auditLog = new SysAuditLog();
//		User user = (User) request.getSession().getAttribute("user");
//		auditLog.setActorumid(user.getLoginName());
//		auditLog.setActorname(user.getName());
//		auditLog.setCreatetime(new Date());
//		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
//		auditLog.setOperationtype(CommonConstant.AUDIT_LOG_OPERATION_TYPE_TAKEOVER);
//		auditLog.setTargettype(CommonConstant.AUDIT_LOG_TARGET_TYPE_TAKEOVER);
//		Map<String,Object> map = new HashMap<String,Object>();
//		record.setUserumid(user.getLoginName());
//		try {
//			
//			// 新增临时接管设置
//			if (salesConfigService.selectTakeOverUserByTime(record) > 0) {
//				auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
//				auditLog.setDescription("用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid()
//						+ "失败,在授权时间内,接管人已被其他用户授权或接管人已授权给其他人!");
//				auditLogService.addAuditLog(auditLog);
//				//return new ExecuteInfo().error().info("新增失败,在授权时间内,接管人已被其他用户授权或接管人已授权给其他人.重新选择.");
//				map.put(CommonConstant.WARN_INFO, "新增失败,在授权时间内,接管人已被其他用户授权或接管人已授权给其他人.重新选择.");
//				return map;
//			} else if (salesConfigService.selectUserByTime(record) > 0) {
//				auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
//				auditLog.setDescription("用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid()
//						+ "失败,在授权时间内,用户已被其他用户授权或用户已授权给其他人!");
//				auditLogService.addAuditLog(auditLog);
//				//return new ExecuteInfo().error().info("新增失败,在授权时间内,用户已被其他用户授权或用户已授权给其他人.重新选择.");
//				map.put(CommonConstant.WARN_INFO, "新增失败,在授权时间内,用户已被其他用户授权或用户已授权给其他人.重新选择.");
//				return map;
//			} else {
//				salesConfigService.saveSalesTakeoverSetting(record);
//				auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
//				auditLog.setDescription("用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid() + "成功!");
//				map.put(CommonConstant.RESPONSE_SEND_PAGE_SUCCESS_MSG, "用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid() + "成功!");
//			}
//		} catch (Exception e) {
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
//			auditLog.setDescription("用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid() + "失败,SQL异常:"
//					+ e.getMessage() + "!");
//			auditLogService.addAuditLog(auditLog);
//			e.printStackTrace();
//			//return new ExecuteInfo().error().info("新增失败,请联系系统运维人员或稍后再试.");
//			map.put(CommonConstant.WARN_INFO, "新增失败,请联系系统运维人员或稍后再试.");
//			return map;
//		}
//		SysTakeoverExtends vo = null;
//		try {
//			vo = salesConfigService.getSalesTakeoversettingByBusinessKey(record);
//			auditLog.setTargetid(vo.getId().toString());
//			auditLogService.addAuditLog(auditLog);
//		} catch (Exception e) {
//			e.printStackTrace();
//			auditLogService.addAuditLog(auditLog);
//			//return new ExecuteInfo().error().info("新增成功,但获取记录ID失败,请刷新页面.");
//			map.put(CommonConstant.WARN_INFO, "新增成功,但获取记录ID失败,请刷新页面.");
//			return map;
//		}
//		return map;
//	}
//	/**
//	 * 修改临时接管设置
//	 * 
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value = "/updateSalesTakeoverSetting.do" , method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> updateSalesTakeoverSetting(SysTakeover record, HttpServletRequest request,
//			HttpServletResponse response) {
//		Map<String, Object> returnMap = new HashMap<String, Object>();
//		HttpSession session = request.getSession();
//		User user = (User) session.getAttribute("user");
//		/**
//		 * 审计日志
//		 */
//		SysAuditLog auditLog = new SysAuditLog();
//		auditLog.setActorumid(user.getLoginName());
//		auditLog.setActorname(user.getName());
//		auditLog.setCreatetime(new Date());
//		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
//		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_UPDATE);
//		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_TAKEOVER);
//		auditLog.setTargetid(record.getId().toString());
//		try {
//			salesConfigService.updateSalesTakeoverSetting(record);
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
//			auditLog.setDescription("用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid() + "修改成功!");
//			returnMap.put("sucMessage","用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid() + "修改成功!");
//		} catch (Exception e) {
//			e.printStackTrace();
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
//			auditLog.setDescription("用户:" + record.getUserumid() + ",临时授权给:" + record.getTargetuserumid() + "修改失败!"
//					+ e.toString());
//			//return new ExecuteInfo().error().info("更新失败,请联系系统运维人员或稍后再试.");
//			returnMap.put("failMessage","更新失败,请联系系统运维人员或稍后再试.");
//			return returnMap;
//		} finally {
//			auditLogService.addAuditLog(auditLog);
//		}
//		return returnMap;
//	}
//
//	/**
//	 * 删除临时接管设置
//	 * 
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value = "/removeSalesTakeoverSetting.do")
//	@ResponseBody
//	public Map<String, Object> removeSalesTakeoverSetting(Long id, HttpServletRequest request, HttpServletResponse response) {
//		Map<String, Object> returnMap = new HashMap<String, Object>();
//		HttpSession session = request.getSession();
//		User user = (User) session.getAttribute("user");
//		/**
//		 * 审计日志
//		 */
//		SysAuditLog auditLog = new SysAuditLog();
//		auditLog.setActorumid(user.getLoginName());
//		auditLog.setActorname(user.getName());
//		auditLog.setCreatetime(new Date());
//		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
//		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_DELETE);
//		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_TAKEOVER);
//		auditLog.setTargetid(id.toString());
//		try {
//			salesConfigService.removeSalesTakeoverSetting(id);
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
//			auditLog.setDescription("删除临时授权成功!");
//			returnMap.put("sucMessage", "删除临时授权成功!");
//		} catch (Exception e) {
//			e.printStackTrace();
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
//			auditLog.setDescription("删除临时授权失败!");
//			//return new ExecuteInfo().error().info("删除失败,请联系系统运维人员或稍后再试.");
//			returnMap.put("failMessage","删除失败,请联系系统运维人员或稍后再试.");
//			return returnMap;
//		} finally {
//			auditLogService.addAuditLog(auditLog);
//		}
//		return returnMap;
//	}
//	/**
//	 * 删除临时接管设置
//	 * 
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping(value = "/removeSalesTakeoverSettingPerson.do")
//	@ResponseBody
//	public Map<String,Object> removeSalesTakeoverSettingPerson(Long id, HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		User user = (User) session.getAttribute("user");
//		/**
//		 * 审计日志
//		 */
//		SysAuditLog auditLog = new SysAuditLog();
//		auditLog.setActorumid(user.getLoginName());
//		auditLog.setActorname(user.getName());
//		auditLog.setCreatetime(new Date());
//		auditLog.setSystemcode(CommonConstant.SYSTEM_CODE_FINANCE);
//		auditLog.setOperationtype(AuditConstant.AUDIT_LOG_OPERATION_TYPE_DELETE);
//		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_TAKEOVER);
//		auditLog.setTargetid(id.toString());
//		Map<String,Object> map = new HashMap<String,Object>();
//		try {
//			salesConfigService.removeSalesTakeoverSetting(id);
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
//			auditLog.setDescription("删除临时授权成功!");
//			map.put("msg", "删除临时授权成功!");
//		} catch (Exception e) {
//			e.printStackTrace();
//			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
//			auditLog.setDescription("删除临时授权失败!");
//			map.put("msg", "删除临时授权失败!");
//		} finally {
//			auditLogService.addAuditLog(auditLog);
//		}
//		return map;
//	}
//
//	@InitBinder
//	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
//		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
//		binder.registerCustomEditor(Date.class, dateEditor);
//	}

}
