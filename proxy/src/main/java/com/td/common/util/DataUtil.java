package com.td.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * 
 * @description: 数据处理
 * @author: liucaixing  2013-9-27
 * @version: 1.0
 * @modify: 
 * @Copyright: 
 */
public class DataUtil {
	
	 private static final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";

	 private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";

	 private static final double MAX_VALUE = 9999999999999.99D;

	 public static String change(double v) {
	  if (v < 0 || v > MAX_VALUE)
	   return "参数非法!";
	  long l = Math.round(v * 100);
	  if (l == 0)
	   return "零元整";
	  String strValue = l + "";
	  // i用来控制数
	  int i = 0;
	  // j用来控制单位
	  int j = UNIT.length() - strValue.length();
	  String rs = "";
	  boolean isZero = false;
	  for (; i < strValue.length(); i++, j++) {
	   char ch = strValue.charAt(i);

	   if (ch == '0') {
	    isZero = true;
	    if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {
	     rs = rs + UNIT.charAt(j);
	     isZero = false;
	    }
	   } else {
	    if (isZero) {
	     rs = rs + "零";
	     isZero = false;
	    }
	    rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
	   }
	  }

	  if (!rs.endsWith("分")) {
	   rs = rs + "整";
	  }
	  rs = rs.replaceAll("亿万", "亿");
	  return rs;
	 }
	
	
	public static BigDecimal  formaterBigDecimal(String data){
		
	 DecimalFormat f = new DecimalFormat("#.##");        
	 Double d = null;
	try {
		d = Double.parseDouble(f.parseObject(data).toString());
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}        
	 BigDecimal big = new BigDecimal(d);
	 
	 return big;
	 
	}
	
	
	/**
	 * 发票档期转换
	 * @return
	 */
	public static String level(long i){
		//发票档，1，千元，2，万元，3，十万元，4，百万元，5，千万元
		String s=Long.toString(i);
		
		String level="";
		
		int j=Integer.parseInt(s);
		
		switch(j){
		
		case 1:
			level= "千元";
		    break;
		case 2:
			level= "万元";
			break;
		case 3:
			level="十万元";
			break;
		case 4:
			level="百万元";
			break;
		case 5:
			level="千万元";
			break;
		default:
			break;
		
		}
		//return null;
		return level;
		
		
	}
	
	/**
	 * 判断id是否为空
	 * @param id
	 * @return
	 */
	public static String isNull(String id){
		
		if(id==null){
			id="0";
		}else{
		 id=id.trim();
		 if(id.equals("null")){
			 id="0";
			 
		 }else{
			 if(id.length()==0){
				 id="0";
			 }
		 }
		}
		return id;
		
	}
	
	/**
	 * 判断字符串是否为Null 为nuLL的改为""空串，不为null的trim一下
	 * TODO 添加方法注释
	 * @param args
	 */
	public static String getIsNullStr(String str){
		
		if(str==null){
			
			return "";
		}else{
			str=str.trim();
			if(str.equals("null")){
				str="";
			}
			return str;
		}
		
	}
	
	
	/**
	 * 获得数字字符串 去除字符串里的","
	 * @return
	 */
	public static String getNumber(String numberStr){
		return numberStr.replace(",", "");
	}
	
	 public static void main(String[] args){   
         System.out.println(change(Double.parseDouble("0")));   
            
     }
	

}
