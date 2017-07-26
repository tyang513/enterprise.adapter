package com.td.common.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.talkingdata.casclient.User;
import com.td.common.constant.CommonConstant;

@Controller
public class WebSecurityController {

	private final static Logger logger = LoggerFactory.getLogger(WebSecurityController.class);
	
	public static final String SECURITY_CODE = "securityCode";
	
	public static String dcdsuserurl;
	
	@RequestMapping(value = "/")
	public ModelAndView mainPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		ModelAndView mv = new ModelAndView("index");
		// ----------获取用户信息----------
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(CommonConstant.SESSION_USER_FLAG);
		if (user == null){
			mv.setViewName("redirect:/view/login.jsp");
			return mv;
		}
		return mv;
	}
	
	@RequestMapping("/keepLive.do")
	@ResponseBody
	public Map<Object, Object> keepLive(HttpServletRequest request, HttpServletResponse response) {
		return new HashMap<Object, Object>();
	}

	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws IOException {	
//		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
//		String url = applicationContext.getBean("serverName")==null?"":applicationContext.getBean("serverName").toString();
//		String casServerLogoutUrl = applicationContext.getBean("casServerLogoutUrl").toString();
//		String casServerLoginUrl = applicationContext.getBean("casServerLoginUrl").toString();
//		String r = casServerLogoutUrl+"?service="+URLEncoder.encode(casServerLoginUrl+"?service="+ url+request.getContextPath()+"/","UTF8");
		String r = "/view/login.jsp";
		request.getSession().invalidate();
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("redirect:/view/login.jsp");
		return modelView;
	}
	
	@RequestMapping("/showUser.do")
	public void showUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Date nowTime=new Date();
		String showUserUrl = dcdsuserurl+nowTime.getTime();
		response.sendRedirect(showUserUrl);
	}
	
	/**
	 * 获取用户所有业务权限
	 * @param request
	 * @param response
	 * @return
	 */
//	@RequestMapping("/getUserAcl.do")
//	@ResponseBody
//	public List<ExtResource> getUserAcl(HttpServletRequest request, HttpServletResponse response){		
//		HttpSession session = request.getSession();
//		List<ExtResource> userAcl = (List<ExtResource>) session.getAttribute("UserACL");
//		if(userAcl==null){
//			User user = (User)session.getAttribute(CommonConstant.SESSION_USER_FLAG);
//			userAcl = UmRmiServiceFactory.getSecurityService().getExtResourcesByTypeAndUmid(204L, user.getLoginName());
//			session.setAttribute("UserACL",userAcl);
//		}
//		return userAcl;
//	}
	
	
	/**
	 * 验证资源是否有权限
	 * http参数resources，用逗号隔开不同资源；验证的结果是一个List，每个list元素包括resource（字符串）和permission（boolean）
	 * @param request
	 * @param response
	 * @return
	 */
//	@RequestMapping("/validatePermissions.do")
//	@ResponseBody
//	public List<Map<String, Object>> validateResourcePermission(HttpServletRequest request, HttpServletResponse response){		
//		HttpSession session = request.getSession();
//		List<ExtResource> userAcl = (List<ExtResource>) session.getAttribute("UserACL");
//		if(userAcl==null){
//			User user = (User)session.getAttribute(CommonConstant.SESSION_USER_FLAG);
//			userAcl = UmRmiServiceFactory.getSecurityService().getExtResourcesByTypeAndUmid(204L, user.getLoginName());
//			session.setAttribute("UserACL",userAcl);
//		}
//		// 从UI获取要验证的资源，放到toValidateResources中
//		List<String> toValidateResources = new ArrayList<String>();
//		String resourcesStr = request.getParameter("resources");
//		if(StringUtils.isNotBlank(resourcesStr)){
//			toValidateResources = Arrays.asList(resourcesStr.split(","));
//		};
//		// 验证
//		List<Map<String, Object>> validateResult = new ArrayList<Map<String,Object>>();
//		for(String resource : toValidateResources){
//			boolean rst = false;
//			for(ExtResource acl :userAcl){
//				if(resource.equals(acl.getResourceDesc())){
//					rst = true;
//					break;
//				}
//			}
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("resource", resource);
//			map.put("permission", rst);
//			validateResult.add(map);
//		}
//		return validateResult;
//	}
	
}