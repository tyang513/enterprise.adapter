define([ "text!./templates/processHistoryDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.processHistoryDialog", BaseWidget, {
		// default options
		options : {},

		templateString : template,

		_create : function() {
			this._super();
			this._initData();
		},
		_initData : function() {
			var processId = this.options.processId;
			var remark = this.options.remark;
			var data = {
					processinstanceid : processId
				};
			$.ajax(app.buildServiceData("queryByprocessId", {
				data : data,
				context : this,
				success : function(data) {
					this.dataGrid.datagrid('loadData', data);
					
					var order = this.options.finSalesOrder;
					var starttime = this.options.starttime||order.applyDate;
					var formTy = this.options.formTy;
					var summary= '<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'; //原来是单独字符串，没办法解析，非得包一个html标签才能解析
					var constants = app.constants;
					
					if(formTy==constants.SHEET_SUBJECTUM){//积分使用
//						summary += order.applierName+'在'+new Date(order.applyDate).dateFormat("yyyy-MM-dd hh:mm")+'提交申请';
//						summary += order.applierName+'在'+new Date(order.applyDate).dateFormat("yyyy-MM-dd hh:mm:ss")+'提交申请';
						summary += app.userInfo.user.name+'在'+new Date(starttime).dateFormat("yyyy-MM-dd hh:mm:ss")+'提交申请';

					}
					
					if(formTy==constants.SHEET_SETTLE_SHEET){//积分使用
//						summary += order.umName+'在'+new Date(order.applyDate).dateFormat("yyyy-MM-dd hh:mm")+'提交申请';
						summary += order.umName+'在'+new Date(starttime).dateFormat("yyyy-MM-dd hh:mm")+'提交申请';
					}
					
					if(remark != null && remark !=''){//20131115 备注信息修改为从流程表的备注字段取 
						summary += ',申请内容为"'+remark+'"';
					}
					summary += '</span>';
					$(summary).appendTo(this.info);
				}
			}));	
		},
		// -------------------interface------------------
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				if (callback && rowData) {
					callback(rowData);
				}
			} else if (eventId === "cancel") {
				if (callback) {
					callback();
				}
			}
		}
		
	});
});
