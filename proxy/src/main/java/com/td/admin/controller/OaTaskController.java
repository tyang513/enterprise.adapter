package com.td.admin.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.td.admin.entity.SysOaTask;
import com.td.admin.service.OaTaskService;
import com.td.common.bean.PageBean;
import com.td.common.util.UIUtil;

@Controller
@RequestMapping("oaTask")
public class OaTaskController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(OaTaskController.class);
	
	@Autowired
	private OaTaskService oaTaskService ;

	@RequestMapping(value="/queryOaTask.do")
	@ResponseBody
	public String queryOaTask(SysOaTask record,HttpServletRequest request,HttpServletResponse response){
		Long rows = new Long(request.getParameter("rows"));
		Long page = new Long(request.getParameter("page"));
		record.setPageNum(page);
		record.setNumPerPage(rows);
		String gridJson = "" ;
		PageBean pageBean = oaTaskService.queryOaTask(record);
		gridJson = UIUtil.getEasyUiGridJson(pageBean.getTotalCount(),pageBean);
		return gridJson;
	}
	
	
//	
//	
//	@RequestMapping(value="/getAllRole2Select.do")
//	@ResponseBody
//	public String getAllRole2Select(HttpServletRequest request,HttpServletResponse response){
//		String gridJson = "" ;
//		List<FinRole> processList = finUserManageService.queryRole();
//	    gridJson = UIUtil.getJSONFromList(processList);
//		return gridJson;
//	}
//	
//	@RequestMapping(value="/saveFinUser.do")
//	@ResponseBody
//	public Body saveFinUser(FinUser record,HttpServletRequest request,HttpServletResponse response){
//		Body body = null ;
//		try{
//			body = finUserManageService.saveFinUser(record);
//		}catch(Exception e){
//			body = new Body();
//			String message = "message : " + e.getMessage();
//			body.error();
//			body.setMessage(message);
//			logger.debug(message);
//		}
//		return body;
//	}
//	
//	@RequestMapping(value="/deleteFinUser.do")
//	@ResponseBody
//	public Body deleteFinUser(FinUser record,HttpServletRequest request,HttpServletResponse response){
//		Body body = null ;
//		try{
//			body = finUserManageService.deleteFinUser(record);
//		}catch(Exception e){
//			body = new Body();
//			String message = "message : " + e.getMessage();
//			body.error();
//			body.setMessage(message);
//			logger.debug(message);
//		}
//		return body;
//	}
//	
//	@RequestMapping(value="/updateFinUser.do")
//	@ResponseBody
//	public Body updateFinUser(FinUser record,HttpServletRequest request,HttpServletResponse response){
//		Body body = null ;
//		try{
//			body = finUserManageService.updateFinUser(record);
//		}catch(Exception e){
//			body = new Body();
//			String message = "message : " + e.getMessage();
//			body.error();
//			body.setMessage(message);
//			logger.debug(message);
//		}
//		return body;
//	}
//	
}
