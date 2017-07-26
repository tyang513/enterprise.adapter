package com.td.admin.constant;

public class AuditConstant {
	
	
	/**
     * 审计日志操作类型 
     */
    public static final String AUDIT_LOG_OPERATION_TYPE_STOP_PROCESS = "1";
    public static final String AUDIT_LOG_OPERATION_TYPE_RE_ASSIGNEE = "2";
    public static final String AUDIT_LOG_OPERATION_TYPE_TAKEOVER = "3";
    
    /***
     * 发票操作类型
     */
    //新增开票申请
    public static final String AUDIT_LOG_OPERATION_TYPE_ADD_INVOICE_APPLY = "4";
    //修改开票申请
    public static final String AUDIT_LOG_OPERATION_TYPE_UPDATE_INVOICE_APPLY = "5";
    //删除开票申请
    public static final String AUDIT_LOG_OPERATION_TYPE_DELETE_INVOICE_APPLY = "6";
    //新增红冲申请
    public static final String AUDIT_LOG_OPERATION_TYPE_ADD_RED_INVOICE_APPLY = "7";
    //新增作废申请
    public static final String AUDIT_LOG_OPERATION_TYPE_ADD_CANCLE_INVOICE_APPLY = "8";
    //修改作废申请
    public static final String AUDIT_LOG_OPERATION_TYPE_UPDATE_CANCLE_INVOICE_APPLY = "9";
    //删除作废申请
    public static final String AUDIT_LOG_OPERATION_TYPE_DELETE_CANCLE_INVOICE_APPLY = "10";
    //开票
    public static final String AUDIT_LOG_OPERATION_TYPE_OPEN_INVOICE = "11";
    //修改发票
    public static final String AUDIT_LOG_OPERATION_TYPE_UPDATE_INVOICE = "12";
    //删除发票
    public static final String AUDIT_LOG_OPERATION_TYPE_DELETE_INVOICE = "13";
    //红冲发票
    public static final String AUDIT_LOG_OPERATION_TYPE_RED_INVOICE = "14";
    //作废发票
    public static final String AUDIT_LOG_OPERATION_TYPE_CANCLE_INVOICE = "15";
    
    
    /**
     * 系统管理操作类型
     */
    //编辑参数
    public static final String AUDIT_LOG_OPERATION_TYPE_UPDATE_PARAM = "16";
    //17模板修改
    public static final String AUDIT_LOG_OPERATION_TYPE_UPDATE_MOD = "17";
    
    //18模板删除
    public static final String AUDIT_LOG_OPERATION_TYPE_DELETE_MOD = "18";
    //邮件配置修改
    public static final String AUDIT_LOG_OPERATION_TYPE_UPDATE_EMAIL_CONFIG = "19";
    //删除
    public static final String AUDIT_LOG_OPERATION_TYPE_DELETE = "20";
    
   //修改
    public static final String AUDIT_LOG_OPERATION_TYPE_UPDATE = "21";
    
  //添加
    public static final String AUDIT_LOG_OPERATION_TYPE_ADD = "22";
    //手动重试
    public static final String AUDIT_LOG_OPERATION_TYPE_MANUAL_RETRY="23";
    //24启用模板
    public static final String AUDIT_LOG_OPERATION_TYPE_ENABLE_TEMPLATE="24";
    //25停用模板
    public static final String AUDIT_LOG_OPERATION_TYPE_DISABLE_TEMPLATE="25";
    
    /**
     * 审计日志目标类型 1流程,2任务,3临时授权
     */
    public static final String AUDIT_LOG_TARGET_TYPE_PROCESS = "1";
    public static final String AUDIT_LOG_TARGET_TYPE_TASK = "2";
    public static final String AUDIT_LOG_TARGET_TYPE_TAKEOVER = "3";
    
    
    /**
     * 发票目标类型
     */
    
    //增值税专用发票
    public static final String AUDIT_LOG_TARGET_TYPE_VATINVOICE = "4";
    //增值税普通发票
    public static final String AUDIT_LOG_TARGET_TYPE_VATINVOICE_ORD = "5";
    //营业税发票
    public static final String AUDIT_LOG_TARGET_TYPE_BUSINESS = "6";
    
    /**
     * 系统管理目标类型
     */
    //7系统参数
    public static final String AUDIT_LOG_TARGET_TYPE_SYSTEM_PARAM = "7";
    //8email
    public static final String AUDIT_LOG_TARGET_TYPE_EMAIL = "8";
    //9数据字典
    public static final String AUDIT_LOG_TARGET_TYPE_DATA_ITEMS = "9";
    //10日历
    public static final String AUDIT_LOG_TARGET_TYPE_CALENDAR = "10";
    //11销售
    public static final String AUDIT_LOG_TARGET_TYPE_SALE = "11";
    //12OA审批
    public static final String AUDIT_LOG_TARGET_TYPE_OA_APPROVE = "12";
    //13合作伙伴
    public static final String AUDIT_LOG_TARGET_TYPE_PARTNER= "13";
    
    //14合同
    public static final String AUDIT_LOG_TARGET_TYPE_OAU_CONtRACT= "14";
}
