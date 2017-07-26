package com.td.common.util;

/**
 * @（#）:DateTimeUtil.java
 * @description: 时间日期工具
 * @version: Version 1.
 * @modify:
 * @Copyright: 
 */

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 日期工具 修改历史: V:0.2 U:wang M:把sdf声明为不可变静态常量
 */
public class DateTimeUtil {
	private static final String FormatDate = "yyyy-MM-dd";

	private static final String FormatDateTime = "yyyyMMddHHmmss";

	private static final String FormatTimestamp = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 到毫秒级数据转换
	 */
	private static final String FormatTimestampMil = "yyyy-MM-dd HH:mm:ss.S";

	private static final String SpecificFormat = "yyyyMMdd";

	private static final String SPECIFIC_FORMAT = "yyMMdd";

	private static final SimpleDateFormat sdf = new SimpleDateFormat(FormatTimestamp);
	private static SimpleDateFormat sdfm = new SimpleDateFormat(FormatTimestampMil);
	private static SimpleDateFormat dffd = new SimpleDateFormat(FormatTimestamp);
	private static SimpleDateFormat dffdL = new SimpleDateFormat(FormatDate);
	private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static {
		dffd.setTimeZone(new GregorianCalendar().getTimeZone());
		dffd.setLenient(false);
		dffdL.setTimeZone(new GregorianCalendar().getTimeZone());
		dffdL.setLenient(false);
	}

	/**
	 * 根据传入日期增加i天
	 * 
	 * @param date
	 *            要增加的日期
	 * @param i
	 * @param date
	 * @return 格式为"yyyy-MM-dd"的字符串
	 * @throws Exception
	 * @throws IllegalArgumentException
	 * @roseuid 44D6EB790190
	 */
	public static String AddDate(String date, int i) throws IllegalArgumentException {
		Date d = null;

		d = DateTimeUtil.dateFormat(date);
		long ldate = d.getTime();
		ldate += i * 24 * 60 * 60 * 1000;
		return new SimpleDateFormat(DateTimeUtil.FormatDate).format(new Date(ldate));
	}

	/**
	 * 
	 * @param datetime
	 * @param i
	 * @return java.lang.String 格式为"yyyy-MM-dd HH:mm:ss"的字符串
	 * @throws IllegalArgumentException
	 * @roseuid 44D6EB79026C
	 */
	public static String AddMinute(String datetime, int i) throws IllegalArgumentException {
		String date = datetime.substring(0, 10);
		String time = datetime.substring(11);
		Date d = DateTimeUtil.dateFormat(date, time);
		long ldate = d.getTime();
		ldate += i * 60 * 1000;
		return new SimpleDateFormat(DateTimeUtil.FormatTimestamp).format(new Date(ldate));
	}

	/**
	 * 
	 * @param datetime
	 * @param i
	 * @return java.lang.String 格式为"yyyy-MM-dd HH:mm:ss"的字符串
	 * @throws IllegalArgumentException
	 * @roseuid 44D6EB79026C
	 */
	public static String AddSecond(String datetime, int i) throws IllegalArgumentException {
		String date = datetime.substring(0, 10);
		String time = datetime.substring(11);
		Date d = DateTimeUtil.dateFormat(date, time);
		long ldate = d.getTime();
		ldate += i * 1000;
		return new SimpleDateFormat(DateTimeUtil.FormatTimestamp).format(new Date(ldate));
	}

	/**
	 * 根据传入日期增加i天
	 * 
	 * @param date
	 *            要增加的日期
	 * @param i
	 * @param date
	 * @return 增加后的日期
	 * @roseuid 44D6EB790257
	 */
	public static Date dateAdd(Date date, long i) {
		long ldate = date.getTime();
		ldate += i * 24 * 60 * 60 * 1000;
		return new Date(ldate);
	}

	/**
	 * 根据传入参数两个时间差单位:天 减数放前 被减数放后
	 * 
	 * @param EndDate
	 * @param BegDate
	 * @return int
	 * @throws IllegalArgumentException
	 * @throws java.lang.Exception
	 * @roseuid 44D6EB780197
	 */
	public static int dateDiff(String EndDate, String BegDate) throws IllegalArgumentException {
		Date firDate = dateFormat(BegDate);
		Date secDate = dateFormat(EndDate);
		long r = secDate.getTime() - firDate.getTime();
		return (int) (r / 60000 / 60 / 24);
	}

	/**
	 * 根据传入参数两个时间差单位:分钟 减数放前 被减数放后
	 * 
	 * @param EndDate
	 * @param EndTime
	 * @param BegDate
	 * @param BegTime
	 * @return int
	 * @throws IllegalArgumentException
	 * @roseuid 44D6EB7800D9
	 */
	public static int dateDiff(String EndDate, String EndTime, String BegDate, String BegTime) throws IllegalArgumentException {
		Date firDate = dateFormat(BegDate, BegTime);
		Date secDate = dateFormat(EndDate, EndTime);
		long r = secDate.getTime() - firDate.getTime();
		return (int) (r / 60000);
	}

	/**
	 * 根据传入参数两个时间差单位:分钟 减数放前 被减数放后
	 * 
	 * @param EndDate
	 * @param EndTime
	 * @param BegDate
	 * @param BegTime
	 * @return int
	 * @throws IllegalArgumentException
	 * @roseuid 44D6EB7800D9
	 */
	public static int dateDiff(Date startTime, Date endTime) throws IllegalArgumentException {
		long r = endTime.getTime() - startTime.getTime();
		return (int) (r / 60000);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            输入的日期String
	 * @return java.util.Date 返回日期yyyy-MM-dd
	 * @throws cn.com.sseeai.oc.OCException
	 * @roseuid 44D6EB7903A1
	 */
	public static Date dateFormat(String date) {
		try {
			return fromDateString(date, FormatDate);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            输入的日期String
	 * @param lt
	 *            是否长日期
	 * @return java.util.Date lt为true返回日期yyyy-MM-dd hh:mm:ss，否则返回yyyy-MM-dd
	 * @throws cn.com.sseeai.oc.OCException
	 * @roseuid 44D6EB7903A1
	 */
	public static Date dateFormat(String date, boolean lt) {
		try {
			return lt ? fromDateStringFixed(date) : fromDateStringFixedL(date);
			// return lt ? fromDateString(date, FormatTimestamp) :
			// fromDateString(
			// date, FormatDate);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 根据传入日期和时间返回Date对象
	 * 
	 * @param date
	 * @param time
	 * @return java.util.Date 返回时间，格式为yyyy-MM-dd hh:mm:ss
	 * @throws IllegalArgumentException
	 * @roseuid 44D6EB790333
	 */
	public static Date dateFormat(String date, String time) {
		try {
			return fromDateString(date + " " + time, FormatTimestamp);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 转换日期类型为字符串YYYY-MM-DD格式
	 * 
	 * @param dt
	 *            要转换的时间
	 * @return 转换后的字符串
	 * @roseuid 44D6EB7A00E6
	 */
	public static String formatDate(Date dt) {
		SimpleDateFormat sdf = new SimpleDateFormat(FormatDate);
		if (dt == null) {
			return "";
		}
		return sdf.format(dt);
	}

	/**
	 * 转换日期类型为字符串yyyy-MM-dd hh:MM:ss格式
	 * 
	 * @param dt
	 *            要转换的时间
	 * @return 转换后的字符串
	 * @roseuid 44D6EB7A00E6
	 */
	public static String formatLongDate(Date dt) {
		if (dt == null) {
			return null;
		}
		String dateStr = null;
		synchronized (sdf) {
			dateStr = sdf.format(dt);
		}
		return dateStr;
	}

	/**
	 * 根据dateStr获得年月日格式的Date。
	 * 
	 * @author liuxinghua
	 * 
	 * @param dateStr
	 *            yyyy-MM-dd年月日的字符格式
	 * @return
	 * @since 1.0_2012-10-24
	 */
	public static Date toDate(String dateStr) {
		ParsePosition pos = new ParsePosition(0);
		return formatDate.parse(dateStr, pos);
	}

	/**
	 * 转换日期类型为字符串yyyy-MM-dd hh:MM:ss.S格式
	 * 
	 * @param dt
	 *            要转换的时间
	 * @return 转换后的字符串
	 * @roseuid 44D6EB7A00E6
	 */
	public static String formatLongDateMil(Date dt) {
		if (dt == null) {
			return null;
		}
		String dateStr = null;
		synchronized (sdfm) {
			dateStr = sdfm.format(dt);
		}
		return dateStr;
	}

	/**
	 * @param temp
	 * @return java.lang.String
	 * @roseuid 44D6EB7800CF
	 */
	private static String formatNum(int temp) {
		String reStr;
		if (temp < 10) {
			reStr = "0" + temp;
		} else {
			reStr = "" + temp;
		}
		return reStr;
	}

	/**
	 * @param dateString
	 * @param fromFormat
	 * @return java.util.Date
	 * @roseuid 44D6EB7A0027
	 */
	public static Date fromDateString(String dateString, String fromFormat) {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat df = new SimpleDateFormat(fromFormat);
		df.setTimeZone(gc.getTimeZone());
		df.setLenient(false);
		Date date = df.parse(dateString, new ParsePosition(0));
		return date;
	}

	/**
	 * @param dateString
	 * @param fromFormat
	 * @return java.util.Date
	 * @roseuid 44D6EB7A0027
	 */
	public static Date fromDateStringFixed(String dateString) throws ParseException {
		Date date = null;
		synchronized (dffd) {
			date = dffd.parse(dateString);
		}
		return date;
	}

	public static Date dateStringToDateTime(String date, String time) throws ParseException {
		String dateTime = date + " " + time;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.parse(dateTime);

	}

	/**
	 * @param Date
	 *            date类型数据
	 * @param fromFormat
	 * @return String 返回"yyyy-MM-dd HH:mm:ss"格式日期
	 * @roseuid 44D6EB7A0027
	 */
	public static String fromDateStringFixed(Date dateString) {
		if (dateString == null) {
			return null;
		}
		String date = null;
		synchronized (dffd) {
			date = dffd.format(dateString);
		}
		return date;
	}

	/**
	 * @param dateString
	 * @param fromFormat
	 * @return java.util.Date
	 * @roseuid 44D6EB7A0027
	 */
	public static Date fromDateStringFixedL(String dateString) throws ParseException {
		Date date = null;
		synchronized (dffdL) {
			date = dffdL.parse(dateString);
		}
		return date;
	}

	/**
	 * @param Date
	 *            传入Date类型日期
	 * @param fromFormat
	 * @return String 返回"yyyy-MM-dd"格式日期
	 * @roseuid 44D6EB7A0027
	 */
	public static String fromDateStringFixedL(Date date) {
		String dateString = null;
		synchronized (dffdL) {
			dateString = dffdL.format(date);
		}
		return dateString;
	}

	/**
	 * 取日期的小时,24小时模式
	 * 
	 * @param dt
	 * @return
	 */
	public static int getHours(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 取日期的分钟
	 * 
	 * @param dt
	 * @return
	 */
	public static int getMinutes(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		return cal.get(Calendar.MINUTE);
	}

	/**
	 * 返回当月最大时间
	 * 
	 * @param dtime
	 * @return java.lang.String
	 * @throws IllegalArgumentException
	 * @roseuid 44D6EB780008
	 */
	public static String getMonthMaxDay(String dtime) throws IllegalArgumentException {
		Calendar date = Calendar.getInstance();
		String month = "";
		String day = "";
		date.setTime(DateTimeUtil.dateFormat(dtime));
		month = DateTimeUtil.formatNum(date.get(Calendar.MONTH) + 1);
		day = DateTimeUtil.formatNum(date.getActualMaximum(Calendar.DAY_OF_MONTH));
		return date.get(Calendar.YEAR) + "-" + month + "-" + day;
	}

	/**
	 * 取日期的秒
	 * 
	 * @param dt
	 * @return
	 */
	public static int getSeconds(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		return cal.get(Calendar.SECOND);
	}

	/**
	 * 
	 * @param strDate
	 * @param strTime
	 * @param strComBDate
	 * @param strComBTime
	 * @param strComEDate
	 * @param strComETime
	 * @return true,在范围之内；否则， false，不在范围之内
	 * @throws IllegalArgumentException
	 */
	public static boolean inBound(String strDate, String strTime, String strComBDate, String strComBTime, String strComEDate, String strComETime)
			throws IllegalArgumentException {
		boolean flag = false;
		Date date = dateFormat(strDate, strTime);

		Date comBDate = dateFormat(strComBDate, strComBTime);
		Date comEDate = dateFormat(strComEDate, strComETime);

		if (date.after(comBDate) && date.before(comEDate)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @param strBDate
	 * @param strBTime
	 * @param strEDate
	 * @param strETime
	 * @param strComBDate
	 * @param strComBTime
	 * @param strComEDate
	 * @param strComETime
	 * @return boolean true -- 在范围之内 false -- 不在范围之内
	 * @throws IllegalArgumentException
	 * @roseuid 44D6EB780315
	 */
	public static boolean inBound(String strBDate, String strBTime, String strEDate, String strETime, String strComBDate, String strComBTime,
			String strComEDate, String strComETime) throws IllegalArgumentException {
		boolean flag = true;
		Date bDate = dateFormat(strBDate, strBTime);
		Date eDate = dateFormat(strEDate, strETime);

		Date comBDate = dateFormat(strComBDate, strComBTime);
		Date comEDate = dateFormat(strComEDate, strComETime);

		if (inBoundBeg(bDate, eDate, comBDate)) {
			// 判断结束时间
			if (!inBoundEnd(bDate, eDate, comEDate)) {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * @param bDate
	 * @param eDate
	 * @param comBDate
	 * @return boolean true -- 在范围之内 false -- 不在范围之内
	 * @roseuid 44D6EB7803E9
	 */
	private static boolean inBoundBeg(Date bDate, Date eDate, Date comBDate) {
		boolean flag = true;
		if ((bDate.before(comBDate) || bDate.equals(comBDate)) && eDate.after(comBDate)) {
			flag = false;
		}
		return flag;
	}

	/**
	 * @param bDate
	 * @param eDate
	 * @param comEDate
	 * @return boolean true -- 在范围之内 false -- 不在范围之内
	 * @roseuid 44D6EB7900C7
	 */
	private static boolean inBoundEnd(Date bDate, Date eDate, Date comEDate) {
		boolean flag = true;
		if (bDate.before(comEDate) && (eDate.after(comEDate) || eDate.equals(comEDate))) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 取大时间
	 * 
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return 返回大的时间
	 * @throws IllegalArgumentException
	 */
	public static String maxDate(String date1, String date2) throws IllegalArgumentException {
		if (DateTimeUtil.dateFormat(date1, false).after(DateTimeUtil.dateFormat(date2, false))) {
			return date1;
		} else {
			return date2;
		}
	}

	/**
	 * 转换日期类型为字符串YYYYMMDD格式
	 * 
	 * @param dt
	 *            要转换的时间
	 * @return 转换后的字符串
	 * @roseuid 44D6EB7A00E6
	 */
	public static String specificFormatDate(Date dt) {
		SimpleDateFormat sdf = new SimpleDateFormat(SpecificFormat);
		if (dt == null) {
			return "";
		}
		return sdf.format(dt);
	}

	/**
	 * 转换日期类型为int YYYYMMDD格式
	 * 
	 * @param dt
	 *            要转换的时间
	 * @return 转换后的字符串
	 * @roseuid 44D6EB7A00E6
	 */
	public static int getCurrDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(SpecificFormat);
		return Integer.parseInt(sdf.format(new Date()));
	}

	/**
	 * 转换日期类型为字符串YYMMDD格式
	 * 
	 * @param dt
	 *            要转换的时间
	 * @return 转换后的字符串
	 * @roseuid 44D6EB7A00E6
	 */
	public static String specificFormatDateYYMMDD(Date dt) {
		SimpleDateFormat sdf = new SimpleDateFormat(SPECIFIC_FORMAT);
		if (dt == null) {
			return "";
		}
		return sdf.format(dt);
	}

	/**
	 * 转换日期类型为字符串yyyyMMddHHmmss
	 * 
	 * @param dt
	 *            Date对象
	 * @return 字符串
	 */
	public static String formatDateTime(Date dt) {
		SimpleDateFormat sdf = new SimpleDateFormat(FormatDateTime);
		if (dt == null) {
			return "";
		}
		return sdf.format(dt);
	}

	/**
	 * 转换日期类型为字符串 可以自定义格式yyyyMMddHHmmss
	 * 
	 * @param dt
	 *            Date对象
	 * @return 字符串
	 */
	public static String formatDateTime(Date dt, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (dt == null) {
			return "";
		}
		return sdf.format(dt);
	}

	/**
	 * @since 1.0
	 * @roseuid 44C5A3840067
	 */
	public DateTimeUtil() {

	}

	/**
	 * 时间表示格式
	 */
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Get the current date string representation.
	 * 
	 * @param dateFormat
	 *            the input dateFormat. See the
	 *            <code>java.text.SimpleDateFormat</code> API for date format
	 *            string examples 示例： String currDateStr =
	 *            DateUtil.getCurrentDateString("yyyy-MM-dd HH:mm:ss");
	 */
	public static String getCurrentDateString(String dateFormat) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setTimeZone(TimeZone.getDefault());

		return sdf.format(cal.getTime());
	}

	/**
	 * 得到系统当前时间。格式："yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return 系统当前时间。格式："yyyy-MM-dd HH:mm:ss"
	 */
	public static String getCurrentDateString() {
		return getCurrentDateString(DEFAULT_FORMAT);
	}

	/**
	 * 得到当前系统时间的毫秒数
	 * 
	 * @return long 毫秒数
	 */
	public static long getCurrentDateLong() {

		Calendar cal = Calendar.getInstance();

		return cal.getTime().getTime();
	}

	/**
	 * 得到当前系统时间
	 * 
	 * @return long 毫秒数
	 */
	public static Date getCurrentDate() {

		Date currDate = new Date();

		return currDate;
	}

	/**
	 * 比较系统当前时间是否在传入的开始与结束时间范围内
	 * 
	 * @param startDate
	 *            String yyyy-MM-dd HH:mm:ss 开始时间
	 * @param endDate
	 *            String yyyy-MM-dd HH:mm:ss 结束时间
	 * @param compareTs
	 * @return true在范围内,false不在范围内
	 */
	public static boolean compare(String startDate, String endDate, long compareTs) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(fromDateStringFixed(startDate));
			long startMi = cal.getTimeInMillis();
			cal.setTime(fromDateStringFixed(endDate));
			long endMi = cal.getTimeInMillis();
			if (compareTs >= startMi && compareTs < endMi) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * addMonths 将月份进行增加，如果是减去，将value赋值为负数即可
	 * 
	 * @param value
	 *            int
	 * @return Date
	 */
	public static Date addMonths(int value) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, value);

		return cal.getTime();
	}

	/**
	 * addDays 将天数进行增加，如果是减去，将value赋值为负数即可
	 * 
	 * @param date
	 *            Dat 需要增加的日期
	 * @param i
	 *            int 天数
	 * @return Date
	 */
	public static Date addDays(Date date, int i) {
		return dateAdd(date, i);
	}

	/**
	 * 
	 * 获得上个月时间返回（yyyy-MM-dd）
	 * 
	 * @param date
	 */
	public static String getPreMonth(Date date) {

		GregorianCalendar calender = new GregorianCalendar();

		calender.setTime(date);

		calender.add(Calendar.MONTH, -1);

		return DateFormatUtils.format(calender.getTimeInMillis(), "yyyy-MM-dd");
	}

	/**
	 * 得到指定日期当天开始的最早的时间。
	 * 
	 * @author jiafangyao
	 * @param date
	 *            传入日期
	 * @return
	 * @since 0.1_2012-8-2
	 */
	public static Date getDateFirstTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 得到指定日期当天开始的最晚的时间。
	 * 
	 * @author jiafangyao
	 * @param date
	 *            传入日期
	 * @return
	 * @since 0.1_2012-8-2
	 */
	public static Date getDateLastTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 获取指定日期的上周第一天。
	 * 
	 * @author jiafangyao
	 * @param date
	 *            　传入日期
	 * @return
	 * @since 0.1_2012-7-3
	 */
	public static Date getPreMonday(Date date) {

		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.add(Calendar.DATE, -7);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 获取指定日期的上周最后一天。
	 * 
	 * @author jiafangyao
	 * @param date
	 *            　传入日期
	 * @return
	 * @since 0.1_2012-7-3
	 */
	public static Date getPreSunday(Date date) {

		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.add(Calendar.DATE, -7);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Monday
		return c.getTime();
	}

	/**
	 * 取得指定日期所在周的第一天 。
	 * 
	 * @author jiafangyao
	 * @param date
	 *            传入日期
	 * @return
	 * @since 0.1_2012-6-20
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 取得指定日期所在周的最后一天 。
	 * 
	 * @author jiafangyao
	 * @param date
	 *            传入日期
	 * @return
	 * @since 0.1_2012-6-20
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/**
	 * 获取指定日期的下周第一天。
	 * 
	 * @author jiafangyao
	 * @param date
	 *            　传入日期
	 * @return
	 * @since 0.1_2012-7-3
	 */
	public static Date getNextMonday(Date date) {

		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.add(Calendar.DATE, 7);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 获取指定日期的下周最后一天。
	 * 
	 * @author jiafangyao
	 * @param date
	 *            　传入日期
	 * @return
	 * @since 0.1_2012-7-3
	 */
	public static Date getNextSunday(Date date) {

		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.add(Calendar.DATE, 7);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Monday
		return c.getTime();
	}

	/**
	 * 将当天从0时0分0秒开始的秒数转换为HH:mm:ss。
	 * 
	 * @author jiafangyao
	 * @param seconds
	 *            秒数
	 * @return
	 * @since 0.1_2013-4-19
	 */
	public static String getTimeStrBySeconds(int seconds) {
		Calendar curCalendar = Calendar.getInstance();
		curCalendar.setTime(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, curCalendar.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, curCalendar.get(Calendar.MONTH));
		calendar.set(Calendar.DATE, curCalendar.get(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, seconds / 3600);
		calendar.set(Calendar.MINUTE, seconds % 3600 / 60);
		calendar.set(Calendar.SECOND, seconds % 3600 % 60 % 60);
		return format.format(calendar.getTime());
	}

	/**
	 * 得到当天从0时0分0秒开始的秒数。
	 * 
	 * @author jiafangyao
	 * @param TimeStr
	 *            如：12:10:10
	 * @return
	 * @since 0.1_2013-4-19
	 */
	public static int getSecondsByTimeStr(String TimeStr) {
		ParsePosition pos = new ParsePosition(0);
		Date date = format.parse(TimeStr, pos);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int seconds = calendar.get(Calendar.HOUR_OF_DAY) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);
		return seconds;
	}

	public static void main(String args[]) {
		// Date now = new Date();
		// System.out.println(DateTimeUtil.getHours(now));
		// try {
		// for (int i = 0; i < 10000; i++) {
		// System.out.println(DateTimeUtil
		// .fromDateStringFixed("2010-10-14 18:34:30"));
		// System.out.println(DateTimeUtil
		// .fromDateStringFixed("2010-10-16 18:34:30"));
		// }
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		System.out.println("result=" + DateTimeUtil.compare("2012-09-24 10:00:00", "2012-09-24 16:00:00", 10000));
	}

	public static String fromStringString(String dateString) throws ParseException {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf = new SimpleDateFormat(FormatDate);
		Date date = df.parse(dateString);
		return sdf.format(date);
	}
}