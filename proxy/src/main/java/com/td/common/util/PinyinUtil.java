package com.td.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PinyinUtil {
	
	/**
	 * 得到中文字符串首字母(大写)
	 * @param str
	 * @return
	 */
	public static String getUpperCasePinyinHeadChar(String str) {
		String rst = getPinyinHeadChar(str).toUpperCase();
		return rst;
	}

	/**
	 * 得到中文字符串首字母
	 * @param str
	 * @return
	 */
    public static String getPinyinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }	
	
	public static void main(String[] args) {
		
	}

}
