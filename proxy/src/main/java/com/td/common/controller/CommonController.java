package com.td.common.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.talkingdata.casclient.User;
import com.talkingdata.vo.AccreditUser;
import com.td.admin.service.DcdsUserAndRoleService;
import com.td.common.bean.UserBeanInfo;
import com.td.common.constant.CommonConstant;
import com.td.common.exception.BusinessException;
import com.td.common.util.UIUtil;
import com.td.proxy.entity.admin.SysUser;
import com.td.proxy.service.admin.SysUserService;
import com.td.um.service.MenuUtil;

@Controller
@RequestMapping("main")
@SuppressWarnings({ "rawtypes", "unused" , "unchecked"})
public class CommonController {

	private static final Logger logger = Logger.getLogger(CommonController.class);

	@Autowired
	private DcdsUserAndRoleService dcdsUserAndRoleService;
	
	@Autowired
	private SysUserService sysUserService;
	
//	private static SecurityService securityService = UmRmiServiceFactory.getSecurityService();

	@RequestMapping(value = "/login.do")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException{
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		SysUser sysUser = sysUserService.findUserByUserIdAndPasswod(userId, password);
		if (sysUser == null){
			ModelAndView modelView = new ModelAndView();
			modelView.setViewName("redirect:/view/login.jsp");
			modelView.addObject("loginMsg", "用户名或密码不正确.");
			return modelView;
		}
		logger.info("userName = " + userId + " password = " + password);
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("redirect:/");
		HttpSession session = request.getSession();
		User user = new User();
		user.setLoginName(sysUser.getUserId());
		user.setName(sysUser.getUserName());
		user.setUid(sysUser.getUserId());
		user.setPartnerFullName(sysUser.getUserName());
		session.setAttribute(CommonConstant.SESSION_USER_FLAG, user);
		modelView.addObject("CommonConstant.SESSION_USER_FLAG", user);
		Map<String, Object> sessionMap = MenuUtil.findMenuInfo(user);
		List<Map> menuList = (List<Map>) sessionMap.get("levelmenu1");
		session.setAttribute("menuList", menuList);
		modelView.addObject("menuList", menuList);
		return modelView;
	}
	
	@RequestMapping(value = "/main.do")
    public String indexPage() {
        logger.debug("index");
        return "/welcome";
    }
	
	@RequestMapping(value = "/getUsers.do")
	@ResponseBody
	public List<Map<String, Object>> getUsers(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("CommonController.getUsers()");
//		String umidOrUsername = request.getParameter("umidOrUsername");
//		String q = request.getParameter("q");
//		logger.info(" umidOrUsername == " + umidOrUsername + " q == " + q);
//		if (umidOrUsername == null) {
//			umidOrUsername = q;
//		}
//		List<Map<String, Object>> list = dcdsUserAndRoleService.getUsersByUmidOrUsername(umidOrUsername);
		return null;
	}

	@RequestMapping(value = "/getUserInfo.do")
	@ResponseBody
	public Map<String, Object> getUserInfo(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(CommonConstant.SESSION_USER_FLAG);
		
		AccreditUser accreditUser = new AccreditUser(); // (AccreditUser) session.getAttribute("accreditUser");
		
		accreditUser.setAccreditUserName("admin");
		accreditUser.setAccreditUserUmid("admin");
		accreditUser.setCurrentUser(true);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();	
//		String appcode = (String)session.getServletContext().getAttribute("appcode");
//		List<com.pingan.wlt.dc.umic.entity.Role> roleTmpList = securityService.getRolesByUmid(user.getLoginName(), appcode);
		List<Object> roleTmpList = new ArrayList<Object>();
		resultMap.put("user", user);
		resultMap.put("accreditUser", accreditUser);
		resultMap.put("roles", roleTmpList);
		return resultMap;
	}

	@RequestMapping(value = "/getAllMenus.do")
	@ResponseBody
	public List<Map> getAllMenus(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("CommonController.getAllMenus()");
		HttpSession session = request.getSession();

		List<Map> menuList = (List<Map>) session.getAttribute("levelmenu1");
		Map menuTreeMap = (Map) session.getAttribute("menu");

		for (Map topMenu : menuList) {
			topMenu.put("childrens", getSubMenu1(menuTreeMap, (String) topMenu.get("resourceId")));
		}
		return menuList;
	}

	private List<Map> getSubMenu1(Map menuTreeMap, String resourceId) {
		List<Map> list = new ArrayList<Map>();
		Map resourceMap = (Map) menuTreeMap.get(resourceId);
		List<Map> odlList = (List<Map>) resourceMap.get("nodes");
		for (Map m : odlList) {
			Set setKey = m.keySet();
			for (Object key : setKey) {
				Map m2 = (Map) m.get(key);
				Map m3 = (Map) m2.get("node");
				if (m2.containsKey("nodes")) {
					m3.put("childrens", getSubMenu1(menuTreeMap, (String) m3.get("resourceId")));
				}
				list.add(m3);
			}
		}
		return list;
	}

	@RequestMapping(value = "/getLevel1.do")
	@ResponseBody
	public List<Map> getLevel1(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("CommonController.getLevel1()");
		String json = "";
		logger.info("获取一级菜单信息");
		List<Map> list = (List<Map>) request.getSession().getAttribute("levelmenu1");
		if (list == null) {
			list = new ArrayList<Map>();
		}
		return list;
	}

	@RequestMapping(value = "/getMenu.do")
	@ResponseBody
	public List<Map> getMenu(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("CommonController.getMenu()");
		logger.info("获取菜单信息");
		String resourceId = request.getParameter("resourceId");
		logger.debug("菜单id::" + resourceId);

		Map map = (Map) request.getSession().getAttribute("menu");
		Map resourceMap = (Map) map.get(resourceId);
		List<Map> list = new ArrayList<Map>();
		try {
			Map node = (Map) resourceMap.get("node");
			List<Map> odlList = (List<Map>) resourceMap.get("nodes");
			for (Map m : odlList) {
				Set setKey = m.keySet();
				for (Object key : setKey) {
					Map m2 = (Map) m.get(key);
					Map m3 = (Map) m2.get("node");
					list.add(m3);
				}
			}
			String messeageInfo = UIUtil.getJSONFromList(list);

			logger.debug("菜单" + messeageInfo);
		} catch (Exception e) {
			logger.error("获取菜单异常！", e);
		}
		return list;
	}

	@RequestMapping(value = "/getSubMenu.do")
	@ResponseBody
	public List getSubMenu(HttpServletRequest request, HttpServletResponse response) {
		String json = "";
		logger.info("获取菜单信息");
		String resourceId = request.getParameter("resourceId");
		logger.debug("菜单id::" + resourceId);

		Map map = (Map) request.getSession().getAttribute("menu");
		Map resourceMap = (Map) map.get(resourceId);
		List list = new ArrayList();
		try {
			Map node = (Map) resourceMap.get("node");
			List<Map> odlList = (List<Map>) resourceMap.get("nodes");

			int loopNum = 0;

			if (odlList != null && odlList.size() > 0) {
				// loopNum++;
				loopNum = loopSubMenu(odlList, list, loopNum);
				logger.debug("==loopNum==" + loopNum);
			}

			// for (Map m : odlList) {
			// Set setKey = m.keySet();
			// for (Object key : setKey) {
			// Map m2 = (Map)m.get(key);
			// Map m3 = (Map)m2.get("node");
			// m3.put("name", m3.get("resourceLabel"));
			// //m3.put("url", m3.get("resourceDesc"));
			// list.add(m3);
			// }
			// }

			logger.debug("菜单" + list);
		} catch (Exception e) {
			logger.error("获取菜单异常！", e);
		}
		return list;
	}

	private int loopSubMenu(List<Map> odlList, List list, int loopNum) {
		loopNum += 1;
		logger.debug("loopSubMenu===" + loopNum);

		// List<Map> odlList = (List<Map>)resourceMap.get("nodes");
		for (Map m : odlList) {
			Set setKey = m.keySet();
			for (Object key : setKey) {
				Map m2 = (Map) m.get(key);
				Map m3 = (Map) m2.get("node");

				m3.put("name", m3.get("resourceLabel"));
				// m3.put("url", m3.get("resourceDesc"));
				list.add(m3);
				List<Map> nodesList = (List<Map>) m2.get("nodes");

				if (nodesList != null && nodesList.size() > 0) {
					loopSubMenu(nodesList, list, loopNum);
				}
			}
		}
		return loopNum;
	}

	/**
	 * 查询申请单信息,顶层接口,用于任务界面查询申请单信息
	 * 
	 * @param sheetId
	 * @param sheetType
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getSheetInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSheetInfoBySheetId(String sheetId, String sheetType) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	@RequestMapping(value = "changeUserPassword.do")
	@ResponseBody
	public Map<String, Object> changeUserPassword(UserBeanInfo userBeanInfo) {
//		Map<String, Object> info = new HashMap<String, Object>();
//		try {
//			SecurityService securityService = UmRmiServiceFactory.getSecurityService();
//		    com.pingan.jinke.dc.jrkj.casclient.User user = UserInfoUtil.getUser();
//
//			securityService.changeUserPassword(user.getLoginName(), userBeanInfo.getOldPassword(),
//					userBeanInfo.getNewPassword());
//			info.put(CommonConstant.RESPONSE_SEND_PAGE_SUCCESS_MSG, "修改密码成功");
//		} catch (Exception e) {
//			logger.error("修改密码异常", e);
//			info.put(CommonConstant.RESPONSE_SEND_PAGE_ERROR_MSG, e.getMessage());
//			return info;
//		}
		return null;
	}
}
