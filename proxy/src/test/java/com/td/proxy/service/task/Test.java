package com.td.proxy.service.task;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.td.common.util.ApplicationContextManager;
import com.td.um.service.MenuUtil;
import com.td.um.service.SecurityService;

public class Test {

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
		SecurityService securityService = ApplicationContextManager.getBean(SecurityService.class);
		
		List<LinkedHashMap<String, Object>> menuResourceList = securityService.getAuthMenu4Map("1", null); // 菜单
		List<LinkedHashMap<String, Object>> buttonResourceList = securityService.getAuthMenu4Map("2", null); // 按钮
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		MenuUtil.getTree(menuResourceList, sessionMap, buttonResourceList);
	}
	
}
