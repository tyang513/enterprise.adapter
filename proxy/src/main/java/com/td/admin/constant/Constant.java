package com.td.admin.constant;

/**
 * 
 * @author 杨涛
 * @date 2015年3月31日
 */
public class Constant {

	/**
	 * 系统参数,文件输出目录
	 */
	public static final String SYS_PARAM_OUTPUT_FOLDER = "OUTPUT_FOLDER";
	
	/**
	 * 文件成功标识
	 */
	public static final String FILE_SUCCESS_FLAG = ".fin";
	
	/**
	 * 文件错误标识
	 */
	public static final String FILE_ERROR_FLAG = ".err";
	
	/**
	 * 文件统计标识
	 */
	public static final String FILE_STAT_FLAG = ".stat";
	
	// 公共状态
	
	/**
	 * 未启用
	 */
	public static Integer UNENABLE = 0;
	
	/**
	 * 启用
	 */
	public static Integer ENABLE = 1;
	
	/**
	 * 禁用
	 */
	public static Integer FORBIDDEN = 2;
	
	// 处理类型
	
	/**
	 * 文件请求检查处理
	 */
	public static String CHECK_REQUEST_FILE_PROCESS = "1";
	
	/**
	 * 请求文件处理处理
	 */
	public static String REQUEST_FILE_PROCESS = "2";
	
	/**
	 * 响应文件处理处理
	 */
	public static String RESPONSE_FILE_PROCESS = "3";
	
	// job input 公共处理状态
	
	/**
	 * 未处理 
	 */
	public static String JOB_INPUT_UNTREATED = "0";
	
	/**
	 * 正在处理
	 */
	public static String JOB_INPUT_RUNNING = "1";
	
	/**
	 * 处理完成
	 */
	public static String JOB_INPUT_PROCESSED = "2";
	
	/**
	 * 异常
	 */
	public static String JOB_INPUT_EXCEPTION = "-1";
	
}
