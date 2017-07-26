define([ "text!./templates/taskProcessInfo.html", "app/base/BaseFormModule","app/proxy/task/searchbar/taskProcessSearchBar"], function(template,
		BaseFormModule, searchBar) {
	return $.widget("app.taskProcessInfo", BaseFormModule, {
		// default options
		options : {},

		templateString : template,
		// ---------------- interface -----------------
		onFormAction : function(data) {
			this.logger.debug();
			console.dir([ 'data', data ]);
			var eventId = data.eventId;
			if (eventId === "editSettleSheet") {
			}
		},
		render : function(formData) {
			this._super(formData);
			console.dir([ 'formData', formData ]);
			this._initData();
		},
		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("taskInfo/taskProcessList.do", {
				queryParams : {
					taskCode : this.formData.taskInfo.taskCode
				},
				onLoadSuccess : _.bind(function(rowData) {
				}, this)
			}));
			app.utils.bindDatagrid(this, this.dataGrid);
		},
		refresh : function() {
			this.logger.debug();
			this.dataGrid.datagrid('reload');
		},
		_doAction : function(event, data) {
			this.logger.debug();
			console.dir([ event, data ]);
			var handlerName = app.constants.ACTION_HANDLER_PREFIX + _.string.capitalize(data.action);
			if (this[handlerName] && _.isFunction(this[handlerName])) {
				this[handlerName](data);
			}
		},
		_handleException : function() {
			this.logger.debug();
			var gdata = this.dataGrid.datagrid('getSelected');
			console.dir(['gdata', gdata]);
			if (gdata) {
				this._openExceptionDialog({
					item : gdata
				});
			} else {
				app.messager.warn('信息');
				return;
			}
		},
		_openExceptionDialog : function(rowdata) {
			var buttons = [{
				text : '取消',
				eventId : "cancel",
				handler : function() {
					dlg.dialogExt('destroy');
				}
			}];
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '查看异常',
				width : 600,
				height : 420,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/proxy/task/dialog/exceptionDialog',
				modal : true,
				data : {
					item: rowdata,
					"action": app.constants.ACT_VIEW
				},
				buttons : buttons
			});
		}
	});
});