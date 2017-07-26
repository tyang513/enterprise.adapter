define([], function() {
	var constants = {
		/**
		 * search bar 事件处理函数前缀
		 */
		ACTION_HANDLER_PREFIX : '_handle',
		/**
		 * 确认信息Title
		 */			
		CONFIRM_TITLE : '确认信息',
		
		/**
		 * 确认删除信息
		 */			
		CONFIRM_DELETE : '确认删除',
		
		/**
		 * 删除成功
		 */			
		DELETE_SUCCESS : '删除成功',
		
		
		
		/***
		 * B2C
		 */
		CATEGORY_B2C : 'B2C',
		/***
		 * 积分托管
		 */
		CATEGORY_JFTG : 'JFTG',
		/***
		 * 积分托管名
		 */
		CATEGORY_NAME_JFTG : '积分托管',
		/***
		 * 车主商城
		 */
		CATEGORY_CZSC : 'CZSC',
		/***
		 * 车主商城
		 */
		CATEGORY_ZYDZF : 'ZYDZF',
		/***
		 * 车主商城
		 */
		CATEGORY_CYDZF : 'CYDZF',
		
		/***
		 * WLT 万里通为准
		 */
		WHOINCHARGE_WLT : '1',
		/***
		 * Business 以商户为准
		 */
		WHOINCHARGE_BUSINESS : '2',
		
		
		
		/***
		 * 报表未生成
		 */
		SETTLE_BATCH_REPORT_STATUS_WAIT : "1",
		/***
		 * 报表生成中
		 */
		SETTLE_BATCH_REPORT_STATUS_DURING : "2",
		/***
		 * 报表生成完成
		 */
		SETTLE_BATCH_REPORT_STATUS_COMPLETE : "3",
		/***
		 * 报表生成异常
		 */
		SETTLE_BATCH_REPORT_STATUS_ERROR : "-1",
		
		/***
		 * 对账批次
		 */
		SHEET_RECON_BATCH : 'reconBatch',
		
		SHEET_SETTLE_BATCH : 'settleBatch',
		/***
		 * 结算主体
		 */
		SHEET_SUBJECTUM : 'subjectum',		
		/***
		 * 结算模板
		 */
		TEMPLATE : 'template',
		/***
		 * 结算单
		 */
		SHEET_SETTLE_SHEET : 'settleSheet',	
		
		/***
		 * 商户基本信息
		 */
		SHEET_SETTLE_PARTNER : 'settlePartner',	
		
		/***
		 * 金额调整
		 */
		ADJUST_AMOUNT_SHEET : 'adjustAmount',	
		
		/**
		 * 对账配置
		 */
		RECON_CONFIG : 'reconConfig',
		
		/**
		 * 商户对账文件配置项
		 */
		MERCHANT_RECONFILECONFIG : 'merchantReconFileConfig',
		/**
		 * 确认按钮标识
		 */
		BTN_OK : 'ok',
		
		/**
		 * 确认按钮名称
		 */
		BTN_OK_LABEL : '确定',
		
		/**
		 * 取消按钮标识
		 */
		BTN_CANCEL : 'cancel',
		
		/**
		 * 取消按钮名称
		 */
		BTN_CANCEL_LABEL : '取消',
		
		/**
		 * 创建
		 */
		ACT_CREATE : 'create',
		
		/**
		 * 修改
		 */
		ACT_EDIT : 'edit',
		
		/**
		 * 查看
		 */
		ACT_VIEW : 'view',
		
		/**
		 * 调整金额
		 */
		ACT_ADJUST : 'adjust',
		/**
		 * 以万里通为准 调整金额
		 */
		ACT_WLT_ADJUST : 'wlt_adjust',
		
		/**
		 * 报损
		 */
		ACT_LOST :'lost',	
		/**
		 * 删除
		 */
		ACT_DELETE : 'delete',
			
		/**
		 * 搜索
		 */
		ACT_SEARCH : 'search',
			
		/**
		 * 重置
		 */
		ACT_RESET : 'reset',		
		
		/**
		 * 
		 */
		SHEET_SUMMARY_BUTTONS : 'app/summary/buttons',
		
		/**
		 * 通用逻辑定义状态 : 0，未启用 1，启用 -1，禁用
		 */
		LOGIC_UNENABLE : "0",
		LOGIC_ENABLE : "1",
		LOGIC_DISABLE : "-1",
		
		
		/**
		 * 审批状态 task 中approve  0 :不同意, 1:同意，2 ：征求意见3 ：转发处理
		 */
		TASK_DISAPPORVE : 0,
		TASK_APPORVE : 1,
		TASK_CONSULT : 2,
		TASK_FORWARD : 3,
		
		
		SHEET_TYPE_SUBJECTUM : 'SC',
		
		SHEET_TYPE_RECON_BATCH : 'RB',
		
		SHEET_TYPE_SETTLE_BATCH : 'SB',
		
		SHEET_TYPE_DEFAULT_SUBJECTUM : 'SD',
		
		SHEET_TYPE_VIRTUAL_SUBJECTUM : 'SV',
		
		AUDIT_LOG_TARGET_TYPE_RECON_BATCH : '20',
		/**
		 * 结算主体审批模版code : MerchantConfTemplate
		 */
		SUBJECTUM_APPLY_TEMPLATE : "MerchantConfTemplate",
		/**
		 * 结算单审批模版code : SettleSheetTemplate
		 */
		SETTLE_SHEET_APPLY_TEMPLATE : "SettleSheetTemplate",
		/**
		 * 结算单审批模版code : SettleSheetTemplate
		 */
		SETTLE_SHEET_RECONIMBALANCE_TEMPLATE : "SettleSheetReconImbalanceTemplate",
		
		
		/**
		 *调整金额CODE
		 */
		SHEET_TYPE_ADJUST_SHEET : 'TZ',
		/**
		 * 逻辑状态 是否前缀
		 */
		LOGIC_BASIS_STATUS_NO : "2",
		
		LOGIC_BASIS_STATUS_YES : "1",
		
		MERCHANT_CONF_TEMPLATE: "MerchantConfTemplate",
		
		/**
		 * 代表平台管理员
		 */
		USER_PLATFORM_ADMIN : 'SETTLE_ADMIN',
		
		/**
		 * 平台超级用户
		 */
		USER_SUPER_ADMIN : 'SETTLE_SUPER',
		
		/**
		 * 商户维护岗
		 */
		SETTLE_MERCHANT_MAINTAIN_STAFF : 'SETTLE_MERCHANT_MAINTAIN_STAFF',
		
		/**
		 * IT配置岗
		 */
		SETTLE_IT_MAINTAIN_STAFF : 'SETTLE_IT_MAINTAIN_STAFF',
		
		/**
		 * 财务复核岗
		 */
		SETTLE_FINANCE_REVIEWER : 'SETTLE_FINANCE_REVIEWER',
		
		/**
		 * 模板状态  0，未启用 1，启用 -1，禁用
		 */
		SENTIMENT_UNENABLE  : "0",
		SENTIMENT_ENABLE : "1",
		SENTIMENT_DISABLE : "-1",
		TASK_INFO : 'taskInfo',
		
	};
	return constants;
});
