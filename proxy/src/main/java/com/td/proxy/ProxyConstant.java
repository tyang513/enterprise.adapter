package com.td.proxy;

/**
 * proxy常量类
 * 
 * @author yangtao
 */
/**
 * @author yangtao
 *
 */
public interface ProxyConstant {

	/**
	 * 任务上下文键：任务输入
	 */
	public static String TASK_CONTEXT_KEY_JOB_INPUT = "JobInput";

	/**
	 * 任务上下文键：模板
	 */
	public static String TASK_CONTEXT_KEY_TEMPLATE = "Template";

	/**
	 * 任务上下文键：规则定义
	 */
	public static String TASK_CONTEXT_KEY_RULE_DEFINITION = "RuleDefinition";

	/**
	 * 任务上下文键：任务信息
	 */
	public static String TASK_CONTEXT_KEY_TASK_INFO = "TaskInfo";

	/**
	 * 任务编号前缀
	 */
	public static String PREFIX_TASK_CODE = "TI";

	// 规则常量定义
	/**
	 * 规则配置：下载文件存储根目录
	 */
	public static String RULE_CONFIG_PARTNER_ROOT_PATH = "ROOT_PATH";

	/**
	 * 上传文件根目录
	 */
	public static String RULE_CONFIG_UPLOAD_ROOT_PATH = "UPLOAD_ROOT_PATH";
	
	/**
	 * 规则配置：上传服务代码
	 */
	public static String RULE_CONFIG_UPLOAD_SERVICE_CODE = "UPLOAD_SERVICE_CODE";
	
	/**
	 * 规则配置：下载服务代码
	 */
	public static String RULE_CONFIG_DOWNLOAD_SERVER_CODE = "DOWNLOAD_SERVER_CODE";
	
	/**
	 * 规则配置：营销 计算新创建的受众
	 */
	public static String RULE_CONFIG_CREATE_SEGMENT_ID_SERVICE_CODE = "CREATE_SEGMENT_ID_SERVICE_CODE";
	
	/**
	 * 规则配置：营销 创建受众接口
	 */
	public static String RULE_CONFIG_CREATE_SEGMENT_SERVICE_CODE = "CREATE_SEGMENT_SERVICE_CODE";
	
	/**
	 * 规则配置：营销 分发受众接口
	 */
	public static String RULE_CONFIG_SWITCH_SEGMENT_SERVICE_CODE = "SWITCH_SEGMENT_SERVICE_CODE";
	
	/**
	 * 成功文件标识
	 */
	public static String SUCCESS_FILE_ID = "SUCCESS_FILE_ID";

	/**
	 * 日志状态 正常
	 */
	public static Integer TASK_LOG_NORMAL = 1;

	/**
	 * 日志状态 异常
	 */
	public static Integer TASK_LOG_EXCEPTIN = 2;

	// TaskInfo状态
	
	/**
	 * 未处理
	 */
	public static Integer TASK_INFO_UNTREATED = 0;

	/**
	 * 正在处理
	 */
	public static Integer TASK_INFO_RUNNING = 1;

	/**
	 * 处理完成
	 */
	public static Integer TASK_INFO_PROCESSED = 2;

	/**
	 * 异常
	 */
	public static Integer TASK_INFO_EXCEPTION = -1;
	
	// TaskLog 日志类型
	/**
	 * 扫描到新上传文件
	 */
	public static Integer TASK_LOG_TYPE_FILE_SCAN = 0;
	
	/**
	 * 从ftp下载文件
	 */
	public static Integer TASK_LOG_TYPE_DOWNLOAD_FILE = 1;
	
	/**
	 * 解密文件
	 */
	public static Integer TASK_LOG_TYPE_DECRYPT_FILE = 2;
	
	/**
	 * 压缩文件 
	 */
	public static Integer TASK_LOG_TYPE_GZIP_FILE = 3;
	
	/**
	 * 上传文件到云端
	 */
	public static Integer TASK_LOG_TYPE_UPDATE_FILE_TO_CLOUD = 4;
	
	/**
	 * 收到回调请求
	 */
	public static Integer TASK_LOG_TYPE_CALLBACK = 5;
	
	/**
	 * 从云端下载文件
	 */
	public static Integer TASK_LOG_TYPE_DOWNLOAD_FILE_FROM_CLOUD = 6;
	
	/**
	 * 解压文件
	 */
	public static Integer TASK_LOG_TYPE_UNGZIP_FILE = 7;
	
	/**
	 * 加密文件
	 */
	public static Integer TASK_LOG_TYPE_ENCRYPT_FILE = 8;
	
	/**
	 * 上传文件到ftp
	 */
	public static Integer TASK_LOG_TYPE_UPLOAD_FILE_TO_FTP = 9;
	
	/**
	 * 连接类型: http
	 */
	public static String TASK_SERVICE_PROTOCOL_TYPE_HTTP = "http";
	
	/**
	 * 连接类型：https
	 */
	public static String TASK_SERVICE_PROTOCOL_TYPE_HTTPS = "https";
	
	/**
	 * id匹配
	 */
	public static Integer API_TALKINGDATA_IDUPLPAD_TYPE = 1;
	
	/**
	 * 5 tdid
	 */
	public static Integer API_TALKINGDATA_IDUPLPAD_INPUT_ID_TYPE = 5;
	
	/**
	 * 5 tdid
	 */
	public static String API_TALKINGDATA_IDUPLPAD_OUTPUT_ID_TYPE = "5";
	
	// TaskResponseFileProcessJobInput Trigger_Type
	
	/**
	 * job类型,corn型任务
	 */
	public static String JOB_TRIGGER_TYPE_CRON = "C";
	
	/**
	 * job类型,普通类型任务
	 */
	public static String JOB_TRIGGER_TYPE_SIMPLE = "S";
	
	/**
	 * dsp
	 */
	public static String TASK_INFO_TYPE_DSP = "DSP";
	
	/**
	 * dmp
	 */
	public static String TASK_INFO_TYPE_DMP = "DMP";

}
