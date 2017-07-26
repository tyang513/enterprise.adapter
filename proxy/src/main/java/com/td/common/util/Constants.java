package com.td.common.util;

/**
 * @author lianjie
 * 
 */
public class Constants {

	public static final String SCRIPT_INFO_CODE_POINT_RECONBATCH_STATIS_DOWNLOAD = "point_recon_batch_stati_download";

	/***
	 * ScriptCallJob Handler处理中
	 */
	public final static String SCRIPT_CALL_JOB_HANDLER_STATUS_DURING = "1";

	/***
	 * ScriptCallJob Handler未处理状态
	 */
	public final static String SCRIPT_CALL_JOB_HANDLER_STATUS_UNTREATED = "0";

	/***
	 * ScriptCallJob 未处理状态
	 */
	public final static String SCRIPT_CALL_JOB_STATUS_UNTREATED = "0";
	/***
	 * ScriptCallJob 处理中
	 */
	public final static String SCRIPT_CALL_JOB_STATUS_DURING = "1";
	/***
	 * ScriptCallJob 处理异常
	 */
	public final static String SCRIPT_CALL_JOB_STATUS_ERROR = "-1";
	/***
	 * ScriptCallJob 处理完成
	 */
	public final static String SCRIPT_CALL_JOB_STATUS_COMPLETE = "2";

	/***
	 * ScriptCallJob 运行超时
	 */
	public final static String SCRIPT_CALL_JOB_STATUS_OUT_OF_TIME = "-2";
	/** 规则定义业务类型 */

	public final static String RULE_BUSINESS_TYPE_RECON_FILE_PARSE = "2";
	public final static String RULE_BUSINESS_TYPE_DATA_PREPROCESS = "3";
	public final static String RULE_BUSINESS_TYPE_RECON = "4";
	public final static String RULE_BUSINESS_TYPE_SETTLE = "5";

	/** 交易类型 */
	public final static String JFTG = "JFTG";
	public final static String B2C = "B2C";

	/** （基础常量）禁用 */
	public final static String BASIS_STATUS_DISABLE = "-1";
	/** （基础常量）启用 */
	public final static String BASIS_STATUS_ENABLE = "1";
	/** （基础常量）未启用 */
	public final static String BASIS_STATUS_UNENABLE = "0";

	/**
	 * 业务逻辑处理状态
	 */
	public final static String LOGIC_SUCCESS = "success";

	public final static String LOGIC_FAILURE = "failure";

	/** 一览舆情监控系统管理员角色 */
	public final static String MONITOR_ADMIN = "MONITOR_ADMIN";

	/***
	 * 规则定义号
	 */
	public final static String SHEET_RULE_DEFINITION = "RD";

	/***
	 * 配置号
	 */
	public final static String SHEET_TYPE_SUBJECTUM = "SC";

	/***
	 * 默认结算配置号
	 */
	public final static String SHEET_TYPE_DEFAULT_SUBJECTUM = "SD";

	/** 默认结算主体下 结算模板ID 常量 */
	public final static String DEFAULT_SETTLE_TEMPLATE_ID = "oldSettleTemplateId";

	/***
	 * 模板号
	 */
	public final static String TEMPLATE_NUMBER = "ST";

	/** copy默认结算主体下 结算模板 生成结算模板的ID 常量 */
	public final static String NEW_SETTLE_TEMPLATE_ID = "newSettleTemplateId";

	// 脚本超时邮件模板Code
	public final static String EMAIL_CODE_SCRIPT_OUT_TIME_REMIND = "ScriptOutTimeRemind";
	// 定时任务异常
	public final static String EMAIL_CODE_SCRIPT_CALL_JOB_EXECUTE_ERROR = "ScriptCallJobExecuteError";
	/**
	 * 邮件发送配置
	 */
	public static final String SYSTEM_SEND_EMAIL = "SENTIMENT_SEND_EMAIL";
	/***
	 * 邮件状态 0 未发送
	 */
	public static final Integer EMAIL_STATUS_NO = 1;
	/**
	 * 邮件模版-模版到期提醒
	 */
	public final static String EMAIL_TEMPLATE_KEY_TIMEOUT_REMIND = "TimeoutRemind";
	/***
	 * ScriptCallJob Handler处理异常
	 */
	public final static String SCRIPT_CALL_JOB_HANDLER_STATUS_ERROR = "-1";
	/** ftpServerCode */
	public static final String TASK_FTP_SERVER_CODE = "SETTLE_FTP_SERVER";

	/**
	 * 报表文件类型 db
	 */
	public final static String REPORT_CONFIG_FILE_TYPE_DB = "5";

	/**
	 * 邮件类型，为Job任务邮件列表
	 */
	public final static String REPORT_CONFIG_EMAIL_TYPE_JOB_TASK = "2";

	/** 基础逻辑状态 否 */
	public final static String LOGIC_BASIS_STATUS_NO = "2";

	/***
	 * 通用报表 REPORT_COMMON_CONFIG
	 */
	public static final String SCRIPT_INFO_CODE_REPORT_COMMON_CONFIG = "reportCommonConfig";

	/**
	 * 调用脚本重试次数
	 */
	public static final Integer IMPORT_FILE_JOB_INPUT_RETRY = 0;

	/**
	 * 调用脚本最大重试次数
	 */
	public static final Integer IMPORT_FILE_JOB_INPUT_MAX_RETRY = 3;

}
