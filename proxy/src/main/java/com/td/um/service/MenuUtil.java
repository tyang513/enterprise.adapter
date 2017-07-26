package com.td.um.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.talkingdata.casclient.User;
import com.td.common.util.ApplicationContextManager;

/**
 * 菜单处理类
 * 
 * @author changpengfei
 * 
 */
public class MenuUtil {

	public static Map<String, Object> findMenuInfo(User user) throws IllegalAccessException, InvocationTargetException {
		SecurityService securityService = ApplicationContextManager.getBean(SecurityService.class);
		List<LinkedHashMap<String, Object>> menuResourceList = securityService.getAuthMenu4Map("1", user.getUid()); // 菜单
		List<LinkedHashMap<String, Object>> buttonResourceList = securityService.getAuthMenu4Map("2", user.getUid()); // 按钮
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		MenuUtil.getTree(menuResourceList, sessionMap, buttonResourceList);
		return sessionMap;
	}

	public static void getTree(List<LinkedHashMap<String, Object>> munuResouseList, Map<String, Object> session,
			List<LinkedHashMap<String, Object>> buttonResouseList) throws IllegalAccessException, InvocationTargetException {

		// 菜单权限处理
		List<ExtResource> menuList = formateToMenu(munuResouseList); //菜单转换成ExtResource对象

		for (ExtResource menu : menuList) {
			getChildren(menu, menuList);
		}

		ExtResource root = getRootMenu(menuList); // 获取root

		List<ExtResource> list2 = new ArrayList<ExtResource>();
		for (ExtResource menu : menuList) {
			if (root != null && menu.getPrid().equals(root.getRid())) {
				list2.add(menu);
			}
		}

		session.put("menuList", list2);
		// url放入session
		List<String> authList = new ArrayList<String>();
		for (ExtResource menu : menuList) {
			String attr = menu.getExtAttr1();
			if (!StringUtils.isBlank(attr)) {
				if (attr.contains(",")) {
					for (String s : attr.split(",")) {
						authList.add(s);
					}
				} else {
					if (!StringUtils.isBlank(attr))
						authList.add(attr);
				}
			}
		}

		// 按钮权限处理
		List<ExtResource> buttonsList = formateToMenu(buttonResouseList);
		for (ExtResource button : buttonsList) {
			// 兼容以前系统
			button.setResourceDesc(button.getResourceUri());
			String attr = button.getExtAttr1();
			if (!StringUtils.isBlank(attr)) {
				if (attr.contains(",")) {
					for (String s : attr.split(",")) {
						authList.add(s);
					}
				} else {
					if (!StringUtils.isBlank(attr))
						authList.add(attr);
				}
			}
		}

		session.put("UserACL", buttonsList);
		session.put("authList", authList);
		// 财务专门处理
		getTreeExtend(list2, session);
	}

	/**
	 * 财务专用
	 * 
	 * @param listMenuList
	 * @param session
	 */
	@SuppressWarnings("unchecked")
	public static void getTreeExtend(List<ExtResource> listMenuList, Map<String, Object> session) {
		// 得到第一级菜单
		List<LinkedHashMap<String, Object>> listConvert = new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> levelMenu1 = new ArrayList<LinkedHashMap<String, Object>>();
		ObjectMapper om = new ObjectMapper();
		listConvert = om.convertValue(listMenuList, levelMenu1.getClass());
		session.put("levelmenu1", listConvert);
		// 得到menu子菜单
		Map<String, Object> menuMap = new HashMap<String, Object>();
		String mark = "first";
		for (ExtResource extResource : listMenuList) {
			menuMap.put(extResource.getResourceCode(), getChildMap(extResource, mark));
		}
		session.put("menu", menuMap);
	}

	public static Map<String, Object> getChildMap(ExtResource extResource, String mark) {
		if (extResource.getResourceCode().startsWith("101")) {
			System.out.println(extResource.getResourceCode());
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map.put("node", mapper.convertValue(extResource, Map.class));
		List<ExtResource> children = extResource.getChildrens();
		if (children != null && children.size() > 0) {
			List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
			for (ExtResource resource : children) {
				nodes.add(getChildMap(resource, null));
			}
			map.put("nodes", nodes);
		}
		map1.put(extResource.getResourceCode(), map);
		if ("first".equals(mark)) {
			map1 = map;
		}
		return map1;
	}

	/**
	 * @description 判断是否是父节点
	 * @param list
	 * @param resourceId
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean isParrent(List<LinkedHashMap<String, Object>> list, String resourceId) {
		boolean isParent = false;
		int size = resourceId.length();
		for (LinkedHashMap<String, Object> map : list) {
			if (map.get("resourceCode") == null || map.get("resourceCode").toString().equals("root"))
				continue;
			String resourceId2 = map.get("resourceCode").toString();
			if (resourceId.length() < resourceId2.length()) {
				resourceId2 = resourceId2.substring(0, size);
				if (resourceId.equals(resourceId2)) {
					isParent = true;
					break;
				}
			}
		}
		return isParent;
	}

	private static ExtResource getRootMenu(List<ExtResource> menuList) {
		ExtResource menu = null;
		for (ExtResource temp : menuList) {
			if (temp.getPrid().equals(0)) {
				menu = temp;
			}
		}
		return menu;
	}

	private static void getChildren(ExtResource menu, List<ExtResource> list) {
		List<ExtResource> mList = new ArrayList<ExtResource>();
		for (ExtResource temp : list) {
			if (menu.getRid().equals(temp.getPrid())) {
				mList.add(temp);
			}
		}
		menu.setChildrens(mList);
	}

	public static List<ExtResource> formateToMenu(List<LinkedHashMap<String, Object>> list) {
		List<ExtResource> menuList = new ArrayList<ExtResource>();
		for (LinkedHashMap<String, Object> linkedHashMap : list) {
			ExtResource menu = new ExtResource();
			menu.setRid((Integer) linkedHashMap.get("rid"));
			menu.setPrid((Integer) linkedHashMap.get("parentResourceRid"));
			menu.setResourceDesc((linkedHashMap.get("resourceUri") == null) ? null : linkedHashMap.get("resourceUri").toString());
			menu.setResourceId((linkedHashMap.get("resourceCode") == null) ? null : linkedHashMap.get("resourceCode").toString());
			menu.setResourceLabel((linkedHashMap.get("resourceName") == null) ? null : linkedHashMap.get("resourceName").toString());
			menu.setAction((linkedHashMap.get("resourceName") == null) ? null : linkedHashMap.get("resourceName").toString());
			menu.setAppRid((Integer) linkedHashMap.get("appRid"));
			menu.setExtAttr1((linkedHashMap.get("extAttr1") == null) ? null : linkedHashMap.get("extAttr1").toString());
			menu.setExtAttr2((linkedHashMap.get("extAttr2") == null) ? null : linkedHashMap.get("extAttr2").toString());
			menu.setExtAttr3((linkedHashMap.get("extAttr3") == null) ? null : linkedHashMap.get("extAttr3").toString());
			menu.setExtAttr4((linkedHashMap.get("extAttr4") == null) ? null : linkedHashMap.get("extAttr4").toString());
			menu.setExtAttr5((linkedHashMap.get("extAttr5") == null) ? null : linkedHashMap.get("extAttr5").toString());
			menu.setExtAttr6((linkedHashMap.get("extAttr6") == null) ? null : linkedHashMap.get("extAttr6").toString());
			menu.setParentResourceRid((Integer) linkedHashMap.get("parentResourceRid"));
			menu.setResourceCode((linkedHashMap.get("resourceCode") == null) ? null : linkedHashMap.get("resourceCode").toString());
			menu.setResourceName((linkedHashMap.get("resourceName") == null) ? null : linkedHashMap.get("resourceName").toString());
			menu.setResourceTypeRid((Integer) linkedHashMap.get("resourceTypeRid"));
			menu.setResourceUri((linkedHashMap.get("resourceUri") == null) ? null : linkedHashMap.get("resourceUri").toString());
			menu.setParentId((linkedHashMap.get("parentId") == null) ? null : linkedHashMap.get("parentId").toString());
			menuList.add(menu);
		}
		return menuList;
	}
}
