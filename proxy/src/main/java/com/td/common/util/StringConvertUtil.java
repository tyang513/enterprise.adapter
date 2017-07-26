package com.td.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @description: 字符串转换 下划线格式字符串<---->成驼峰格式字符串 相互转换
 * @author: zhuwenyao 2013-11-19
 * @version: 1.0
 * @modify:
 * @Copyright: 
 */
public class StringConvertUtil {

	/**
	 * @param strs
	 *            待转化字符串
	 * @return
	 * @author estone
	 * @description 下划线格式字符串转换成驼峰格式字符串 eg: player_id -> playerId;<br>
	 *              player_name -> playerName;
	 */
	public static String underScore2CamelCase(String strs) {
		String[] elems = strs.split("_");
		for (int i = 0; i < elems.length; i++) {
			elems[i] = elems[i].toLowerCase();
			if (i != 0) {
				String elem = elems[i];
				char first = elem.toCharArray()[0];
				elems[i] = "" + (char) (first - 32) + elem.substring(1);
			}
		}
		for (String e : elems) {
			System.out.print(e);
		}
		return elems.toString();
	}

	/**
	 * @param param
	 *            待转换字符串
	 * @return
	 * @author estone
	 * @description 驼峰格式字符串 转换成 下划线格式字符串 eg: playerId -> player_id;<br>
	 *              playerName -> player_name;
	 */
	public static String camelCase2Underscore(String param) {
		Pattern p = Pattern.compile("[A-Z]");
		if (param == null || param.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder(param);
		Matcher mc = p.matcher(param);
		int i = 0;
		while (mc.find()) {
			builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
			i++;
		}
		if ('_' == builder.charAt(0)) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}

}
