define([ "text!./templates/auditLog.html", "app/base/BaseFormModule" ], function(template, BaseFormModule) {
	return $.widget("app.auditLog", BaseFormModule, {

		options : {},

		templateString : template,

		render : function(formData) {
			this._super(formData);
			this._initData();
		},

		_initData : function() {
			var targetid = null;
			var targettype = null;
			if (this.formData.formType === app.constants.SHEET_RECON_BATCH) {
				targetid = this.formData.reconBatch.batchNo;
				targettype = app.constants.AUDIT_LOG_TARGET_TYPE_RECON_BATCH;
			}
			if( !targetid && !targettype){
				this.logger.error("sheetId与sheetType不能为空");
				return;
			}
			this.auditLog.datagrid(app.buildServiceData("queryAuditLog", {
				queryParams : {
					targettype : targettype,
					targetid : targetid
				}, // 查询条件
				loadMsg : '数据加载中请稍后……'
	       }));		
		}
	});
});
