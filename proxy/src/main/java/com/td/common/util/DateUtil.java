package com.td.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.DateValidator;



/**
 * 日期工具类
 * 
 * @author 何云洋
 * 
 */
public class DateUtil {

	public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static final String PATTERN_DATE = "yyyy-MM-dd";

	// 2015年01月27日11:38
	public static final String PATTERN_SINA_DATE = "yyyy年MM月dd日 HH:mm";
	
	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @return 日期字符串
	 */
	public static String format(Date date) {
		return format(date, PATTERN);
	}

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @param pattern
	 *            字符串格式
	 * @return 日期字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "null";
		}
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = PATTERN;
		}
		return new java.text.SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @return 日期类型
	 */
	public static Date format(String date) {
		return format(date, null);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @param pattern
	 *            格式
	 * @return 日期类型
	 */
	public static Date format(String date, String pattern) {
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = PATTERN;
		}
		if (date == null || date.equals("") || date.equals("null")) {
			return new Date();
		}
		Date d = null;
		try {
			d = new java.text.SimpleDateFormat(pattern).parse(date);
		} catch (ParseException pe) {
		}
		return d;
	}
	

	public static boolean isValidDate(String inDate) {
		///////方法1：
		 //获取日期验证  
	      DateValidator validator = DateValidator.getInstance();  
	      
	      // 验证/转换日期  
	      Date fooDate = validator.validate(inDate, "yyyy/MM/dd");  
	      if (fooDate == null) {  
	          // 错误 不是日期  
	          return false;  
	      }  
		///////方法2：
		if (inDate == null)
			return false;

		// set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		if (inDate.trim().length() != dateFormat.toPattern().length())
			return false;

		dateFormat.setLenient(false);

		try {
			// parse the inDate parameter
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		
		return true;
	}
	public static boolean isValidDate_1(String inDate) {
		///////方法1：
		 //获取日期验证  
	      DateValidator validator = DateValidator.getInstance();  
	      
	      // 验证/转换日期  
	      Date fooDate = validator.validate(inDate, "yyyy-MM-dd");  
	      if (fooDate == null) {  
	          // 错误 不是日期  
	          return false;  
	      }  
		///////方法2：
		if (inDate == null)
			return false;

		// set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if (inDate.trim().length() != dateFormat.toPattern().length())
			return false;

		dateFormat.setLenient(false);

		try {
			// parse the inDate parameter
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		
		return true;
	}
	/*
	public static void main(String[] args) {

		DateTest test = new DateTest();

		System.out.println(test.isValidDate("2004/02/28"));
		System.out.println(test.isValidDate("2005.02.29"));
		System.out.println(test.isValidDate("2005/02/30"));
	}
	*/
	public static Date format_Date(String date) {
		// set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		try {
			return dateFormat.parse(date.trim());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public static Date format_Date1(String date) {
		// set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			return dateFormat.parse(date.trim());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public static Date getAfterDay(Date date) {
		
	    //Date date = parseDate(strdate, "yyyyMMdd");
	    
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(date);
//	    calendar.add(calendar.DATE,0);
	    calendar.add(calendar.DATE,1);
	    date=calendar.getTime();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    String dateString = formatter.format(date);
	    return date;
	}
    public static java.util.Date parseDate(String dateStr, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
        	return format.parse(dateStr);
        } catch (ParseException e) {
        	e.printStackTrace();
        	return null;
        }
    }
	public static Date convertDate(String lSysTime) {
		long lSysTime1 =0;
			try {
				lSysTime1 = Long.parseLong(lSysTime);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		  SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
		  //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型  
		  
		  java.util.Date dt = new Date(lSysTime1 * 1000);    
		  String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00  
		  System.out.println(sDateTime);  
	    return dt;
	}
	
	public static String dynamicDateFormat(String date) throws ParseException {
		if (date == null || "".equals(date)) {
			return "";
		}
		Date d = null;
		if (Pattern.compile("^\\d{4}年\\d{2}月\\d{2}日 \\d{2}:\\d{2}$").matcher(date).matches()) {
			d = new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:mm").parse(date);
			SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);
			return dateFormat.format(d);
		}
		else if (Pattern.compile("^\\d{4}年\\d{2}月\\d{2}日\\d{2}:\\d{2}$").matcher(date).matches()){
			d = new java.text.SimpleDateFormat("yyyy年MM月dd日HH:mm").parse(date);
			SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);
			return dateFormat.format(d);
		}
		return "";

	}
	
	public static int dateInterval(String startDate, String endDate){
		return dateInterval(format(startDate, "yyyyMMdd"), format(endDate, "yyyyMMdd"));
	}
	
	public static int dateInterval(Date startDate, Date endDate){
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(startDate);  
        long startTime = calendar.getTimeInMillis();               
        calendar.setTime(endDate);
        long endTime = calendar.getTimeInMillis();       
        long interval=(endTime-startTime)/(1000*3600*24);
        
       return Integer.parseInt(String.valueOf(interval));
	}
	
	public static void main(String[] args) {
//		System.out.println(Pattern.compile("^\\d{4}年\\d{2}月\\d{2}日\\d{2}:\\d{2}$").matcher("2015年01月29日01:19").matches());
//		System.out.println(Pattern.compile("^\\d{4}-\\d{2}-$\\d{2}\\s\\d{2}:\\d{2}$").matcher("2015-01-29 01:19").matches());
		
		System.out.println(DateUtil.dateInterval("20150604", "20150608"));
		
	}
	
}
