package com.td.common.util;

/**
 * 
 * @author 杨涛
 * @date 2015年3月9日
 */
public class StringUtils {

	public static String trim(String s) {
		if (s == null || "".equals(s)) {
			return "";
		}
		s = s.replace("&nbsp;", " ").replace("\u3000", " ").replace("§", "");
		return s.trim();
	}
	
	public static String lowerFirst(String s) {
		if (s == null || "".equals(s)) {
			return "";
		}
		String firstStr = s.substring(0, 1);
		String endStr = s.substring(1, s.length());
		return firstStr.toLowerCase() + endStr;
	}
	
	public static void main(String[] args) {
		String s = "StringString";
		System.out.println(StringUtils.lowerFirst(s));
	}
	
}
