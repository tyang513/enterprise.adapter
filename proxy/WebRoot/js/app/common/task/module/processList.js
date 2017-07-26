define([ "text!./templates/processList.html", "app/base/BaseFormModule" ], function(template, BaseFormModule) {
	return $.widget("app.processList", BaseFormModule, {

		options : {},

		templateString : template,

		_create : function() {
			this._super();
			
			this.sheetId = null;
		},
		_initData : function() {
			this.sheetId = data.sheetId;
			
		},
		// ---------------- interface -----------------
		validate : function() {

			return true;
		},

		value : function() {
			var obj = {};
			return obj;
		},

		render : function(formData) {
			this._super(formData);
			this._initData();
		},

		_destroy : function() {
			this._super();
		},

		_viewItem : function(event) {
			var constants = app.constants;
			this.logger.debug();
			var options = app.utils.parseOptions($(event));
			console.dir(["event", event, options]);
			var formTy=this.formType;//单子的类型
			var orderInfo = '';
			orderInfo = this.commonOrder;
			if(formTy==constants.SHEET_SUBJECTUM){//结算主体
				orderInfo = this.subjectum;
			}else if(formTy==constants.SHEET_SETTLE_SHEET){//结算单
				orderInfo = this.settleSheet;
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '查看流程记录',
				width : 750,
				height : 300,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/processHistoryDialog',
				modal : true,
				data : {processId : options.id,remark : options.remark,formTy:formTy, finSalesOrder : orderInfo}
				
			});
		},

		_formatAct : function(value, row, index) {
			var remark = row.remark == null ?'':row.remark;
			remark = remark.replace(/\n/g, ' ');
			var html = "<a href='javascript:void(0);' class='easyui-linkbutton' data-options=\"plain:true,type:'" + row.type + "',remark:'"+remark+"',id:" + row.id + ",index:"
					+ index + "\" onClick=_viewItem();>查看</a>";
			return app.utils.bind(html, this);
		}
	});
});
