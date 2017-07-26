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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talkingdata.casclient.User;
import com.td.admin.constant.AuditConstant;
import com.td.admin.entity.SysAuditLog;
import com.td.admin.entity.SysDic;
import com.td.admin.entity.SysDicItem;
import com.td.admin.service.AuditLogService;
import com.td.admin.service.BusinessSystemService;
import com.td.admin.service.DictService;
import com.td.common.constant.CommonConstant;

/**
 * 数据字典控制器
 * @author zhouguoping
 */
@Controller
@RequestMapping("dict")
public class DictController {
	private static final Logger logger = LoggerFactory.getLogger(MailManageController.class);

	@Autowired
	private DictService dictService;
	@Autowired
	private BusinessSystemService businessSystemService;
	@Autowired
	private AuditLogService auditLogService;
	
	/**
	 * 获取所有的数据字典
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllDicts.do")
	@ResponseBody
	public String getAllDicts(SysDic dict,@RequestParam Long rows,@RequestParam Long page, @RequestParam(value = "dicName[]", required = false, defaultValue = "") List<String> dicNameList){
		String json = dictService.getAllDicts(dict,rows,page, dicNameList);
		return json;
	}	
	
	/**
	 * 删除数据字典
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/removeDict.do")
	@ResponseBody
	public Object removeDict(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap= new HashMap<String,Object>();

		String id = request.getParameter("id");
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
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_DATA_ITEMS);
		auditLog.setTargetid(id);
		try {
			dictService.deleteDictByPrimaryKey(Long.valueOf(id));
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("删除数据字典成功");
			resultMap.put("message", "OK");

		} catch (Exception e) {
			e.printStackTrace();
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription("删除数据字典失败");
			logger.info("删除失败,请联系系统运维人员或稍后再试.");
			resultMap.put("message", "删除数据字典失败!");

			//return new ExecuteInfo().error().info("删除失败,请联系系统运维人员或稍后再试.");
		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		return resultMap;
	}	
	
	/**
	 * 新增字典
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/saveDict.do")
	@ResponseBody
	public Object saveDict(SysDic finDic){
		Map<String,Object> resultMap= new HashMap<String,Object>();

		finDic.setDickey(Long.valueOf(0));
		finDic.setSystemcode(finDic.getSystemName());
		try {
			SysDic finDicTemp = dictService.getDictByName(finDic.getName());
			if(finDicTemp!=null){
				//return new ExecuteInfo().error().info("新增失败:字典英文名已存在.");
				logger.info("新增失败:字典英文名已存在.");
				resultMap.put("message", "新增失败:字典英文名已存在!");

			}
		} catch (Exception e) {
			e.printStackTrace();
			//return new ExecuteInfo().error().info("检查字典英文名是否重复失败,请联系系统运维人员或稍后再试.");
			logger.info("检查字典英文名是否重复失败,请联系系统运维人员或稍后再试.");
			resultMap.put("message", "检查字典英文名是否重复失败,请联系系统运维人员或稍后再试!");
		}
		try {
			dictService.saveDict(finDic);	
			resultMap.put("message", "OK");

		} catch (Exception e) {
			e.printStackTrace();
			//return new ExecuteInfo().error().info("新增失败,请联系系统运维人员或稍后再试.");
			logger.info("新增失败,请联系系统运维人员或稍后再试.");
			resultMap.put("message", "新增失败,请联系系统运维人员或稍后再试!");
		}
		try {
			finDic = dictService.getDictByName(finDic.getName());
			resultMap.put("message", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			//return new ExecuteInfo().error().info("新增成功,但获取字典ID失败,请刷新页面.");
			logger.info("新增成功,但获取字典ID失败,请刷新页面.");
			resultMap.put("message", "新增成功,但获取字典ID失败,请刷新页面!");
		}
		return resultMap;
	}	

	/**
	 * 修改字典
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateDict.do")
	@ResponseBody
	public Object updateDict(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap= new HashMap<String,Object>();
		String id = request.getParameter("id");
		//String dickey = request.getParameter("dickey");
		String name = request.getParameter("name");
		String description = request.getParameter("description");	
		SysDic finDic = new SysDic();
		finDic.setId(Long.valueOf(id));
		finDic.setDickey(Long.valueOf(0));
		finDic.setName(name);
		finDic.setDescription(description);
		finDic.setMtime(new Date());
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
		auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_DATA_ITEMS);
		auditLog.setTargetid(id);
		try {// 判断name是否重复
			SysDic finDicTemp = dictService.getDictByName(name);
			if(finDicTemp!=null && finDicTemp.getId()!=finDic.getId()){
//				return new ExecuteInfo().error().info("更新失败:字典英文名已存在.");
				logger.info("更新失败:字典英文名已存在!");
				resultMap.put("message", "更新失败:字典英文名已存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
//			return new ExecuteInfo().error().info("检查字典英文名是否重复失败,请联系系统运维人员或稍后再试.");
			logger.info("检查字典英文名是否重复失败,请联系系统运维人员或稍后再试!");
			resultMap.put("message", "检查字典英文名是否重复失败,请联系系统运维人员或稍后再试!");
		}
		try {
			dictService.updateDict(finDic);
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("数据字典修改成功！");
			resultMap.put("message", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription("数据字典修改失败！");
			resultMap.put("message", "数据字典修改失败!");

//			return new ExecuteInfo().error().info("更新失败,请联系系统运维人员或稍后再试.");
			logger.info("更新失败,请联系系统运维人员或稍后再试!");
		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		return resultMap;
	}	
	
	///////////////////////////////////// 字典项操作 /////////////////////////////////////////////////
	
	/**
	 * 获取所有的数据字典
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getDictItems.do")
	@ResponseBody
	public List<SysDicItem> getDictItems(@RequestParam(value="dicid",required=false)Long dicid,@RequestParam(value="parentid",required=false)Long parentid){
		if(parentid==null){
			parentid = 0L;
		}
		List<SysDicItem> list = dictService.getDictItems(dicid,parentid);
		return list;
	}	
	
	/**
	 * 获取所有的数据字典,根据dicKey和parentDicItemKey
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getDictItemsByKey.do")
	@ResponseBody
	public List<SysDicItem> getDictItemsByKey(HttpServletRequest request,HttpServletResponse response){
		String dicKey = request.getParameter("dicKey");
		String parentDicItemKey = request.getParameter("parentDicItemKey");
		List<SysDicItem> list = dictService.getDictItemsByKey(dicKey,parentDicItemKey);
		return list;
	}	
	
	/**
	 * 新增字典项
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/saveDictItem.do")
	@ResponseBody
	public Object saveDictItem(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap= new HashMap<String,Object>();
		String dicItemkey = request.getParameter("dicitemkey");
		String dicItemvalue = request.getParameter("dicitemvalue");
		String dicId = request.getParameter("dicid");	
		String parentid = request.getParameter("parentid");
		if("".equals(parentid)||parentid==null){
			parentid="0";
		}
		SysDicItem finDicItem = new SysDicItem();
		finDicItem.setDicitemkey(dicItemkey);
		finDicItem.setDicitemvalue(dicItemvalue);
		finDicItem.setDicid(Long.valueOf(dicId));
		finDicItem.setParentid(Long.valueOf(parentid));	
		try {// 判断key是否重复
			SysDicItem finDicItemTemp = dictService.getDictItemByBusinessKey(finDicItem.getDicid(),finDicItem.getParentid(), dicItemkey);
			if (finDicItemTemp != null) {
				//return new ExecuteInfo().error().info("新增失败:字典项代码已存在.");
				logger.info("新增失败:字典项代码已存在.");
				resultMap.put("message", "新增失败:字典项代码已存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//return new ExecuteInfo().error().info("检查字典项代码是否重复失败,请联系系统运维人员或稍后再试.");
			logger.info("检查字典项代码是否重复失败,请联系系统运维人员或稍后再试.");
			resultMap.put("message", "检查字典项代码是否重复失败,请联系系统运维人员或稍后再试!");
		}
		try {
			dictService.saveDictItem(finDicItem);	
			resultMap.put("message", "OK");

		} catch (Exception e) {
			e.printStackTrace();
//			return new ExecuteInfo().error().info("新增失败,请联系系统运维人员或稍后再试.");
			logger.info("新增失败,请联系系统运维人员或稍后再试.");
			resultMap.put("message", "新增失败,请联系系统运维人员或稍后再试!");
		}
		return resultMap;
	}	

	/**
	 * 修改字典项
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateDictItem.do")
	@ResponseBody
	public Object updateDictItem(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap= new HashMap<String,Object>();
		String id = request.getParameter("id");
		String dicItemkey = request.getParameter("dicitemkey");
		String dicItemvalue = request.getParameter("dicitemvalue");
		String dicId = request.getParameter("dicid");	
		String parentid = request.getParameter("parentid");
		if("".equals(parentid)||parentid==null){
			parentid="0";
		}
		SysDicItem finDicItem = new SysDicItem();
		finDicItem.setId(Long.valueOf(id));
		finDicItem.setDicitemkey(dicItemkey);
		finDicItem.setDicitemvalue(dicItemvalue);
		finDicItem.setDicid(Long.valueOf(dicId));
		finDicItem.setParentid(Long.valueOf(parentid));
		    /**
		     * 审计信息
		     */
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
			auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_DATA_ITEMS);
			auditLog.setTargetid(id);
		try {// 判断key是否重复
			SysDicItem finDicItemTemp = dictService.getDictItemByBusinessKey(finDicItem.getDicid(),finDicItem.getParentid(), dicItemkey);
			if (finDicItemTemp != null && finDicItemTemp.getId()!=finDicItem.getId()) {
//				return new ExecuteInfo().error().info("新增失败:字典项代码已存在.");
				logger.info("新增失败:字典项代码已存在!");
				resultMap.put("message", "新增失败:字典项代码已存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
//			return new ExecuteInfo().error().info("检查字典项代码是否重复失败,请联系系统运维人员或稍后再试.");
			logger.info("检查字典项代码是否重复失败,请联系系统运维人员或稍后再试!");
			resultMap.put("message", "检查字典项代码是否重复失败,请联系系统运维人员或稍后再试!");
		}
		try {
			dictService.updateDictItem(finDicItem);
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("修改数据字典项成功");
			resultMap.put("message", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription("修改数据字典项失败");
//			return new ExecuteInfo().error().info("更新失败,请联系系统运维人员或稍后再试.");
			logger.info("检查字典项代码是否重复失败,请联系系统运维人员或稍后再试!");
			resultMap.put("message", "修改数据字典项失败!");
		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		return resultMap;
	}	

	/**
	 * 删除字典项
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/removeDictItem.do")
	@ResponseBody
	public Object removeDictItem(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap= new HashMap<String,Object>();

		String id = request.getParameter("id");
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
			auditLog.setTargettype(AuditConstant.AUDIT_LOG_TARGET_TYPE_DATA_ITEMS);
			auditLog.setTargetid(id);
		try {
			dictService.deleteDictItemByPrimaryKey(Long.valueOf(id));
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_YES);
			auditLog.setDescription("数据字典项删除成功!");
			resultMap.put("message", "OK");

		} catch (Exception e) {
			e.printStackTrace();
			auditLog.setResult(CommonConstant.AUDIT_LOG_RESULT_NO);
			auditLog.setDescription("数据字典项删除失败!");
//			return new ExecuteInfo().error().info("删除失败,请联系系统运维人员或稍后再试.");
			logger.info("删除失败,请联系系统运维人员或稍后再试!");
			resultMap.put("message", "数据字典项删除失败!");
		}finally{
			auditLogService.addAuditLog(auditLog);
		}
		return resultMap;
	}	
	
}
