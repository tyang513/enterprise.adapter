package com.td.common.constant;

public class CommonConstant {
	/** 短信平台角色前缀 */
	public static final String DCDS_ROLE_PREFIX = "SENTIMENT_PLATFORM_";

	/**
	 * 系统参数 附件存储路径
	 */
	public final static String SYSTEM_PARAM_ATTACHMENT_PATH = "ATTACHMENT_PATH";

	/**
	 * 系统参数 定时任务最大重试次数
	 */
	public final static String SYSTEM_PARAM_JOB_MAX_RETRY = "JOB_MAX_RETRY";
	
	/**
	 * 系统参数 定时任务最大重试次数
	 */
	public final static String SYSTEM_PARAM_CALLBACK_URL = "CALL_BACK_URL";
	
	/**
	 * 系统参数 广告主ID
	 */
	public final static String SYSTEM_PARAM_ADVERTISER_ID = "ADVERTISER_ID";
	
	/**
	 * 
	 */
	public final static String CLOUD_USER_TALKINGDATA_CLOUD_USER = "talkingdataCloudUser";

	/**
	 * 发送模块主机登陆IP地址
	 */
	public static final String SEND_HOST_IP = "SEND_HOST_IP";
	
	/**
	 * 发送模块主机端口号
	 */
	public static final String SEND_HOST_PORT = "SEND_HOST_PORT";
	
	/**
	 * 服务器文件分隔符
	 */
	public static final String FILE_SEPARATOR = "/";
	
	/**
	 * 未完成
	 */
	public static final int HADOOP_SEND_JOB_STATUS_INCOMPLATE = 0;
	
	public static final int HADOOP_SEND_JOB_STATUS_RUNNING = 1;
	
	/**
	 * 完成
	 */
	public static final int HADOOP_SEND_JOB_STATUS_COMPLETE = 2;
	
	/**
	 * 异常
	 */
	public static final int HADOOP_SEND_JOB_STATUS_EXCEPTION = -1;
	
	/**
	 * 禁发时间
	 */
	public static final int HADOOP_SEND_JOB_STATUS_FORBIDDEN_TIME = -2;
	
	/**
	 * 未暂停
	 */
	public static final int PAUSE_STATUS_1 = 1;
	
	/**
	 * 已暂停
	 */
	public static final int PAUSE_STATUS_2 = 2;
	

	/**
	 * 获取邮件配置常量
	 */
	public static final String EMAIL_SERVER_CONFIG = "emailServerConfig";

	/**
	 * 系统参数cache元素的key
	 * 
	 */
	public static final String SYSTEM_PARAM_CACHE_ELEMENT_KEY = "systemparamkey";

	/**
	 * 系统参数elemment的key
	 */

	public static final String SYSTEM_PARAM_CACHE_NAME = "finSystemParam";

	/**
	 * 页面列表标识
	 */
	public static final String PAGE_LIST_DATA_ROWS = "rows";

	/**
	 * 页面记录总数标识
	 */
	public static final String PAGE_LIST_DATA_TOTAL = "total";
	/**
	 * 表格页角标识
	 */
	public static final String GRID_FOOTER = "footer";

	/**
	 * 缓存数据字段标识
	 */
	public static final String CACHE_DIC_MAP = "cache_dic_map";

	/**
	 * 缓存数据字段标识
	 */
	public static final String CACHE_SUB_DIC_MAP = "cache_sub_dic_map";
	/**
	 * 数据字典积分销售订单类型标识
	 */
	public static final String FIN_DIC_POINTS_SALES_TYPE = "PointsSalesType";

	/**
	 * 数据字典成本中心标识
	 */
	public static final String FIN_DIC_POINTS_COST_CENTER = "costcenter";

	/**
	 * 数据字典积分销售订单类型标识
	 */
	public static final String FIN_DIC_POINTS_SALES_ATTACHMENT_TYPE = "PointsSalesAttachmentType";
	/**
	 * 发票处理类型标识
	 */
	public static final String FIN_DIC_OPRATER_TYPE = "opraterType";
	/**
	 * 发票状态类型标识
	 */
	public static final String FIN_DIC_STATUS_TYPE = "invoiceStatus";


	// ===========邮件=================================
	/**
	 * 任务超时通知邮件模版code
	 */
	public static final String TIMEOUT_TASK_EMAIL_TEMP_CODE = "TimeoutTask";
	
	

	/**
	 * 邮件发送状态 1未发送 ,2已发送 ,3重试,-1错误,4已同步
	 */
	public static final Integer EMAIL_STATUS_NO = 1;
	public static final Integer EMAIL_STATUS_YES = 2;
	public static final Integer EMAIL_STATUS_RETRY = 3;
	public static final Integer EMAIL_STATUS_SYNC = 4;
	public static final Integer EMAIL_STATUS_ERROR = -1;
	public static final Integer EMAIL_STATUS_NO_SYNC = -2;

	/**
	 * 邮件接收状态：1，已接收，2，已处理，-1，无效邮件
	 */
	public static final Integer EMAIL_RECEIVE_STATUS_RECEIVEED = 1;
	public static final Integer EMAIL_RECEIVE_STATUS_PROCESSED = 2;
	public static final Integer EMAIL_RECEIVE_STATUS_ERROR = -1;
	public static final Integer EMAIL_RECEIVE_STATUS_NO_SYNC = -2;

	// ===========邮件=================================

	/**
	 * session中 用户的标识
	 */
	public static final String SESSION_USER_FLAG = "user";

	/**
	 * session中 用户数据权限
	 */
	public static final String SESSION_USER_DATAAUTHOR = "UserDataAuthor";

	

	/**
	 * 页面错误信息
	 */
	public static final String RESPONSE_SEND_PAGE_ERROR_MSG = "errorMsg";

	/**
	 * 页面成功信息
	 */
	public static final String RESPONSE_SEND_PAGE_SUCCESS_MSG = "successMsg";

	/**
	 * 警告信息标志
	 */
	public static final String WARN_INFO = "warnInfo";

	/**
	 * 页面成功信息
	 */
	public static final String RESPONSE_SEND_PAGE_MSG = "msg";

	/**
	 * 页面操作标识
	 */
	public static final String PAGE_ACTION = "act";

	/**
	 * 页面操作 ，add 新增
	 */
	public static final String PAGE_ACTION_ADD = "add";

	/**
	 * 页面操作 ，update 修改
	 */
	public static final String PAGE_ACTION_UPDATE = "update";

	/**
	 * 页面操作 ，show 展示
	 */
	public static final String PAGE_ACTION_SHOW = "show";
	
	/**
	 * 财务系统code
	 */
	public static final String SYSTEM_CODE_FINANCE = "Sentiment";

	/**
	 * 审计日志操作类型 1停止流程,2重新分配任务,3工作流临时授权
	 */
	public static final String AUDIT_LOG_OPERATION_TYPE_STOP_PROCESS = "1";
	public static final String AUDIT_LOG_OPERATION_TYPE_RE_ASSIGNEE = "2";
	public static final String AUDIT_LOG_OPERATION_TYPE_TAKEOVER = "3";
	/**
	 * 审计日志目标类型 1流程,2任务,3临时授权
	 */
	public static final String AUDIT_LOG_TARGET_TYPE_PROCESS = "1";
	public static final String AUDIT_LOG_TARGET_TYPE_TASK = "2";
	public static final String AUDIT_LOG_TARGET_TYPE_TAKEOVER = "3";
	/**
	 * 审计日志操作结果 1成功,2失败
	 */
	public static final String AUDIT_LOG_RESULT_YES = "1";
	public static final String AUDIT_LOG_RESULT_NO = "2";

	
	/* ========================创建券卡 常量=======end=================== */

	/**
	 * 缓存key 系统参数缓存
	 */
	public static final String SYSTEM_PARAM_CACHE_KEY = "systemParamCache";

	/**
	 * 批量插入数 默认1000条
	 */
	public static final Integer COMMIT_BATCH_NUM = 1000;

	public static final String COST_ITEM_CODE = "COST_ITEM_CODE";

	public static final String COST_SUB_ITEM_CODE = "COST_SUB_ITEM_CODE";

	/**
	 * 附件最大行数,包含标题行
	 */
	public static final Integer ATTACHMENT_LARGEST_LINE_NUMBER = 5000;

	// =============参数常量 paramkey===========================================


	/**
	 * 单位元兑换积分比率
	 */
	public static final Integer INTEGRAL_SCALE = 500;

	/**
	 * key：UMID 用户的登录名， key：ACTIVE_UMID 当前活动的用户身份， key：ROLES 用户所有的角色， key：TEMPID
	 * 用户的临时授权身份， key：SALES_CODE 权限CODE
	 */
	public static final String USER_UMID = "UMID";
	public static final String USER_ACTIVE_UMID = "ACTIVE_UMID";
	public static final String USER_ROLES = "ROLES";
	public static final String USER_TEMPID = "TEMPID";
	public static final String USER_SALES_CODE = "SALES_CODE";
	/**
	 * 任务最大重试次数
	 */
	public static final String JOB_MAX_RETRY = "JOB_MAX_RETRY";
	/**
	 * 财务系统邮件发送配置
	 */
	public static final String FINANCE_EMAIL_SERVER_CODE = "FinanceEmailServiceCode";
	
	/**
	 * 系统参数:邮件系统URL
	 */
	public static final String SYSTEM_PARAM_EMAIL_PROJECT_URL = "EMAIL_PROJECT_URL";
	
	/**
	 * 定时任务触发器 simpleTrigger
	 */
	public static final String JOB_TRIGGER_TYPE_SIMPLE = "S";
	
	/**
	 * 批量提交数据，根据paramValue的值分批提交。
	 */
	public static final String BATCH_COMMIT_NUMBER = "BATCH_COMMIT_NUMBER";
	
	/**
	 * 财务系统邮件接收配置
	 */
	public static final String FINANCE_EMAIL_RECEIVE_SERVER_CODE = "FinanceEmailReceiveServiceCode";
	
	// 媒体来源
	/**
	 * 新闻
	 */
	public static final Integer MEDIA_SOURCE_NEWS = 1;
	
	/**
	 * 微博
	 */
	public static final Integer MEDIA_SOURCE_WEIBO = 2;
	
	/**
	 * 微信
	 */
	public static final Integer MEDIA_SOURCE_WECHAT = 3;
	
	/**
	 * 论坛 
	 */
	public static final Integer MEDIA_SOURCE_BBS = 4;
	
	/**
	 * 问卷调查
	 */
	public static final Integer MEDIA_SOURCE_WJDC = 4;
	
	/**
	 * 解析规则
	 */
	public static final String RULE_BUSINESS_TYPE_CRAWL = "1";
	
	/**
	 * 预处理解析规则
	 */
	public static final String RULE_BUSINESS_TYPE_PROCESS = "2";

}
